package datastore.noop

import datastore.DataWriter
import model.transactions.RawTransaction

import scala.concurrent.Future

object NoopDataWriterImpl extends DataWriter {

  def addTransaction(tx: RawTransaction): Future[Unit] = {
    println(s"adding Transaction: $tx")
    Future.successful(Unit)
  }
}
