package simulation

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
class Check_Response_Code extends Simulation {
  val httpConf = http.baseUrl("https://reqres.in/")
    .header("content-type", value = "application/json")
    .header("content", value = "application/json")

  //scenario conf
  val scn = scenario("firstAPIrequest")
    .exec(http("getUserAPI")
      .get("api/users?page=2")
      // Check if the response code is 200 (OK)
      .check(status is 200))
    .pause(3)

    .exec(http("getSingleUser")
      .get("api/users/2")
      // Check if the response code is in between 200 to 208
      .check(status in (200 to 208)))
    .pause(5)

  //setup load
  setUp(scn.inject(constantConcurrentUsers(5).during(60))).protocols(httpConf)
}