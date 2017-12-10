package parser.impl

import java.io.FileReader
import java.text.SimpleDateFormat

import com.opencsv.CSVReader
import model.metadata.Account
import model.transactions.RawTransaction
import parser.DataParser

import scala.collection.JavaConverters._
import scala.collection.immutable.{Seq, Set}

class CapitalOneDataParserImpl(filePath: String) extends DataParser {

  private val dateFormatter = new SimpleDateFormat("yyyy-MM-dd")

  private def ensureHeader(csvEntry: Seq[String]): Unit = {
    val expectedHeader =
      Seq("Transaction Date", "Posted Date", "Card No.", "Description", "Category", "Debit", "Credit")

    csvEntry match {
      case `expectedHeader` =>  // good
      case _ => throw new RuntimeException(s"Unexpected header, expected: $expectedHeader, got: $csvEntry")
    }
  }

  private def parseTransaction(csvEntry: Seq[String]): Option[RawTransaction] = {
    csvEntry match {
      case Seq(_, _, _, _, _, "", _) => None
      case Seq(transactionDate, _, _, description, _, debit, _) =>
        val date = dateFormatter.parse(transactionDate)
        Some(RawTransaction(Account.CapitalOne, date.getTime, description, debit.toDouble))
      case t => throw new RuntimeException(s"xcxc - unexpected transaction format: $t")
    }
  }

  def parseTransactions: Set[RawTransaction] = {
    val reader = new CSVReader(new FileReader(filePath))
    val iterator = reader.iterator()
    ensureHeader(iterator.next().to[Seq])
    iterator.asScala.flatMap(e => parseTransaction(e.to[Seq])).to[Set]
  }
}
