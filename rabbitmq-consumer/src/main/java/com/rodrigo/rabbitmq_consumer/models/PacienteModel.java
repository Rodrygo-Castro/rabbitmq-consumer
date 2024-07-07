package com.rodrigo.rabbitmq_consumer.models;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "pacientes") // Convenção de nome de tabela no plural
public class PacienteModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Informe o nome completo")
    @Column(nullable = false, length = 50)
    private String nomeCompleto;

    @CPF(message = "CPF inválido")
    @NotBlank(message = "Informe o CPF")
    @Column(unique = true, length = 14)
    private String cpf;

    @Email(message = "Informe um e-mail válido")
    @NotBlank(message = "Informe um e-mail")
    @Column(length = 50, nullable = false)
    private String email;

    @NotBlank(message = "Informe o número de celular")
    @Column(length = 15, nullable = false)
    private String celular;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Informe a Data de Nascimento")
    @Column(nullable = false, name = "data_nascimento", columnDefinition = "DATE")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Informe o gênero")
    @Column(nullable = false)
    private Sexo sexo;

    // Dados de endereço
    @Column(length = 50)
    private String bairro;

    @Column(length = 50)
    private String rua;

    @Column(length = 50)
    private String cidade;

    @Column(length = 10)
    private String cep;

    @NotNull(message = "Informe o número")
    @Column(nullable = false)
    private Long numero;

    @Column(length = 20)
    private String estado;

    // Dados médicos
    @Enumerated(EnumType.STRING)
    private Gestante gestante;

    @Enumerated(EnumType.STRING)
    private Febre febre;
    
    @NotBlank(message = "Informe o resultado do exame")
    @Column(nullable = false, length = 500)
    private String exame;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Informe a Data do Diagnóstico")
    @Column(nullable = false, name = "data_diagnostico", columnDefinition = "DATE")
    private LocalDate diagnostico;

    @NotBlank(message = "Informe os sintomas")
    @Column(nullable = false, length = 500)
    private String sintomas;
}
