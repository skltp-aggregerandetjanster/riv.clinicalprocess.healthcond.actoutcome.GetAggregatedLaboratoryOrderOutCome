package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.v4;

import lombok.extern.log4j.Log4j2;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Service;
import riv.clinicalprocess.healthcond.actoutcome._4.AccessControlHeaderType;
import riv.clinicalprocess.healthcond.actoutcome._4.CVType;
import riv.clinicalprocess.healthcond.actoutcome._4.ContactInformationType;
import riv.clinicalprocess.healthcond.actoutcome._4.HeaderType;
import riv.clinicalprocess.healthcond.actoutcome._4.IIType;
import riv.clinicalprocess.healthcond.actoutcome._4.LaboratoryOrderOutcomeBodyType;
import riv.clinicalprocess.healthcond.actoutcome._4.LaboratoryOrderOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome._4.OrgUnitType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeType;
import se.skltp.aggregatingservices.data.TestDataGenerator;

@Log4j2
@Service
public class ServiceTestDataGenerator extends TestDataGenerator {
	@Override
	public String getPatientId(MessageContentsList messageContentsList) {
		GetLaboratoryOrderOutcomeType request = (GetLaboratoryOrderOutcomeType) messageContentsList.get(1);
		return request.getPatientId().getExtension();
	}

	@Override
	public Object createResponse(Object... responseItems) {
		log.info("Creating a response with {} items", responseItems.length);
		GetLaboratoryOrderOutcomeResponseType response = new GetLaboratoryOrderOutcomeResponseType();
		for (int i = 0; i < responseItems.length; i++) {
			response.getLaboratoryOrderOutcome().add((LaboratoryOrderOutcomeType) responseItems[i]);
		}

		log.info("response.toString:" + response.toString());

		return response;
	}

	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {

		log.debug("Created LaboratoryOrderOutcomeType for logical-address {}, registeredResidentId {} and businessObjectId {}",
				new Object[]{logicalAddress, registeredResidentId, businessObjectId});

		LaboratoryOrderOutcomeType labOrderOutcome = new LaboratoryOrderOutcomeType();

		HeaderType header = new HeaderType();
		labOrderOutcome.setHeader(header);
		LaboratoryOrderOutcomeBodyType body = new LaboratoryOrderOutcomeBodyType();
		labOrderOutcome.setBody(body);

		header.setSourceSystemId(createIIType("1.2.3", logicalAddress));

		ContactInformationType contactinfo = new ContactInformationType();
		contactinfo.setText("Testv�gen 3, 12345 GLOO");
		body.setContactInformation(contactinfo);

		OrgUnitType orgUnit = new OrgUnitType();
		orgUnit.setName("Organisation 1");
		orgUnit.setId(createIIType(logicalAddress, "1.2.3"));
		body.setRecipientUnit(orgUnit);

		AccessControlHeaderType ac = new AccessControlHeaderType();
		header.setAccessControlHeader(ac);

		ac.setOriginalPatientId(createIIType("1.2.752.129.2.1.3.1", registeredResidentId));

		ac.setApprovedForPatient(true);

		//Body start
		body.setText("Test av GLOO4");

		return labOrderOutcome;
	}

	@Override
	public Object createRequest(String patientId, String sourceSystemHSAId) {
		GetLaboratoryOrderOutcomeType getLaboratoryOrderOutcomeType = new GetLaboratoryOrderOutcomeType();
		getLaboratoryOrderOutcomeType.setPatientId(createIIType("1.2.752.129.2.1.3.1", patientId));
		getLaboratoryOrderOutcomeType.setSourceSystemHSAId(createIIType("1.2.3", sourceSystemHSAId));
		return getLaboratoryOrderOutcomeType;
	}

	private IIType createIIType(String root, String extension) {
		IIType systemId = new IIType();
		systemId.setRoot(root);
		systemId.setExtension(extension);
		return systemId;
	}

}
