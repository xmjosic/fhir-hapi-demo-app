package hr.xmjosic.fhirhapidemoapp.util.identifier;

import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Resource;

/** Identifier generator */
public interface IdentifierGenerator {

  /**
   * Generate identifier and add to identifier list.
   *
   * @param resource Resource.
   */
  void apply(Resource resource);

  /**
   * Fetch identifier.
   *
   * @param resource Resource
   * @return Returns identifier.
   */
  Identifier getIdentifier(Resource resource);
}
