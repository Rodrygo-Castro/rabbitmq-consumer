package com.rodrigo.rabbitmq_consumer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigo.rabbitmq_consumer.models.PacienteModel;
import com.rodrigo.rabbitmq_consumer.repositorys.PacienteRepository;

import jakarta.transaction.Transactional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public PacienteModel salvaOuAtualiza(PacienteModel pacienteModel) {
        try {
            PacienteModel savedPaciente = pacienteRepository.save(pacienteModel);
            return savedPaciente;
        } catch (Exception e) {
            // Lidar com a exceção, como registrar logs ou lançar uma exceção personalizada
            throw new RuntimeException("Erro ao salvar ou atualizar o paciente", e);
        }
    }    

    @Transactional
    public void saveOrUpdate(PacienteModel pacienteModel) {
        // Verifica se o paciente já existe pelo CPF
        Optional<PacienteModel> existingPacienteOptional = pacienteRepository.findByCpf(pacienteModel.getCpf());

        if (existingPacienteOptional.isPresent()) {
            // Paciente já existe, então atualiza os dados
            PacienteModel existingPaciente = existingPacienteOptional.get();
            // Atualize os campos necessários do paciente existente com os novos dados
            existingPaciente.setNomeCompleto(pacienteModel.getNomeCompleto());
            existingPaciente.setDataNascimento(pacienteModel.getDataNascimento());
            existingPaciente.setSexo(pacienteModel.getSexo());
            existingPaciente.setCelular(pacienteModel.getCelular());
            existingPaciente.setEmail(pacienteModel.getEmail());
            existingPaciente.setCep(pacienteModel.getCep());
            existingPaciente.setBairro(pacienteModel.getBairro());
            existingPaciente.setCidade(pacienteModel.getCidade());
            existingPaciente.setEstado(pacienteModel.getEstado());
            existingPaciente.setNumero(pacienteModel.getNumero());
            existingPaciente.setRua(pacienteModel.getRua());
            existingPaciente.setGestante(pacienteModel.getGestante());
            existingPaciente.setFebre(pacienteModel.getFebre());
            existingPaciente.setDiagnostico(pacienteModel.getDiagnostico());
            existingPaciente.setExame(pacienteModel.getExame());
            existingPaciente.setSintomas(pacienteModel.getSintomas());

            // Salva o paciente atualizado
            pacienteRepository.save(existingPaciente);
        } else {
            // Paciente não existe, então insere um novo
            pacienteRepository.save(pacienteModel);
        }
    }

    public PacienteModel findByid(Long id) {
        Optional<PacienteModel> pacienteModel = pacienteRepository.findById(id);
        return pacienteModel.orElse(null);
    }

    public List<PacienteModel> findAll() {
        return pacienteRepository.findAll();
    }
}
