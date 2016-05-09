package test

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import org.specs2.mutable.Specification
import play.api.Application
import play.api.test.WithApplicationLoader
import models._

object TodosSpec extends Specification {

  "Todos" should {
    "work" in new WithApplicationLoader {
      val app2dao = Application.instanceCache[Todos]
      val dao: Todos = app2dao(app)

      val testTodos = Set(
        Todo(Some(1), "hello"),
        Todo(Some(2), "todos")
      )

      Await.result(Future.sequence(testTodos.map(dao.create)), 1 seconds)
      val storedTodos = Await.result(dao.all(), 1 seconds)

      storedTodos.toSet must equalTo(testTodos)
    }
  }

}