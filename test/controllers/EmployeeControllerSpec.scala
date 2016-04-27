package controllers


import models.Employee
import org.specs2.mock.Mockito
import play.api.libs.json.Json
import play.api.mvc._
import play.api.test._
import repo.EmployeeRepository
import utils.JsonFormat._

import scala.concurrent.Future

class EmployeeControllerSpec  extends PlaySpecification with Mockito with Results {

  val mockedRepo = mock[EmployeeRepository]
  val employeeController= new EmployeeController(mockedRepo)

  "EmployeeController " should {

    "create a employee" in {
        val emp = Employee("sky", "sky@knoldus.com", "knoldus", "Senior Consultant")
        mockedRepo.insert(emp) returns Future.successful(1)

        val result = employeeController.create().apply(FakeRequest().withBody(Json.toJson(emp)))
        val resultAsString = contentAsString(result)
        resultAsString === """{"status":"success","data":"","msg":"Employee has been added successfully."}"""
      }


  }

}
