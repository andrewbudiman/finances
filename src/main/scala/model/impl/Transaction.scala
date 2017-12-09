package model.impl

case class Transaction(
  account: Account,
  epoch: Long,
  description: String,
  category: Category,
  amount: Double)

