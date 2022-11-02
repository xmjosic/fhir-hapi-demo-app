package hr.xmjosic.fhirhapidemoapp.util;

import ca.uhn.fhir.rest.api.MethodOutcome;
import hr.xmjosic.fhirhapidemoapp.exception.HapiFhirExecuteException;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static java.util.List.of;
import static org.hl7.fhir.r4.model.OperationOutcome.IssueType.EXCEPTION;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class ValidateUtilsTest {

  @Test
  void isCreatedOrElseThrow() {
    MethodOutcome methodOutcome = new MethodOutcome().setCreated(true);

    assertDoesNotThrow(() -> ValidateUtils.isCreatedOrElseThrow(methodOutcome));
  }

  @Test
  void isCreatedOrElseThrowWithThrowingException() {
    MethodOutcome methodOutcome = new MethodOutcome().setCreated(false);
    methodOutcome.setStatusCode(400);
    OperationOutcome outcome =
        new OperationOutcome()
            .setIssue(of(new OperationOutcome.OperationOutcomeIssueComponent().setCode(EXCEPTION)));
    methodOutcome.setOperationOutcome(outcome);

    HapiFhirExecuteException e =
        assertThrows(
            HapiFhirExecuteException.class,
            () -> ValidateUtils.isCreatedOrElseThrow(methodOutcome));
    assertEquals(outcome, e.getOperationOutcome());
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "2", "3"})
  void isValidOrElseThrow(String key) {
    Map<String, MethodOutcome> outcomeMap =
        Map.of(
            "2",
            new MethodOutcome().setOperationOutcome(null),
            "3",
            new MethodOutcome()
                .setOperationOutcome(
                    new OperationOutcome()
                        .setIssue(
                            of(
                                new OperationOutcome.OperationOutcomeIssueComponent()
                                    .setSeverity(OperationOutcome.IssueSeverity.INFORMATION)))));

    MethodOutcome methodOutcome = outcomeMap.get(key);
    assertDoesNotThrow(() -> ValidateUtils.isValidOrElseThrow(methodOutcome));
  }

  @ParameterizedTest
  @ValueSource(strings = {"error", "fatal"})
  void isValidOrElseThrowWithThrowingException(String severity) {
    OperationOutcome outcome =
        new OperationOutcome()
            .setIssue(
                of(
                    new OperationOutcome.OperationOutcomeIssueComponent()
                        .setSeverity(OperationOutcome.IssueSeverity.fromCode(severity))));
    MethodOutcome methodOutcome = new MethodOutcome().setOperationOutcome(outcome);

    HapiFhirExecuteException e =
        assertThrows(
            HapiFhirExecuteException.class, () -> ValidateUtils.isValidOrElseThrow(methodOutcome));
    assertEquals(outcome, e.getOperationOutcome());
    assertEquals(BAD_REQUEST.value(), e.getStatus());
  }
}
