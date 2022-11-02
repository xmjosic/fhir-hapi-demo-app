package hr.xmjosic.fhirhapidemoapp.configuration;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.hl7.fhir.r4.model.Contract;
import org.hl7.fhir.r4.model.Invoice;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/** HAPI FHIR configuration */
@Configuration
@EnableConfigurationProperties(HapiFhirProps.class)
public class FhirConfig {

  /**
   * FhirContext bean.
   *
   * @return Returns {@link FhirContext}
   */
  @Primary
  @Bean
  public FhirContext fhirContext() {
    return FhirContext.forR4Cached();
  }

  /**
   * IGeneric client bean.
   *
   * @param fhirContext {@link FhirContext}
   * @param hapiFhirProps {@link HapiFhirProps}
   * @return Returns {@link IGenericClient}
   */
  @Primary
  @Bean
  public IGenericClient iGenericClient(FhirContext fhirContext, HapiFhirProps hapiFhirProps) {
    return fhirContext.newRestfulGenericClient(hapiFhirProps.getBaseServerAddress());
  }

  /**
   * Register simple module with deserializers.
   *
   * @param fhirContext FHIR context
   * @return Returns {@link Module}
   */
  @Bean
  public Module fhirModule(FhirContext fhirContext) {
    SimpleModule retVal = new SimpleModule();
    retVal.addDeserializer(Invoice.class, new FhirDeserializer<>(fhirContext, Invoice.class));
    retVal.addDeserializer(Contract.class, new FhirDeserializer<>(fhirContext, Contract.class));
    return retVal;
  }
}
