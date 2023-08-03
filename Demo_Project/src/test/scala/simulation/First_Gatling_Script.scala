package simulation

//first import the gatling built-in package
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class First_Gatling_Script extends Simulation{


  //http conf (Here we define http, baseurl and common Header

  val httpConf = http.baseUrl("https://reqres.in/")
    .header("content-type", value="application/json")
    .header("content", value= "application/json")

  //scenario conf (A scenario represents a sequence of user actions)

  val scn = scenario("firstAPIrequest")
    .exec(http("getUserAPI")
      .get("api/users?page=2")
      .check(status is 200))

  //load setup (specify the number of virtual users)

  //setUp(scn.inject(constantConcurrentUsers(5).during(60))).protocols(httpConf)
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
  //In gatling default time is in second.

}
