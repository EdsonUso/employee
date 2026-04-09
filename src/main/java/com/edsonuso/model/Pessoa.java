package com.edsonuso.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    private static final DateTimeFormatter FMT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "Data de nascimento não pode ser nula");
    }

    public Integer getIdade() {
        LocalDate dataAtual = LocalDate.now();

        return Period.between(dataNascimento, dataAtual).getYears();
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getDataNascimentoFormatada() {
        return dataNascimento.format(FMT_DATA);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
