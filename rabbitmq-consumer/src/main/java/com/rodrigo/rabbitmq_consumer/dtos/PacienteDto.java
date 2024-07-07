package com.rodrigo.rabbitmq_consumer.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.rodrigo.rabbitmq_consumer.models.Febre;
import com.rodrigo.rabbitmq_consumer.models.Gestante;
import com.rodrigo.rabbitmq_consumer.models.Sexo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PacienteDto {

    private Long id;

    @NotBlank(message = "Informe o nome completo")
    private String nomeCompleto;

    @CPF(message = "CPF inválido")
    private String cpf;

    @Email(message = "Informe um e-mail válido")
    @NotBlank(message = "Informe um e-mail")
    private String email;

    @NotBlank(message = "Informe o número de celular")
    private String celular;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Informe a Data de Nascimento")
    private LocalDate dataNascimento;

    @NotNull(message = "Informe o gênero")
    private Sexo sexo;

    // Dados de endereço
    private String bairro;
    private String rua;
    private String cidade;
    private String cep;
    private Long numero;
    private String estado;

    // Dados médicos
    @NotNull(message = "Informe se é gestante")
    private Gestante gestante;

    @NotNull(message = "Informe se teve febre")
    private Febre febre;
    
    @NotBlank(message = "Informe o resultado do exame")
    private String exame;

    @NotNull(message = "Informe a Data do Diagnóstico")
    private LocalDate diagnostico;

    @NotBlank(message = "Informe os sintomas")
    private String sintomas;
}
