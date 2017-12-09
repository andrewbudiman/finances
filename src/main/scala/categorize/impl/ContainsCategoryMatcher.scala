package categorize.impl

import categorize.CategoryMatcher
import model.impl.Category

case class ContainsCategoryMatcher(subString: String, category: Category) extends CategoryMatcher {

  protected def matches(description: String): Boolean = {
    description.contains(subString)
  }
}
