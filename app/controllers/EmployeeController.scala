package controllers

import com.google.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

import service.{Response, EmployeeService}
import utils.{LoggerApi, Constants, JsonHelper}


/**
  * Handles all requests related to employee
  */
class EmployeeController @Inject()(service: EmployeeService) extends Controller with JsonHelper with LoggerApi{

  import Constants._

  private def recoverBlock: PartialFunction[Throwable, Result] = {
    case t: Exception => Ok(write(Response(ERROR, Map(), "Oops, Something wrong!!!")))
  }

  /**
    * Handles request for getting all employee from the database
    */
  def list = Action.async {
    service.getAll().map { res => Ok(res) }.recover(recoverBlock)
  }

  /**
    * Handles request for creation of new employee
    */
  def create = Action.async { request =>
    val json = request.body.asJson.toString
    info("Employee Json  " + json)
    service.create(json).map { res => Ok(res) }.recover(recoverBlock)
  }

  /**
    * Handles request for deletion of existing employee by employee_id
    */
  def delete(empId: Int) = Action.async { request =>
    service.delete(empId).map { res => Ok(res) }.recover {
      recoverBlock
    }
  }

  /**
    * Handles request for get employee details for editing
    */
  def edit(empId: Int) = Action.async { request =>
    service.getById(empId).map { res => Ok(res) }.recover {
      recoverBlock
    }
  }

  /**
    * Handles request for update existing employee
    */
  def update = Action.async { request =>
    val json = request.body.asJson.toString
    service.update(json).map { res => Ok(res) }.recover(recoverBlock)
  }


}

