package simulation
//first import the gatling built-in package
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Add_Pause_Time extends Simulation{

  val httpConf = http.baseUrl("https://reqres.in/")
    .header("content-type", value = "application/json")
    .header("content", value = "application/json")

  //scenario conf
  val scn = scenario("firstAPIrequest")
    .exec(http("getUserAPI")
      .get("api/users?page=2"))
    // Pause for 3 seconds after the first request
    .pause(3)

    .exec(http("getSingleUser")
      .get("api/users/2"))
    // Pause for 5 seconds after the second request
    .pause(5)

  //setup load
  setUp(scn.inject(constantConcurrentUsers(5).during(60))).protocols(httpConf)
}

