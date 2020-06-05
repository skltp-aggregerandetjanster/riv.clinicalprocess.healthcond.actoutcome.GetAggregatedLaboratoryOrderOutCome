package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import riv.clinicalprocess.healthcond.actoutcome._4.LaboratoryOrderOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;

@RunWith(CamelSpringRunner.class)
@SpringBootTest
public class GLAOOAgpServiceFactoryTest {

  private static String patientId = "patientId";
  private static String  sourceSystemHSAId = "sourceSystemHSAId";
  private static int responses = 7;

  private static AgServiceFactoryBase<GetLaboratoryOrderOutcomeType, GetLaboratoryOrderOutcomeResponseType> agpServiceFactory;
  private static GetLaboratoryOrderOutcomeType request;
  private static List<GetLaboratoryOrderOutcomeResponseType> response ;

  @BeforeClass
  public static void before() {
    agpServiceFactory = new GLAOOAgpServiceFactoryImpl();
    request = TestDataGenerator.generateRequest(patientId, sourceSystemHSAId);
    response = TestDataGenerator.generateResponse(responses);
  }

  @Test
  public void testGetPatientId() {
    assertEquals(patientId, agpServiceFactory.getPatientId(request));
  }

  @Test
  public void testGetSourceSystemHsaId(){
    assertEquals(sourceSystemHSAId, agpServiceFactory.getSourceSystemHsaId(request));
  }

  @Test

  public void aggregateResponseTest(){
    GetLaboratoryOrderOutcomeResponseType responseType = agpServiceFactory.aggregateResponse(response);
    assertEquals(responses, responseType.getLaboratoryOrderOutcome().size());
  }


}
