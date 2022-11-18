package io.simplelocalize.demo.simplelocalizespringboot;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Component
public class HtmlRenderer
{
  private static final String HTML_TEMPLATE_FILE_NAME = "my-html-template";
  private final TemplateEngine templateEngine;

  public HtmlRenderer(TemplateEngine templateEngine)
  {
    this.templateEngine = templateEngine;
  }

  public String renderHtmlFromTemplate(Locale locale, String userName)
  {
    Context context = new Context();
    context.setLocale(locale);
    context.setVariable("userName", userName);
    context.setVariable("lang", locale.getLanguage());
    context.setVariable("url", "https://simplelocalize.io");
    return templateEngine.process(HTML_TEMPLATE_FILE_NAME, context);
  }
}
