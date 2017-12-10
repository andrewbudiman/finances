package datastore

import model.metadata.UserMetadata

trait MetadataReader {

  def loadMetadata(): UserMetadata
}
