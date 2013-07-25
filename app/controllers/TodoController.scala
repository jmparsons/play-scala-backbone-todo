package controllers

import play.api._
import play.api.mvc._
import play.api.Play._
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._
import models._

object TodoController extends Controller {

  val todoForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "content" -> nonEmptyText
    )(Todo.apply)(Todo.unapply)
  )

  def index = Action {
    Ok(Json.toJson(Todos.all()))
  }

  def todoList(id: Long) = Action {
    Ok(Json.toJson(Todos.findById(id)))
  }

  def createTodo = Action(parse.json) { request =>
    request.body.validate[Todo].map {
      case (todo) => Ok(Json.toJson(Todos.findById(Todos.create(Todo(None, todo.content.trim)))))
    }.recoverTotal{
      e => BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(e)))
    }
  }

  def updateTodo(id: Long) = Action(parse.json) { request =>
    request.body.validate[Todo].map {
      case (todo) => Ok(Json.toJson(Todos.update(id, Todo(todo.id, todo.content.trim))))
    }.recoverTotal{
      e => BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(e)))
    }
  }

  def deleteTodo(id: Long) = Action {
    (Todos.delete(id) == 1) match {
      case true => Ok(Json.obj("status" ->"OK", "message" -> ("Record deleted successfully.")))
      case _ => BadRequest(Json.obj("status" ->"KO", "message" -> "Record does not exist."))
    }
  }

}