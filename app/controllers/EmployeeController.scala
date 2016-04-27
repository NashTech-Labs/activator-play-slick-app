package controllers

import com.google.inject.Inject
import models.Employee
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsError
import play.api.libs.json.Json._

import play.api.mvc._
import repo.EmployeeRepository
import utils.Constants
import utils.JsonFormat._

import scala.concurrent.Future

/**
  * Handles all requests related to employee
  */
class EmployeeController @Inject()(empRepository: EmployeeRepository) extends Controller  {

  import Constants._

  val logger = Logger(this.getClass())
  /**
    * Handles request for getting all employee from the database
    */
  def list() = Action.async {

    empRepository.getAll().map { res =>
      logger.debug("Emp list " + res)
      Ok(obj("status" -> SUCCESS, "data" ->res,"msg"-> "Getting Employee list successfully"))
    }
  }

  /**
    * Handles request for creation of new employee
    */
  def create() = Action.async(parse.json) { request =>
    logger.info("Employee Json ===> " + request.body)
    request.body.validate[Employee].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { emp =>
      empRepository.insert(emp).map { res =>
        Ok(obj("status" -> SUCCESS, "data"-> "",  "msg"-> "Employee has been added successfully."))
      }
    })
  }

  /**
    * Handles request for deletion of existing employee by employee_id
    */
  def delete(empId: Int) = Action.async { request =>
    empRepository.delete(empId).map { _ =>
      Ok(obj("status" ->SUCCESS, "data" ->"", "msg" -> "Employee has been deleted successfully."))
    }
  }

  /**
    * Handles request for get employee details for editing
    */
  def edit(empId: Int): Action[AnyContent] = Action.async { request =>
    empRepository.getById(empId).map { empOpt =>
      empOpt.fold(Ok(obj("status" -> ERROR, "data"-> "", "msg" ->"Employee does not exist.")))(emp =>Ok(
        obj("status" ->SUCCESS, "data" ->emp, "msg" ->"Got Employee successfully")))
    }
  }

  /**
    * Handles request for update existing employee
    */
  def update = Action.async(parse.json) { request =>
    logger.info("Employee Json ===> " + request.body)
    request.body.validate[Employee].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { emp =>
      empRepository.update(emp).map { res => Ok(obj("status" ->SUCCESS, "data" ->"", "msg" -> "Employee has been updated successfully.")) }
    })
  }

}



