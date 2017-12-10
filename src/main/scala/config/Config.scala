package config

import java.io.File
/*
import config.Config.{CategoryConfig, MatchersConfig, PriorityConfig}
import org.json4s.{DefaultFormats, Formats}
import org.json4s.jackson.{JsonMethods, Serialization}

import scala.collection.immutable.Seq

case class Config(
  priorities: Seq[PriorityConfig],
  categories: Seq[CategoryConfig],
  matchers: MatchersConfig)

object Config {

  implicit val jsonDefaultFormats: Formats = DefaultFormats

  def load(file: String): Config = {
    JsonMethods.parse(util.readFileContents(new File(file))).extract[Config]
  }

  def write(config: Config, file: String): Unit = {
    util.writeContentsToFile(Serialization.writePretty(config), new File(file))
  }

  case class PriorityConfig(id: Long, name: String, createdAt: Long)

  case class CategoryConfig(id: Int, name: String, priorityId: Int, createdAt: Long)

  case class ContainsMatcherConfig(value: String, categoryId: Int, createdAt: Long)

  case class EqualityMatcherConfig(value: String, categoryId: Int, createdAt: Long)

  case class MatchersConfig(
    contains: Seq[ContainsMatcherConfig],
    equality: Seq[EqualityMatcherConfig])
}*/
