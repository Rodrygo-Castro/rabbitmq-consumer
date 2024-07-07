package com.rodrigo.rabbitmq_consumer.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.rodrigo.rabbitmq_consumer.models.PacienteModel;

@Service
public class PdfService {

    @Autowired
    private SpringTemplateEngine templateEngine;

    public byte[] gerarPdf(PacienteModel paciente) throws IOException {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não pode ser nulo");
        }

        // Renderizar o HTML com Thymeleaf
        Context context = new Context();
        context.setVariable("paciente", paciente);
        String htmlContent = templateEngine.process("paciente", context);

        // Converter HTML para PDF
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, "classpath:/templates/viewPdf");
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IOException("Erro ao gerar PDF", e);
        }
    }
}
