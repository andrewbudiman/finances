package categorize

import model.impl.Category

trait CategoryMatcher {

  protected def matches(description: String): Boolean

  def category: Category

  def matchCategory(description: String): Option[Category] = {
    if (matches(description)) {
      Some(category)
    } else {
      None
    }
  }
}
