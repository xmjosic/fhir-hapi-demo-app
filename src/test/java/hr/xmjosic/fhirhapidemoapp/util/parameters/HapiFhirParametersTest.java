package hr.xmjosic.fhirhapidemoapp.util.parameters;

import org.hl7.fhir.r4.model.Parameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HapiFhirParametersTest {

  @Test
  void changeStatusToCancelledParameter() {
    Parameters invoiceParameters =
        new HapiFhirParameters().changeStatusToCancelledParameter("Invoice");

    assertNotNull(invoiceParameters);
    assertEquals("operation", invoiceParameters.getParameter().get(0).getName());
    assertEquals(
        "add",
        invoiceParameters.getParameter().get(0).getPart().get(0).getValue().primitiveValue());
    assertEquals(
        "Invoice",
        invoiceParameters.getParameter().get(0).getPart().get(1).getValue().primitiveValue());
    assertEquals(
        "status",
        invoiceParameters.getParameter().get(0).getPart().get(2).getValue().primitiveValue());
    assertEquals(
        "cancelled",
        invoiceParameters.getParameter().get(0).getPart().get(3).getValue().primitiveValue());
  }
}
