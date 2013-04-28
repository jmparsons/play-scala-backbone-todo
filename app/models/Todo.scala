package models

import java.util.Date
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Todo(id: Pk[Long], title: String, content: String)

object Todo {

  implicit object PkFormat extends Format[Pk[Long]] {
    def reads(json: JsValue):JsResult[Pk[Long]] = JsSuccess(Id(json.as[Long]))
    def writes(id: Pk[Long]):JsNumber = JsNumber(id.get)
  }

  implicit val todoReads = Json.reads[Todo]

  implicit val todoWrites = Json.writes[Todo]

  val simple = {
    get[Pk[Long]]("todo.id") ~
    get[String]("todo.title") ~
    get[String]("todo.content") map {
      case id ~ title ~ content => Todo(
        id, title, content
      )
    }
  }

  def create(todo: Todo): Todo = {
    DB.withConnection { implicit c =>

      val id: Long = todo.id.getOrElse {
        SQL("select next value for todo_id_seq").as(scalar[Long].single)
      }

      SQL(
        """
          insert into todo values (
            {id}, {title}, {content}
          )
        """
      ).on(
        'id -> id,
        'title -> todo.title,
        'content -> todo.content
      ).executeUpdate()

      todo.copy(id = Id(id))
    }
  }

  def update(id: Long, todo: Todo) = {
    DB.withConnection { implicit c =>
      SQL(
        """
          update todo
          set title = {title}, content = {content}
          where id = {id}
        """
      ).on(
        'id -> id,
        'title -> todo.title,
        'content -> todo.content
      ).executeUpdate()
    }
  }

  def delete(id: Long) = {
    DB.withConnection { implicit c =>
      SQL(
        """
          delete from todo
          where id = {id}
        """
      ).on(
        'id -> id
      ).executeUpdate()
    }
  }

  def findById(id: Long): Option[Todo] = {
    DB.withConnection { implicit c =>
      SQL("select * from todo where id = {id}").on(
        'id -> id
      ).as(Todo.simple.singleOpt)
    }
  }

  def count() = {
    DB.withConnection { implicit connection =>
      SQL("select count(*) from todo").as(scalar[Long].single)
    }
  }

  def all(): List[Todo] = {
    DB.withConnection { implicit connection =>
      SQL("select * from todo").as(Todo.simple *)
    }
  }

}