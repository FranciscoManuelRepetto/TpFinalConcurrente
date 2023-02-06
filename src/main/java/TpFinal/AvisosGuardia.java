/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

import java.util.concurrent.LinkedBlockingQueue;



/**
 *
 * @author Usuario
 */
public class AvisosGuardia {

    private LinkedBlockingQueue puestosPendientes = new LinkedBlockingQueue();
    
    
    public synchronized void guardarPendiente(PuestoAtencion puesto) throws InterruptedException {
        puestosPendientes.put(puesto);
        notifyAll();
    }
    
    public synchronized void esperarAvisar() throws InterruptedException{
        while(puestosPendientes.isEmpty()){
            wait();
        }
        ((PuestoAtencion)puestosPendientes.poll()).avisarEnHall();
    }
    
}
