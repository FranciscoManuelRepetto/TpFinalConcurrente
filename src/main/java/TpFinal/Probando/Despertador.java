/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal.Probando;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Despertador implements Runnable {
    private Recurso recurso;
    
    public Despertador(Recurso recurso){
        this.recurso = recurso;
    }
    
    public void run(){
       /* System.out.println("Me duermo");
        try {
            Thread.sleep(5000);
             recurso. avisar();
        } catch (InterruptedException ex) {
            Logger.getLogger(Despertador.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
    }
}
