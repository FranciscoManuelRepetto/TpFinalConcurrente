/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Conductor implements Runnable {
    
   private Transporte transporte;

    Conductor(Transporte transporte) {
       this.transporte = transporte;
    }
    
   @Override
    public void run(){
        while(true){
            try {
                transporte.esperarAvanzar();
                Thread.sleep(7000);
                if (transporte.esperandoTerminalA()) {
                    System.out.println("TRANSPORTE: llego a la Terminal A");
                    //VER SI EL METODO ES LEGAL
                    transporte.bajarPersonasTerA();
                } else {
                    System.out.println("TRANSPORTE: ningun pasajero solicito bajar en la Terminal A");
                }
                Thread.sleep(7000);
                if (transporte.esperandoTerminalB()) {
                    System.out.println("TRANSPORTE: llego a la Terminal B");
                    //VER SI EL METODO ES LEGAL
                    transporte.bajarPersonasTerB();
                } else {
                    System.out.println("TRANSPORTE: ningun pasajero solicito bajar en la Terminal B");
                }
                Thread.sleep(7000);
                if (transporte.esperandoTerminalC()) {
                    System.out.println("TRANSPORTE: llego a la Terminal C");
                    //VER SI EL METODO ES LEGAL
                    transporte.bajarPersonasTerC();
                } else {
                    System.out.println("TRANSPORTE: ningun pasajero solicito bajar en la Terminal C");
                }
                transporte.avisardejoAvance();
            } catch (InterruptedException ex) {
                Logger.getLogger(Conductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
