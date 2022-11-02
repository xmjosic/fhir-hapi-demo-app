package hr.xmjosic.fhirhapidemoapp.configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/** HAPI FHIR Properties */
@Getter
@ToString
@EqualsAndHashCode
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "hapi.fhir")
public class HapiFhirProps {

  /** Public HAPI base server address. */
  private final String baseServerAddress;
}
