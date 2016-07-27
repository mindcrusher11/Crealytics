package org.crealytics.sparker

/**
  * Created by gaur on 25/7/16.
  */

import java.io.File
import java.util

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}
import org.crealytics.traitcode.FileReader
import org.crealytics.utility.{ReadFile, Utilites}

class SparkCsvReader(confPath:String) extends FileReader[Option[String]]{

  val confInfo = ConfigFactory.parseFile(new File(confPath))
  val sparkConf = new SparkConf().setMaster(confInfo.getString("spark_csv.masterUrl")).setAppName(confInfo.getString("spark_csv.appName"))
  val sparkContext = new SparkContext(sparkConf)
  val sqlContext = new SQLContext(sparkContext)
  var joinedDataFrame:DataFrame = _
  var inDataFrame:DataFrame =_

  def processFile(confPath:String): Unit ={

    if(ReadFile.isLocalFileExist(confInfo.getString("spark_csv.csvFilePath"))){

      readFile(confInfo.getString("spark_csv.csvFilePath"))

      val groupedData = inDataFrame.groupBy(confInfo.getString("spark_csv.groupColumn")).pivot(confInfo.getString("spark_csv.pivotColumn")).sum("value").sort(confInfo.getString("spark_csv.groupColumn"))

      val formatedData = groupedData.withColumn(confInfo.getString("spark_csv.groupColumn"), date_format(groupedData.col(confInfo.getString("spark_csv.groupColumn")), confInfo.getString("spark_csv.colDateFormat")))

      val maxMinDates = groupedData.select(max(confInfo.getString("spark_csv.groupColumn")) as ("max"), min("date") as ("min"))

      val minDate = maxMinDates.head().get(1).asInstanceOf[util.Date]
      val maxDate = maxMinDates.head().get(0).asInstanceOf[util.Date]
      val dateRange = Utilites.genDateRange(minDate, maxDate, confInfo.getString("spark_csv.colDateFormat").toString)

      import sqlContext.implicits._
      val outrdd = sparkContext.parallelize(dateRange)
      val joinedDf = outrdd.toDF().withColumnRenamed("_1", confInfo.getString("spark_csv.groupColumn"))

      joinedDataFrame = joinedDf.join(formatedData, formatedData(confInfo.getString("spark_csv.groupColumn")) === joinedDf(confInfo.getString("spark_csv.groupColumn")), "left").drop(formatedData.col(confInfo.getString("spark_csv.groupColumn")))

      saveFile(confInfo.getString("spark_csv.outPath"))
    }
    else{
      println("file does not exist")
    }
  }

  def readFile(inputPath:String):Unit ={
      inDataFrame = sqlContext.read.format(confInfo.getString("spark_csv.readFormat"))
        .option("header", confInfo.getString("spark_csv.isReadCsvHeader")) // Use first line of all files as header
        .option("inferSchema", confInfo.getString("spark_csv.isCsvInferSchema")) // Automatically infer data types
        .option("delimiter", confInfo.getString("spark_csv.csvDelimiter"))
        .option("dateFormat", confInfo.getString("spark_csv.colDateFormat"))
        .load(inputPath)
  }

  def saveFile(outPutPath:String): Unit ={
    joinedDataFrame.repartition(confInfo.getInt("spark_csv.outFileCount")).write.format(confInfo.getString("spark_csv.writeFormat"))
      .option("header",confInfo.getString("spark_csv.isWriteCsvHeader"))
      .option("delimiter",confInfo.getString("spark_csv.writeCsvDelimiter"))
      .option("dateFormat",confInfo.getString("spark_csv.colDateFormat"))
      .mode(SaveMode.Overwrite)
      .save(outPutPath)

  }


}
