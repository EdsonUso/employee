package com.edsonuso;

import com.edsonuso.model.Funcionario;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("0.10");
    private static final Set<Integer> MESES_ANIVERSARIO = Set.of(10, 12);

    /*
    private static final String payload = """
            [
              { "nome": "Maria", "dataNascimento": "2000-10-18", "salario": 2009.44, "funcao": "Operador" },
              { "nome": "João", "dataNascimento": "1990-05-12", "salario": 2284.38, "funcao": "Operador" },
              { "nome": "Caio", "dataNascimento": "1961-05-02", "salario": 9836.14, "funcao": "Coordenador" },
              { "nome": "Miguel", "dataNascimento": "1988-10-14", "salario": 19119.88, "funcao": "Diretor" },
              { "nome": "Alice", "dataNascimento": "1995-01-05", "salario": 2234.68, "funcao": "Recepcionista" },
              { "nome": "Heitor", "dataNascimento": "1999-11-19", "salario": 1582.72, "funcao": "Operador" },
              { "nome": "Arthur", "dataNascimento": "1993-03-31", "salario": 4071.84, "funcao": "Contador" },
              { "nome": "Laura", "dataNascimento": "1994-07-08", "salario": 3017.45, "funcao": "Gerente" },
              { "nome": "Heloísa", "dataNascimento": "2003-05-24", "salario": 1606.85, "funcao": "Eletricista" },
              { "nome": "Helena", "dataNascimento": "1996-09-02", "salario": 2799.93, "funcao": "Gerente" }
            ]
            """;

    List<Funcionario> funcionarios = JsonUtil.jsonToFuncionarios(payload);
    */

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(List.of(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        funcionarios.removeIf(f -> f.getNome().equals("João"));
        imprimirSeparador("Todos os funcionários");
        imprimirFuncionarios(funcionarios);

        funcionarios.forEach(f -> f.aplicarAumento(PERCENTUAL_AUMENTO));
        imprimirSeparador("Após aumento de 10%");
        imprimirFuncionarios(funcionarios);

         Map<String, List<Funcionario>> colPorFuncao = funcionarios.stream()
                 .collect(Collectors.groupingBy(
                         Funcionario::getFuncao,
                         TreeMap::new,
                         Collectors.toList()
                 ));

         imprimirSeparador("Funcionários agrupados por função");

         colPorFuncao.forEach((funcao, lista) -> {
             System.out.println(" " + funcao + ":");
             lista.forEach(f -> System.out.println("    - " + f));
         });

         imprimirSeparador("Aniversariantes (mês 10 e 12)");
         funcionarios.stream()
                         .filter(f -> MESES_ANIVERSARIO.contains(f.getDataNascimento().getMonthValue()))
                                 .forEach(f -> System.out.println(" " + f));

         imprimirSeparador("Funcionario com maior idade");
         funcionarios.stream()
                 .min(Comparator.comparing(Funcionario::getDataNascimento))
                 .ifPresent(f -> System.out.printf(" Nome: %s, Idade: %d anos%n",
                         f.getNome(), f.getIdade()));

         imprimirSeparador("Funcionarios ordenados por ordem alfabética");
         funcionarios.stream()
                 .sorted(Comparator.comparing(Funcionario::getNome))
                 .forEach(f -> System.out.println(" " + f));


         BigDecimal totalSalarios = funcionarios.stream()
                 .map(Funcionario::getSalario)
                 .reduce(BigDecimal.ZERO, BigDecimal::add);

         imprimirSeparador("Total dos salários");
         System.out.printf(" Total: R$ %s%n", formatarValor(totalSalarios));


         imprimirSeparador("Quantidade de salarios mínimos por funcionario");
         funcionarios.forEach(f -> System.out.printf(" %s: %s salarios minimos%n",
                 f.getNome(), f.calcularSalariosMinimos(SALARIO_MINIMO)));
    }


    private static void imprimirSeparador(String titulo) {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println(titulo);
        System.out.println("=".repeat(60));
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
         funcionarios.forEach(f -> System.out.println(" " + f));
    }



    private static String formatarValor(BigDecimal valor) {
        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return df.format(valor);
    }

}
