package hr.xmjosic.fhirhapidemoapp.util;

import ca.uhn.fhir.rest.api.MethodOutcome;
import hr.xmjosic.fhirhapidemoapp.exception.HapiFhirExecuteException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl7.fhir.r4.model.OperationOutcome;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/** Exception utils */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateUtils {

  /**
   * Check is created or else throw {@link HapiFhirExecuteException}.
   *
   * @param methodOutcome {@link MethodOutcome}
   */
  public static void isCreatedOrElseThrow(MethodOutcome methodOutcome) {
    if (!methodOutcome.getCreated())
      throw new HapiFhirExecuteException(
          methodOutcome.getOperationOutcome(), methodOutcome.getResponseStatusCode());
  }

  /**
   * Check if valid else throw {@link HapiFhirExecuteException}.
   *
   * @param methodOutcome {@link MethodOutcome}
   */
  public static void isValidOrElseThrow(MethodOutcome methodOutcome) {
    if (nonNull(methodOutcome) && methodOutcome.getOperationOutcome() instanceof OperationOutcome operationOutcome) {
      if (operationOutcome.hasIssue()
          && operationOutcome.getIssue().stream()
              .anyMatch(
                  issue ->
                      OperationOutcome.IssueSeverity.ERROR.equals(issue.getSeverity())
                          || OperationOutcome.IssueSeverity.FATAL.equals(issue.getSeverity())))
        throw new HapiFhirExecuteException(methodOutcome.getOperationOutcome(), BAD_REQUEST.value());
    }
  }
}
