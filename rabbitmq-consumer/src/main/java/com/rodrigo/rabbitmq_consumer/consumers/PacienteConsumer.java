package com.rodrigo.rabbitmq_consumer.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rodrigo.rabbitmq_consumer.dtos.PacienteRecordDto;
import com.rodrigo.rabbitmq_consumer.models.PacienteModel;
import com.rodrigo.rabbitmq_consumer.services.PacienteService;

@Component
public class PacienteConsumer {

    @Autowired
    private PacienteService pacienteService;
    
    @RabbitListener(queues = "${broker.queue.patient.name}")
    public void listenPacienteQueue(@Payload PacienteRecordDto pacienteRecordDto) {
        PacienteModel pacienteModel = new PacienteModel();
        pacienteModel.setId(pacienteRecordDto.id());
        pacienteModel.setNomeCompleto(pacienteRecordDto.nomeCompleto());
        pacienteModel.setCpf(pacienteRecordDto.cpf());
        pacienteModel.setDataNascimento(pacienteRecordDto.dataNascimento());
        pacienteModel.setSexo(pacienteRecordDto.sexo());
        pacienteModel.setCelular(pacienteRecordDto.celular());
        pacienteModel.setEmail(pacienteRecordDto.email());
        pacienteModel.setCep(pacienteRecordDto.cep());
        pacienteModel.setBairro(pacienteRecordDto.bairro());
        pacienteModel.setCidade(pacienteRecordDto.cidade());
        pacienteModel.setEstado(pacienteRecordDto.estado());
        pacienteModel.setNumero(pacienteRecordDto.numero());
        pacienteModel.setRua(pacienteRecordDto.rua());
        pacienteModel.setGestante(pacienteRecordDto.gestante());
        pacienteModel.setFebre(pacienteRecordDto.febre());
        pacienteModel.setDiagnostico(pacienteRecordDto.diagnostico());
        pacienteModel.setExame(pacienteRecordDto.exame());
        pacienteModel.setSintomas(pacienteRecordDto.sintomas());
        
        pacienteService.saveOrUpdate(pacienteModel);
    }
}
