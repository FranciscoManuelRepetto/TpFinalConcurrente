/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class PuestoInforme {
    
    private ReentrantLock atencion= new ReentrantLock(true);
    //Yo pense que cada Aerolinea tendria un puesto de atencion porque no lo aclara en el enunciado
    private ConcurrentHashMap puestosAten = new ConcurrentHashMap();
    private int maxPasFila = 2;
    

    public PuestoInforme(int cantAero) {
        cantAero++;
        for (int i = 1; i < cantAero; i++) {
           puestosAten.put("Aerolinea"+i, new PuestoAtencion(i,maxPasFila));
        }
        
    }
    
    public void preguntarPuestoAten(Pasaje pasaje,int numPas){
        //Le asigna el puesto de atencion al pasajero en el pasaje
        try {
            atencion.lock();
            System.out.println("PUESTO DE INFORMES: atendiendo al Pasajero "+numPas);
            Thread.sleep(4000);
            String aerolinea = pasaje.getVuelo().getAerolinea();
            pasaje.setPuestoAtencion( (PuestoAtencion) puestosAten.get(aerolinea));
            atencion.unlock();
        } catch (InterruptedException ex) {
            Logger.getLogger(PuestoInforme.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
}
