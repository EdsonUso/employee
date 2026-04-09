package com.edsonuso;

import com.edsonuso.model.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FuncionarioTest {
    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario("Maria", LocalDate.of(2000, 10, 18),
                new BigDecimal("2009.44"), "Operador");
    }

    @Test
    @DisplayName("Deve formatar salário com separador brasileiro")
    void deveFormatarSalario() {
        Funcionario f = new Funcionario("Teste", LocalDate.of(1990, 1, 1),
                new BigDecimal("19119.88"), "Diretor");
        assertEquals("19.119,88", f.getSalarioFormatado());
    }

    @Test
    @DisplayName("Deve aplicar aumento de 10% corretamente")
    void deveAplicarAumento() {
        funcionario.aplicarAumento(new BigDecimal("0.10"));
        assertEquals(0, new BigDecimal("2210.38").compareTo(funcionario.getSalario()));
    }

    @Test
    @DisplayName("Deve calcular salários mínimos corretamente")
    void deveCalcularSalariosMinimos() {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        BigDecimal resultado = funcionario.calcularSalariosMinimos(salarioMinimo);
        assertEquals(0, new BigDecimal("1.66").compareTo(resultado));
    }

    @Test
    @DisplayName("Deve agrupar funcionários por função")
    void deveAgruparPorFuncao() {
        List<Funcionario> lista = List.of(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador")
        );

        Map<String, List<Funcionario>> agrupado = lista.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        assertEquals(2, agrupado.size());
        assertEquals(2, agrupado.get("Operador").size());
        assertEquals(1, agrupado.get("Contador").size());
    }

    @Test
    @DisplayName("Deve rejeitar nome nulo")
    void deveRejeitarNomeNulo() {
        assertThrows(NullPointerException.class, () ->
                new Funcionario(null, LocalDate.of(2000, 1, 1), new BigDecimal("1000"), "Teste"));
    }

    @Test
    @DisplayName("Deve remover funcionário por nome")
    void deveRemoverPorNome() {
        List<Funcionario> lista = new ArrayList<>(List.of(
                funcionario,
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador")
        ));

        lista.removeIf(f -> f.getNome().equals("João"));
        assertEquals(1, lista.size());
        assertEquals("Maria", lista.get(0).getNome());
    }
}
