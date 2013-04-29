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

  implicit val rds = (
    (__ \ "content").read[String]
  )

  def updateTodo(id: Long) = Action(parse.json) { request =>
    request.body.validate[String].map{
      case (content) => Ok(Json.toJson(Todo.update(id, Todo(Id(id), content))))
    }.recoverTotal{
      e => BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(e)))
    }
  }

  def createTodo = Action(parse.json) { request =>
    request.body.validate[String].map{
      case (content) => Ok(Json.toJson(Todo.create(Todo(NotAssigned, content))))
    }.recoverTotal{
      e => BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(e)))
    }
  }

  def deleteTodo(id: Long) = Action {
    (Todo.delete(id) == 1) match {
      case true => Ok("1")
      case _ => BadRequest(Json.obj("status" ->"KO", "message" -> "Record does not exist."))
    }
  }

}