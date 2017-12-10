package model.transactions

import model.metadata.{Account, Category}

case class Transaction(
  account: Account,
  epoch: Long,
  description: String,
  category: Category,
  amount: Double
)
