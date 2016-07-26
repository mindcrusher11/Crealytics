package org.crealytics.utility

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util
import java.util.Calendar
import scala.collection.mutable

/**
  * Created by gaur on 26/7/16.
  */
object Utilites {

  def between(fromDate: LocalDate, toDate: LocalDate) = {
    fromDate.toEpochDay.until(toDate.toEpochDay).map(LocalDate.ofEpochDay)
  }

  def genDateRange (startDate: util.Date, endDate: util.Date,dateForm:String): List[String] = {
    val dateFormat = new SimpleDateFormat(dateForm)
    var dt = startDate
    val res: mutable.MutableList[String] = new mutable.MutableList[String]()
    val c = Calendar.getInstance()
    while ( dt.before(endDate) || dt.equals(endDate)) {
      res += dateFormat.format(dt)
      c.setTime(dt)
      c.add(Calendar. DATE, 1)
      dt = c.getTime
    }
    res.toList
  }

}
