package com.rodrigo.rabbitmq_consumer.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.rodrigo.rabbitmq_consumer.models.PacienteModel;
import com.rodrigo.rabbitmq_consumer.services.PacienteService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @GetMapping("/novo")
    public ModelAndView novoFormulario(ModelMap model) {
        model.addAttribute("pacienteModel", new PacienteModel());
        return new ModelAndView("paciente/form", model);
    }

    @PostMapping("/salvar")
    public String savePaciente(@Valid @ModelAttribute("pacienteModel") PacienteModel pacienteModel,
            BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "paciente/form";
        }

        @SuppressWarnings("unused")
        PacienteModel savedPaciente;

        if (pacienteModel.getId() != null) {
            // Atualizar paciente existente
            PacienteModel existingPaciente = pacienteService.findByid(pacienteModel.getId());
            if (existingPaciente == null) {
                attr.addFlashAttribute("error", "Paciente não encontrado.");
                return "redirect:/pacientes/list";
            }
            savedPaciente = pacienteService.salvaOuAtualiza(pacienteModel);
            attr.addFlashAttribute("feedback", "Paciente atualizado com sucesso!");
        } else {
            // Salvar novo paciente
            savedPaciente = pacienteService.salvaOuAtualiza(pacienteModel);
            attr.addFlashAttribute("feedback", "Paciente cadastrado com sucesso!");
        }

        // Redirecionar para a lista de pacientes
        return "redirect:/pacientes/list";
    }

    @GetMapping("/list")
    public ModelAndView listPaciente(ModelMap model) {
        List<PacienteModel> listPaciente = pacienteService.findAll();
        model.addAttribute("pacienteModel", listPaciente);
        return new ModelAndView("paciente/list", model);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView viewPacienteForm(@PathVariable("id") Long id, ModelMap model) {
        PacienteModel viewPaciente = pacienteService.findByid(id);
        if (viewPaciente == null) {
            model.addAttribute("pacienteModel", "viewPaciente");
            return new ModelAndView("redirect:/pacientes/list");
        }

        model.addAttribute("pacienteModel", viewPaciente);
        return new ModelAndView("paciente/form", model);
    }

    @GetMapping("/gerarPdf/{id}")
    public void gerarPdf(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        PacienteModel gerarPdfPaciente = pacienteService.findByid(id);
        if (gerarPdfPaciente == null) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Paciente não encontrado");
            return;
        }

        // Crie um contexto para o Thymeleaf e adicione o paciente ao modelo
        Context context = new Context();
        context.setVariable("paciente", gerarPdfPaciente);

        // Renderize o template Thymeleaf para HTML
        String htmlContent = templateEngine.process("paciente/viewPdf", context);

        // Gera o PDF a partir do HTML
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        try (OutputStream outputStream = pdfStream) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, "/");
            builder.toStream(outputStream);
            builder.run();
        }

        // Crie o nome do arquivo PDF com o nome do paciente
        String fileName = gerarPdfPaciente.getNomeCompleto().replaceAll("\\s+", "_") + ".pdf";

        // Defina os cabeçalhos da resposta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        response.setContentLength(pdfStream.size());

        // Envie o PDF na resposta
        try (OutputStream responseOutputStream = response.getOutputStream()) {
            pdfStream.writeTo(responseOutputStream);
            responseOutputStream.flush();
        }
    }
}
