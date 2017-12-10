package datastore

import model.metadata.UserMetadata

trait MetadataWriter {

  def writeMetadata(userMetadata: UserMetadata): Unit
}
