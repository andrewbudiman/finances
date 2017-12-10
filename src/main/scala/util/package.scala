import java.io.{BufferedWriter, File, FileWriter}

import org.json4s.JsonAST.JValue
import org.json4s.{DefaultFormats, Formats, TypeHints}
import org.json4s.jackson.{JsonMethods, Serialization}
import resource._

import scala.io.Source

package object util {

  val jsonDefaultFormats: Formats = DefaultFormats

  def readFileContents(file: File): String = {
    for (input <- managed(Source.fromFile(file))) {
      input.getLines().mkString
    }
    managed(Source.fromFile(file)).acquireAndGet(_.getLines.mkString)
  }

  def writeContentsToFile(content: String, file: File): Unit = {
    managed(new BufferedWriter(new FileWriter(file))).acquireAndGet(_.append(content))
  }

  def customFormats(customTypeHints: TypeHints): Formats = new DefaultFormats {
    override val typeHintFieldName = "type"
    override val typeHints: TypeHints = customTypeHints
  }

  def parseJson(json: String)(implicit formats: Formats = jsonDefaultFormats): JValue = {
    JsonMethods.parse(json)
  }

  def toJson(obj: AnyRef)(implicit formats: Formats = jsonDefaultFormats): String = {
    Serialization.writePretty(obj)
  }
}
