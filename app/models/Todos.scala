package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json._
import play.api.data.Form
import play.api.data.Forms._
import slick.driver.H2Driver.api._
import slick.driver.JdbcProfile
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class Todo(id: Option[Long] = None, content: String)

object Todo {
  implicit val todoFmt = Json.format[Todo]
}

object TodoForm {
  val form = Form(
    mapping(
      "id" -> optional(longNumber),
      "content" -> nonEmptyText
    )(Todo.apply)(Todo.unapply)
  )
}

class TodosTable(tag: Tag) extends Table[Todo](tag, "TODO") {
  def id = column[Option[Long]]("ID", O.PrimaryKey, O.AutoInc)
  def content = column[String]("CONTENT")
  def * = (id, content).shaped <> ((Todo.apply _).tupled, Todo.unapply _)
}

class Todos @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfig[JdbcProfile] {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  val todos = TableQuery[TodosTable]

  def all(): Future[Seq[Todo]] = db.run(todos.result)

  def create(todo: Todo): Future[Todo] = db.run(todos returning todos.map(_.id) += todo).map(id => todo.copy(id = id))

  def update(todo: Todo): Future[Todo] = db.run(todos.filter(_.id === todo.id).update(todo)).map(_ => todo)

  def findById(id: Long): Future[Option[Todo]] = db.run(todos.filter(_.id === id).result.headOption)

  def count(): Future[Int] = db.run(todos.length.result)

  def delete(id: Long): Future[Unit] = db.run(todos.filter(_.id === id).delete).map(_ => ())

}