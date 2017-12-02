package contextbot.decisiontree

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.GradientBoostedTrees
import org.apache.spark.mllib.tree.configuration.BoostingStrategy
import org.apache.spark.rdd.RDD

import scala.io.Source

object DecisionTree extends App {
  val conf : SparkConf = new SparkConf().setMaster("local[1]").setAppName("DecisionTree")
  val sc : SparkContext = new SparkContext(conf)

  val fileName = "vectors.txt"
  val pattern = "(.*?)  \\[(.*?)\\]".r

  val map = Source.fromFile(fileName).getLines().map(line => {
    val pattern(word, vector) = line
    word -> vector.split(", ").map(_.toDouble)
  }).toMap

  def getConcatenedVectors(sentence : String) : Array[Double] = {
    sentence
      .split(" ")
      .flatMap(map.get)
      .reduce(
        (a, b) => (a, b).zipped.map(_ + _)
      )
  }

  map.keys.foreach(key => println(s"'$key'"))

  val sentence1 : Array[Double] = getConcatenedVectors("edwin je suis dispo demain")
  val point1 : LabeledPoint = new LabeledPoint(1.0, Vectors.dense(sentence1))

  val sentence2 : Array[Double] = getConcatenedVectors("aimad je raconte du caca lol oui")
  val point2 : LabeledPoint = new LabeledPoint(0.0, Vectors.dense(sentence2))

  val rdd : RDD[LabeledPoint] =
    sc.parallelize(
      (1 to 1000).map(_ => point1 :: point2 :: Nil).reduce(_ ::: _)
    )


  val boostingStrategy = BoostingStrategy.defaultParams("Classification")
  boostingStrategy.numIterations = 3
  boostingStrategy.treeStrategy.numClasses = 2
  boostingStrategy.treeStrategy.maxDepth = 5
  // Empty categoricalFeaturesInfo indicates all features are continuous.
  boostingStrategy.treeStrategy.categoricalFeaturesInfo = Map[Int, Int]()

  val tree = GradientBoostedTrees.train(rdd, boostingStrategy)

  def evaluate(array : Array[Double]) = {
    //tree.evaluateEachIteration(sc.parallelize(array :: Nil), LogLoss)
    tree.predict(Vectors.dense(array))
  }

  println(evaluate(sentence1))
  println(evaluate(sentence2))
  println(evaluate(getConcatenedVectors("nicolas dispo")))
}