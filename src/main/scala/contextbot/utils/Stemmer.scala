package contextbot.utils

import org.apache.lucene.analysis.{Analyzer, StopFilter, TokenStream}
import org.apache.lucene.analysis.fr.{FrenchAnalyzer, FrenchLightStemFilter}
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute

object Stemmer {
  def stemText(input: String) : Seq[String] = {
    val analyzer: Analyzer = new FrenchAnalyzer()
    var result: TokenStream = analyzer.tokenStream(null, input)
    result = new FrenchLightStemFilter(result)
    result = new StopFilter(result, FrenchAnalyzer.getDefaultStopSet)
    val resultAttr : CharTermAttribute = result.addAttribute(classOf[CharTermAttribute])
    result.reset()

    var resultSeq : Seq[String] = Nil
    while (result.incrementToken()) {
      //println(resultAttr)
      resultSeq = resultSeq :+ resultAttr.toString
    }
    resultSeq
  }

}
