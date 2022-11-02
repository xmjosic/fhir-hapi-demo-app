package hr.xmjosic.fhirhapidemoapp.util.identifier;

import hr.xmjosic.fhirhapidemoapp.configuration.ResourceConfigProperties;
import hr.xmjosic.fhirhapidemoapp.dto.IdentifierType;
import hr.xmjosic.fhirhapidemoapp.util.identifier.impl.IdentifierGeneratorImpl;
import hr.xmjosic.fhirhapidemoapp.util.identifier.impl.RandomIdentifierGenerator;
import org.hl7.fhir.r4.model.Contract;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentifierGeneratorTest {

  private IdentifierGenerator generator;

  @BeforeEach
  void init() {
    ResourceConfigProperties properties =
        new ResourceConfigProperties(
            new ResourceConfigProperties.IdentifierProps(IdentifierType.OID, "system", "profile"),
            new ResourceConfigProperties.IdentifierProps(
                IdentifierType.UUID, "system", "profile2"));
    generator = new IdentifierGeneratorImpl(new RandomIdentifierGenerator(), properties);
  }

  @Test
  void applyOid() {
    Invoice invoice = new Invoice();
    assertDoesNotThrow(() -> generator.apply(invoice));
    assertEquals(1, invoice.getIdentifier().size());
    assertEquals("system", invoice.getIdentifier().get(0).getSystem());
    assertTrue(invoice.getIdentifier().get(0).getValue().startsWith("urn:oid:"));
  }

  @Test
  void applyUuid() {
    Contract contract = new Contract();
    assertDoesNotThrow(() -> generator.apply(contract));
    assertEquals(1, contract.getIdentifier().size());
    assertTrue(contract.getIdentifier().get(0).getValue().startsWith("urn:uuid:"));
  }

  @Test
  void getIdentifier() {
    Identifier identifier =
        generator.getIdentifier(
            new Invoice().addIdentifier(new Identifier().setSystem("system").setValue("test")));

    assertNotNull(identifier);
    assertEquals("system", identifier.getSystem());
    assertEquals("test", identifier.getValue());
  }
}
