package model.impl

case class RawTransaction(
  account: Account,
  epoch: Long,
  description: String,
  amount: Double)
