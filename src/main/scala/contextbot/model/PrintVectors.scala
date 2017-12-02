package contextbot.model

import org.apache.spark.mllib.feature.Word2VecModel
import org.apache.spark.{SparkConf, SparkContext}

object PrintVectors extends App {

  val conf: SparkConf = new SparkConf().setMaster("local[4]").setAppName("TrainingBot")
  val sc = new SparkContext(conf)

  val word2vecModel = Word2VecModel.load(sc, "modal/word2vecForDiscordBot")


}
