package io.simplelocalize.demo.simplelocalizespringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalizedRestApiExceptionController
{

  private int counter = -1;
  @Autowired
  private MessageSource messageSource;
  @GetMapping("/api/my-health")
  public ApiResponse getMyHealth()
  {
    counter++;
    if (counter == 0)
    {
      throw new IllegalArgumentException(); // Treated as bad request
    }

    if (counter == 1)
    {
      throw new IllegalStateException(); // Treated as internal server error
    }
    counter = -1;
    return new ApiResponse(
            messageSource.getMessage("message", null, LocaleContextHolder.getLocale()),
            HttpStatus.OK
    );
  }
}
