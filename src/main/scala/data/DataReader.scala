package data

import model.impl._

import scala.collection.immutable.Set
import scala.concurrent.Future

trait DataReader {

  def getAccounts: Future[Set[Account]]

  def getCategories: Future[Set[Category]]

  def getTransactions: Future[Set[RawTransaction]]
}
