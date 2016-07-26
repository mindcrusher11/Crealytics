name := "Crealytics"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.1",
  "com.github.tototoshi" %% "scala-csv" % "1.3.3",
  "com.databricks" % "spark-csv_2.10" % "1.4.0",
  "org.apache.spark" % "spark-sql_2.10" % "1.6.0",
  "io.lamma" % "lamma_2.10" % "2.3.0",
  "joda-time" % "joda-time" % "2.9.4",
  "com.novocode" % "junit-interface" % "0.8" % "test->default"
)