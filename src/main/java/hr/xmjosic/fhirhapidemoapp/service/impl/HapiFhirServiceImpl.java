package hr.xmjosic.fhirhapidemoapp.service.impl;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import hr.xmjosic.fhirhapidemoapp.dao.HapiFhirRepository;
import hr.xmjosic.fhirhapidemoapp.service.HapiFhirService;
import hr.xmjosic.fhirhapidemoapp.util.ValidateUtils;
import hr.xmjosic.fhirhapidemoapp.util.identifier.IdentifierGenerator;
import hr.xmjosic.fhirhapidemoapp.util.parameters.HapiFhirParameters;
import hr.xmjosic.fhirhapidemoapp.util.validate.ResourceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HapiFhirServiceImpl implements HapiFhirService {

  private final HapiFhirRepository fhirRepository;
  private final IdentifierGenerator identifierGenerator;
  private final ResourceValidator validator;

  @Override
  public <T extends Resource> T getById(Class<T> returnType, String identifier) {
    log.info("Get resource {} by ID: {}", returnType.getSimpleName(), identifier);
    Bundle bundle = fhirRepository.search(returnType, identifier);
    return bundle.getEntry().stream()
        .map(Bundle.BundleEntryComponent::getResource)
        .filter(returnType::isInstance)
        .map(returnType::cast)
        .findFirst()
        .orElseThrow(
            () -> {
              log.info("Resource not found!");
              throw new ResourceNotFoundException(identifier);
            });
  }

  @Override
  public <T extends Resource> IBaseResource create(T resource) {
    log.info("Add identifier.");
    identifierGenerator.apply(resource);
    log.info("Validate resource.");
    validator.validate(resource);
    log.info("Fetch identifier.");
    Identifier identifier =
        Optional.ofNullable(identifierGenerator.getIdentifier(resource))
            .orElseThrow(IllegalArgumentException::new);
    log.info("Create resource.");
    MethodOutcome methodOutcome = fhirRepository.create(resource, identifier);
    log.info("Validate method outcome.");
    ValidateUtils.isCreatedOrElseThrow(methodOutcome);
    return methodOutcome.getResource();
  }

  @Override
  public <T extends Resource> IBaseResource update(T resource) {
    validator.validate(resource);
    Identifier identifier =
        Optional.ofNullable(identifierGenerator.getIdentifier(resource))
            .orElseThrow(IllegalArgumentException::new);
    MethodOutcome update = fhirRepository.update(resource, identifier);
    return update.getResource();
  }

  @Override
  public <T extends Resource> IBaseResource patch(T resource) {
    Parameters parameters =
        new HapiFhirParameters()
            .changeStatusToCancelledParameter(resource.getClass().getSimpleName());
    Identifier identifier =
        Optional.ofNullable(identifierGenerator.getIdentifier(resource))
            .orElseThrow(IllegalArgumentException::new);
    MethodOutcome patch = fhirRepository.patch(parameters, identifier, resource.getClass());
    return patch.getResource();
  }
}
