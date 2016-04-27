package controllers

import com.google.inject.Inject
import models.Employee
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import repo.EmployeeRepository

import utils.{LoggerApi, Constants, JsonHelper}

import scala.concurrent.Future



/**
  * Handles all requests related to employee
  */
class EmployeeController @Inject()(empRepository: EmployeeRepository) extends Controller with JsonHelper with LoggerApi {

  import Constants._

  /**
    * Handles request for getting all employee from the database
    */
  def list() = Action.async {
    empRepository.getAll().map { res => Ok(write(res)) }
  }

  /**
    * Handles request for creation of new employee
    */
  def create() = Action.async(jsonParser) { request =>
    info("Employee Json ===> " + request.body)
    request.body.fold(error =>Future.successful(error) , { emp =>
      empRepository.insert(emp).map { res => Response(status =SUCCESS, msg= "Employee has been added successfully.")  }
    }).map{res => Ok(write(res))}
  }

  /**
    * Handles request for deletion of existing employee by employee_id
    */
  def delete(empId: Int) = Action.async { request =>
    empRepository.delete(empId).map { _ =>
      Ok(write(Response(status=SUCCESS, msg="Employee has been deleted successfully."))) }
  }

  /**
    * Handles request for get employee details for editing
    */
  def edit(empId: Int): Action[AnyContent] = Action.async { request =>
    empRepository.getById(empId).map{ empOpt=>
      empOpt.fold(Response(status = ERROR, msg= "Employee does not exist."))(emp => Response(SUCCESS, emp, "Getting Employee successfully"))
    }.map{res => Ok(write(res))}
  }

  /**
    * Handles request for update existing employee
    */
  def update: Action[Either[Response, Employee]] = Action.async(jsonParser) { request =>
    info("Employee Json ===> " + request.body)
   val res: Future[Response] = request.body.fold(error =>Future.successful(error) , { emp =>

      empRepository.update(emp).map { res => Response(status =SUCCESS, msg= "Employee has been updated successfully.")  }
    })
     res.map{ r => Ok(write(r))}

  }


  val jsonParser: BodyParser[Either[Response, Employee]] = parse.tolerantText.map(text =>parser(text))

  def parser(request: String): Either[Response, Employee]  =
    (try
      Right(parse(request).extract[Employee])
    catch {
      case exception: Exception =>
        error(s"Error in  json parsing ", exception)
        Left(Response(ERROR, Map(), INVALID_REQUEST_JSON))
    })


}

case class Response(status: String, data: AnyRef="{}", msg: String)

