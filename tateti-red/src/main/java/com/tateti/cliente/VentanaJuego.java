package com.tateti.cliente;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class VentanaJuego extends JFrame {

    private JLabel labelEstado;
    private JButton[][] botones;
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private JPanel panelTablero;

    public VentanaJuego() {

        conectarServidor();

        setTitle("Ta-Te-Ti");

        setSize(600, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        botones = new JButton[3][3];

        panelTablero = new JPanel(new GridLayout(3, 3));

        labelEstado = new JLabel("Conectado", SwingConstants.CENTER);

        labelEstado.setFont(new Font("Segoe UI", Font.BOLD, 20));

        inicializarBotones();
        add(labelEstado, BorderLayout.NORTH);
        add(panelTablero, BorderLayout.CENTER);
        escucharServidor();
        setVisible(true);
    }

    private void conectarServidor() {
        try {
            socket = new Socket("localhost", 5000);

            salida = new PrintWriter(socket.getOutputStream(), true);

            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Conectado al servidor");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar al servidor");

            System.exit(0);
        }
    }

    private void inicializarBotones() {

        for (int fila = 0; fila < 3; fila++) {

            for (int columna = 0; columna < 3; columna++) {

                JButton boton = new JButton();

                boton.setFont(new Font("Segoe UI", Font.BOLD, 60));

                int filaFinal = fila;
                int columnaFinal = columna;

                boton.addActionListener(e -> {

                    enviarMovimiento(filaFinal, columnaFinal);
                });

                botones[fila][columna] = boton;

                panelTablero.add(boton);
                boton.setOpaque(true);
                boton.setBackground(Color.gray);
                boton.setForeground(Color.white);
                boton.setBorder(BorderFactory.createLineBorder(Color.black));
            }
        }
    }

    private void enviarMovimiento(int fila, int columna) {

        salida.println(fila + "," + columna);
    }

    private void escucharServidor() {

        Thread hiloServidor = new Thread(() -> {

            try {

                String mensaje;

                while ((mensaje = entrada.readLine()) != null) {
                    procesarMensajeServidor(mensaje);
                }

            } catch (IOException e) {

                System.out.println("Conexión cerrada");
            }
        });

        hiloServidor.start();
    }

    private void procesarMensajeServidor(String mensaje) {

        String[] partes = mensaje.split(",");

        if (partes[0].equals("MOVIMIENTO")) {

            int fila = Integer.parseInt(partes[1]);

            int columna = Integer.parseInt(partes[2]);

            String simbolo = partes[3];

            botones[fila][columna].setText(simbolo);
            // botones[fila][columna].setEnabled(false)

            if (simbolo.equals("X")) {
                botones[fila][columna].setForeground(Color.pink);
            } else {
                botones[fila][columna].setForeground(Color.cyan);
            }

        } else if (mensaje.equals("REINICIO")) {

            reiniciarTablero();
        } else {

            labelEstado.setText(mensaje);

            if (mensaje.contains("ganó")) {

                JOptionPane.showMessageDialog(this, mensaje);

                reiniciarTablero();
            }

            if (mensaje.equals("Empate")) {

                JOptionPane.showMessageDialog(this, "La partida terminó en empate");
                reiniciarTablero();
            }
        }
    }

    private void reiniciarTablero() {

        for (int fila = 0; fila < 3; fila++) {

            for (int columna = 0; columna < 3; columna++) {

                botones[fila][columna].setText("");

                botones[fila][columna].setEnabled(true);
            }
        }
    }
}