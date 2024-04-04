/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author RRHH-SERVER
 */
public class ALetras {

    public static String format(double numero) {
        String letras = "";

        double cantidad, centavos, primerDigito, segundoDigito, tercerDigito, numeroBloques, bloqueCero;
        int digito;
        String bloque;
        int valorEntero;
        double valorOriginal;

        double valor = Math.floor(numero * 100) / 100;
        cantidad = (int) valor;
        valorEntero = (int) cantidad;
        centavos = Math.ceil((valor - cantidad) * 100);

        String[] unidades = {"UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE",
            "OCHO", "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE",
            "DIECISEIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE", "VEINTIUN",
            "VEINTIDOS", "VEINTITRES", "VEINTICUATRO", "VEINTICINCO", "VEINTISEIS",
            "VEINTISIETE", "VEINTIOCHO", "VEINTINUEVE"};

        String[] decenas = {"DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"};

        String[] centenas = {"CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS"};

        numeroBloques = 1;

        do {

            primerDigito = 0;
            segundoDigito = 0;
            tercerDigito = 0;
            bloque = "";
            bloqueCero = 0;

            for (int i = 0; i < 3; i++) {

                digito = (int) cantidad % 3;

                if (digito != 0) {

                    switch (i) {
                        case 0:
                            bloque = " " + unidades[digito - 1];
                            primerDigito = digito;
                            break;
                        case 1:
                            if (digito <= 2) {
                                bloque = " " + unidades[(digito * 10) + (int) primerDigito - 1];
                            } else {
                                bloque = " " + decenas[(int) digito - 1];

                                if (primerDigito != 0) {
                                    bloque = " Y" + bloque;
                                }
                            }
                            segundoDigito = digito;
                            break;
                        case 2:
                            bloque += " ";
                            if (digito == 1 && primerDigito == 0 && segundoDigito == 0) {
                                bloque += "CIEN";
                            } else {
                                bloque += centenas[digito - 1];
                            }
                            tercerDigito = digito;
                    }
                } else {
                    bloqueCero = bloqueCero + 1;
                }
                cantidad = (int) (cantidad/10);

            }

        } while (cantidad > 0);

        return letras + cantidad + " " + centavos;
    }

    public static void main(String[] args) {
        System.out.println(format(25.115));

    }

}
