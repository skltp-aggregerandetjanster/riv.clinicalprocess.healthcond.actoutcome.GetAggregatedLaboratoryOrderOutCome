package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.v4;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcome.v4.rivtabp21.GetLaboratoryOrderOutcomeResponderInterface;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcome.v4.rivtabp21.GetLaboratoryOrderOutcomeResponderService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "getaggregatedlaboratoryorderoutcome.v4")
public class GLOOAgpServiceConfiguration extends se.skltp.aggregatingservices.configuration.AgpServiceConfiguration {

public static final String SCHEMA_PATH = "/schemas/clinicalprocess-healthcond-actoutcome/interactions/GetLaboratoryOrderOutcomeInteraction/GetLaboratoryOrderOutcomeInteraction_4.0_RIVTABP21.wsdl";

  public GLOOAgpServiceConfiguration() {

    setServiceName("GetAggregatedLaboratoryOrderOutcome-v4");
    setTargetNamespace("urn:riv:clinicalprocess:healthcond:actoutcome:GetLaboratoryOrderOutcome:4:rivtabp21");

    // Set inbound defaults
    setInboundServiceURL("http://0.0.0.0:9024/GetAggregatedLaboratoryOrderOutcome/service/v4");
    setInboundServiceWsdl(SCHEMA_PATH);
    setInboundServiceClass(GetLaboratoryOrderOutcomeResponderInterface.class.getName());
    setInboundPortName(GetLaboratoryOrderOutcomeResponderService.GetLaboratoryOrderOutcomeResponderPort.toString());

    // Set outbound defaults
    setOutboundServiceWsdl(SCHEMA_PATH);
    setOutboundServiceClass(getInboundServiceClass());
    setOutboundPortName(getInboundPortName());

    // FindContent
    setEiServiceDomain("riv:clinicalprocess:healthcond:actoutcome");
    setEiCategorization("und-kkm-ure");

    // TAK
    setTakContract("urn:riv:clinicalprocess:healthcond:actoutcome:GetLaboratoryOrderOutcomeResponder:4");

    // Set service factory
    setServiceFactoryClass(GLOOAgpServiceFactoryImpl.class.getName());
    }


}
