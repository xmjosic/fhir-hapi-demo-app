package hr.xmjosic.fhirhapidemoapp.util.validate;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import hr.xmjosic.fhirhapidemoapp.configuration.ResourceConfigProperties;
import hr.xmjosic.fhirhapidemoapp.dao.HapiFhirRepository;
import hr.xmjosic.fhirhapidemoapp.dto.IdentifierType;
import hr.xmjosic.fhirhapidemoapp.exception.HapiFhirExecuteException;
import hr.xmjosic.fhirhapidemoapp.util.validate.impl.ResourceValidatorImpl;
import org.hl7.fhir.r4.model.Invoice;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hl7.fhir.r4.model.OperationOutcome.IssueSeverity.ERROR;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResourceValidatorTest {

  private ResourceValidator validator;
  private HapiFhirRepository fhirRepository;

  @BeforeEach
  void init() {
    fhirRepository = mock(HapiFhirRepository.class);
    ResourceConfigProperties properties =
        new ResourceConfigProperties(
            new ResourceConfigProperties.IdentifierProps(IdentifierType.OID, "system", "profile"),
            new ResourceConfigProperties.IdentifierProps(
                IdentifierType.UUID, "system", "profile2"));
    validator = new ResourceValidatorImpl(fhirRepository, properties);
  }

  @Test
  void validate() {
    assertDoesNotThrow(() -> validator.validate(new Invoice()));
  }

  @Test
  void validateWithBaseServerResponseException() {
    when(fhirRepository.validate(any(Resource.class))).thenThrow(ResourceNotFoundException.class);

    Assertions.assertThrows(
        HapiFhirExecuteException.class, () -> validator.validate(new Invoice()));
  }

  @Test
  void validateWithHapiFhirExecuteException() {
    when(fhirRepository.validate(any(Resource.class)))
        .thenReturn(
            new MethodOutcome()
                .setOperationOutcome(
                    new OperationOutcome()
                        .setIssue(
                            List.of(
                                new OperationOutcome.OperationOutcomeIssueComponent()
                                    .setSeverity(ERROR)))));

    Assertions.assertThrows(
        HapiFhirExecuteException.class, () -> validator.validate(new Invoice()));
  }
}
