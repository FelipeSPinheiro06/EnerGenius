package br.com.fiap.energenius.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Data
@Table(name = "customer")
public class User {

    @Id
    String email;

    String password;
}
