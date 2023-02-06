/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Usuario
 */
public class Hall {
    
    private int cantGente = 0;
    private int idActual = -1;
    private LinkedBlockingQueue orden = new LinkedBlockingQueue();
    private Lock esperar = new ReentrantLock();
    private Condition condicionId = esperar.newCondition();
    private Semaphore mutex = new Semaphore(1);

    public boolean genteEsperando() throws InterruptedException {
      boolean hayGente = false;
      mutex.acquire();
       hayGente = cantGente != 0;
       mutex.release();
       return hayGente;
    }

    public void esperar(int id) throws InterruptedException {
        orden.put(id);
        
        mutex.acquire();
        cantGente++;
        mutex.release();
        
        esperar.lock();
        while(id != idActual){
            condicionId.await();
        }
        esperar.unlock();
        
        mutex.acquire();
        cantGente--;
        mutex.release();
    }
    
   public void avisarPasajeros(){
       if(cantGente!=0){
           esperar.lock();
           idActual = (int)orden.poll();
           condicionId.signalAll();
           esperar.unlock();
       }
   }
}
