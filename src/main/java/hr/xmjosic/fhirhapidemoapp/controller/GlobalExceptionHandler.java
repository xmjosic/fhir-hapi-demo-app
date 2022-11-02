package hr.xmjosic.fhirhapidemoapp.controller;

import hr.xmjosic.fhirhapidemoapp.exception.HapiFhirExecuteException;
import org.hl7.fhir.instance.model.api.IBaseOperationOutcome;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Global exception handler */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles custom exception.
   *
   * @param e {@link HapiFhirExecuteException}
   * @return Returns {@link org.hl7.fhir.r4.model.OperationOutcome}
   */
  @ExceptionHandler(HapiFhirExecuteException.class)
  public ResponseEntity<IBaseOperationOutcome> handle(HapiFhirExecuteException e) {
    return ResponseEntity.status(e.getStatus()).body(e.getOperationOutcome());
  }
}
