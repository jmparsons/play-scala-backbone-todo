import play.api._
import play.api.mvc._
import models._
import java.util.Date
import org.joda.time.DateTime
import anorm._
import anorm.SqlParser._

object Global extends GlobalSettings {

  override def onRouteRequest(request: RequestHeader): Option[Handler] = {
     // println("executed before every request:" + request.toString)
     super.onRouteRequest(request)
  }

  override def onStart(app: Application) {
    InitialData.insert()
  }

  object InitialData {

    def insert() = {

      if (Todo.all.isEmpty) {
        Seq(
          Todo(Id(1), "Test 1", "Hello world."),
          Todo(Id(2), "Test 2", "How are you?"),
          Todo(Id(3), "Test 3", "This is cool.")
        ).foreach(Todo.create)
      }

    }
  }

}