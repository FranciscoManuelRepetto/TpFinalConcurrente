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
public class Reloj implements Runnable {
    
    private Aeropuerto aero;

    public Reloj(Aeropuerto aero) {
        this.aero = aero;
    }
    
    public void run(){
        while(true){
            try {
                Thread.sleep(18000);
                int aux = aero.actualizarHora();
                System.out.println("-------------- HORA ACTUAL "+aux+"--------------");
                aero.avisarAVuelos();
            } catch (InterruptedException ex) {
                Logger.getLogger(Reloj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
