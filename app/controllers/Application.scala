package controllers

import play.api._
import play.api.mvc._
import models._

import play.api.Play._

import anorm._
import play.api.libs.json._
import play.api.libs.json.Json._

import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  val todoForm = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "title" -> nonEmptyText,
      "content" -> nonEmptyText
    )(Todo.apply)(Todo.unapply)
  )
  
  def index = Action {
    Ok(views.html.index("Todos", Todo.all(), todoForm))
  }

  def todos = Action {
    Ok(
      Json.toJson(
        Todo.all().map { t =>
          Map(
            "id" -> t.id.toString,
            "title" -> t.title,
            "content" -> t.content
          )
        }
      )
    )
  }
  
  def todoList(id: Long) = Action {
    Ok(
      Json.toJson(
        Todo.findById(id).map { t =>
          Map(
            "id" -> t.id.toString,
            "title" -> t.title,
            "content" -> t.content
          )
        }
      )
    )
  }

  def createTodo = Action { implicit request =>
    todoForm.bindFromRequest.fold(
      errors => {
        BadRequest
      },
      values => {
        Todo.create(Todo(NotAssigned, values.title, values.content));
        Ok
      }
    )
  }

  def javascriptRoutes = Action { implicit request =>
    import routes.javascript._
    Ok(
      Routes.javascriptRouter("jsRoutes")(
        
      )
    ).as("text/javascript")
  }
}