package hr.xmjosic.fhirhapidemoapp.util.identifier.impl;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.UUID;

/** UUID or OID generator. */
@Component
public class RandomIdentifierGenerator {

  private static final String UUID_DELIMITER = "-";
  private static final String EMPTY = "";
  private static final int RADIX = 16;
  private static final String OID_ENCODED_UID = "2.25.";

  /**
   * Generate random UUID.
   *
   * @return Returns identifier.
   */
  public String generateUuid() {
    return UUID.randomUUID().toString();
  }

  /**
   * Generate OID encoded UUID.
   *
   * @return Returns identifier.
   */
  public String generateOid() {
    return OID_ENCODED_UID + new BigInteger(UUID.randomUUID().toString().replace(UUID_DELIMITER, EMPTY), RADIX);
  }
}
