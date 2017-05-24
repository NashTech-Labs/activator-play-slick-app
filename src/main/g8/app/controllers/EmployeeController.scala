package controllers

import com.google.inject.Inject
import models.Employee
import play.api.Logger
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json._
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc._
import repo.EmployeeRepository
import utils.Constants
import utils.JsonFormat._

import scala.concurrent.Future

/**
  * Handles all requests related to employee
  */
class EmployeeController @Inject()(empRepository: EmployeeRepository, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  import Constants._

  val logger = Logger(this.getClass())

  /**
    * Handles request for getting all employee from the database
    */
  def list() = Action.async {
    empRepository.getAll().map { res =>
      logger.info("Emp list: " + res)
      Ok(successResponse(Json.toJson(res), Messages("emp.success.empList")))
    }
  }

  /**
    * Handles request for creation of new employee
    */
  def create() = Action.async(parse.json) { request =>
    logger.info("Employee Json ===> " + request.body)
    request.body.validate[Employee].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { emp =>
      empRepository.insert(emp).map { createdEmpId =>
        Ok(successResponse(Json.toJson(Map("id" -> createdEmpId)), Messages("emp.success.created")))
      }
    })
  }

  /**
    * Handles request for deletion of existing employee by employee_id
    */
  def delete(empId: Int) = Action.async { request =>
    empRepository.delete(empId).map { _ =>
      Ok(successResponse(Json.toJson("{}"), Messages("emp.success.deleted")))
    }
  }

  /**
    * Handles request for get employee details for editing
    */
  def edit(empId: Int): Action[AnyContent] = Action.async { request =>
    empRepository.getById(empId).map { empOpt =>
      empOpt.fold(Ok(errorResponse(Json.toJson("{}"), Messages("emp.error.empNotExist"))))(emp => Ok(
        successResponse(Json.toJson(emp), Messages("emp.success.employee"))))
    }
  }

  private def errorResponse(data: JsValue, message: String) = {
    obj("status" -> ERROR, "data" -> data, "msg" -> message)
  }

  /**
    * Handles request for update existing employee
    */
  def update = Action.async(parse.json) { request =>
    logger.info("Employee Json ===> " + request.body)
    request.body.validate[Employee].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { emp =>
      empRepository.update(emp).map { res => Ok(successResponse(Json.toJson("{}"), Messages("emp.success.updated"))) }
    })
  }

  private def successResponse(data: JsValue, message: String) = {
    obj("status" -> SUCCESS, "data" -> data, "msg" -> message)
  }

}



