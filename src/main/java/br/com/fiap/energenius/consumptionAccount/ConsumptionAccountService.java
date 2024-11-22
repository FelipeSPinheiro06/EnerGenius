package br.com.fiap.energenius.consumptionAccount;

import br.com.fiap.energenius.auth.SecurityConfig;
import br.com.fiap.energenius.user.User;
import br.com.fiap.energenius.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionAccountService {

    private final ConsumptionAccountRepository consumptionAccountRepository;
    private final SecurityConfig securityConfig;
    private final UserRepository userRepository;

    public ConsumptionAccountService(ConsumptionAccountRepository consumptionAccountRepository, SecurityConfig securityConfig, UserRepository userRepository) {
        this.consumptionAccountRepository = consumptionAccountRepository;
        this.securityConfig = securityConfig;
        this.userRepository = userRepository;
    }

    public ConsumptionAccount sendData(ConsumptionAccount c) {
        String email = securityConfig.getLoggedInUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        c.setEmail(user);
        System.out.println("ConsumptionAccount antes do save: " + c);
        return consumptionAccountRepository.save(c);
    }
}
