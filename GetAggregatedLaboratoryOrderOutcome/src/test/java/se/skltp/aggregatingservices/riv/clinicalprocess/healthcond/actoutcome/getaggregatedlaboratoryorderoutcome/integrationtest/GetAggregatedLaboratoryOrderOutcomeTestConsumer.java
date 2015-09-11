package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedlaboratoryorderoutcome.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcome.v3.rivtabp21.GetLaboratoryOrderOutcomeResponderInterface;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v3.GetLaboratoryOrderOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v3.GetLaboratoryOrderOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome.v3.PersonIdType;
import se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedlaboratoryorderoutcome.GetAggregatedLaboratoryOrderOutcomeMuleServer;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;

public class GetAggregatedLaboratoryOrderOutcomeTestConsumer extends AbstractTestConsumer<GetLaboratoryOrderOutcomeResponderInterface> {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedLaboratoryOrderOutcomeTestConsumer.class);

	public static void main(String[] args) {
		String serviceAddress = GetAggregatedLaboratoryOrderOutcomeMuleServer.getAddress("SERVICE_INBOUND_URL");
		String personnummer = TEST_RR_ID_ONE_HIT;

		GetAggregatedLaboratoryOrderOutcomeTestConsumer consumer = new GetAggregatedLaboratoryOrderOutcomeTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID, SAMPLE_CORRELATION_ID);
		Holder<GetLaboratoryOrderOutcomeResponseType> responseHolder = new Holder<GetLaboratoryOrderOutcomeResponseType>();
		Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();

		consumer.callService("logical-adress", personnummer, processingStatusHolder, responseHolder);
		log.info("Returned #timeslots = " + responseHolder.value.getLaboratoryOrderOutcome().size());
	}

	public GetAggregatedLaboratoryOrderOutcomeTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId, String correlationId) {

		// Setup a web service proxy for communication using HTTPS with Mutual Authentication
		super(GetLaboratoryOrderOutcomeResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId, correlationId);
	}

	public void callService(String logicalAddress, String registeredResidentId, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetLaboratoryOrderOutcomeResponseType> responseHolder) {

		log.debug("Calling GetRequestActivities-soap-service with registered resident id = {}", registeredResidentId);

		GetLaboratoryOrderOutcomeType request = new GetLaboratoryOrderOutcomeType();

		PersonIdType personIdType = new PersonIdType();
		personIdType.setId(registeredResidentId);
		personIdType.setType("1.2.752.129.2.1.3.1");
		request.setPatientId(personIdType);

		GetLaboratoryOrderOutcomeResponseType response = _service.getLaboratoryOrderOutcome(logicalAddress, request);
		responseHolder.value = response;

		processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
	}
}
