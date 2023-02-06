/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal.Probando;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author Usuario
 */
public class Recurso {

    private LinkedBlockingQueue cola;
    private int cantidad;
    private Lock esperar = new ReentrantLock();
    private Condition sala = esperar.newCondition();
    private Semaphore semaforo = new Semaphore(1);
    private CyclicBarrier barrera = new CyclicBarrier(2);
    private CountDownLatch cuenta = new CountDownLatch(2);
    
    public Recurso(){
        cola = new LinkedBlockingQueue();
        cantidad = 0;
    }
        
    public  void entrarEnCola() {
        try {
            /*try {
            esperar.lock();
            cola.put(Thread.currentThread());
            System.out.println("Soy "+(Thread.currentThread().getName()));
            sala.await();
            cantidad--;
            esperar.unlock();
            } catch (InterruptedException ex) {
            Logger.getLogger(Recurso.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            /*if(semaforo.tryAcquire()){
            System.out.println("Pude entrar y los permisos que hay son: "+semaforo.availablePermits());
            }*/
            System.out.println(barrera.await()+" oli");
            cuenta.countDown();
            cuenta.await();
            System.out.println("estoy en la cuenta jij "+Thread.currentThread().getName());
        } catch (InterruptedException ex) {
            Logger.getLogger(Recurso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(Recurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void avisar(){
            /*esperar.lock();
             System.out.println("Lo voy a despertar al primero jiji");
             cantidad++;
             sala.notify();
           esperar.unlock();*/
            
            }
             
        
    }
    

