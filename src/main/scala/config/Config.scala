package config

import config.Config.StorageConfig

case class Config(storageConfig: StorageConfig)

object Config {

  sealed trait StorageConfig

  case class FileStorageConfig(
    directoryPath: String,
    accountsFile: String,
    categoriesFile: String,
    transactionsFile: String
  ) extends StorageConfig
}
