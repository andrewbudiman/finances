package apps

import data.{DataReader, DataWriter}
import model.impl.{Account, Category, RawTransaction}
import parser.DataParser
import org.scalatest.FunSuite
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.easymock.EasyMockSugar

import scala.collection.immutable.Set
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AddNewTransactionsSuite extends FunSuite with ScalaFutures with EasyMockSugar {

  def createTransaction(amount: Double): RawTransaction = {
    RawTransaction(Account.BofA, 0L, "desc", amount)
  }

  test("Add only new transactions") {
    val dataParser = mock[DataParser]
    val dataReader = mock[DataReader]
    val dataWriter = mock[DataWriter]

    val tx0 = createTransaction(0)
    val tx1 = createTransaction(1)
    val tx2 = createTransaction(2)
  }
}
