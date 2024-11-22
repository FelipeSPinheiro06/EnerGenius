package br.com.fiap.energenius.consumptionAccount;

import br.com.fiap.energenius.auth.SecurityConfig;
import br.com.fiap.energenius.user.User;
import br.com.fiap.energenius.user.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ConsumptionAccountController {

    private final ConsumptionAccountService consumptionAccountService;
    private final RabbitTemplate rabbitTemplate;
    private final ConsumptionAccountRepository consumptionAccountRepository;
    private final SecurityConfig securityConfig;
    private final UserRepository userRepository;

    public ConsumptionAccountController(ConsumptionAccountService consumptionAccountService, RabbitTemplate rabbitTemplate, ConsumptionAccountRepository consumptionAccountRepository, SecurityConfig securityConfig, UserRepository userRepository) {
        this.consumptionAccountService = consumptionAccountService;
        this.rabbitTemplate = rabbitTemplate;
        this.consumptionAccountRepository = consumptionAccountRepository;
        this.securityConfig = securityConfig;
        this.userRepository = userRepository;
    }

    @GetMapping("/energy-data-form")
    public String showForm(Model model) {
        return "form";
    }

    @GetMapping("/")
    public String getConsumptionData(Model model) {
        // Obtém o e-mail do usuário logado
        String loggedUserEmail = securityConfig.getLoggedInUserEmail();

        User user = userRepository.findByEmail(loggedUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Busca os dados no banco de dados para o usuário logado
        List<ConsumptionAccount> consumptionAccounts = consumptionAccountRepository.findByEmail(user);

        // Extrai os dados para gráficos
        List<String> labels = consumptionAccounts.stream()
                .map(ConsumptionAccount::getMonthYearBill)
                .toList();
        List<Float> consumptionData = consumptionAccounts.stream()
                .map(ConsumptionAccount::getEnergyConsumption)
                .toList();
        List<Float> costData = consumptionAccounts.stream()
                .map(ConsumptionAccount::getBillCost)
                .toList();

        // Adiciona os dados ao modelo para renderização na página
        model.addAttribute("labels", labels);
        model.addAttribute("consumptionData", consumptionData);
        model.addAttribute("costData", costData);
        model.addAttribute("pageTitle", "Energy Consumption Dashboard");

        return "index";
    }

    @PostMapping("/save-energy-data")
    public String sendData(@ModelAttribute ConsumptionAccount c) {
        System.out.println(c);
        consumptionAccountService.sendData(c);
        rabbitTemplate.convertAndSend("email-queue", "Nova tarefa cadastrada: " + c.email);
        return "redirect:/";
    }

}
