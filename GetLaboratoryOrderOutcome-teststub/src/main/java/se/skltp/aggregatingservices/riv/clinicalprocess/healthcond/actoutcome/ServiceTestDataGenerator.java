package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome;

import lombok.extern.log4j.Log4j2;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Service;
import riv.clinicalprocess.healthcond.actoutcome._4.AccessControlHeaderType;
import riv.clinicalprocess.healthcond.actoutcome._4.ContactInformationType;
import riv.clinicalprocess.healthcond.actoutcome._4.HeaderType;
import riv.clinicalprocess.healthcond.actoutcome._4.IIType;
import riv.clinicalprocess.healthcond.actoutcome._4.LaboratoryOrderOutcomeBodyType;
import riv.clinicalprocess.healthcond.actoutcome._4.LaboratoryOrderOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome._4.OrgUnitType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeType;
import se.skltp.aggregatingservices.data.ProducerTestDataGenerator;


@Log4j2
@Service
public class ServiceTestDataGenerator extends ProducerTestDataGenerator {

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

    IIType systemId = new IIType();
    systemId.setRoot("1.2.3");
    systemId.setExtension(logicalAddress);
    header.setSourceSystemId(systemId);

    ContactInformationType contactinfo = new ContactInformationType();
    contactinfo.setText("Testv�gen 3, 12345 GLOO");
    body.setContactInformation(contactinfo);

    OrgUnitType orgUnit = new OrgUnitType();
    orgUnit.setName("Organisation 1");
    IIType id = new IIType();
    id.setRoot(logicalAddress);
    id.setExtension("1.2.3");
    orgUnit.setId(id);
    body.setRecipientUnit(orgUnit);

    AccessControlHeaderType ac = new AccessControlHeaderType();
    header.setAccessControlHeader(ac);

    IIType patient = new IIType();
    patient.setExtension(registeredResidentId);
    patient.setRoot("1.2.752.129.2.1.3.1");
    ac.setOriginalPatientId(patient);

    ac.setApprovedForPatient(true);

    //Body start
    body.setText("Test av GLOO");

    return labOrderOutcome;
  }
}
