package datastore

import config.Config.{DatastoreConfig, JsonDatastoreConfig}
import datastore.impl.jsonstore.JsonMetadataStore

package object impl {

  def getMetadataReader(datastoreConfig: DatastoreConfig): MetadataReader = datastoreConfig match {
    case JsonDatastoreConfig(metadataFile, _) => JsonMetadataStore.reader(metadataFile)
    case c => throw new RuntimeException(s"Unknown datastore config: $c")
  }

  def getMetadataWriter(datastoreConfig: DatastoreConfig): MetadataWriter = datastoreConfig match {
    case JsonDatastoreConfig(_, outputMetadataFile) => JsonMetadataStore.writer(outputMetadataFile)
    case c => throw new RuntimeException(s"Unknown datastore config: $c")
  }
}
