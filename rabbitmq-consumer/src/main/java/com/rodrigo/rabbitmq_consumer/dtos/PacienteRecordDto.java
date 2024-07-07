package com.rodrigo.rabbitmq_consumer.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.rodrigo.rabbitmq_consumer.models.Febre;
import com.rodrigo.rabbitmq_consumer.models.Gestante;
import com.rodrigo.rabbitmq_consumer.models.Sexo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PacienteRecordDto(Long id,
                @NotBlank String nomeCompleto,
                @NotBlank @CPF String cpf,
                @NotBlank @Email String email,
                @NotBlank String celular,
                @NotNull LocalDate dataNascimento,
                @NotNull Sexo sexo,
                String bairro,
                String rua,
                String cidade,
                String cep,
                Long numero,
                String estado,
                @NotNull Gestante gestante,
                @NotNull Febre febre,
                @NotBlank String exame,
                @NotNull LocalDate diagnostico,
                @NotBlank String sintomas) {

}
