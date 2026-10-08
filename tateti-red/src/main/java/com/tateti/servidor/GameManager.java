package com.tateti.servidor;

import com.tateti.modelo.Tablero;

public class GameManager {

    private Tablero tablero;
    private ClienteHandler jugador1;
    private ClienteHandler jugador2;
    private char turnoActual;

    public GameManager() {

        tablero = new Tablero();

        turnoActual = 'X';
    }

    public void setJugadores(
            ClienteHandler jugador1,
            ClienteHandler jugador2) {

        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    public synchronized void procesarMovimiento(ClienteHandler jugador, int fila, int columna, char simbolo) {

        if (simbolo != turnoActual) {

            jugador.enviarMensaje("No es tu turno");

            return;
        }

        System.out.println(
                jugador.getNombreJugador()
                        + " jugó en "
                        + fila + "," + columna);

        boolean valido = tablero.hacerMovimiento(
                fila,
                columna,
                simbolo);

        if (valido) {

            tablero.mostrarTablero();

            enviarATodos(
                    "MOVIMIENTO,"
                            + fila + ","
                            + columna + ","
                            + simbolo);

            if (tablero.verificarGanador(simbolo)) {

                enviarATodos(jugador.getNombreJugador()+ " ganó la partida");
                reiniciarJuego();
                return;
            }

            if (tablero.tableroLleno()) {
                enviarATodos("Empate");
                reiniciarJuego();
                return;
            }

            cambiarTurno();
        } else {

            jugador.enviarMensaje("Casilla ocupada");
        }
    }

    // ===== ENVIAR A TODOS =====
    private void enviarATodos(String mensaje) {

        if (jugador1 != null) {

            jugador1.enviarMensaje(mensaje);
        }

        if (jugador2 != null) {

            jugador2.enviarMensaje(mensaje);
        }
    }

    // ===== CAMBIAR TURNO =====
    private void cambiarTurno() {

        if (turnoActual == 'X') {

            turnoActual = 'O';

        } else {

            turnoActual = 'X';
        }

        enviarATodos("Turno de: " + turnoActual);
    }

    private void reiniciarJuego() {

        tablero.reiniciarTablero();

        turnoActual = 'X';

        enviarATodos("REINICIO");
    }
}