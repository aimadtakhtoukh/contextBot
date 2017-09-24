package contextbot.model

import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.rdd.RDD

object ModelCreation extends App {

  def showSynonyms(model: Word2VecModel)(input : String): Unit = {
    println(input)
    for ((synonym, cosineSimilarity) <- model.findSynonyms(input, 10)) {
      println(s"$synonym $cosineSimilarity")
    }
  }

  val conf: SparkConf = new SparkConf().setMaster("local[4]").setAppName("TrainingBot")
  val sc = new SparkContext(conf)

  val input: RDD[Seq[String]] = sc.textFile("corpus-stem.txt").map(line => line.split(" ").toSeq)

  val word2Vec = new Word2Vec()
    .setWindowSize(50)
    .setNumPartitions(1)
    .setVectorSize(300)


  val model: Word2VecModel = word2Vec.fit(input)
  //model.save(sc, "model/word2vecForDiscordBot")

  val show = showSynonyms(model)(_: String)

  val first = model.getVectors.getOrElse("shadowrun", Array())
  val secon = model.getVectors.getOrElse("aimad", Array())
  val third = model.getVectors.getOrElse("nicolas", Array())

  val result = ((first, secon).zipped.map(_ - _), third).zipped.map(_ + _).map(_.toDouble)
  val resultVector = Vectors.dense(result)

  println("shadowrun - aimad + nicolas")
  for ((synonym, cosineSimilarity) <- model.findSynonyms(resultVector, 5)) {
    println(s"$synonym $cosineSimilarity")
  }
  show("libre")
  show("dispo")


  //model.getVectors.keys.foreach(println)

}