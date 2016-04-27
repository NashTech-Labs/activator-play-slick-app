package models

import java.util.Date

case class Employee(name: String, email: String, dob: Date, companyName: String,position:String, id: Int = 0)

case class Emp(name: String, email: String)