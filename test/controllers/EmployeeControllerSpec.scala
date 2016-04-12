package controllers


import akka.util.ByteString
import org.scalatest.mock.MockitoSugar
import play.api.libs.streams.Accumulator
import play.api.mvc._

import play.api.test.FakeRequest
import service.EmployeeService

import scala.concurrent.Future

import org.mockito.Mockito._
import play.api.test.Helpers._
import org.scalatestplus.play._

import play.api.mvc._
import play.api.test._

class EmployeeControllerSpec  extends PlaySpec with MockitoSugar with Results{


  val mockedService = mock[EmployeeService]
  val employeeController= new EmployeeController(mockedService)

  "EmployeeController " should {

    "create a employee" in {
     when( mockedService.create("") ) thenReturn( Future.successful(""))
      val result: Future[ Result] = employeeController.create().apply(FakeRequest())
      val body = contentAsString(result)
      body mustBe "OK"
    }
  }

}
