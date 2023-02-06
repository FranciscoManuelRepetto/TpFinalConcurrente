/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

/**
 *
 * @author Usuario
 */
public class Main {

    public static void main(String[] args) {
        int cantPasajeros = 15;
        int cantAerolineas = 2;
        Aeropuerto aero = new Aeropuerto(cantAerolineas);
        
        //Crea el reloj
        Reloj reloj = new Reloj(aero);
        Thread hiloReloj = new Thread(reloj);
        hiloReloj.start();

        //Crea los pasajeros
        Pasajero[] pasajeros = new Pasajero[cantPasajeros];
        for (int i = 0; i < cantPasajeros; i++) {
            int aux = i + 1;
            pasajeros[i] = new Pasajero(aero, aux);
        }

        Thread[] hilos = new Thread[cantPasajeros];
        for (int i = 0; i < cantPasajeros; i++) {
            hilos[i] = new Thread(pasajeros[i]);
        }

        for (int i = 0; i < cantPasajeros; i++) {
            hilos[i].start();
        }
    }
}
