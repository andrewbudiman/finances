package categorize

import model.metadata.Category

trait Categorizor {

  def categorize(description: String): Option[Category]
}
