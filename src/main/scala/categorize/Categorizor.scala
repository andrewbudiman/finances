package categorize

import model.impl.Category

trait Categorizor {

  def categorize(description: String): Option[Category]
}
