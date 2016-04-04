package controllers

import com.google.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.api.libs.json._
import repo.EmployeeRepository
import utils.JsonHelper
import models.Emp

import scala.concurrent._

/**
  * Handles all requests related to employee
  */
class EmployeeController @Inject()(employeeRepo: EmployeeRepository) extends Controller with JsonHelper {

  private def recoverBlock: PartialFunction[Throwable, Result] = {
    case t: TimeoutException => Ok(Json.obj("status" -> "error", "message" -> "Oops, Something wrong!!!"))
  }

  /**
    * Handles request for getting all employee from the database
    */
  def list = Action.async {
    employeeRepo.getAll.map { empList =>
      Ok(write(("data" -> empList)))
    }.recover(recoverBlock)
  }

  /**
    * Handles request for creation of new employee
    */
  def create = Action(parse.json) { request =>
    val empResult = request.body.validate[Emp]
    empResult.fold(
      errors => {
        BadRequest(errorJsonMessage("Oops, Something wrong!!!"))
      },
      place => {
        Ok(successJsonMessage("Employee has been successfully added."))
      }
    )
  }

  /**
    * Handles request for deletion of existing employee by employee_id
    */
  def delete(empId: Int) = Action.async { request =>
    employeeRepo.delete(empId).map { result =>
      if (result > 0)
        Ok(successJsonMessage("Employee has been successfully deleted."))
      else
        Ok(errorJsonMessage("Oops, Something wrong!!!"))
    }.recover {
      recoverBlock
    }
  }

  /**
    * Handles request for get employee details for editing
    */
  def edit(empId: Int) = Action.async { request =>
    employeeRepo.getById(empId).map { emp =>
      Ok(Json.obj("empName" -> "OK", "empEmail" -> "s@gmail.com"))
    }.recover {
      recoverBlock
    }
  }

  /**
    * Handles request for update existing employee
    */
  def update = Action(parse.json) { request =>
    val empResult = request.body.validate[Emp]
    empResult.fold(
      errors => {
        BadRequest(errorJsonMessage("Oops, Something wrong!!!"))
      },
      place => {
        Ok(successJsonMessage("Employee has been successfully updated."))
      }
    )
  }

}

