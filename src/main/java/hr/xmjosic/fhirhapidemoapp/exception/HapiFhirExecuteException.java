package hr.xmjosic.fhirhapidemoapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.instance.model.api.IBaseOperationOutcome;
import org.hl7.fhir.r4.model.OperationOutcome;

/** HAPI FHIR execute exception */
@Getter
@RequiredArgsConstructor
public class HapiFhirExecuteException extends RuntimeException {
  public static final long serialVersionUID = -1705291720812585773L;

  /** {@link OperationOutcome} */
  private final IBaseOperationOutcome operationOutcome;

  /** HTTP status code */
  private final int status;
}
