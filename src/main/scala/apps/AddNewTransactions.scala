package apps

import categorize.Categorizor
import categorize.impl.CategorizorImpl
import datastore.csv.CsvDataWriterImpl
import datastore.jsonstore.JsonMetadataStore
import model.transactions.{RawTransaction, Transaction}
import org.rogach.scallop.{ScallopConf, ScallopOption}
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
      println(s"Categorized transactions:")
      categorizedTransactions.foreach { t =>
        println(s"\t$t")
      }
      //val newCategorizor = getNewCategorizor(categorizor)
      //transformTransactions(transactions, newCategorizor)
      Set.empty
    } else {
      categorizedTransactions.map {
        case (t, Some(category)) => Transaction(t.account, t.epoch, t.description, category, t.amount)
        case _ => throw new RuntimeException(s"xcxc - shouldn't happen")
      }
    }
  }

  class ArgParser(arguments: Seq[String]) extends ScallopConf(arguments) {
    val configFile: ScallopOption[String] = opt[String](required = true)
    val resultConfigFile: ScallopOption[String] = opt[String](required = true)

    verify()

    if (configFile() == resultConfigFile()) {
      throw new RuntimeException(s"xcxc - Must create a new config, not override the old one")
    }
  }

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global

    // Load configuration
    val argParser = new ArgParser(args)
    val metadataReader = JsonMetadataStore.reader(argParser.configFile())
    val userMetadata = metadataReader.loadMetadata()

    // Parse transactions
    val parser = new CapitalOneDataParserImpl("/tmp/capital_one.csv")
    val rawTransactions = parser.parseTransactions

    // Categorize transactions
    val categorizor = new CategorizorImpl(userMetadata.matchers)
    val transactions = transformTransactions(rawTransactions, categorizor)
    transactions.foreach(t => println(s"\t$t"))

    // Output new config
    val newUserMetadata = userMetadata
    val metadataWriter = JsonMetadataStore.writer(argParser.resultConfigFile())
    metadataWriter.writeMetadata(newUserMetadata)

    //val writer = new CsvDataWriterImpl("/tmp/dummy.csv")
  }
}
