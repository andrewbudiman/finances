package datastore.jsonstore

import java.io.File

import datastore.{MetadataReader, MetadataWriter}
import model.metadata.UserMetadata
import org.json4s.jackson.{JsonMethods, Serialization}
import org.json4s.{DefaultFormats, Formats}

object JsonMetadataStore {

  implicit val jsonDefaultFormats: Formats = DefaultFormats

  def loadMetadata(file: String): UserMetadata = {
    JsonMethods.parse(util.readFileContents(new File(file)))
      .extract[JsonUserMetadata]
      .toUserMetadata
  }

  def writeMetadata(file: String, userMetadata: UserMetadata): Unit = {
    util.writeContentsToFile(Serialization.writePretty(userMetadata), new File(file))
  }

  def reader(file: String): MetadataReader =
    () => JsonMetadataStore.loadMetadata(file)

  def writer(file: String): MetadataWriter =
    (userMetadata: UserMetadata) => JsonMetadataStore.writeMetadata(file, userMetadata)
}
