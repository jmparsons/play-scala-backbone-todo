package models

import play.api._
import play.api.db._
import play.api.Play.current
import play.api.libs.json._
import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._

case class Todo(id: Option[Long] = None, content: String)

object Todo {
  implicit val todoFmt = Json.format[Todo]
}

object Todos extends Table[Todo]("TODO") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def content = column[String]("content")

  def * = id.? ~ content <>(Todo.apply _, Todo.unapply _)
  def autoInc = * returning id

  val byId = createFinderBy(_.id)

  def create(todo: Todo) = DB.withSession { implicit session =>
    Todos.autoInc.insert(todo)
  }

  def update(id: Long, todo: Todo) = DB.withSession { implicit session =>
    val todoToUpdate: Todo = todo.copy(Some(id))
    Todos.where(_.id === id).update(todoToUpdate)
  }

  def findById(id: Long): Option[Todo] = DB.withSession { implicit session =>
    Todos.byId(id).firstOption
  }

  def all(): List[Todo] = DB.withSession { implicit session =>
    (for(m <- Todos) yield m).sortBy(_.id).list
  }

  def count: Int = DB.withSession { implicit session =>
    Query(Todos.length).first
  }

  def delete(id: Long) = DB.withSession { implicit session =>
    Todos.where(_.id === id).delete
  }

}