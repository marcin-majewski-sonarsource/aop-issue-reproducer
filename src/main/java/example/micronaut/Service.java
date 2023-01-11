package example.micronaut;

import jakarta.inject.Singleton;

@Singleton
public class Service {

  @ExampleAopAnnotation
  public String methodToBeIntercepted(){
    return "not-intercepted";
  }

}
