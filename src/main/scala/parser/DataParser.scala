package parser

import model.impl.RawTransaction

import scala.collection.immutable.Set

trait DataParser {

  def parseTransactions: Set[RawTransaction]
}
