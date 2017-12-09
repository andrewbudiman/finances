package apps

import categorize.CategoryMatcher
import categorize.impl.{ContainsCategoryMatcher, EqualityCategoryMatcher}
import config.Config
import model.impl.{Category, Priority}

import scala.collection.immutable.{Map, Seq, Set}

case class UserMetadata(
  priorities: Set[Priority],
  categories: Set[Category],
  matchers: Set[CategoryMatcher])

object UserMetadata {

  implicit class SeqById[T](values: Seq[T]) {

    def byId(id: T => Int): Map[Int, T] = {
      val valuesById = values.map(v => id(v) -> v).toMap
      if (values.size != valuesById.size) {
        throw new RuntimeException(s"xcxc - duplicate value ids: $values")
      }
      valuesById
    }
  }

  def fromConfig(config: Config): UserMetadata = {
    val priorities = config.priorities.map(p => Priority(p.id, p.name))
    val prioritiesById = priorities.byId(_.id)

    val categories = config.categories.map(c => Category(c.id, c.name, prioritiesById(c.priorityId)))
    val categoriesById = categories.byId(_.id)

    val containsMatchers: Seq[CategoryMatcher] = config.matchers.contains.map { c =>
      ContainsCategoryMatcher(c.value, categoriesById(c.categoryId))
    }

    val equalityMatchers: Seq[CategoryMatcher] = config.matchers.equality.map { c =>
      EqualityCategoryMatcher(c.value, categoriesById(c.categoryId))
    }

    UserMetadata(
      priorities.to[Set],
      categories.to[Set],
      (containsMatchers ++ equalityMatchers).to[Set])
  }
}
