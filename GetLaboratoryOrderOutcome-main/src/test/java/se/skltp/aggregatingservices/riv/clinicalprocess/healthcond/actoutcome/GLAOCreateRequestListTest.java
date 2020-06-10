package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.cxf.message.MessageContentsList;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.data.FindContentTestData;
import se.skltp.aggregatingservices.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.aggregatingservices.utility.RequestListUtil;

@RunWith(CamelSpringRunner.class)
@SpringBootTest
public class GLAOCreateRequestListTest {
  private static GLAOOAgpServiceConfiguration configuration;
  private static AgpServiceFactory<GetLaboratoryOrderOutcomeResponseType> agpServiceFactory;

  private FindContentTestData eiResponseDataHelper  = new FindContentTestData();

  private static String patientId1 = "121212121212";

  private static String patientId2 = "198611062384";
  private String producer2 = "HSA-ID-2";

  @BeforeClass
  public static void before() {
    configuration = new GLAOOAgpServiceConfiguration();
    agpServiceFactory = new GLAOOAgpServiceFactoryImpl();
    agpServiceFactory.setAgpServiceConfiguration(configuration);
  }

  @Test
  public void testCreateRequestList_allProducers(){
    MessageContentsList messageContentsList = RequestListUtil.createRequest("logiskAdress", RequestGenerator.generateRequest(patientId1, null));
    FindContentResponseType eiResponse = eiResponseDataHelper.getResponseForPatient(patientId1);


    List<MessageContentsList> requestList = agpServiceFactory.createRequestList(messageContentsList, eiResponse);
    assertEquals(3, requestList.size());
  }

  @Test
  public void testCreateRequestList_concreteProducer(){
    MessageContentsList messageContentsList = RequestListUtil.createRequest("logiskAdress", RequestGenerator.generateRequest(patientId2, producer2));
    FindContentResponseType eiResponse = eiResponseDataHelper.getResponseForPatient(patientId2);


    List<MessageContentsList> requestList = agpServiceFactory.createRequestList(messageContentsList, eiResponse);

    assertEquals(1, requestList.size());
  }

  @Test
  public void testCreateRequestList_noDataInEIForThisProducer(){
    MessageContentsList messageContentsList = RequestListUtil.createRequest("logiskAdress", RequestGenerator.generateRequest(patientId1, producer2));

    FindContentResponseType eiResponse = eiResponseDataHelper.getResponseForPatient(patientId1);

    List<MessageContentsList> requestList = agpServiceFactory.createRequestList(messageContentsList, eiResponse);

    assertEquals(0, requestList.size());
  }


}


















