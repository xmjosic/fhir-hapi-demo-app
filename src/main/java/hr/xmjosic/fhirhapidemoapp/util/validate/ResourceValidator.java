package hr.xmjosic.fhirhapidemoapp.util.validate;

import org.hl7.fhir.r4.model.Resource;

/** Resource validator. */
public interface ResourceValidator {

  /**
   * Validate resource based on the profile.
   *
   * @param resource Resource
   */
  void validate(Resource resource);
}
