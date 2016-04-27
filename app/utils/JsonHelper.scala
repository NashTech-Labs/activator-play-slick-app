package utils

import java.text.SimpleDateFormat

import org.json4s._
import org.json4s.native.JsonMethods.{parse => jParser}
import org.json4s.native.Serialization.{write => jWrite}


trait JsonHelper {

  implicit val formats = new org.json4s.DefaultFormats {


    override def dateFormatter: SimpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy")

  }

  protected def write[T <: AnyRef](value: T): String = jWrite(value)

  protected def parse(value: String): JValue = jParser(value)

  implicit  def extractOrEmptyString(json: JValue): String = json match {
    case JNothing => ""
    case data => data.extract[String]
  }

   def successMessage(msg: String) = write(Map("status" -> "success", "message" -> msg))


   def errorMessage(msg: String) = write(Map("status" -> "error", "message" -> msg))

}

