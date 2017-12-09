package data.csv

import java.io.{BufferedWriter, FileWriter}

import com.opencsv.CSVWriter
import data.DataWriter
import model.impl.RawTransaction

import scala.concurrent.Future

class CsvDataWriterImpl(file: String) extends DataWriter {

  val writer = new CSVWriter(new BufferedWriter(new FileWriter(file)))

  def addTransaction(tx: RawTransaction): Future[Unit] = {
    val value: Array[String] = Array(
      tx.account.toString,//xcxc - .name,
      tx.epoch.toString,
      tx.description,
      //tx.category.name,
      tx.amount.toString)

    writer.writeNext(value)
    writer.flush()
    Future.successful(Unit)
  }
}
