package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome;

import java.util.ArrayList;
import java.util.List;
import riv.clinicalprocess.healthcond.actoutcome._4.IIType;
import riv.clinicalprocess.healthcond.actoutcome._4.LaboratoryOrderOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeType;


public class TestDataGenerator {
  static GetLaboratoryOrderOutcomeType generateRequest(String patientId, String sourceSystemHSAId) {
    GetLaboratoryOrderOutcomeType outcomeType = new GetLaboratoryOrderOutcomeType();

    IIType patient = new IIType();
    patient.setExtension(patientId);
    patient.setRoot("1.2.3");
    outcomeType.setPatientId(patient);

    IIType sourceSystem = new IIType();
    sourceSystem.setExtension(sourceSystemHSAId);
    sourceSystem.setRoot("1.2.3");
    outcomeType.setSourceSystemHSAId(sourceSystem);
    return outcomeType;

  }

  static List<GetLaboratoryOrderOutcomeResponseType> generateResponse(int responseNumber){
    List<GetLaboratoryOrderOutcomeResponseType> response = new ArrayList<GetLaboratoryOrderOutcomeResponseType>();
    int a = responseNumber/2;
    int b = responseNumber - a;


    GetLaboratoryOrderOutcomeResponseType responseTypeA = new GetLaboratoryOrderOutcomeResponseType();
    GetLaboratoryOrderOutcomeResponseType responseTypeB = new GetLaboratoryOrderOutcomeResponseType();

    for (int i = 0; i < a; i++) {
      LaboratoryOrderOutcomeType outcomeType = new LaboratoryOrderOutcomeType();
      responseTypeA.getLaboratoryOrderOutcome().add(outcomeType);
    }

    for (int i = 0; i < b; i++) {
      LaboratoryOrderOutcomeType outcomeType = new LaboratoryOrderOutcomeType();
      responseTypeB.getLaboratoryOrderOutcome().add(outcomeType);
    }

    response.add(responseTypeA);
    response.add(responseTypeB);
    return response;
  }



}
