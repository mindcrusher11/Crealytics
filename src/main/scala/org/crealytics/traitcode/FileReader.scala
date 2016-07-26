package org.crealytics.traitcode

/**
  * Created by gaur on 26/7/16.
  */
trait FileReader[T] {

  def readFile(path:String )

  def saveFile(path:String )

  def processFile(path:String)

}
