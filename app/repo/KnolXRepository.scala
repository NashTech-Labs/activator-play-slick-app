package repo

import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import models.KnolX
import java.sql.Date
import play.api.db.slick.DatabaseConfigProvider

class KnolXRepository(protected val dbConfigProvider: DatabaseConfigProvider) extends KnolXTable with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  def insert(knolx: KnolX) = db.run(knolXTableQueryInc += knolx)

  def getKnolXByKnolId(id: Int) = db.run { knolXTableQuery.filter(_.knolId === id).to[List].result }

}

trait KnolXTable extends KnolTable { self: HasDatabaseConfigProvider[JdbcProfile] =>

  import driver.api._

  private[repo] class KnolXTable(tag: Tag) extends Table[KnolX](tag, "knolx") {
    val id = column[Int]("id", O.AutoInc, O.PrimaryKey)
    val topic = column[String]("topic")
    val date = column[Date]("date")
    val knolId = column[Int]("knol_id")
    def * = (topic, date, knolId, id.?) <> (KnolX.tupled, KnolX.unapply)
  }

  protected val knolXTableQuery = TableQuery[KnolXTable]

  protected val knolXTableQueryInc = knolXTableQuery returning knolXTableQuery.map(_.id)

}