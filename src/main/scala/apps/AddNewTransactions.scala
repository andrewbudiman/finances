package apps

import categorize.Categorizor
import categorize.impl.{CategorizorImpl, ContainsCategoryMatcher}
import data.csv.CsvDataWriterImpl
import model.impl._
import parser.impl.CapitalOneDataParserImpl

import scala.collection.immutable.Set
import scala.concurrent.ExecutionContext

object AddNewTransactions {

  def getNewCategorizor(categorizor: Categorizor): Categorizor = {
    Thread.sleep(5000)
    categorizor
  }

  def transformTransactions(transactions: Set[RawTransaction],
                            categorizor: Categorizor)
                           (implicit ec: ExecutionContext): Set[Transaction] = {
    val transactionToCategory = transactions.map(t => (t, categorizor.categorize(t.description)))
    val (uncategorizedTransactions, categorizedTransactions) = transactionToCategory.partition(_._2.isEmpty)

    if (uncategorizedTransactions.nonEmpty) {
      println(s"Uncategorized transactions:")
      uncategorizedTransactions.foreach { t =>
        println(s"\t$t")
      }
      val newCategorizor = getNewCategorizor(categorizor)
      transformTransactions(transactions, newCategorizor)
    } else {
      categorizedTransactions.map {
        case (t, Some(category)) => Transaction(t.account, t.epoch, t.description, category, t.amount)
        case _ => throw new RuntimeException(s"xcxc - shouldn't happen")
      }
    }
  }

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global

    val parser = new CapitalOneDataParserImpl("/tmp/capital_one.csv")
    val rawTransactions = parser.parseTransactions

    val categorizor = new CategorizorImpl(Set(ContainsCategoryMatcher("ANOTHER CAFE", Category("Another Cafe", Priority()))))

    val transactions = transformTransactions(rawTransactions, categorizor)
    transactions.foreach(t => println(s"\t$t"))

    val writer = new CsvDataWriterImpl("/tmp/dummy.csv")
  }
}
