package datastore

import model.metadata.Metadata

trait MetadataWriter {

  def writeMetadata(metadata: Metadata): Unit
}
