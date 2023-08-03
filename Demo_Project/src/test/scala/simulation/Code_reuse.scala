package simulation
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Code_reuse extends Simulation {

  val httpConf = http.baseUrl("https://reqres.in/")
    .header("content-type", value = "application/json")
    .header("content", value = "application/json")

  //Here we are defining the methods

  def allUserDeatails() = {
    exec(http("Get all video games - 1st call")
      .get("api/users?page=2")
      .check(status.is(200)))
  }

  def singleUserDeatils() = {
    exec(http("Get specific game")
      .get("api/users/2")
      .check(status.in(200 to 210)))
  }

  val scn = scenario("Code reuse")

    //Here we can reuse the method

    .exec(allUserDeatails())
    .pause(5)

    .exec(singleUserDeatils())
    .pause(5)
  //here we can reuse first scenario again.
    .exec(allUserDeatails())

  //setup load
  setUp(scn.inject(constantConcurrentUsers(5).during(60))).protocols(httpConf)

}
