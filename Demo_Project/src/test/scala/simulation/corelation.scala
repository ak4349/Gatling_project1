package simulation

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class corelation extends Simulation{

  // http conf
  val httpConf =http.baseUrl("https://reqres.in/")
    .header("content-type", value = "application/json")
    .header("content", value = "application/json")


  //scenario conf
  val scn = scenario("firstAPIrequest")
    .exec(http("getUserAPI")
      .get("api/users?page=2")
      .check(status is 200)
      //by using jsonPath exp we can save id in userid variable
      .check(jsonPath("$.data[1].id").saveAs("userid"))
    )
    .pause(3)

    .exec(http("getUserDetailsAPI")
      //here we are using the userid variable in next request
      .get("api/users/${userid}")
      .check(status is 200)
      .check(jsonPath("$.data.id").is("8"))
      .check(jsonPath("$.data.first_name").is("Lindsay"))
    )
    .pause(5)
  //setup
  setUp(scn.inject(atOnceUsers(5))).protocols(httpConf)

}
