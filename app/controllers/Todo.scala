package controllers

import play.api._
import play.api.mvc._
import play.api.Play._
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._
import anorm._
import models._
import views._

object Todos extends Controller {

  val todoForm = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "content" -> nonEmptyText
    )(Todo.apply)(Todo.unapply)
  )

  def index = Action {
    Ok(Json.toJson(Todo.all()))
  }

  def todoList(id: Long) = Action {
    Ok(Json.toJson(Todo.findById(id)))
  }

  def createTodo = Action(parse.json) { request =>
    request.body.validate[Todo].map {
      case (todo) => Ok(Json.toJson(Todo.create(Todo(NotAssigned, todo.content.trim))))
    }.recoverTotal{
      e => BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(e)))
    }
  }

  def updateTodo(id: Long) = Action(parse.json) { request =>
    request.body.validate[Todo].map {
      case (todo) => Ok(Json.toJson(Todo.update(id, Todo(Id(todo.id.get), todo.content.trim))))
    }.recoverTotal{
      e => BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(e)))
    }
  }

  def deleteTodo(id: Long) = Action {
    (Todo.delete(id) == 1) match {
      case true => Ok(Json.obj("status" ->"OK", "message" -> ("Record deleted successfully.")))
      case _ => BadRequest(Json.obj("status" ->"KO", "message" -> "Record does not exist."))
    }
  }

}