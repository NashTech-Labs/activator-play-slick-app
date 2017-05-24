package controllers

import play.api.mvc._
import views.html

class Application extends Controller {

  def index = Action {
    Ok(html.index())
  }

}
