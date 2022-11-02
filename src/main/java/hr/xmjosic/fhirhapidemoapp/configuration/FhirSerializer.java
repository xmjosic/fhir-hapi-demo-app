package hr.xmjosic.fhirhapidemoapp.configuration;

import ca.uhn.fhir.context.FhirContext;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * Json serializer
 *
 * @param <T> extends {@link IBaseResource}
 */
@JsonComponent
@RequiredArgsConstructor
public class FhirSerializer<T extends IBaseResource> extends JsonSerializer<T> {

  private final FhirContext fhirContext;

  @Override
  public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    if (jsonGenerator instanceof ToXmlGenerator) {
      ((ToXmlGenerator)jsonGenerator)._handleStartObject();
      jsonGenerator.writeRaw(fhirContext.newXmlParser().setPrettyPrint(true).encodeResourceToString(t));
      ((ToXmlGenerator) jsonGenerator)._handleEndObject();
    } else {
      jsonGenerator.writeRaw(
              fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(t));
    }
  }
}
