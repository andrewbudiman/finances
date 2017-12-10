package config

import java.io.File

import util._
import config.Config.DatastoreConfig
import org.json4s.{DefaultFormats, Formats, ShortTypeHints}

case class Config(
  datastoreConfig: DatastoreConfig
)

object Config {

  implicit val formats: Formats = new DefaultFormats {
    override val typeHintFieldName = "type"
    override val typeHints = ShortTypeHints(List(classOf[JsonDatastoreConfig]))
  }

  def load(file: String): Config = {
    parseJson(util.readFileContents(new File(file))).extract[Config]
  }

  trait DatastoreConfig

  case class JsonDatastoreConfig(
    metadataFile: String,
    outputMetadataFile: String
  ) extends DatastoreConfig {

    if (metadataFile == outputMetadataFile) {
      throw new RuntimeException(s"xcxc - cannot overwrite the input metadata file")
    }
  }
}
