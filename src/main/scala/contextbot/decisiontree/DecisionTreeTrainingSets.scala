package contextbot.decisiontree

object DecisionTreeTrainingSets {

  def trainingSet() : List[String] = {
    val users = "aimad" :: "lucas" :: "matthieu" :: "nicolas" :: "edwin" :: "elouwdie" :: "mohamed" :: "nil" :: "simon kurze" :: Nil
    val templates = Seq(
      "je suis dispo",
      "je suis libre"
    )
    val time = Seq(
      "demain",
      "mardi"
    )
    val tuples = for (user <- users; template <- templates; t <- time) yield (user, template, t)
    tuples.map {case (x, y, z) => s"$x $y $z"}
  }

  def main(args: Array[String]): Unit = {

    trainingSet().foreach(println)
  }

}
