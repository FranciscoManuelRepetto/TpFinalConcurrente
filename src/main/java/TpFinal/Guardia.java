/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Guardia implements Runnable {
    
    private AvisosGuardia avisos;

    public Guardia(AvisosGuardia avisos) {
        this.avisos = avisos;
    }
    
    public void run(){
        //Espera a recibir avisos
        while(true){
            try {
                avisos.esperarAvisar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Guardia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
