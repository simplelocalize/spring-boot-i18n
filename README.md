![translated spring boot page](/readme/translated-springboot-webpages.png)

# Simple internationalization (i18n) for Spring Boot

This project provides a simple guide and code to internationalize your Spring Boot application using default methods for Spring Framework. 
[Step-by-step guide with configuration part is available here](https://simplelocalize.io/blog/posts/spring-boot-simple-internationalization/).

### Project details:
- Java 19
- Spring Boot 3.0
- Thymeleaf
- Maven
- SimpleLocalize Editor

![simplelocalize](/readme/simplelocalize-editor.png)

## Usage


### Get translated messages

Use `MessageSource` to get translated messages. This is the default way to get translated messages in Spring Boot.

```java
import org.springframework.context.MessageSource;

@Autowired
private MessageSource messageSource;

@Test
void shouldGetTranslatedTextFromLocalFileAndLocale()
{
    //given
    Locale locale = Locale.of("pl", "PL");

    //when
    String titleTextWithArgument = messageSource.getMessage("title", new Object[]{"Foo Bar"}, locale);

    //then
    assert titleTextWithArgument.equals("Hej Foo Bar!");
}
```


### Thymeleaf: render HTML with translations


Use `ThymeleafEngine` bean to render HTML with translated messages. This is _probably_ the most 
popular way to render HTML with translated messages in Spring Boot.

```java

@Autowired
private TemplateEngine templateEngine;

public String renderHtmlFromTemplate(Locale locale, String userName)
{
    Context context = new Context();
    context.setLocale(locale);
    context.setVariable("userName", userName);
    context.setVariable("lang", locale.getLanguage());
    context.setVariable("url", "https://simplelocalize.io");
    return templateEngine.process("my-html-template", context);
}
```

![render custom HTML with translated texts](/readme/ide-with-spring-boot.png)

### Return translated web pages

You can also return translated web pages (HTML) by using standard Spring Boot `@Controller`.
Spring Boot will automatically resolve user locale and render HTML from `my-html-template.html` template with translated messages.

This is the default way to return translated web pages in Spring Boot. 

```java
@Controller
public class WelcomeController
{
  @GetMapping("/welcome")
  public String renderHtmlFromTemplate(Model model)
  {
    model.addAttribute("userName", "Jakub");
    return "my-html-template";
  }
}
```

Run the application and open `http://localhost:8080/welcome` in your browser.
You can change the language by adding `?lang=pl_PL` to the URL.

![changing lang parameter in spring boot](/readme/change-language-parameter.gif)
