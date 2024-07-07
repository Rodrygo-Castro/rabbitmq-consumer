package com.rodrigo.rabbitmq_consumer.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodrigo.rabbitmq_consumer.models.PacienteModel;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {
    Optional<PacienteModel> findByCpf(String cpf);
}
