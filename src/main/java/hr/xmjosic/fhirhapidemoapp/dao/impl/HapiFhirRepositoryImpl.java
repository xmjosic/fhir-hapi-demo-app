package hr.xmjosic.fhirhapidemoapp.dao.impl;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ICriterion;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import hr.xmjosic.fhirhapidemoapp.dao.HapiFhirRepository;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.stereotype.Repository;

import static ca.uhn.fhir.rest.api.PreferReturnEnum.REPRESENTATION;

@Repository
@RequiredArgsConstructor
public class HapiFhirRepositoryImpl implements HapiFhirRepository {

  public static final TokenClientParam IDENTIFIER = new TokenClientParam("identifier");

  private final IGenericClient iGenericClient;

  @Override
  public <T extends IBaseResource> Bundle search(Class<T> returnType, String identifier) {
    return iGenericClient
        .search()
        .forResource(returnType)
        .where(IDENTIFIER.exactly().code(identifier))
        .returnBundle(Bundle.class)
        .execute();
  }

  @Override
  public MethodOutcome create(Resource resource, Identifier identifier) {
    return iGenericClient
        .create()
        .resource(resource)
        .conditional()
        .where(getCriterion(identifier))
        .prefer(REPRESENTATION)
        .execute();
  }

  @Override
  public MethodOutcome update(Resource resource, Identifier identifier) {
    return iGenericClient
        .update()
        .resource(resource)
        .conditional()
        .where(getCriterion(identifier))
        .prefer(REPRESENTATION)
        .execute();
  }

  @Override
  public <T extends IBaseResource> MethodOutcome patch(
      Parameters parameters, Identifier identifier, Class<T> clazz) {
    return iGenericClient
        .patch()
        .withFhirPatch(parameters)
        .conditional(clazz)
        .where(getCriterion(identifier))
        .prefer(REPRESENTATION)
        .execute();
  }

  @Override
  public MethodOutcome validate(Resource resource) {
    return iGenericClient.validate().resource(resource).execute();
  }

  /**
   * Generate {@code identifier} param.
   *
   * @param identifier FHIR resource identifier.
   * @return Returns criteria.
   */
  private ICriterion<TokenClientParam> getCriterion(Identifier identifier) {
    return IDENTIFIER.exactly().systemAndCode(identifier.getSystem(), identifier.getValue());
  }
}
