package model.transactions

import model.metadata.Account

case class RawTransaction(
  account: Account,
  epoch: Long,
  description: String,
  amount: Double)
