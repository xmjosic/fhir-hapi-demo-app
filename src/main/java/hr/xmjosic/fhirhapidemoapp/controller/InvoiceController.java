package hr.xmjosic.fhirhapidemoapp.controller;

import hr.xmjosic.fhirhapidemoapp.service.HapiFhirService;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Invoice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** HAPI FHIR controller */
@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController {

  public static final Class<Invoice> INVOICE_RETURN_TYPE = Invoice.class;
  private final HapiFhirService service;

  /**
   * Get {@link Invoice} by ID.
   *
   * @param identifier Resource ID
   * @return Returns {@link Invoice}
   */
  @GetMapping(
      path = "/{identifier}",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<Invoice> getInvoiceById(@PathVariable("identifier") String identifier) {
    return ResponseEntity.ok(service.getById(INVOICE_RETURN_TYPE, identifier));
  }

  /**
   * Create {@link Invoice}.
   *
   * @param invoice Resource
   * @return Returns {@link Invoice}
   */
  @PostMapping("/create")
  public ResponseEntity<IBaseResource> createInvoice(@RequestBody Invoice invoice) {
    return ResponseEntity.ok(service.create(invoice));
  }

  /**
   * Update {@link Invoice}.
   *
   * @param invoice Resource
   * @return Returns {@link Invoice}
   */
  @PostMapping("/update")
  public ResponseEntity<IBaseResource> updateInvoice(@RequestBody Invoice invoice) {
    return ResponseEntity.ok(service.update(invoice));
  }

  /**
   * Patch {@link Invoice}.
   *
   * @param invoice Resource
   * @return Returns {@link Invoice}
   */
  @PostMapping("/patch")
  public ResponseEntity<IBaseResource> patchInvoice(@RequestBody Invoice invoice) {
    return ResponseEntity.ok(service.patch(invoice));
  }
}
