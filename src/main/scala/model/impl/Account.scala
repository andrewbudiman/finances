package model.impl

sealed trait Account

object Account {

  case object BofA extends Account

  case object CapitalOne extends Account

  case object Discover extends Account
}
