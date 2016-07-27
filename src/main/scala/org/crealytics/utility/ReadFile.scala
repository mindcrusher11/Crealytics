package org.crealytics.utility


import com.typesafe.config.ConfigFactory
import com.github.tototoshi.csv._
import java.nio.file.{Paths, Files}


/**
  * Created by gaur on 25/7/16.
  */
object ReadFile {

  def readCsv(filePath:String): Unit ={
    val reader = CSVReader.open(filePath)
  }

  def isLocalFileExist(filePath:String): Boolean ={
    Files.exists(Paths.get(filePath))
  }


}
