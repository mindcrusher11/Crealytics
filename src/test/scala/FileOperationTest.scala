package org.crealytics.sparker

import com.typesafe.config.ConfigFactory
import org.junit.Test
import junit.framework.TestCase
import org.junit.Assert._
import com.typesafe.config
/**
  * Created by gaur on 26/7/16.
  */
class FileOperationTest extends TestCase{

  var fileReader: SparkCsvReader = _
  var confInfo:config.Config = _

  override def setUp {
    fileReader = new SparkCsvReader("src/main/resources/config.conf")
    confInfo = fileReader.confInfo
  }

  def testOneTopping {

    fileReader.processFile(confInfo.getString("spark_csv.testFilePath"))

    val csvReader = fileReader.sqlContext.read.format(confInfo.getString("spark_csv.readFormat"))
      .option("header", confInfo.getString("spark_csv.isReadCsvHeader")) // Use first line of all files as header
      .option("inferSchema", confInfo.getString("spark_csv.isCsvInferSchema")) // Automatically infer data types
      .option("delimiter",confInfo.getString("spark_csv.csvDelimiter")) // defining delimiter to be used to seprate columns in csv file
      .option("dateFormat",confInfo.getString("spark_csv.colDateFormat")) // date format to be used


    val testDataFrame = csvReader.load(confInfo.getString("spark_csv.testFilePath"))

    val savedDataFrame = csvReader.load(confInfo.getString("spark_csv.outPath") + "/part-00000")

    assertEquals(savedDataFrame.count(), testDataFrame.intersect(savedDataFrame).count())
  }

}
