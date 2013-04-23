package controllers

import play.api._
import play.api.mvc._
import models._

import play.api.Play._

import anorm._
import play.api.libs.json._
import play.api.libs.json.Json._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Todos", Todo.all()))
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
}