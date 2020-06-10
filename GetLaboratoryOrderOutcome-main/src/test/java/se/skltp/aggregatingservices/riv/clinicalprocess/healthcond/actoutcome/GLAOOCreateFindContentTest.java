package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome;

import static org.junit.Assert.assertEquals;

import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.cxf.message.MessageContentsList;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;

import se.skltp.aggregatingservices.utility.RequestListUtil;

@RunWith(CamelSpringRunner.class)
@SpringBootTest
public class GLAOOCreateFindContentTest {

  private static String patientId = "121212121212";

  private static AgpServiceFactory<GetLaboratoryOrderOutcomeResponseType> agpServiceFactory;
  private static GLAOOAgpServiceConfiguration configuration;

  @BeforeClass
  public static void before() {
    configuration = new GLAOOAgpServiceConfiguration();
    agpServiceFactory = new GLAOOAgpServiceFactoryImpl();
    agpServiceFactory.setAgpServiceConfiguration(configuration);
  }

  @Test
  public void testCreateFindContent(){
    MessageContentsList messageContentsList = RequestListUtil.createRequest("logiskAdress", RequestGenerator.generateRequest(patientId, null));

    FindContentType type = agpServiceFactory.createFindContent(messageContentsList);

    assertEquals(configuration.getEiCategorization(), type.getCategorization());
    assertEquals(configuration.getEiServiceDomain(), type.getServiceDomain());
    assertEquals(patientId, type.getRegisteredResidentIdentification());
  }
}
