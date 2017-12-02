package contextbot.scrapper

import java.io.{File, FileWriter}

import sx.blah.discord.api.ClientBuilder

import scala.collection.JavaConversions._

object Scrapper extends App {
  val token = "Mjk1NTc3MzEyNjY4MTU1OTE2.C8ptoQ.dMO6C384E8bNwRxHhFwXvnVuhr4"
  val file = "corpus.txt"

  new File(file).delete()
  val writer = new FileWriter(file, true)

  val client = {
    val res = new ClientBuilder().withToken(token).login()
    while (!res.isReady) {}
    res
  }
  println(client.getGuilds.map(_.getName))
  client
    .getGuildByID(265467993507627009L)
    .getChannels
    .toList
    .foreach(channel => {
      println(channel.getName)

      val messages =
        channel
          .getFullMessageHistory
          .iterator
          .toList
          .reverse
          .map(message => s"${message.getAuthor.getDisplayName(message.getGuild)} ${message.getContent}")

        messages.foreach(line => writer.write(line.replace("\n", " ") + "\n"))
        writer.write('\n')
    })
  writer.close()
  client.logout()
}