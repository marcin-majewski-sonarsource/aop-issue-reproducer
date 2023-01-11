package example.micronaut;

import io.micronaut.function.aws.MicronautRequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import jakarta.inject.Inject;
import java.util.Collections;

public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
  @Inject
  ObjectMapper objectMapper;

  @Inject
  Service service;

  @Override
  public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    try {
      //this will return not-intercepted
      //String json = objectMapper.writeValueAsString(Collections.singletonMap("message", methodToBeIntercepted()));

      //this will return intercepted
      String json = objectMapper.writeValueAsString(Collections.singletonMap("message", service.methodToBeIntercepted()));

      response.setStatusCode(200);
      response.setBody(json);
    } catch (JsonProcessingException e) {
      response.setStatusCode(500);
    }
    return response;
  }

  @ExampleAopAnnotation
  public String methodToBeIntercepted() {
    return "not-intercepted";
  }

}
