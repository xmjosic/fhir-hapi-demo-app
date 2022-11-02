package hr.xmjosic.fhirhapidemoapp.configuration;

import hr.xmjosic.fhirhapidemoapp.dto.IdentifierType;
import lombok.*;
import org.hl7.fhir.r4.model.Contract;
import org.hl7.fhir.r4.model.Invoice;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Map;
import java.util.function.Supplier;

/** Identifier configuration properties */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "hapi.fhir.config")
public class ResourceConfigProperties {

  /** Properties for {@link Invoice} */
  private final IdentifierProps invoice;

  /** Properties for {@link Contract} */
  private final IdentifierProps contract;

  @Getter(AccessLevel.NONE)
  private final Map<String, Supplier<IdentifierProps>> configMap =
      Map.of(
          Invoice.class.getSimpleName(),
          this::getInvoice,
          Contract.class.getSimpleName(),
          this::getContract);

  /**
   * Get resource identifier properties.
   *
   * @param resource
   * @return
   */
  public IdentifierProps getProperties(Resource resource) {
    return configMap.get(resource.getClass().getSimpleName()).get();
  }

  /**
   * Identifier properties
   *
   * @param identifierType Identifier type, e.g. OID, UUID
   * @param identifierSystem Identifier system
   * @param profile Profile system
   */
  public record IdentifierProps(IdentifierType identifierType, String identifierSystem, String profile) {}
}
