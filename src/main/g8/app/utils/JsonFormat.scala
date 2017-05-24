package utils



import models._
import play.api.libs.json.Json


object JsonFormat {

  implicit val employeeFormat = Json.format[Employee]


}


