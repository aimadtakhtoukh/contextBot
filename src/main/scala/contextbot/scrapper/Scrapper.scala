package contextbot.scrapper

import java.io.{BufferedWriter, FileWriter}
import java.util

import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.handle.obj.IMessage

import scala.collection.JavaConversions._

object Scrapper extends App {

  val token = "Mjk1NTc3MzEyNjY4MTU1OTE2.C8ptoQ.dMO6C384E8bNwRxHhFwXvnVuhr4"
  val file = "corpus.txt"

  val client =  new ClientBuilder().withToken(token).login()
  Thread.sleep(3000)
  println(client.getGuilds)
  client
    .getGuildByID(265467993507627009L)
    .getChannels
    .toList
      .foreach(x => {
        Thread.sleep(3000)
        println(x.getName)
        val messages = new util.ArrayList[String]()
        val history : Iterator[IMessage] = x.getFullMessageHistory.iterator
        while(history.hasNext) {
          val message = history.next
          val formattedMessage = message.getAuthor.getDisplayName(message.getGuild) + " " + message.getContent
          messages.add(formattedMessage)
        }
        util.Collections.reverse(messages)
        val writer = new BufferedWriter(new FileWriter(file, false))
        messages.foreach(line => writer.write(line.replace("\n", " ") + "\n"))
        writer.write('\n')
        writer.flush()
        writer.close()
      })
  System.exit(0)
}
