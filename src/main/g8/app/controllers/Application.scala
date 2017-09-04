package controllers

import javax.inject.Inject
import play.api.mvc._
import views.html

class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(html.index())
  }

}
