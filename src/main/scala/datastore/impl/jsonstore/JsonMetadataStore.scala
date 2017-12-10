package datastore.impl.jsonstore

import java.io.File

import datastore.impl.jsonstore.JsonMetadata.{JsonContainsMatcher, JsonEqualityMatcher}
import datastore.{MetadataReader, MetadataWriter}
import model.metadata.Metadata
import org.json4s.{Formats, ShortTypeHints}
import util._

object JsonMetadataStore {

  implicit val formats: Formats =
    customFormats(ShortTypeHints(List(classOf[JsonContainsMatcher], classOf[JsonEqualityMatcher])))

  def reader(file: String): MetadataReader =
    () => parseJson(readFileContents(new File(file))).extract[JsonMetadata].toMetadata

  def writer(file: String): MetadataWriter =
    (metadata: Metadata) => writeContentsToFile(toJson(metadata), new File(file))
}
