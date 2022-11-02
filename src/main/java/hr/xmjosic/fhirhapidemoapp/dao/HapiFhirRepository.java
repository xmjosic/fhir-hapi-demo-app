package hr.xmjosic.fhirhapidemoapp.dao;

import ca.uhn.fhir.rest.api.MethodOutcome;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.Resource;

/** HAPI FHIR Repository */
public interface HapiFhirRepository {

  /**
   * Fetch resource by ID.
   *
   * @param returnType Resource type.
   * @param identifier FHIR resource identifier
   * @param <T> extends {@link IBaseResource}
   * @return Returns created resource
   */
  <T extends IBaseResource> Bundle search(Class<T> returnType, String identifier);

  /**
   * Create new resource.
   *
   * @param resource FHIR resource
   * @param identifier FHIR resource identifier
   * @return Returns {@link MethodOutcome}
   */
  MethodOutcome create(Resource resource, Identifier identifier);

  /**
   * Update resource.
   *
   * @param resource FHIR resource
   * @param identifier FHIR resource identifier
   * @return Returns {@link MethodOutcome}
   */
  MethodOutcome update(Resource resource, Identifier identifier);

  /**
   * Patch resource.
   *
   * @param parameters FHIR resource parameters
   * @param identifier FHIR resource identifier
   * @param clazz FHIR resource class
   * @return Returns {@link MethodOutcome}
   */
  <T extends IBaseResource> MethodOutcome patch(
      Parameters parameters, Identifier identifier, Class<T> clazz);

  /**
   * Validate resource
   *
   * @param resource Resource
   * @return Returns {@link MethodOutcome}
   */
  MethodOutcome validate(Resource resource);
}
