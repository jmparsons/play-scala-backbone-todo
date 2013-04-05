package models

import java.util.Date

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Todo(id: Pk[Long], title: String, content: String) {

}

object Todo {

  val simple = {
    get[Pk[Long]]("todo.id") ~
    get[String]("todo.title") ~
    get[String]("todo.content") map {
      case id ~ title ~ content => Todo(
        id, title, content
      )
    }
  }

  def create(todo: Todo) = {
    DB.withConnection { implicit c =>
      SQL(
        """
          insert into todo(title, content) values (
            {title}, {content}
          )
        """
      ).on(
        'title -> todo.title,
        'content -> todo.content
      ).executeUpdate()
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