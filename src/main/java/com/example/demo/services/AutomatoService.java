package com.example.demo.services;

import org.springframework.stereotype.Service;

@Service
public class AutomatoService {

    private enum Estado{
        Q0, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14
    }

    public static boolean VerificarFormatoCPF(String cpf){
        Estado estadoAtual = Estado.Q0; //Estado inicial

        for(char c : cpf.toCharArray()){

            switch (estadoAtual){
                case Q0:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q1;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q1:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q2;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q2:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q3;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q3:
                    if (c == '.'){
                        estadoAtual = Estado.Q4;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q4:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q5;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q5:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q6;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q6:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q7;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q7:
                    if (c == '.'){
                        estadoAtual = Estado.Q8;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q8:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q9;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q9:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q10;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q10:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q11;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q11:
                    if (c == '-'){
                        estadoAtual = Estado.Q12;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q12:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q13;
                    }
                    else{
                        return false;
                    }
                    break;

                case Q13:
                    if (Character.isDigit(c)){
                        estadoAtual = Estado.Q14;
                    }
                    else{
                        return false;
                    }
                    break;
            }
        }
        return estadoAtual == Estado.Q14;
    }

    public static int[] calcularDigitosVerificadores(String cpf){
        int somaTotal = 0;
        int primeiro_digito;
        cpf = cpf.replaceAll("[^0-9]", "");

        for (int i = 0; i < 9; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            somaTotal += num * (10 - i);
        }

        int resto = somaTotal % 11;

        if (resto < 2){
            primeiro_digito = 0;
        }
        else {
            primeiro_digito = 11 - resto;
        }

        somaTotal = 0;

        for (int i = 0; i < 9; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            somaTotal += num * (11 - i);
        }
        somaTotal += primeiro_digito*2;

        int restoSegundo = somaTotal % 11;
        int segundo_digito;

        if (restoSegundo < 2) {
            segundo_digito = 0;
        } else {
            segundo_digito = 11 - restoSegundo;
        }

        return new int[]{primeiro_digito, segundo_digito};
    }


    //FEITO PELA IA PARA TESTAR
    public static String gerarCpfValido() {
        java.util.Random random = new java.util.Random();
        StringBuilder sb = new StringBuilder();

        // 1. Gera 9 dígitos aleatórios (0 a 9)
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }

        // 2. Calcula os dois dígitos verificadores para essa sequência
        // Passamos a string base e reaproveitamos o seu algoritmo
        int[] dvs = calcularDigitosVerificadores(sb.toString());

        // 3. Monta a string no formato aceito pelo AFD: XXX.XXX.XXX-XX
        return String.format("%s.%s.%s-%d%d",
                sb.substring(0, 3),
                sb.substring(3, 6),
                sb.substring(6, 9),
                dvs[0],
                dvs[1]
        );
    }
}
