package repo

import org.specs2.mutable.Specification
import play.api.Application
import play.api.test.WithApplication
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import java.util.Date
import scala.concurrent.Future

class EmployeeRepositorySpec extends Specification {

  import models._

  "Employee repository" should {

    def empRepo(implicit app: Application) = Application.instanceCache[EmployeeRepository].apply(app)

    "get all rows" in new WithApplication {
      val result = await(empRepo.getAll)
      result.length === 1
      result.head.name === "test"
    }

    "insert a row" in new WithApplication {
      val knolId = await(empRepo.insert(Employee("sky", "sky@knoldus.com", new Date, "knoldus")))
      knolId === 2
    }

    "insert multiple rows" in new WithApplication {
      val result = empRepo.insertAll(List(Employee("sky1", "sky1@knoldus.com", new Date, "knoldus"), Employee("sky2", "sky2@knoldus.com", new Date, "knoldus")))
      val knolIds = await(result)
      knolIds === Seq(2, 3)
    }

    "update a row" in new WithApplication {
      val result = await(empRepo.update(Employee("sky", "sky@knoldus.com", new Date, "knoldus", Some(1))))
      result === 1
    }

    "delete a row" in new WithApplication {
      val result = await(empRepo.delete(1))
      result === 1
    }
  }

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)

}
