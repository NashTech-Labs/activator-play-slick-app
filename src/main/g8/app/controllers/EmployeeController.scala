package controllers

import com.google.inject.Inject
import models.Employee
import play.api.Logger
import play.api.libs.json.Json._
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc._
import repo.EmployeeRepository
import utils.Constants
import utils.JsonFormat._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Handles all requests related to employee
  */
class EmployeeController @Inject()(cc: ControllerComponents,
                                   empRepository: EmployeeRepository)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  import Constants._

  val logger = Logger(this.getClass())

  /**
    * Handles request for getting all employee from the database
    */
  def list() = Action.async {
    empRepository.getAll().map { res =>
      Ok(successResponse(Json.toJson(res)))
    }
  }

  /**
    * Handles request for creation of new employee
    */
  def create() = Action.async(parse.json) { request =>
    request.body.validate[Employee].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { emp =>
      empRepository.insert(emp).map { createdEmpId =>
        Ok(successResponse(Json.toJson(Map("id" -> createdEmpId))))
      }
    })
  }

  /**
    * Handles request for deletion of existing employee by employee_id
    */
  def delete(empId: Int) = Action.async { request =>
    empRepository.delete(empId).map { _ =>
      Ok(successResponse(Json.toJson("{}")))
    }
  }

  /**
    * Handles request for get employee details for editing
    */
  def edit(empId: Int): Action[AnyContent] = Action.async { request =>
    empRepository.getById(empId).map { empOpt =>
      empOpt.fold(Ok(errorResponse(Json.toJson("{}"))))(emp => Ok(
        successResponse(Json.toJson(emp))))
    }
  }

  /**
    * Handles request for update existing employee
    */
  def update = Action.async(parse.json) { request =>
    logger.info("Employee Json ===> " + request.body)
    request.body.validate[Employee].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { emp =>
      empRepository.update(emp).map { res => Ok(successResponse(Json.toJson("{}"))) }
    })
  }

  private def successResponse(data: JsValue) = obj("status" -> SUCCESS, "data" -> data)
  private def errorResponse(data: JsValue) = obj("status" -> ERROR, "data" -> data)

}



