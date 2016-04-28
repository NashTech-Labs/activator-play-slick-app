package models

case class Employee(name: String, email: String, companyName: String,position:String, id: Option[Int]=None)

