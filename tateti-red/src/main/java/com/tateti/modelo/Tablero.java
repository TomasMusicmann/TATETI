package com.tateti.modelo;

public class Tablero {

    private char[][] tablero;

    public Tablero() {
        tablero = new char[3][3];
        inicializarTablero();
    }

    private void inicializarTablero() {

        for (int fila = 0; fila < 3; fila++) {

            for (int columna = 0; columna < 3; columna++) {

                tablero[fila][columna] = '-';
            }
        }
    }

    public boolean hacerMovimiento(int fila, int columna, char simbolo) {
        if (tablero[fila][columna] == '-') {
            tablero[fila][columna] = simbolo;
            return true;
        }
        return false;
    }

    public void mostrarTablero() {

        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                System.out.println(tablero[fila][columna] + " ");
            }
            System.out.println();
        }
    }

    public boolean verificarGanador(char simbolo) {

        // VERIFICAR FILAS
        for (int fila = 0; fila < 3; fila++) {

            if (tablero[fila][0] == simbolo &&
                    tablero[fila][1] == simbolo &&
                    tablero[fila][2] == simbolo) {

                return true;
            }
        }

        // VERIFICAR COLUMNAS
        for (int columna = 0; columna < 3; columna++) {

            if (tablero[0][columna] == simbolo &&
                    tablero[1][columna] == simbolo &&
                    tablero[2][columna] == simbolo) {

                return true;
            }
        }

        // DIAGONAL PRINCIPAL
        if (tablero[0][0] == simbolo &&
                tablero[1][1] == simbolo &&
                tablero[2][2] == simbolo) {

            return true;
        }

        // DIAGONAL SECUNDARIA
        if (tablero[0][2] == simbolo &&
                tablero[1][1] == simbolo &&
                tablero[2][0] == simbolo) {

            return true;
        }

        return false;
    }

    public boolean tableroLleno() {

        for (int fila = 0; fila < 3; fila++) {

            for (int columna = 0; columna < 3; columna++) {

                if (tablero[fila][columna] == '-') {

                    return false;
                }
            }
        }

        return true;
    }

    public void reiniciarTablero() {

    for (int fila = 0; fila < 3; fila++) {

        for (int columna = 0; columna < 3; columna++) {

            tablero[fila][columna] = '-';
        }
    }
}
}
