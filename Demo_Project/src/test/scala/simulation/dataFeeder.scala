package simulation

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class dataFeeder extends Simulation{

  val httpConf = http.baseUrl("https://reqres.in/")
    .header("content-type", value = "application/json")
    .header("content", value = "application/json")

  val datafeeder =(csv("./src/test/resources/data/username.csv").circular)

  val jsonTemplate = scala.io.Source.fromResource("payload.json").mkString

  val scn = scenario("testdata")
    .feed(csv("./src/test/resources/data/GetUserdata.csv").circular)
    .exec(http("getUserdetails")
      .get("api/users/${userid}")
      .check(jsonPath("$.data.first_name").is("${name}"))
      .check(status.in(200,304))
    )
    .feed(datafeeder)
    .exec(http("Create new user")
      .post("api/users")
      .body(RawFileBody("./src/test/resources/Payloads/AddUser.json")).asJson
      .check(status is 201)
    )
  setUp(scn.inject(atOnceUsers(2))).protocols(httpConf)
}
