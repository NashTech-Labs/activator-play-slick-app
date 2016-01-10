package repo

import org.specs2.mutable.Specification
import play.api.Application
import play.api.test.WithApplication
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import java.util.Date

class EmployeeRepositorySpec extends Specification {

  import models._

  "Employee repository" should {

    def empRepo(implicit app: Application) =
      Application.instanceCache[EmployeeRepository].apply(app)

    "insert a row" in new WithApplication {
      val knolId = Await.result(empRepo.insert(Employee("sky", "knoldus", new Date, "")), Duration.Inf)
      knolId === 1
    }

  }
}
