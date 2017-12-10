package datastore

import model.metadata.Metadata

trait MetadataReader {

  def loadMetadata(): Metadata
}
