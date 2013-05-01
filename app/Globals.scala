import play.api._
import play.api.mvc._
import models._
import java.util.Date
import org.joda.time.DateTime
import anorm._
import anorm.SqlParser._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

  object InitialData {
    def insert() = {
      if (Todo.all.isEmpty) {
        // Seq(
        //   Todo(Id(1), "Hello todos.")
        // ).foreach(Todo.create)
      }
    }
  }

}