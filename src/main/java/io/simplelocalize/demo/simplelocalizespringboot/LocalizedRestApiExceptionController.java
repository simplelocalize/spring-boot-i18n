package io.simplelocalize.demo.simplelocalizespringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class LocalizedRestApiExceptionController
{
  @GetMapping("/api/my-health")
  public String getMyHealth()
  {
    if (ThreadLocalRandom.current().nextBoolean())
    {
      throw new IllegalArgumentException(); // Treated as bad request
    }

    if (ThreadLocalRandom.current().nextBoolean())
    {
      throw new IllegalStateException(); // Treat as internal server error
    }

    return "Everything is fine!";
  }
}
