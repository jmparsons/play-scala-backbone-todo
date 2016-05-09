package test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.openqa.selenium._
import org.openqa.selenium.firefox._
import java.util.concurrent.TimeUnit._

object IntegrationSpec extends Specification {

  "Application" should {

    "work in a browser" in {
      val port = 3333
      running(TestServer(port), classOf[FirefoxDriver]) { browser =>
        browser.goTo("http://localhost:" + port)
        browser.$("h1").first.getText must equalTo("Todos")
        browser.executeScript("$('#content').val('Hello todos.')")
        browser.submit(browser.$("form"))
        browser.await().atMost(5, SECONDS).until("span.edit").isPresent
        browser.$("span.edit").first.getText must equalTo("Hello todos.")
        browser.executeScript("$('a.delete').click()")
        browser.await().atMost(5, SECONDS).until("span.edit").isNotPresent;
        browser.executeScript("$('#content').val('bar')")
        browser.submit(browser.$("form"))
        browser.await().atMost(5, SECONDS).until("span.edit").isPresent
        browser.$("span.edit").first.getText must equalTo("bar")
      }
    }
  }
}