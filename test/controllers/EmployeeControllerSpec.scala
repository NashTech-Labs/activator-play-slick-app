package controllers


import java.util.Date

import models.Employee
import org.specs2.mock.Mockito
import play.api.libs.iteratee.Iteratee
import play.api.mvc._
import play.api.test._
import repo.EmployeeRepository
import utils.JsonHelper

import scala.concurrent.Future

class EmployeeControllerSpec  extends PlaySpecification with Mockito with Results with JsonHelper{

  val mockedRepo = mock[EmployeeRepository]
  val employeeController= new EmployeeController(mockedRepo)

  "EmployeeController " should {

    "create a employee" in {
      val emp=Employee("sky", "sky@knoldus.com", new Date("1989-01-19"), "knoldus","Senior Consultant")
      mockedRepo.insert(emp)  returns Future.successful(1)
      val result: Future[Result] = employeeController.create().apply(FakeRequest().withBody(write(emp))).run
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"error","data":{},"msg":"Invalid  json format"}"""
    }
  }

}
