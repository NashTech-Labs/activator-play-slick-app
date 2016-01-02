package models

import java.sql.Date

case class Knol(name: String, email: String, companyName: String, id: Option[Int] = None)

case class KnolX(topic: String, date: Date, knolId: Int, id: Option[Int] = None)