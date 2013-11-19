package models

import play.api._
import play.api.Play.current
import play.api.libs.json._
import play.api.db.slick.Config.driver.simple._

private[models] trait DAO extends TodosComponent {
  val Todos = new Todos
}

case class Todo(id: Option[Long] = None, content: String)

object Todo {
  implicit val todoFmt = Json.format[Todo]
}

trait TodosComponent {
  val Todos: Todos

  class Todos extends Table[Todo]("TODO") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def content = column[String]("content", O.DBType("TEXT"))
    def * = id.? ~ content <>(Todo.apply _, Todo.unapply _)
    def autoInc = * returning id
    val byId = createFinderBy(_.id)
  }
}

object Todos extends DAO {

  def create(todo: Todo)(implicit s:Session) = {
    Todos.autoInc.insert(todo)
  }

  def update(id: Long, todo: Todo)(implicit s:Session) = {
    val todoToUpdate: Todo = todo.copy(Some(id))
    Todos.where(_.id === id).update(todoToUpdate)
  }

  def findById(id: Long)(implicit s:Session): Option[Todo] = {
    Todos.byId(id).firstOption
  }

  def all()(implicit s:Session): List[Todo] = {
    Query(Todos).sortBy(_.id).list
  }

  def count(implicit s:Session): Int = {
    Query(Todos.length).first
  }

  def delete(id: Long)(implicit s:Session) = {
    Todos.where(_.id === id).delete
  }

}