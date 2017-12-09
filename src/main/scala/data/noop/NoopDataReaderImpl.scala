package data.noop

import data.DataReader
import model.impl.{Account, Category, RawTransaction}

import scala.collection.immutable.Set
import scala.concurrent.Future

object NoopDataReaderImpl extends DataReader {

  override val getAccounts: Future[Set[Account]] = Future.successful(Set.empty)

  override val getCategories: Future[Set[Category]] = Future.successful(Set.empty)

  override val getTransactions: Future[Set[RawTransaction]] = Future.successful(Set.empty)
}
