package com.harystolho.triangulo;

import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o lado A:");
        float ladoA = scanner.nextFloat();

        System.out.println("Digite o lado B:");
        float ladoB = scanner.nextFloat();

        System.out.println("Digite o lado C:");
        float ladoC = scanner.nextFloat();

        try {
            Triangulo triangulo = new Triangulo(ladoA, ladoB, ladoC);

            switch (triangulo.getTipo()) {
                case EQUILATERO:
                    System.out.println("Triangulo do tipo equilatero");
                    break;
                case ESCALENO:
                    System.out.println("Triangulo do tipo escaleno");
                    break;
                case ISOSCELES:
                    System.out.println("Triangulo do tipo isoceles");
                    break;
            }
        } catch (TrianguloInvalidoException e) {
            System.out.println("Triangulo invalido :/");
        }
    }

}
