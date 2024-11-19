package br.com.fiap.energenius.consumptionAccount;

import br.com.fiap.energenius.auth.SecurityConfig;
import br.com.fiap.energenius.user.User;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionAccountService {

    private final ConsumptionAccountRepository consumptionAccountRepository;
    private final SecurityConfig securityConfig;

    public ConsumptionAccountService(ConsumptionAccountRepository consumptionAccountRepository, SecurityConfig securityConfig) {
        this.consumptionAccountRepository = consumptionAccountRepository;
        this.securityConfig = securityConfig;
    }

    public ConsumptionAccount sendData(ConsumptionAccount c) {
        String email = securityConfig.getLoggedInUserEmail();
        User user = new User();
        user.setEmail(email);
        c.setCostumer_email(user);
        return consumptionAccountRepository.save(c);
    }

}
