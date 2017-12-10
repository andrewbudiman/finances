package categorize.impl

import categorize.Categorizor
import model.metadata.{Category, Matcher}

import scala.collection.immutable.Set

class CategorizorImpl(matchers: Set[Matcher]) extends Categorizor {

  // TODO: implement one-time categorizations
  def categorize(description: String): Option[Category] = {
    matchers.flatMap(_.matchCategory(description.toLowerCase)) match {
      case matchedCategories if matchedCategories.size == 1 => Some(matchedCategories.head)
      case matchedCategories if matchedCategories.isEmpty => None
      case matchedCategories =>
        throw new RuntimeException(s"xcxc - matched more than one category - description: $description, categories: $matchedCategories")
    }
  }
}
