package com.harystolho.triangulo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrianguloTest {

    @Test
    public void teste1() {
        Triangulo triangulo = new Triangulo(10, 10, 10);
        assertEquals(TipoDeTrangulo.EQUILATERO, triangulo.getTipo());
    }

    @Test
    public void teste2() {
        Triangulo triangulo = new Triangulo(3, 4, 5);
        assertEquals(TipoDeTrangulo.ESCALENO, triangulo.getTipo());
    }

    @Test
    public void teste3() {
        Triangulo triangulo = new Triangulo(3, 5, 5);
        assertEquals(TipoDeTrangulo.ISOSCELES, triangulo.getTipo());
    }

    @Test
    public void teste4() {
        Triangulo triangulo = new Triangulo(5, 3, 5);
        assertEquals(TipoDeTrangulo.ISOSCELES, triangulo.getTipo());
    }

    @Test
    public void teste5() {
        Triangulo triangulo = new Triangulo(5, 5, 3);
        assertEquals(TipoDeTrangulo.ISOSCELES, triangulo.getTipo());
    }

    @Test
    public void teste6() {
        assertThrows(TrianguloInvalidoException.class, () -> new Triangulo(3, 4, 0));
    }

    @Test
    public void teste7() {
        assertThrows(TrianguloInvalidoException.class, () -> new Triangulo(3, 4, -5));
    }

    @Test
    public void teste8() {
        assertThrows(TrianguloInvalidoException.class, () -> new Triangulo(2, 2, 4));
    }

    @Test
    public void teste9() {
        assertThrows(TrianguloInvalidoException.class, () -> new Triangulo(2, 4, 2));
    }

    @Test
    public void teste10() {
        assertThrows(TrianguloInvalidoException.class, () -> new Triangulo(4, 2, 2));
    }

    @Test
    public void teste11() {
        assertThrows(TrianguloInvalidoException.class, () -> new Triangulo(5, 4, 10));
    }

    @Test
    public void teste12() {
        assertThrows(TrianguloInvalidoException.class, () -> new Triangulo(10, 5, 4));
    }

    @Test
    public void teste13() {
        assertThrows(TrianguloInvalidoException.class, () -> new Triangulo(4, 5, 10));
    }

    @Test
    public void teste14() {
        assertThrows(TrianguloInvalidoException.class, () -> new Triangulo(0, 0, 0));
    }

    @Test
    public void teste15() {
        Triangulo triangulo = new Triangulo(2.34f, 1.25f, 1.47f);
        assertEquals(TipoDeTrangulo.ESCALENO, triangulo.getTipo());
    }
}