package simulation

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class JsonData_feeder extends Simulation{
  //http conf
  val httpConf = http.baseUrl("https://reqres.in/")
    .header("content-type", value="application/json")
    .header("content", value= "application/json")

  //scenario conf
  val scn = scenario("firstAPIrequest")
    .exec(http("Create new user")
      .post("api/users")
      .body(RawFileBody("./src/test/resources/Payloads/AddUser.json")).asJson
      .check(status is 201)
    )
    .pause(7)
  //setup load
  setUp(scn.inject(constantConcurrentUsers(5).during(60))).protocols(httpConf)
}