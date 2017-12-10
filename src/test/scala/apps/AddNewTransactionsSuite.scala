package apps

import model.transactions.RawTransaction
import model.metadata.Account
import org.scalatest.FunSuite
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.easymock.EasyMockSugar

class AddNewTransactionsSuite extends FunSuite with ScalaFutures with EasyMockSugar {

  def createTransaction(amount: Double): RawTransaction = {
    RawTransaction(Account.BofA, 0L, "desc", amount)
  }

  test("nothing") {

  }
}
