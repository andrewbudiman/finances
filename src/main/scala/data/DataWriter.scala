package data

import model.impl.RawTransaction

import scala.concurrent.Future

trait DataWriter {

  def addTransaction(tx: RawTransaction): Future[Unit]
}
