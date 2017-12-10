package model.metadata

import scala.collection.immutable.Set

case class Metadata(
  priorities: Set[Priority],
  categories: Set[Category],
  matchers: Set[Matcher]
)
