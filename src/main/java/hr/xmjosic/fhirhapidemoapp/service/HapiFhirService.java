package hr.xmjosic.fhirhapidemoapp.service;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Resource;

/** HAPI FHIR service. */
public interface HapiFhirService {

  /**
   * Get resource by ID.
   *
   * @param returnType Resource type.
   * @param identifier Resource identifier.
   * @param <T> extends {@link Resource}
   * @return Returns resource.
   */
  <T extends Resource> T getById(Class<T> returnType, String identifier);

  /**
   * Create resource
   *
   * @param resource FHIR Resource
   * @return Returns created resource.
   * @param <T> extends {@link Resource}
   */
  <T extends Resource> IBaseResource create(T resource);

  /**
   * Update resource
   *
   * @param resource FHIR Resource
   * @return Returns updated resource.
   * @param <T> extends {@link Resource}
   */
  <T extends Resource> IBaseResource update(T resource);

  /**
   * Patch resource
   *
   * @param resource FHIR Resource
   * @return Returns patched resource.
   * @param <T> extends {@link Resource}
   */
  <T extends Resource> IBaseResource patch(T resource);
}
