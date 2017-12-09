package config

import java.io.File

import config.Config.{CategoryConfig, MatchersConfig, PriorityConfig}
import org.json4s.{DefaultFormats, Formats}
import org.json4s.jackson.JsonMethods

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

  case class PriorityConfig(id: Int, name: String)

  case class CategoryConfig(id: Int, name: String, priorityId: Int)

  case class ContainsMatcherConfig(value: String, categoryId: Int)

  case class EqualityMatcherConfig(value: String, categoryId: Int)

  case class MatchersConfig(
    contains: Seq[ContainsMatcherConfig],
    equality: Seq[EqualityMatcherConfig])
}
