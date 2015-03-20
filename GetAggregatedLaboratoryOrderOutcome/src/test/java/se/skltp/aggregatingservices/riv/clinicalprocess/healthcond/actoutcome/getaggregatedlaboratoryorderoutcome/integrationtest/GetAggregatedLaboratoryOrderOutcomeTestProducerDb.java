package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedlaboratoryorderoutcome.integrationtest;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.ThreadSafeSimpleDateFormat;

import riv.clinicalprocess.healthcond.actoutcome.enums.v3.ResultCodeEnum;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v3.GetLaboratoryOrderOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.v3.HealthcareProfessionalType;
import riv.clinicalprocess.healthcond.actoutcome.v3.LaboratoryOrderOutcomeBodyType;
import riv.clinicalprocess.healthcond.actoutcome.v3.LaboratoryOrderOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome.v3.OrderType;
import riv.clinicalprocess.healthcond.actoutcome.v3.OrgUnitType;
import riv.clinicalprocess.healthcond.actoutcome.v3.PatientSummaryHeaderType;
import riv.clinicalprocess.healthcond.actoutcome.v3.PersonIdType;
import riv.clinicalprocess.healthcond.actoutcome.v3.ResultType;
import se.skltp.agp.test.producer.TestProducerDb;

public class GetAggregatedLaboratoryOrderOutcomeTestProducerDb extends TestProducerDb {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedLaboratoryOrderOutcomeTestProducerDb.class);
	private static final ThreadSafeSimpleDateFormat df = new ThreadSafeSimpleDateFormat("YYYYMMDDhhmmss");

	@Override
	public Object createResponse(Object... responseItems) {
		log.debug("Creates a response with {} items", responseItems);
		GetLaboratoryOrderOutcomeResponseType response = new GetLaboratoryOrderOutcomeResponseType();
		for (int i = 0; i < responseItems.length; i++) {
			response.getLaboratoryOrderOutcome().add((LaboratoryOrderOutcomeType)responseItems[i]);
		}

		ResultType result = new ResultType();
		result.setResultCode(ResultCodeEnum.OK);
		result.setLogId(UUID.randomUUID().toString());
		result.setMessage("Ett meddelande till användaren");
		response.setResult(result);

		return response;
	}

	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {

		if (log.isDebugEnabled()) {
			log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}",
				new Object[] {logicalAddress, registeredResidentId, businessObjectId});
		}

		// TODO: CHANGE GENERATED CODE - START

		//Header start
		LaboratoryOrderOutcomeType labOrderOutcome = new LaboratoryOrderOutcomeType();

		PatientSummaryHeaderType header = new PatientSummaryHeaderType();
		header.setDocumentId(UUID.randomUUID().toString());
		header.setSourceSystemHSAId(logicalAddress);
		header.setDocumentTime(df.format(new Date()));

		PersonIdType personIdType = new PersonIdType();
		personIdType.setId(registeredResidentId);
		personIdType.setType("1.2.752.129.2.1.3.1");
		header.setPatientId(personIdType);

		HealthcareProfessionalType hp = new HealthcareProfessionalType();
		hp.setAuthorTime(df.format(new Date()));
		hp.setHealthcareProfessionalHSAId(logicalAddress);

		OrgUnitType orgUnitType = new OrgUnitType();
		orgUnitType.setOrgUnitHSAId(logicalAddress);
		orgUnitType.setOrgUnitName("Organisation 1");
		hp.setHealthcareProfessionalOrgUnit(orgUnitType);
		header.setAccountableHealthcareProfessional(hp);

		header.setApprovedForPatient(true);

		labOrderOutcome.setLaboratoryOrderOutcomeHeader(header);
		//Header end

		//Body start
		LaboratoryOrderOutcomeBodyType body = new LaboratoryOrderOutcomeBodyType();
		body.setResultType("DEF");
		body.setRegistrationTime(df.format(new Date()));
		body.setDiscipline("Klinisk kemi");

		OrderType order = new OrderType();
		order.setOrderId(UUID.randomUUID().toString());
		body.setOrder(order);

		labOrderOutcome.setLaboratoryOrderOutcomeBody(body);
		//Body end

		//response.setCareUnit(logicalAddress);
		//response.setSubjectOfCareId(registeredResidentId);
		//response.setSenderRequestId(businessObjectId);

		// TODO: CHANGE GENERATED CODE - END

		return labOrderOutcome;
	}
}
