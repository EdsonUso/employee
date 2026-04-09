package com.edsonuso.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class Funcionario extends Pessoa{
    private BigDecimal salario;
    private String funcao;

    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private static final DecimalFormat FMT_SALARIO;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(LOCALE_BR);
        FMT_SALARIO = new DecimalFormat("#,##0.00", symbols);
    }

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = Objects.requireNonNull(salario, "Salário não pode ser nulo");
        this.funcao = Objects.requireNonNull(funcao, "Função não pode ser nula");
    }

    public BigDecimal calcularSalariosMinimos(BigDecimal salarioMinimo) {
        if (salarioMinimo == null || salarioMinimo.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Salario minimo inválido");
        }

        return getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }

    public void aplicarAumento(BigDecimal aumento) {
        BigDecimal novoSalario = getSalario()
                .add(getSalario().multiply(aumento))
                .setScale(2, RoundingMode.HALF_UP);

        this.salario = novoSalario;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getSalarioFormatado() {
        return FMT_SALARIO.format(salario);
    }

    public String getFuncao() {
        return funcao;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s, Data Nascimento: %s, Salário: %s, Função: %s",
                getNome(), getDataNascimentoFormatada(), getSalarioFormatado(), funcao);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(salario, that.salario) && Objects.equals(funcao, that.funcao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salario, funcao);
    }
}
