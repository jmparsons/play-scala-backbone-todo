package models

import play.api._
import play.api.Play.current
import play.api.libs.json._
import scala.slick.lifted.Tag
import play.api.db.slick.Config.driver.simple._

case class Todo(id: Option[Long] = None, content: String)

object Todo {
  implicit val todoFmt = Json.format[Todo]
}

class Todos(tag: Tag) extends Table[Todo](tag, "TODO") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def content = column[String]("content", O.DBType("TEXT"))
  def * = (id.?, content) <>((Todo.apply _).tupled, Todo.unapply _)
}

object Todos {

  val todos = TableQuery[Todos]

  def create(todo: Todo)(implicit s:Session) = {
    todos returning todos.map(_.id) += todo
  }

  def update(id: Long, todo: Todo)(implicit s:Session) = {
    val todoToUpdate: Todo = todo.copy(Some(id))
    todos.where(_.id === id).update(todoToUpdate)
  }

  def findById(id: Long)(implicit s:Session): Option[Todo] = {
    todos.filter(_.id === id).firstOption
  }

  def all()(implicit s:Session): List[Todo] = {
    todos.sortBy(_.id).list
  }

  def count(implicit s:Session): Int = {
    Query(todos.length).first
  }

  def delete(id: Long)(implicit s:Session) = {
    todos.where(_.id === id).delete
  }

}