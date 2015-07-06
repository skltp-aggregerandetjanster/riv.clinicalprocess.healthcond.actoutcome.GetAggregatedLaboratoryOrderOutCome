package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedlaboratoryorderoutcome

trait CommonParameters {
  val serviceName:String     = "LaboratoryOrderOutcome"
  val urn:String             = "urn:riv:clinicalprocess:healthcond:actoutcome:GetLaboratoryOrderOutcomeResponder:3"
  val responseElement:String = "GetLaboratoryOrderOutcomeResponse"
  val responseItem:String    = "laboratoryOrderOutcome"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl")
                               } else {
                                   "http://33.33.33.33:8081/GetAggregatedLaboratoryOrderOutcome/service/v3"
                               }
}
