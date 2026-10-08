package com.tateti.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread {

    private Socket socket;
    private String nombreJugador;
    private GameManager gameManager;
    private char simbolo;
    private PrintWriter salida;

    public ClienteHandler(Socket socket, String nombreJugador, GameManager gameManager, char simbolo) {

        this.socket = socket;
        this.nombreJugador = nombreJugador;
        this.gameManager = gameManager;
        this.simbolo = simbolo;
    }

    @Override
    public void run() {

        try {

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            salida = new PrintWriter(
                    socket.getOutputStream(),
                    true);

            enviarMensaje("Conectado como " + simbolo);

            String mensaje;

            while ((mensaje = entrada.readLine()) != null) {

                String[] partes = mensaje.split(",");

                int fila = Integer.parseInt(partes[0]);
                int columna = Integer.parseInt(partes[1]);

                gameManager.procesarMovimiento(this, fila, columna, simbolo);
            }

        } catch (IOException e) {

            System.out.println(nombreJugador + " se desconectó");
        }
    }

    public void enviarMensaje(String mensaje) {

        salida.println(mensaje);
    }

    public String getNombreJugador() {

        return nombreJugador;
    }

    public char getSimbolo() {

        return simbolo;
    }
}