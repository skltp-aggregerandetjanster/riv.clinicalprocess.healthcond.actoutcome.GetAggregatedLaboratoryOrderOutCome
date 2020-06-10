package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.cxf.message.MessageContentsList;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.utility.RequestListUtil;

@RunWith(CamelSpringRunner.class)
@SpringBootTest
public class GLAOCreateAggregatedResponse {

  private static GLAOOAgpServiceConfiguration configuration;
  private static AgpServiceFactory<GetLaboratoryOrderOutcomeResponseType> agpServiceFactory;

  private static ServiceTestDataGenerator producerResponseDataHelper = new ServiceTestDataGenerator();

  private static String patientId1 = "121212121212";
  private static String patientId2 = "198611062384";

  private String producer4 = "HSA-ID-4";
  private String producer5 = "HSA-ID-5";
  private String producer6 = "HSA-ID-6";

  private String producer2 = "HSA-ID-2";


  @BeforeClass
  public static void before() {
    configuration = new GLAOOAgpServiceConfiguration();
    agpServiceFactory = new GLAOOAgpServiceFactoryImpl();
    agpServiceFactory.setAgpServiceConfiguration(configuration);
  }

  @Test
  public void testCreateAggregatedResponse(){
    GetLaboratoryOrderOutcomeResponseType responseFromProducer1 = (GetLaboratoryOrderOutcomeResponseType) producerResponseDataHelper
        .retrieveFromDb(producer4, patientId1);
    GetLaboratoryOrderOutcomeResponseType responseFromProducer2 = (GetLaboratoryOrderOutcomeResponseType) producerResponseDataHelper
        .retrieveFromDb(producer5, patientId1);
    GetLaboratoryOrderOutcomeResponseType responseFromProducer3 = (GetLaboratoryOrderOutcomeResponseType) producerResponseDataHelper
        .retrieveFromDb(producer6, patientId1);

    List<MessageContentsList> listOfResponsesFromAllProducers = new ArrayList<>();
    listOfResponsesFromAllProducers.add(RequestListUtil.createRequest(responseFromProducer1));
    listOfResponsesFromAllProducers.add(RequestListUtil.createRequest(responseFromProducer2));
    listOfResponsesFromAllProducers.add(RequestListUtil.createRequest(responseFromProducer3));

    MessageContentsList originalRequest = RequestListUtil.createRequest("logiskAdress", RequestGenerator.generateRequest(patientId1, null));
    GetLaboratoryOrderOutcomeResponseType aggregeradeResponse = agpServiceFactory.createAggregatedResponseObject(originalRequest, listOfResponsesFromAllProducers);

    assertEquals(3, aggregeradeResponse.getLaboratoryOrderOutcome().size());
  }

  @Test
  public void testCreateAggregatedResponse_MultipleResponseFromOneProducer(){
    GetLaboratoryOrderOutcomeResponseType responseFromProducer2 = (GetLaboratoryOrderOutcomeResponseType) producerResponseDataHelper
        .retrieveFromDb(producer2, patientId2);

    List<MessageContentsList> listOfResponsesFromAllProducers = new ArrayList<>();
    listOfResponsesFromAllProducers.add(RequestListUtil.createRequest(responseFromProducer2));


    MessageContentsList originalRequest = RequestListUtil.createRequest("logiskAdress", RequestGenerator.generateRequest(patientId2, null));
    GetLaboratoryOrderOutcomeResponseType aggregeradeResponse = agpServiceFactory.createAggregatedResponseObject(originalRequest, listOfResponsesFromAllProducers);

    assertEquals(2, aggregeradeResponse.getLaboratoryOrderOutcome().size());
  }
}






















