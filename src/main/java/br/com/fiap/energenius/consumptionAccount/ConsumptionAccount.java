package br.com.fiap.energenius.consumptionAccount;

import br.com.fiap.energenius.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.YearMonth;

@Entity
@Data
@Table(name = "consumption_account")
public class ConsumptionAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "consumption_id_seq", sequenceName = "consumption_id_seq", allocationSize = 1)
    @Column(name = "consumption_id")
    Long consumptionId;

    @Column(name = "energy_consumption")
    float energyConsumption;

    @Column(name = "month_year_bill")
    String monthYearBill;

    @Column(name = "bill_cost")
    float billCost;

    @ManyToOne
    @JoinColumn(name = "customer_email", referencedColumnName = "email", nullable = false)
    User costumerEmail;
}
