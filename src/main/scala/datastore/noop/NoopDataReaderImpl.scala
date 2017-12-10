package datastore.noop

import datastore.MetadataReader
import model.transactions.RawTransaction
import model.metadata.{Account, Category}

import scala.collection.immutable.Set
import scala.concurrent.Future

/*object NoopDataReaderImpl extends MetadataReader {

  override val getAccounts: Future[Set[Account]] = Future.successful(Set.empty)

  override val getCategories: Future[Set[Category]] = Future.successful(Set.empty)

  override val getTransactions: Future[Set[RawTransaction]] = Future.successful(Set.empty)
}*/
