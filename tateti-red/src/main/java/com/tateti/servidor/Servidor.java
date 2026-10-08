package com.tateti.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static final int PUERTO = 5000;

    public static void main(String[] args) {

        System.out.println("Iniciando servidor...");

        GameManager gameManager = new GameManager();

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {

            System.out.println("Servidor escuchando en puerto " + PUERTO);

            System.out.println("Esperando jugador 1...");
            Socket jugador1 = serverSocket.accept();
            System.out.println("Jugador 1 conectado");

            ClienteHandler handler1 = new ClienteHandler(jugador1, "Jugador 1", gameManager, 'X');

            handler1.start();

            System.out.println("Esperando jugador 2...");
            Socket jugador2 = serverSocket.accept();
            System.out.println("Jugador 2 conectado");

            ClienteHandler handler2 = new ClienteHandler(jugador2, "Jugador 2", gameManager, 'O');

            handler2.start();

            gameManager.setJugadores(handler1, handler2);

            System.out.println("Partida iniciada");

        } catch (IOException e) {

            System.out.println("Error en el servidor");
            e.printStackTrace();
        }
    }
}