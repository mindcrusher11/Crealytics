package org.crealytics.sparker

/**
  * Created by gaur on 26/7/16.
  */
object MainClass {

  def main(args:Array[String]): Unit =
  {
    val data = new SparkCsvReader("/home/gaur/Desktop/config.conf")
    println(data.confInfo.getString("spark_csv.masterUrl"))

    data.processFile("/home/gaur/Desktop/config.conf")
  }
}
