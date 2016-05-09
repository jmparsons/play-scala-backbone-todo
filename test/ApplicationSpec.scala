package test

import play.api.test.FakeRequest
import play.api.test.PlaySpecification
import play.api.test.WithApplication

class ApplicationSpec extends PlaySpecification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication {
      route(app, FakeRequest(GET, "/boum")) must beSome.which (status(_) == NOT_FOUND)
    }

    "render the index page" in new WithApplication {
      val home = route(app, FakeRequest(GET, "/")).get
      status(home) mustEqual OK
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain ("Todos")
    }
  }
}