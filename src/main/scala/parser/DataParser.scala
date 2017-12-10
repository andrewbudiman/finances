package parser

import model.transactions.RawTransaction

import scala.collection.immutable.Set

trait DataParser {

  def parseTransactions: Set[RawTransaction]
}
