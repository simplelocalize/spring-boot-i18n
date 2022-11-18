# Internationalization (i18n) for Spring Boot

This project provides a simple way to internationalize your Spring Boot application.

### Project details:
- Java 19
- Spring Boot 3.0
- Thymeleaf
- Maven
- SimpleLocalize


![translated spring boot page](/readme/translated-springboot-webpages.png)


## Configuration

### Create `messages_xx.properties` files

The messages are stored in the `messages_XX.properties` files. The `XX` is the language code. For example, `messages_pl_PL.properties` is the Polish version of the messages.

```properties
footerText=© 2023 SimpleLocalize. Wszelkie prawa zastrzeżone.
linkText=Utwórz konto SimpleLocalize
message=Dziękujemy za wypróbowanie naszego demo SimpleLocalize dla Spring Boot!
title=Hej {0}!
```

If you are using SimpleLocalize, you can download the `messages_XX.properties` files providing your `apiKey` in `simplelocalize.yml` file and invoking `simplelocalize download` command.

![download messages_xx.properties files via simplelocalize cli](/readme/java-properties-download.gif)

### Configure `messages_xx.properties` location

The default location for messages is `src/main/resources/messages`. 
You can change this by setting the `spring.messages.basename` property in your `application.properties` file
 or by providing your `ResourceBundleMessageSource` bean. 
 
```java
@Bean
public ResourceBundleMessageSource messageSource() {
    var resourceBundleMessageSource = new ResourceBundleMessageSource();
    resourceBundleMessageSource.setBasenames("i18n/messages"); // directory with messages_XX.properties
    resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
    resourceBundleMessageSource.setDefaultLocale(Locale.of("en"));
    resourceBundleMessageSource.setDefaultEncoding("UTF-8");
    return resourceBundleMessageSource;
}
```

![messages_xx.properties in IDE](/readme/messages_in_ide.png)

### Configure resolving locale from requests

The default locale resolver is `AcceptHeaderLocaleResolver` which resolves the locale from the `Accept-Language` header.
You can change this by setting the `spring.mvc.locale-resolver` property in your `application.properties` file or by providing your `LocaleResolver` bean, 
creating `LocaleChangeInterceptor` and registering it via `addInterceptors` method (see `WebMvcConfigurer` class).

```java
@Bean
public LocaleResolver localeResolver() {
    SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
    sessionLocaleResolver.setDefaultLocale(Locale.of("en"));
    return sessionLocaleResolver;
}

@Bean
public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    return localeChangeInterceptor;
}

@Override
public void addInterceptors(InterceptorRegistry registry)
{
  registry.addInterceptor(localeChangeInterceptor());
}
```

### Create HTML template with Thymeleaf

If you want to get an HTML document with translated messages, for example, to send via email, you can use Thymeleaf template engine.

```html
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" th:attr="lang=${lang}">

<head>
  <title>Spring Boot Email Example</title>
</head>

<body>
<header>
  <h1 th:utext="#{title(${userName})}"></h1>
</header>

<article>
  <p th:text="#{message}"></p>
  <a th:href="${url}" th:text="#{linkText}"></a>
</article>
<footer>
  <p th:text="#{footerText}"></p>
</footer>
</body>

</html>
```

#### Quick Thymeleaf guide:
 
- `th:attr="lang=${lang}"` - sets the language of the document
- `th:utext="#{title(${userName})}"` - gets a message with `title` key and inserts the value of the `userName` variable
- `th:text="#{message}"` - gets a message with `message` key
- `th:href="${url}"` - inserts a value of the `url` variable
- You can use `th:utext` instead of `th:text` to avoid escaping HTML characters.
- You can use `th:attr` to set the `lang` attribute on the `html` tag.

> You can use [https://mjml.io](https://mjml.io) to create responsive HTML emails for free.

## Usage

### Get translated messages

You can get translated messages by using `MessageSource` bean. This is the default way to get translated messages in Spring Boot.

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

You can render HTML with translated messages by using `ThymeleafEngine` bean. This is _probably_ the most 
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


You can return translated web pages (HTML) by using standard Spring Boot `@Controller`. 
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

![translated webpages from spring boot controller](/readme/translated-springboot-webpages.png)

Run the application and open `http://localhost:8080/welcome` in your browser.
You can change the language by adding `?lang=pl_PL` to the URL.

![changing lang parameter in spring boot](/readme/change-language-parameter.gif)
