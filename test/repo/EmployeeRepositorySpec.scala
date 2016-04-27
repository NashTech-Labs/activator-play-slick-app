package repo


import play.api.Application
import play.api.test.{WithApplication, PlaySpecification}
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import java.util.Date
import scala.concurrent.Future

class EmployeeRepositorySpec extends PlaySpecification{

  import models._

  "Employee repository" should {

    def empRepo(implicit app: Application) = Application.instanceCache[EmployeeRepository].apply(app)

    "get all rows" in new WithApplication()  {
      val result = await(empRepo.getAll)
      result.length === 4
      result.head.name === "Vikas"
    }

    "get single rows" in new  WithApplication()  {
      val result = await(empRepo.getById(1))
      result.isDefined === true
      result.get.name === "Vikas"
    }

    "insert a row" in new  WithApplication()  {
      val knolId = await(empRepo.insert(Employee("sky", "sky@knoldus.com",  "knoldus","Senior Consultant")))
      knolId === 5
    }

    "insert multiple rows" in new  WithApplication()  {
      val result = empRepo.insertAll(List(Employee("sky1", "sky1@knoldus.com", "knoldus","Senior Consultant"),
        Employee("sky2", "sky2@knoldus.com", "knoldus","Senior Consultant")))
      val knolIds = await(result)
      knolIds === Seq(5, 6)
    }

    "update a row" in new  WithApplication()  {
      val result = await(empRepo.update(Employee("sky", "sky@knoldus.com",  "knoldus","Senior Consultant", Some(1))))
      result === 1
    }

    "delete a row" in new  WithApplication()  {
      val result = await(empRepo.delete(1))
      result === 1
    }
  }

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)

}
