package repo

import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.Application
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import java.util.Date
import scala.concurrent.Future

class EmployeeRepositorySpec extends PlaySpec  with OneAppPerSuite{

  import models._

  "Employee repository" should {

    def empRepo(implicit app: Application) = Application.instanceCache[EmployeeRepository].apply(app)

    "get all rows" in new App {
      val result = await(empRepo.getAll)
      result.length === 4
      result.head.name === "Vikas"
    }

    "get single rows" in new App {
      val result = await(empRepo.getById(1))
      result.isDefined === true
      result.get.name === "Vikas"
    }

    "insert a row" in new App {
      val knolId = await(empRepo.insert(Employee("sky", "sky@knoldus.com", new Date, "knoldus","Senior Consultant")))
      knolId === 5
    }

    "insert multiple rows" in new App {
      val result = empRepo.insertAll(List(Employee("sky1", "sky1@knoldus.com", new Date, "knoldus","Senior Consultant"),
        Employee("sky2", "sky2@knoldus.com", new Date, "knoldus","Senior Consultant")))
      val knolIds = await(result)
      knolIds === Seq(5, 6)
    }

    "update a row" in new App {
      val result = await(empRepo.update(Employee("sky", "sky@knoldus.com", new Date, "knoldus","Senior Consultant", Some(1))))
      result === 1
    }

    "delete a row" in new App {
      val result = await(empRepo.delete(1))
      result === 1
    }
  }

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)

}
