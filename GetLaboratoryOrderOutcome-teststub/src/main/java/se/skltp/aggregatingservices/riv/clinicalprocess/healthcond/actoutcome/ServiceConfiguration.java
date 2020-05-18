package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import se.skltp.aggregatingservices.config.TestProducerConfiguration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "gloo.teststub")
public class ServiceConfiguration extends TestProducerConfiguration {
}
