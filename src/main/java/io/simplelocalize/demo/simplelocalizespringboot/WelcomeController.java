package io.simplelocalize.demo.simplelocalizespringboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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
