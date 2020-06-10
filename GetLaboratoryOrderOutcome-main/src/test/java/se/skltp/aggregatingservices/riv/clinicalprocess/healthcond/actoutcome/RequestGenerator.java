package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome;

import riv.clinicalprocess.healthcond.actoutcome._4.IIType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeType;

public class RequestGenerator {
  static GetLaboratoryOrderOutcomeType generateRequest(String patientId, String sourceSystemHSAId) {
    GetLaboratoryOrderOutcomeType outcomeType = new GetLaboratoryOrderOutcomeType();

    IIType patient = new IIType();
    patient.setExtension(patientId);
    patient.setRoot("1.2.3");
    outcomeType.setPatientId(patient);

    if (sourceSystemHSAId != null) {
      IIType sourceSystem = new IIType();
      sourceSystem.setExtension(sourceSystemHSAId);
      sourceSystem.setRoot("1.2.3");
      outcomeType.setSourceSystemHSAId(sourceSystem);
    }

    return outcomeType;
  }
}
