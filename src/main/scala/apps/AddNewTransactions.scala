package apps

import categorize.Categorizor
import categorize.impl.CategorizorImpl
import config.Config
import datastore.impl._
import model.transactions.{RawTransaction, Transaction}
import org.rogach.scallop.{ScallopConf, ScallopOption}
import parser.impl.CapitalOneDataParserImpl

import scala.collection.immutable.Set
import scala.concurrent.ExecutionContext

object AddNewTransactions {

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
      // TODO: prompt for new matchers
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

    verify()
  }

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global

    // Load configuration
    val argParser = new ArgParser(args)
    val config = Config.load(argParser.configFile())

    // Read metadata
    val metadataReader = getMetadataReader(config.datastoreConfig)
    val metadata = metadataReader.loadMetadata()

    // Parse transactions
    val parser = new CapitalOneDataParserImpl("/tmp/capital_one.csv")
    val rawTransactions = parser.parseTransactions

    // Categorize transactions
    val categorizor = new CategorizorImpl(metadata.matchers)
    val transactions = transformTransactions(rawTransactions, categorizor)
    transactions.foreach(t => println(s"\t$t"))

    // Write new metadata
    val newMetadata = metadata
    val metadataWriter = getMetadataWriter(config.datastoreConfig)
    metadataWriter.writeMetadata(newMetadata)
  }
}
