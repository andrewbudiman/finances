import java.io.{BufferedWriter, File, FileWriter}

import resource._

import scala.io.Source

package object util {

  def readFileContents(file: File): String = {
    for (input <- managed(Source.fromFile(file))) {
      input.getLines().mkString
    }
    managed(Source.fromFile(file)).acquireAndGet(_.getLines.mkString)
  }

  def writeContentsToFile(content: String, file: File): Unit = {
    //for (writer <- managed(new BufferedWriter(new FileWriter(file)))) {
    //  writer.append(content)
    //}
    managed(new BufferedWriter(new FileWriter(file))).acquireAndGet(_.append(content))
  }
}
