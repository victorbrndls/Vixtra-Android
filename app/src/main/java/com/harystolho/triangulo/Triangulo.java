package com.harystolho.triangulo;

class Triangulo {

    private TipoDeTrangulo tipo;
    private float ladoA;
    private float ladoB;
    private float ladoC;

    public Triangulo(float ladoA, float ladoB, float ladoC) {
        this.ladoA = ladoA;
        this.ladoB = ladoB;
        this.ladoC = ladoC;
        this.tipo = definirTipo();
    }

    private TipoDeTrangulo definirTipo() {
        if (ladoA + ladoB <= ladoC || ladoA + ladoC <= ladoB || ladoB + ladoC <= ladoA)
            throw new TrianguloInvalidoException();

        if (ladoA == ladoB && ladoB == ladoC)
            return TipoDeTrangulo.EQUILATERO;

        if (ladoA == ladoB || ladoA == ladoC || ladoB == ladoC)
            return TipoDeTrangulo.ISOSCELES;

        return TipoDeTrangulo.ESCALENO;
    }

    public TipoDeTrangulo getTipo() {
        return tipo;
    }
}
