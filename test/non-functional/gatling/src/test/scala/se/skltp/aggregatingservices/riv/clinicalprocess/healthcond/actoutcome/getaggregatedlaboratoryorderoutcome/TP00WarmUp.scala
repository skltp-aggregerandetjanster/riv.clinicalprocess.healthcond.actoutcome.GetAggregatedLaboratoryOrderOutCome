package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedlaboratoryorderoutcome

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedlaboratoryorderoutcome.scenarios.GetAggregatedLaboratoryOrderOutcomeScenario

/**
 * Simple requests to warm up service.
 */
class TP00WarmUp extends Simulation {

  val baseURL           = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) System.getProperty("baseUrl") else "http://33.33.33.33:8084/GetAggregatedLaboratoryOrderOutcome/service/v3"

  val testDuration      = 30 seconds
  val minWaitDuration   =  2 seconds
  val maxWaitDuration   =  4 seconds
  val times:Int         =  1
  
  val httpProtocol = http.baseURL(baseURL).disableResponseChunksDiscarding

  val warmUp = scenario("warm up")
                 .repeat(times) {
//                 feed(csv("patients.csv").queue)
                   exec(session => {
                     session.set("status","200").set("patientid","121212121212").set("name","Tolvan Tolvansson").set("count","3")
                   })    
                   .exec(GetAggregatedLaboratoryOrderOutcomeScenario.request)
                   .pause(1 second)
                  }
                 
  setUp (warmUp.inject(atOnceUsers(1)).protocols(httpProtocol))
}