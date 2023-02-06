/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Transporte {

    private CyclicBarrier barrera;
    private int cantEnTransporte;
    private Semaphore esperaConductor = new Semaphore(0);
    private Semaphore semTerminalA = new Semaphore(0);
    private Semaphore semTerminalB = new Semaphore(0);
    private Semaphore semTerminalC = new Semaphore(0);
    private int asientosDispo;
    private ReentrantLock esperarTransporte = new ReentrantLock();
    private Condition condicionEspera = esperarTransporte.newCondition();

    public Transporte(int cantEnTransporte) {
        this.cantEnTransporte = cantEnTransporte;
        barrera = new CyclicBarrier(cantEnTransporte);
        asientosDispo = cantEnTransporte;
    }

    public void esperaBarrera() throws InterruptedException {
        try {
            esperarTransporte.lock();
            //Si el transporte esta funcionando entonces espera a que vuelva
            while (asientosDispo == 0) {
                condicionEspera.await();
            }
            asientosDispo--;
            esperarTransporte.unlock();
            //Espera a que se abra la barrera
            barrera.await();
            esperaConductor.release();
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(Transporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void esperaTerminal(char terminal) throws InterruptedException {
        switch (terminal) {
            case 'A':
                semTerminalA.acquire();
                break;
            case 'B':
                semTerminalB.acquire();
                break;
            case 'C':
                semTerminalC.acquire();
                break;
        }
    }
    
    public void esperarAvanzar() throws InterruptedException{
        esperaConductor.acquire(cantEnTransporte);
    }
       
    public boolean esperandoTerminalA(){
        return semTerminalA.hasQueuedThreads();
    }
    
    public void bajarPersonasTerA(){
         semTerminalA.release(semTerminalA.getQueueLength());
    }
    
        public boolean esperandoTerminalB(){
        return semTerminalB.hasQueuedThreads();
    }
    
    public void bajarPersonasTerB(){
         semTerminalB.release(semTerminalB.getQueueLength());
    }
    
        public boolean esperandoTerminalC(){
        return semTerminalC.hasQueuedThreads();
    }
    
    public void bajarPersonasTerC(){
         semTerminalC.release(semTerminalC.getQueueLength());
    }
    
    public void avisardejoAvance(){
           esperarTransporte.lock();
           asientosDispo = cantEnTransporte;
           condicionEspera.signalAll();
           esperarTransporte.unlock();
    }
}
