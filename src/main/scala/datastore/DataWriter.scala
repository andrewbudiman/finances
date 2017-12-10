package datastore

import model.transactions.RawTransaction

import scala.concurrent.Future

trait DataWriter {

  def addTransaction(tx: RawTransaction): Future[Unit]
}
