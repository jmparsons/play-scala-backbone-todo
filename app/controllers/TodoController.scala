package controllers

import javax.inject.{Inject, Singleton}
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import models._

@Singleton
class TodoController @Inject() (Todos: Todos) extends Controller {

  def index = Action.async { implicit rs =>
    Todos.all().map(todos => Ok(Json.toJson(todos)))
  }

  def todoList(id: Long) = Action.async { implicit rs =>
    Todos.findById(id).map(todo => Ok(Json.toJson(todo)))
  }

  def createTodo = Action.async(parse.json) { implicit rs =>
    rs.body.validate[Todo].fold(
      errors => Future.successful(BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))),
      todo => Todos.create(Todo(None, todo.content.trim)).map(todo => Ok(Json.toJson(todo)))
    )
  }

  def updateTodo(id: Long) = Action.async(parse.json) { implicit rs =>
    rs.body.validate[Todo].fold(
      errors => Future.successful(BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))),
      todo => Todos.update(Todo(Some(id), todo.content.trim)).map(todo => Ok(Json.toJson(todo)))
    )
  }

  def deleteTodo(id: Long) = Action.async { implicit rs =>
    Todos.delete(id).map { result =>
      Ok(Json.obj("status" ->"OK", "message" -> ("Record deleted.")))
    }
  }

}