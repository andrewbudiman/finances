package parser.impl

import model.impl.RawTransaction
import parser.DataParser

import scala.collection.immutable.Set
import scala.concurrent.Future

class DummyDataParserImpl(transactions: Set[RawTransaction]) extends DataParser {

  def parseTransactions: Set[RawTransaction] = transactions
}
