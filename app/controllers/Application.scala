package controllers


import play.api.mvc._
import repo.EmployeeRepository
import com.google.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import utils.JsonHelper

import views.html

class Application @Inject() (employeeRepo: EmployeeRepository) extends Controller with JsonHelper{

  def index = Action {Ok(html.index())}



  def list = Action.async {
    employeeRepo.getAll.map { emps =>
      Ok(write(("data" ->emps)))
    }
  }


  def create=Action{ implicit request =>
    val body = request.body.asFormUrlEncoded
    println(body)
    Ok("")
  }

}
