package br.com.fiap.energenius.consumptionAccount;

import br.com.fiap.energenius.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumptionAccountRepository extends JpaRepository<ConsumptionAccount, Long> {
    List<ConsumptionAccount> findByEmail(User email);
}
