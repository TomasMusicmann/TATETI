package com.tateti.cliente;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private static final String HOST = "localhost";
    private static final int PUERTO = 5000;

    public static void main(String[] args) {
        new VentanaJuego();

       /* try {

            Socket socket = new Socket(HOST, PUERTO);

            System.out.println("Conectado al servidor");

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            while (true) {

                String mensaje = scanner.nextLine();

                salida.println(mensaje);
            }

        } catch (IOException e) {

            System.out.println("Error en cliente");
            e.printStackTrace();
        }*/
    }
}