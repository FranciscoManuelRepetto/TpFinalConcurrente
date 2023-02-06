/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Usuario
 */
public class Terminal {
     
    private Semaphore semEspera = new Semaphore(1);
    private Semaphore semCaja = new Semaphore(2,true);
    private Semaphore semCapMax;
    private int esperaFreeShop; 

    public Terminal(int capMax) {
        //Capacidad maxima que puede adentro del freeshop
        this.semCapMax = new Semaphore(capMax,true);
    }

    public void pasarFreeShop() throws InterruptedException {
        //Metodo para pasar al freeshop
        semEspera.acquire();
        esperaFreeShop++;
        semEspera.release();
        semCapMax.acquire();
        semEspera.acquire();
        esperaFreeShop--;
        semEspera.release();
    }

    public void cajero() throws InterruptedException{
        semCaja.acquire();
    }
    
    public void dejarFreeShop(){
        semCaja.release();
        semCapMax.release();
    }

    public int calcularTiempo() throws InterruptedException {
        semEspera.acquire();
        return esperaFreeShop;
    }
    
    public void dejarCalcularTiempo(){
        semEspera.release();
    }
    
}
