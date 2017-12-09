import java.io.File

import resource._

import scala.io.Source

package object util {

  def readFileContents(file: File): String = {
    managed(Source.fromFile(file)).acquireAndGet(_.getLines.mkString)
  }
}
