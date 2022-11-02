package hr.xmjosic.fhirhapidemoapp.util.identifier.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RandomIdentifierGeneratorTest {

  private RandomIdentifierGenerator generator;

  @BeforeEach
  void inti() {
    generator = new RandomIdentifierGenerator();
  }

  @Test
  void generateUuid() {
    String result = generator.generateUuid();

    assertNotNull(result);
  }

  @Test
  void generateOid() {
    String result = generator.generateOid();

    assertNotNull(result);
  }
}
