package service

import com.google.inject.Inject
import models.Employee
import repo.EmployeeRepository
import utils.{LoggerApi, JsonHelper,Constants}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future


class EmployeeService @Inject()(repo: EmployeeRepository) extends JsonHelper with LoggerApi {

  import Constants._

  def getAll(): Future[String] =
    repo.getAll().map { employees => write(Response(SUCCESS, Map("employees" -> employees), "Getting Employee List successfully.")) }


  def create(json: String): Future[String] =
    parser(json) {
      emp => repo.insert(emp).map { _ => Response(SUCCESS, Map(), "Employee has been added successfully.") }
    }

  def update(json: String): Future[String] =
    parser(json) {
      emp => repo.update(emp).map { _ => Response(SUCCESS, Map(), "Employee has been updated successfully.") }
    }


  def delete(id: Int): Future[String] =
    repo.delete(id).map { _ => Response(SUCCESS, Map(), "Employee has been updated successfully.") }.map(write)


  def getById(id: Int) = {
    repo.getById(id).map { emp =>
      emp.fold(Response(ERROR, Map(), "Employee does not exist."))(emp => Response(SUCCESS, Map("employee" -> emp), "Getting Employee successfully"))
    }.map(write)

  }

  private def parser(request: String)(fun: Employee => Future[Response]): Future[String] =
    (try
      Right(parse(request).extract[Employee])
    catch {
      case exception: Exception =>
        error(s"Error in  json parsing ", exception)
        Left(Response(ERROR, Map(), INVALID_REQUEST_JSON))
    }).fold(error => Future.successful(error), emp => fun(emp)).map {
      write
    }

}

case class Response(status: String, data: Map[String, Any], msg: String)
