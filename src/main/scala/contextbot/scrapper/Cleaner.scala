package contextbot.scrapper

import java.io.{BufferedWriter, FileWriter}

import scala.io.Source

import contextbot.utils.Stemmer

object Cleaner extends App {
  val sourceFile = "corpus.txt"
  val targetFile = "corpus-stem.txt"

  def cleanInput =
    (entry : String) =>
      entry
        .toLowerCase
        .replaceAll("([\\(\\)\\/\\\\,!?\\.])", " $1 ")

  new FileWriter(targetFile, false) {
    Source.fromFile(sourceFile).mkString
      .split("\n")
      .map(cleanInput)
      .foreach(line => write(line + "\n") + " ")
    close()
  }
}
