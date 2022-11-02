package hr.xmjosic.fhirhapidemoapp.util.parameters;

import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.StringType;

/** FHIR Patch parameters maker. */
public class HapiFhirParameters {

  private static final String OPERATION = "operation";
  private static final String TYPE = "type";
  private static final String PATH = "path";
  private static final String NAME = "name";
  private static final String VALUE = "value";
  private static final String ADD = "add";
  private static final String STATUS = "status";
  private static final String CANCELLED = "cancelled";

  /**
   * Generate parameters to change resource status to {@code CANCELLED} with add operation.
   *
   * @param path Path at which to add the content
   * @return Returns parameters resource which adds/replaces status.
   */
  public Parameters changeStatusToCancelledParameter(String path) {
    Parameters parameters = new Parameters();
    Parameters.ParametersParameterComponent parameter = parameters.addParameter();
    parameter.setName(OPERATION);
    parameter.addPart().setName(TYPE).setValue(new CodeType(ADD));
    parameter.addPart().setName(PATH).setValue(new StringType(path));
    parameter.addPart().setName(NAME).setValue(new StringType(STATUS));
    parameter.addPart().setName(VALUE).setValue(new CodeType(CANCELLED));
    return parameters;
  }
}
