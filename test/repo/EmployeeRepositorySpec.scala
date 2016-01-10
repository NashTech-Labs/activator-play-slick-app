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

    def empRepo(implicit app: Application) = Application.instanceCache[EmployeeRepository].apply(app)

    "get all rows" in new WithApplication {
      val result = Await.result(empRepo.getAll, Duration.Inf)
      result.length === 1
      result.head.name === "test"
    }

    "insert a row" in new WithApplication {
      val knolId = Await.result(empRepo.insert(Employee("sky", "sky@knoldus.com", new Date, "knoldus")), Duration.Inf)
      knolId === 2
    }

    "insert multiple rows" in new WithApplication {
      val result = empRepo.insertAll(List(Employee("sky1", "sky1@knoldus.com", new Date, "knoldus"), Employee("sky2", "sky2@knoldus.com", new Date, "knoldus")))
      val knolIds = Await.result(result, Duration.Inf)
      knolIds === Seq(2, 3)
    }

    "update a row" in new WithApplication {
      val result = Await.result(empRepo.update(Employee("sky", "sky@knoldus.com", new Date, "knoldus", Some(1))), Duration.Inf)
      result === 1
    }

    "delete a row" in new WithApplication {
      val result = Await.result(empRepo.delete(1), Duration.Inf)
      result === 1
    }

  }

}
