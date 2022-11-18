package io.simplelocalize.demo.simplelocalizespringboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
@Slf4j
class HtmlRendererTest
{

  @Autowired
  private HtmlRenderer htmlRenderer;

  @Autowired
  private MessageSource messageSource;

  @Test
  void shouldRenderPolishTextMessages()
  {
    //given
    String userName = "Jakub";
    Locale locale = Locale.of("pl", "PL");

    //when
    String renderHtml = htmlRenderer.renderHtmlFromTemplate(locale, userName);

    //then
    log.info(renderHtml);
    assert renderHtml.contains("lang=\"pl\"");
    assert renderHtml.contains("Hej Jakub!");
    assert renderHtml.contains("Dziękujemy za wypróbowanie naszego demo SimpleLocalize dla Spring Boot!");
    assert renderHtml.contains("Utwórz konto SimpleLocalize");
    assert renderHtml.contains("© 2023 SimpleLocalize. Wszelkie prawa zastrzeżone.");
  }

  @Test
  void shouldRenderDefaultTextMessages()
  {
    //given
    String userName = "Jacob";
    Locale locale = Locale.of("sv", "SE");

    //when
    String renderHtml = htmlRenderer.renderHtmlFromTemplate(locale, userName);

    //then
    log.info(renderHtml);
    assert renderHtml.contains("lang=\"sv\"");
    assert renderHtml.contains("Hey Jacob!");
    assert renderHtml.contains("Thanks for trying out our SimpleLocalize demo for Spring Boot!");
    assert renderHtml.contains("Create SimpleLocalize account");
    assert renderHtml.contains("© 2023 SimpleLocalize. All rights reserved.");
  }

  @Test
  void shouldGetTranslatedTextFromLocalFileAndLocale()
  {
    //given
    Locale locale = Locale.of("pl", "PL");

    //when
    String messageText = messageSource.getMessage("message", null, locale);

    //then
    assert messageText.equals("Dziękujemy za wypróbowanie naszego demo SimpleLocalize dla Spring Boot!");
  }

  @Test
  void shouldGetTranslatedTextWithArgument()
  {
    //given
    Locale locale = Locale.of("pl", "PL");

    //when
    String titleTextWithArgument = messageSource.getMessage("title", new Object[]{"Foo Bar"}, locale);

    //then
    assert titleTextWithArgument.equals("Hej Foo Bar!");
  }
}
