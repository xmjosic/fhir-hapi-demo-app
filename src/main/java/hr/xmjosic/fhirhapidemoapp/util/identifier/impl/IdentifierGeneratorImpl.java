package hr.xmjosic.fhirhapidemoapp.util.identifier.impl;

import hr.xmjosic.fhirhapidemoapp.configuration.ResourceConfigProperties;
import hr.xmjosic.fhirhapidemoapp.util.identifier.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Contract;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Invoice;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

/**
* Identifier generator
*/
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(ResourceConfigProperties.class)
public class IdentifierGeneratorImpl implements IdentifierGenerator {

  public static final String URN_OID = "urn:oid:";
  public static final String URN_UUID = "urn:uuid:";
  private static final Map<String, Function<Resource, List<Identifier>>> resourceFunctionMap =
      Map.of(
          Invoice.class.getSimpleName(),
              t -> t instanceof Invoice ? ((Invoice) t).getIdentifier() : null,
          Contract.class.getSimpleName(),
              t -> t instanceof Contract ? ((Contract) t).getIdentifier() : null);

  private final RandomIdentifierGenerator identifierGenerator;
  private final ResourceConfigProperties properties;

  @Override
  public void apply(Resource resource) {
    List<Identifier> identifiers = resourceFunctionMap.get(resource.getClass().getSimpleName()).apply(resource);
    if (isNull(identifiers)) throw new IllegalArgumentException("Resource is null.");

    ResourceConfigProperties.IdentifierProps props = properties.getProperties(resource);

    if (identifiers.stream().anyMatch(identifier -> props.identifierSystem().equalsIgnoreCase(identifier.getSystem()) && hasText(identifier.getValue()))) return;

    identifiers.removeIf(identifier -> props.identifierSystem().equalsIgnoreCase(identifier.getSystem()) && !hasText(identifier.getValue()));

    Identifier identifier = new Identifier().setSystem(props.identifierSystem());
    switch (props.identifierType()) {
      case OID -> identifier.setValue(URN_OID + identifierGenerator.generateOid());
      case UUID -> identifier.setValue(URN_UUID + identifierGenerator.generateUuid());
    }

    identifiers.add(identifier);
  }

  @Override
  public Identifier getIdentifier(Resource resource) {
    final String identifierSystem = properties.getProperties(resource).identifierSystem();
    List<Identifier> identifiers = resourceFunctionMap.get(resource.getClass().getSimpleName()).apply(resource);
    return nonNull(identifiers) ? identifiers.stream().filter(Objects::nonNull).filter(identifier -> identifierSystem.equalsIgnoreCase(identifier.getSystem()) && hasText(identifier.getValue())).findFirst().orElse(null) : null;
  }
}
