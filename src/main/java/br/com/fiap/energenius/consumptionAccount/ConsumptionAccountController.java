package br.com.fiap.energenius.consumptionAccount;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConsumptionAccountController {

    private final ConsumptionAccountService consumptionAccountService;

    public ConsumptionAccountController(ConsumptionAccountService consumptionAccountService) {
        this.consumptionAccountService = consumptionAccountService;
    }

    @GetMapping("/energy-data-form")
    public String showForm(Model model) {
        return "form";
    }

    @PostMapping("/save-energy-data")
    public String sendData(@ModelAttribute ConsumptionAccount c) {
        System.out.println(c);
        consumptionAccountService.sendData(c);
        return "redirect:/users";
    }

}
