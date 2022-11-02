package hr.xmjosic.fhirhapidemoapp.configuration;

import ca.uhn.fhir.context.FhirContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.instance.model.api.IBaseResource;

import java.io.IOException;

/** FHIR Deserializer */
@RequiredArgsConstructor
public class FhirDeserializer<T extends IBaseResource> extends JsonDeserializer<T> {

  private final FhirContext fhirContext;
  private final Class<T> clazz;

  @Override
  public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    final String s = jsonParser.getCodec().readTree(jsonParser).toString();
    return fhirContext.newJsonParser().parseResource(clazz, s);
  }
}
