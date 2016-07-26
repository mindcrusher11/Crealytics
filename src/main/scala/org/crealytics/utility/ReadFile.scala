package org.crealytics.utility


import com.typesafe.config.ConfigFactory
import com.github.tototoshi.csv._



/**
  * Created by gaur on 25/7/16.
  */
object ReadFile {

  def readCsv(filePath:String): Unit ={
    val reader = CSVReader.open(filePath)
  }

  def isFileExist(filePath:String): Boolean ={
      true
  }

}
