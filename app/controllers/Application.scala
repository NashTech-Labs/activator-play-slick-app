package controllers

import play.api._
import play.api.mvc._
import repo.EmployeeRepository
import com.google.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import models.Employee
import java.util.Date

class Application @Inject() (employeeRepo: EmployeeRepository) extends Controller {

  def index = Action.async {
    employeeRepo.getAll.map { emps => Ok(emps.map(_.name).toString) }

  }

  def list = Action {
    Ok("")
  }

}
