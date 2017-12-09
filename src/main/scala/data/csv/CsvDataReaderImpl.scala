package data.csv

//import config.Config.FileStorageConfig
//import data.DataReader
//import model.impl.{Account, Category, RawTransaction}
//
//import scala.collection.immutable.Set
//import scala.concurrent.Future
//
//class CsvDataReaderImpl(config: FileStorageConfig) extends DataReader {
//
//  val FileStorageConfig(directoryPath, accountsFile, categoriesFile, transactionsFile) = config
//
//  def getAccounts: Future[Set[Account]] = ???
//
//  def getCategories: Future[Set[Category]] = ???
//
//  def getTransactions: Future[Set[RawTransaction]] = ??? /*{
//    val file = Source.fromFile(new File(directoryPath, accountsFile))
//    managed(file)
//  }*/
//}
