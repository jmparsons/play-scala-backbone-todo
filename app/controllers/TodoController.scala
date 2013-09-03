package controllers

import play.api._
import play.api.mvc._
import play.api.Play._
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._
import play.api.db.slick._
import play.api.Play.current
import models._

object TodoController extends Controller {

  val todoForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "content" -> nonEmptyText
    )(Todo.apply)(Todo.unapply)
  )

  def index = DBAction { implicit rs =>
    Ok(Json.toJson(Todos.all()))
  }

  def todoList(id: Long) = DBAction { implicit rs =>
    Ok(Json.toJson(Todos.findById(id)))
  }

  //the reqest body is now in the RequestWithDbSession (rs)
  //the body value is not json in this case, it is AnyContentAsJson
  //to validate against it we need the actual json
  def createTodo = DBAction { implicit rs =>
    rs.request.body.asJson.get.validate[Todo].map {
      case (todo) => Ok(Json.toJson(Todos.findById(Todos.create(Todo(None, todo.content.trim)))))
    }.recoverTotal {
      e => BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(e)))
    }
  }

  //same method essentially as createTodo except we keep the json body parser
  //and instead use a DB.withSession call to slick with an implicit session
  //bound to the slick type reference for the Session
  def updateTodo(id: Long) = Action(parse.json) { request =>
    request.body.validate[Todo].map {
      case (todo) => DB.withSession { implicit s:play.api.db.slick.Session =>
        Ok(Json.toJson(Todos.update(id, Todo(todo.id, todo.content.trim))))
      }
    }.recoverTotal {
      e => BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(e)))
    }
  }

  def deleteTodo(id: Long) = DBAction { implicit rs =>
    (Todos.delete(id) == 1) match {
      case true => Ok(Json.obj("status" ->"OK", "message" -> ("Record deleted successfully.")))
      case _ => BadRequest(Json.obj("status" ->"KO", "message" -> "Record does not exist."))
    }
  }

}