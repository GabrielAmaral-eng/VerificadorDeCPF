package com.example.demo.controllers;

import com.example.demo.services.AutomatoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validador")
public class CpfController {

    @GetMapping
    public ResponseEntity<String> validarCPF(@RequestParam String cpf){
        if(!AutomatoService.VerificarFormatoCPF(cpf)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de CPF inválido. Use XXX.XXX.XXX-XX");
        }
        int dv1Esperado = Character.getNumericValue(cpf.charAt(12));
        int dv2Esperado = Character.getNumericValue(cpf.charAt(13));
        if (AutomatoService.calcularDigitosVerificadores(cpf)[0] == dv1Esperado &&
                AutomatoService.calcularDigitosVerificadores(cpf)[1] == dv2Esperado){
            return ResponseEntity.status(HttpStatus.OK).body("CPF Válido");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro nos digitos verificadores. CPF Inválido");
    }

    @GetMapping("/gerar")
    public ResponseEntity<String> gerarCpfValido() {
        String cpfGerado = AutomatoService.gerarCpfValido();
        return ResponseEntity.ok(cpfGerado);
    }

}
