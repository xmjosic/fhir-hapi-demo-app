package hr.xmjosic.fhirhapidemoapp.util.validate.impl;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.exceptions.BaseServerResponseException;
import hr.xmjosic.fhirhapidemoapp.configuration.ResourceConfigProperties;
import hr.xmjosic.fhirhapidemoapp.dao.HapiFhirRepository;
import hr.xmjosic.fhirhapidemoapp.exception.HapiFhirExecuteException;
import hr.xmjosic.fhirhapidemoapp.util.ValidateUtils;
import hr.xmjosic.fhirhapidemoapp.util.validate.ResourceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.CanonicalType;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceValidatorImpl implements ResourceValidator {

  private final HapiFhirRepository fhirRepository;
  private final ResourceConfigProperties properties;

  @Override
  public void validate(Resource resource) {
    final String profile = properties.getProperties(resource).profile();
    if (!resource.hasMeta()) resource.setMeta(new Meta());
    CanonicalType canonicalType = new CanonicalType();
    canonicalType.setValue(profile);
    log.info("Resource meta - add profile.");
    resource.getMeta().setProfile(List.of(canonicalType));

    try {
      MethodOutcome outcome = fhirRepository.validate(resource);
      ValidateUtils.isValidOrElseThrow(outcome);
    } catch (BaseServerResponseException e) {
      log.error("Base server response exception", e);
      throw new HapiFhirExecuteException(e.getOperationOutcome(), e.getStatusCode());
    }
  }
}
