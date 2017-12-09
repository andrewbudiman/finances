package categorize.impl

import categorize.{Categorizor, CategoryMatcher}
import model.impl.Category

import scala.collection.immutable.Set

class CategorizorImpl(matchers: Set[CategoryMatcher]) extends Categorizor {

  // xcxc - should also take epoch for one-time categorizations
  def categorize(description: String): Option[Category] = {
    matchers.flatMap(_.matchCategory(description.toLowerCase)) match {
      case matchedCategories if matchedCategories.size == 1 => Some(matchedCategories.head)
      case matchedCategories if matchedCategories.isEmpty => None
      case matchedCategories =>
        throw new RuntimeException(s"xcxc - matched more than one category - description: $description, categories: $matchedCategories")
    }
  }
}
