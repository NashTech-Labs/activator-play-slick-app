package repo

import javax.inject.{ Inject, Singleton }
import models.Knol
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future

@Singleton()
class KnolRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends KnolTable with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  def insert(knol: Knol): Future[Int] = db.run { knolTableQueryInc += knol }

  def insertAll(knols: List[Knol]): Future[Seq[Int]] = db.run { knolTableQueryInc ++= knols }

  def getKnolByEmail(email: String): Future[Option[Knol]] = db.run(knolTableQuery.filter(_.email === email).result.headOption)

}

private[repo] trait KnolTable { self: HasDatabaseConfigProvider[JdbcProfile] =>

  import driver.api._

  private[KnolTable] class KnolTable(tag: Tag) extends Table[Knol](tag, "knol") {

    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    val name: Rep[String] = column[String]("name", O.SqlType("VARCHAR(200)"))
    val email: Rep[String] = column[String]("email", O.SqlType("VARCHAR(200)"))
    val company: Rep[String] = column[String]("compnay")

    def emailUnique = index("email_unique_key", email, unique = true)

    def * = (name, email, company, id.?) <> (Knol.tupled, Knol.unapply)
  }

  protected val knolTableQuery = TableQuery[KnolTable]

  protected val knolTableQueryInc = knolTableQuery returning knolTableQuery.map(_.id)

}
