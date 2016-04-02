package controllers

import com.google.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.api.libs.json._
import repo.EmployeeRepository
import utils.JsonHelper
import models.Emp

/**
  * Handles all requests related to employee
  */
class EmployeeController @Inject()(employeeRepo: EmployeeRepository) extends Controller with JsonHelper {

  /**
    * Handles request for getting all employee from the database
    */
  def list = Action.async {
    employeeRepo.getAll.map { empList =>
      Ok(write(("data" -> empList)))
    }
  }

  /**
    * Handles request for creation of new employee
    */
  def create = Action(parse.json) { request =>
    val empResult = request.body.validate[Emp]
    empResult.fold(
      errors => {
        BadRequest(Json.obj("status" ->"error"))
      },
      place => {
        Ok(Json.obj("status" ->"success", "message" -> "Employee has been successfully added."))
      }
    )
  }

  /**
    * Handles request for deletion of existing employee by employee_id
    */
  def delete(empId: Int) = Action { request =>
    println(s"Deleting employee: id = $empId")
    Ok(Json.obj("status" ->"Employee has been successfully deleted."))
  }

  /**
    * Handles request for get employee details for editing
    */
  def edit(empId: Int) = Action { request =>
    println(s"Editing employee: id = $empId")
    Ok(Json.obj("empName" -> "OK", "empEmail" -> "s@gmail.com"))
  }

}

