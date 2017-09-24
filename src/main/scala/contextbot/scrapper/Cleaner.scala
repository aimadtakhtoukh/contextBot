package contextbot.scrapper

import java.io.{BufferedWriter, FileWriter}

import scala.io.Source

import contextbot.utils.Stemmer

object Cleaner extends App {

  def cleanInput(input : String) : String = {
    println(input)
    val result = input.toLowerCase.replaceAll("([,!?\\.])", " $1 ")
    println(result)
    result
  }

  val file = "corpus-stem.txt"
  val writer = new BufferedWriter(new FileWriter(file, true))
  Source.fromFile("corpus.txt").mkString
    .split("\n")
    .map(cleanInput)
    //.map(Stemmer.stemText).map(_.mkString(" "))
    .foreach(line => writer.write(line + "\n"))
  writer.flush()
  writer.close()
}
