package br.com.fiap.energenius.consumptionAccount;

import br.com.fiap.energenius.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.YearMonth;

@Entity
@Data
@Table(name = "consumption_account")
public class ConsumptionAccount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long consumption_id;

    float energy_consumption;

    YearMonth month_year_bill;

    float bill_cost;

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    User costumer_email;
}
