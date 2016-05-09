package controllers

import javax.inject.{Inject, Singleton}
import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.i18n.{I18nSupport, MessagesApi}
import models.{Todos, TodoForm}
import views._

@Singleton
class Application @Inject() (val messagesApi: MessagesApi, Todos: Todos) extends Controller with I18nSupport {

  def index = Action.async { implicit rs =>
    Todos.all().map { todos =>
      Ok(html.index("Todos", todos.toList, TodoForm.form))
    }
  }

}