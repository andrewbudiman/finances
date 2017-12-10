package datastore.jsonstore

import datastore.jsonstore.JsonUserMetadata.{JsonCategory, JsonMatchers, JsonPriority}
import model.metadata.Matcher.{ContainsMatcher, EqualityMatcher}
import model.metadata.{Category, Matcher, Priority, UserMetadata}

import scala.collection.immutable.{Seq, Set}

case class JsonUserMetadata(
  priorities: Seq[JsonPriority],
  categories: Seq[JsonCategory],
  matchers: JsonMatchers
) {

  implicit class SeqById[T](values: Seq[T]) {

    def byId[K](id: T => K): Map[K, T] = {
      val valuesById = values.map(v => id(v) -> v).toMap
      if (values.size != valuesById.size) {
        throw new RuntimeException(s"xcxc - duplicate value ids: $values")
      }
      valuesById
    }
  }

  def toUserMetadata: UserMetadata = {
    val resultPriorities = priorities.map(p => Priority(p.id, p.createdAt, p.name))
    val resultPrioritiesById = resultPriorities.byId(_.id)

    val resultCategories = categories.map(c => Category(c.id, c.createdAt, c.name, resultPrioritiesById(c.priorityId)))
    val resultCategoriesById = resultCategories.byId(_.id)

    val containsMatchers: Seq[Matcher] = matchers.contains.map { c =>
      ContainsMatcher(c.id, c.createdAt, c.value, resultCategoriesById(c.categoryId))
    }

    val equalityMatchers: Seq[Matcher] = matchers.equality.map { c =>
      EqualityMatcher(c.id, c.createdAt, c.value, resultCategoriesById(c.categoryId))
    }

    UserMetadata(
      resultPriorities.to[Set],
      resultCategories.to[Set],
      (containsMatchers ++ equalityMatchers).to[Set])
  }
}

object JsonUserMetadata {

  def fromUserMetadata(userMetadata: UserMetadata): JsonUserMetadata = {
    val jsonPriorities = userMetadata.priorities
      .map(p => JsonPriority(p.id, p.createdAt, p.name))
      .to[Seq]
      .sortBy(_.id)

    val jsonCategories = userMetadata.categories
      .map(c => JsonCategory(c.id, c.createdAt, c.name, c.priority.id))
      .to[Seq]
      .sortBy(_.id)

    val matcherConfigs = userMetadata.matchers.map {
      case m: ContainsMatcher => Left(JsonContainsMatcher(m.id, m.createdAt, m.value, m.category.id))
      case m: EqualityMatcher => Right(JsonEqualityMatcher(m.id, m.createdAt, m.value, m.category.id))
    }

    val containsMatchers = for (Left(c) <- matcherConfigs) yield c
    val equalityMatchers = for (Right(c) <- matcherConfigs) yield c

    JsonUserMetadata(
      jsonPriorities,
      jsonCategories,
      JsonMatchers(
        containsMatchers.to[Seq],
        equalityMatchers.to[Seq]))
  }

  case class JsonPriority(id: Long, createdAt: Long, name: String)

  case class JsonCategory(id: Long, createdAt: Long, name: String, priorityId: Long)

  case class JsonContainsMatcher(id: Long, createdAt: Long, value: String, categoryId: Long)

  case class JsonEqualityMatcher(id: Long, createdAt: Long, value: String, categoryId: Long)

  case class JsonMatchers(
    contains: Seq[JsonContainsMatcher],
    equality: Seq[JsonEqualityMatcher]
  )
}
