import play.api._
import play.api.mvc._
import models._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

  object InitialData {
    def insert() = {
      if (Todos.count == 0) {
        Seq(
          Todo(None, "Hello todos.")
        ).foreach(Todos.create)
      }
    }
  }

}