package repo

import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile

trait ColumnTypeMapper { self: HasDatabaseConfigProvider[JdbcProfile] =>

  import driver.api._

  object DateMapper {

    implicit val utilDateToSQLDateMapper = MappedColumnType.base[java.util.Date, java.sql.Date](
      utilDate => new java.sql.Date(utilDate.getTime),
      sqlDate => new java.util.Date(sqlDate.getTime()))

  }

}
