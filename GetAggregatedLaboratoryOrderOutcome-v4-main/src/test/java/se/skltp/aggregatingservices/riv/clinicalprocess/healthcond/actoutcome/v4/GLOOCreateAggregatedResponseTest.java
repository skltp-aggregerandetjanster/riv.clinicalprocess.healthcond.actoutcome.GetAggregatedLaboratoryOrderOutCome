package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.v4;

import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;
import se.skltp.aggregatingservices.data.TestDataGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GLOOCreateAggregatedResponseTest extends CreateAggregatedResponseTest {

  private static GLOOAgpServiceConfiguration configuration = new GLOOAgpServiceConfiguration();
  private static AgpServiceFactory<GetLaboratoryOrderOutcomeResponseType> agpServiceFactory = new GLOOAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GLOOCreateAggregatedResponseTest() {
      super(testDataGenerator, agpServiceFactory, configuration);
  }

  @Override
  public int getResponseSize(Object response) {
        GetLaboratoryOrderOutcomeResponseType responseType = (GetLaboratoryOrderOutcomeResponseType)response;
    return responseType.getLaboratoryOrderOutcome().size();
  }
}