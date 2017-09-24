name := "contextBot"

version := "1.0"

scalaVersion := "2.11.11"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"

//Machine Learning
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.1.0"

//Stemming
libraryDependencies += "org.apache.lucene" % "lucene-core" % "6.6.1"
libraryDependencies += "org.apache.lucene" % "lucene-analyzers-common" % "6.6.1"



//Discord4j
libraryDependencies +=  "com.github.austinv11" % "Discord4J" % "2.8.4"

resolvers += "jcenter" at "http://jcenter.bintray.com"
resolvers += "jitpack.io" at "https://jitpack.io"