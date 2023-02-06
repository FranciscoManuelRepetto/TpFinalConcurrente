
package TpFinal.Probando;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Usuario
 */
public class Hilo implements Runnable {
    
    private Recurso recurso;
    private AtomicInteger horaActual;
    
    public Hilo(Recurso recurso, AtomicInteger x){
        this.recurso = recurso;
        this.horaActual = x;
    }
    
    
    
    public void run(){
        System.out.println("Buenas tardes compañeros, soy el hilo "+Thread.currentThread().getName());
        int aux = horaActual.get()+1;
        horaActual.set(aux);
    }
}
