package controllers


import models.Employee
import repo.EmployeeRepository
import utils.JsonFormat._

import org.specs2.mock.Mockito

import play.api.test.FakeRequest
import play.api.test.PlaySpecification
import play.api.test.WithApplication

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class EmployeeControllerSpec extends PlaySpecification with Mockito with Results {

  implicit val mockedRepo: EmployeeRepository = mock[EmployeeRepository]

  val testController = new EmployeeController()


  "EmployeeController " should {

    "create a employee" in new WithApplication() {
      val emp = Employee("bob", "bob@xyz.com", "knoldus", "Senior Consultant")
      mockedRepo.insert(emp) returns Future.successful(1)
      val result = employeeController.create().apply(FakeRequest().withBody(Json.toJson(emp)))
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"success","data":{"id":1}}"""
    }

    "update a employee" in new WithApplication() {
      val updatedEmp = Employee("Satendra", "bob@xyz.com", "knoldus", "Senior Consultant", Some(1))
      mockedRepo.update(updatedEmp) returns Future.successful(1)

      val result = employeeController.update().apply(FakeRequest().withBody(Json.toJson(updatedEmp)))
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"success","data":"{}"}"""
    }

    "edit a employee" in new WithApplication() {
      val emp = Employee("bob", "bob@xyz.com", "knoldus", "Senior Consultant", Some(1))
      mockedRepo.getById(1) returns Future.successful(Some(emp))
      val result = employeeController.edit(1).apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"success","data":{"name":"bob","email":"bob@xyz.com","companyName":"knoldus","position":"Senior Consultant","id":1}}"""
    }

    "delete a employee" in new WithApplication() {
      mockedRepo.delete(1) returns Future.successful(1)
      val result = employeeController.delete(1).apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"success","data":"{}"}"""
    }

    "get all list" in new WithApplication() {
      val emp = Employee("bob", "bob@xyz.com", "knoldus", "Senior Consultant", Some(1))
      mockedRepo.getAll() returns Future.successful(List(emp))
      val result = employeeController.list().apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"success","data":[{"name":"bob","email":"bob@xyz.com","companyName":"knoldus","position":"Senior Consultant","id":1}]}"""
    }

  }

}
