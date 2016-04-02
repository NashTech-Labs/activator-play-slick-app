package utils

import java.text.SimpleDateFormat

import models.Emp
import org.json4s._
import org.json4s.native.JsonMethods.{parse => jParser}
import org.json4s.native.Serialization.{write => jWrite}
import play.api.libs.json._
import play.api.libs.functional.syntax._


trait JsonHelper{

  implicit val formats = new org.json4s.DefaultFormats {
    override def dateFormatter: SimpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy")
  }

  protected def write[T <: AnyRef](value: T): String = jWrite(value)

  protected def parse(value: String): JValue = jParser(value)

  implicit protected def extractOrEmptyString(json: JValue): String = json match {
    case JNothing =>""
    case data     => data.extract[String]
  }

  implicit val locationReads: Reads[Emp] = (
    (JsPath \ "empName").read[String] and
      (JsPath \ "empEmail").read[String]
    )(Emp.apply _)

}

