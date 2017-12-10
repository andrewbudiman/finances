package datastore.impl.jsonstore

import datastore.impl.jsonstore.JsonMetadata._
import model.metadata.Matcher.{ContainsMatcher, EqualityMatcher}
import model.metadata.{Category, Matcher, Metadata, Priority}

import scala.collection.immutable.{Seq, Set}

case class JsonMetadata(
  priorities: Seq[JsonPriority],
  categories: Seq[JsonCategory],
  matchers: Seq[JsonMatcher]
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

  def toMetadata: Metadata = {
    val resultPriorities = priorities.map(p => Priority(p.id, p.createdAt, p.name))
    val resultPrioritiesById = resultPriorities.byId(_.id)

    val resultCategories = categories.map(c => Category(c.id, c.createdAt, c.name, resultPrioritiesById(c.priorityId)))
    val resultCategoriesById = resultCategories.byId(_.id)

    val resultMatchers: Seq[Matcher] = matchers.map {
      case m: JsonContainsMatcher => ContainsMatcher(m.id, m.createdAt, m.value, resultCategoriesById(m.categoryId))
      case m: JsonEqualityMatcher => EqualityMatcher(m.id, m.createdAt, m.value, resultCategoriesById(m.categoryId))
    }

    Metadata(
      resultPriorities.to[Set],
      resultCategories.to[Set],
      resultMatchers.to[Set])
  }
}

object JsonMetadata {

  def fromMetadata(metadata: Metadata): JsonMetadata = {
    val jsonPriorities = metadata.priorities
      .map(p => JsonPriority(p.id, p.createdAt, p.name))
      .to[Seq]
      .sortBy(_.id)

    val jsonCategories = metadata.categories
      .map(c => JsonCategory(c.id, c.createdAt, c.name, c.priority.id))
      .to[Seq]
      .sortBy(_.id)

    val jsonMatchers = metadata.matchers.to[Seq].map {
      case m: ContainsMatcher => JsonContainsMatcher(m.id, m.createdAt, m.value, m.category.id)
      case m: EqualityMatcher => JsonEqualityMatcher(m.id, m.createdAt, m.value, m.category.id)
    }

    JsonMetadata(
      jsonPriorities,
      jsonCategories,
      jsonMatchers)
  }

  case class JsonPriority(id: Long, createdAt: Long, name: String)

  case class JsonCategory(id: Long, createdAt: Long, name: String, priorityId: Long)

  sealed trait JsonMatcher

  case class JsonContainsMatcher(id: Long, createdAt: Long, value: String, categoryId: Long) extends JsonMatcher

  case class JsonEqualityMatcher(id: Long, createdAt: Long, value: String, categoryId: Long) extends JsonMatcher
}
