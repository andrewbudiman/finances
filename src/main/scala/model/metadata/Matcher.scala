package model.metadata

import model.ModelObject

sealed trait Matcher extends ModelObject {

  protected def matches(description: String): Boolean

  def category: Category

  def createdAt: Long

  def matchCategory(description: String): Option[Category] = {
    if (matches(description)) {
      Some(category)
    } else {
      None
    }
  }
}

object Matcher {

  case class ContainsMatcher(
    id: Long,
    createdAt: Long,
    value: String,
    category: Category
  ) extends Matcher {

    protected def matches(description: String): Boolean = {
      description.contains(value)
    }
  }

  case class EqualityMatcher(
    id: Long,
    createdAt: Long,
    value: String,
    category: Category
  ) extends Matcher {

    protected def matches(description: String): Boolean = {
      description == value
    }
  }
}
