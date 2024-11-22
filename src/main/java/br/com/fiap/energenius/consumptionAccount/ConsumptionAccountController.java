package br.com.fiap.energenius.consumptionAccount;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ConsumptionAccountController {

    private final ConsumptionAccountService consumptionAccountService;
    private final RabbitTemplate rabbitTemplate;
    private final ConsumptionAccountRepository consumptionAccountRepository;

    public ConsumptionAccountController(ConsumptionAccountService consumptionAccountService, RabbitTemplate rabbitTemplate, ConsumptionAccountRepository consumptionAccountRepository) {
        this.consumptionAccountService = consumptionAccountService;
        this.rabbitTemplate = rabbitTemplate;
        this.consumptionAccountRepository = consumptionAccountRepository;
    }

    @GetMapping("/energy-data-form")
    public String showForm(Model model) {
        return "form";
    }

    @GetMapping("/")
    public String showDashboard(Model model) {
        List<ConsumptionAccount> data = consumptionAccountRepository.findAll();

        // Preparar listas para Thymeleaf
        List<String> labels = data.stream()
                .map(ConsumptionAccount::getMonthYearBill)
                .collect(Collectors.toList());
        List<Float> consumptionData = data.stream()
                .map(ConsumptionAccount::getEnergyConsumption)
                .collect(Collectors.toList());
        List<Float> costData = data.stream()
                .map(ConsumptionAccount::getBillCost)
                .collect(Collectors.toList());

        model.addAttribute("pageTitle", "Energy Consumption Dashboard");
        model.addAttribute("labels", labels);
        model.addAttribute("consumptionData", consumptionData);
        model.addAttribute("costData", costData);

        return "index"; // Nome do arquivo HTML
    }

    @PostMapping("/save-energy-data")
    public String sendData(@ModelAttribute ConsumptionAccount c) {
        System.out.println(c);
        consumptionAccountService.sendData(c);
        rabbitTemplate.convertAndSend("email-queue", "Nova tarefa cadastrada: " + c.costumerEmail);
        return "redirect:/";
    }

}
