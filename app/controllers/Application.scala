package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import models._
import views._

object Application extends Controller {

  def index = Action {
    Ok(html.index("Todos", Todo.all(), Todos.todoForm))
  }

  def jsontest = Action {
    Ok(Json.toJson(Todo.findById(1)))
  }

}