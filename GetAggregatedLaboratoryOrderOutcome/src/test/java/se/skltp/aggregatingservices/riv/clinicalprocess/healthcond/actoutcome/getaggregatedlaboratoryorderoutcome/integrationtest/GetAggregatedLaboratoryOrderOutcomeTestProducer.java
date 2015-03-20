package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedlaboratoryorderoutcome.integrationtest;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcome.v3.rivtabp21.GetLaboratoryOrderOutcomeResponderInterface;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v3.GetLaboratoryOrderOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v3.GetLaboratoryOrderOutcomeType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetLaboratoryOrderOutcomeResponderService", portName = "GetLaboratoryOrderOutcomeResponderPort", targetNamespace = "urn:riv:clinicalprocess:healthcond:actoutcome:GetLaboratoryOrderOutcome:3:rivtabp21", name = "GetLaboratoryOrderOutcomeInteraction")
public class GetAggregatedLaboratoryOrderOutcomeTestProducer implements GetLaboratoryOrderOutcomeResponderInterface {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedLaboratoryOrderOutcomeTestProducer.class);

	private TestProducerDb testDb;
	public void setTestDb(TestProducerDb testDb) {
		this.testDb = testDb;
	}

	@Override
	public GetLaboratoryOrderOutcomeResponseType getLaboratoryOrderOutcome(String logicalAddress, GetLaboratoryOrderOutcomeType request) {
		log.info("### Virtual service for GetLaboratoryOrderOutcome call the source system with logical address: {} and patientId: {}", logicalAddress, request.getPatientId().getId());

		GetLaboratoryOrderOutcomeResponseType response = (GetLaboratoryOrderOutcomeResponseType)testDb.processRequest(logicalAddress, request.getPatientId().getId());
        if (response == null) {
        	// Return an empty response object instead of null if nothing is found
        	response = new GetLaboratoryOrderOutcomeResponseType();
        }

        log.info("### Virtual service got {} lab responses in the reply from the source system with logical address: {} and patientId: {}", new Object[] {response.getLaboratoryOrderOutcome().size(), logicalAddress, request.getPatientId().getId()});

		return response;
	}
}
