package controllers

import play.api._
import play.api.mvc._
import play.api.db.slick._
import play.api.Play.current
import models._
import views._

object Application extends Controller {

  def index = DBAction { implicit rs =>
    Ok(html.index("Todos", Todos.all(), TodoController.todoForm))
  }

}