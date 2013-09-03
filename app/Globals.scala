import play.api._
import play.api.db.slick._
import play.api.Play.current
import models._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

  object InitialData {
    def insert() = {
      DB.withSession { implicit s:Session =>
        println(s)
        if (Todos.count == 0) {
          Seq(
            Todo(None, "Hello todos.")
          ).foreach(Todos.create)
        }
      }
    }
  }

}