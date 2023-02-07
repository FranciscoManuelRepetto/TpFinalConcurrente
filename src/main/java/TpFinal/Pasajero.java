/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Pasajero implements Runnable {

    private Aeropuerto aero;
    private Pasaje pasaje;
    private int id;

    public Pasajero(Aeropuerto aeropuerto, int id) {
        this.aero = aeropuerto;
        this.id = id;
    }

    public void run() {
        //Verifica que el aeropuerto este abierto
        System.out.println("Pasajero " + id + " llego al aeropuerto");
        try {
            if (aero.abierto()) {
                //Va al puesto de informes que le genera un pasaje aleatorio y le dice a que puesto de atencion ir
                buscarCheckIn();
                //Va al puesto de atencion para que le de la terminal a la que tiene que ir
                hacerCheckIn();
                //Toma el transporte y espera para llegar a la terminal
                tomarTransporte();
                //Llega a la terminal y va al free shop si quiere o sino embarca
                embarcar();
            } else {
                System.out.println(Thread.currentThread().getName() + "el aeropuerto esta CERRADO");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Pasajero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarCheckIn() {
        //Le pide al aeropuerto que genere un pasaje
        pasaje = aero.generarPasaje();
        //Llega al puesto de informe
        System.out.println("Pasajero " + id + " llego al puesto de informes");
        aero.llegarPuestoInforme(pasaje, id);
    }

    private void hacerCheckIn() throws InterruptedException {
        PuestoAtencion puesto = pasaje.getPuestoAtencion();
        //Va al puesto de atencion a hacer el check-in
        boolean paso = puesto.llegarPuestoAtencion();
        //Si no pudo pasar a la cola del puesto de Atencion entonces espera en el hall
        if (!paso) {
            System.out.println("Pasajero " + id + " esta esperando en HALL ");
            puesto.esperarEnHall(id);
        }
        System.out.println("Pasajero " + id + " en la fila del puesto del PUESTO DE ATENCION");
        //Pudo pasar a la fila del puesto de atencion y espera a ser atendido
        puesto.esperarSerAtendido(id);
        //Avisa al guardia que ya fue atendido
        aero.avisarGuardia(puesto);
        //Termina de hacer el check in
        puesto.terminarAtencion(pasaje);
        System.out.println("Pasajero " + id + " ya hizo el check-in");
    }

    private void tomarTransporte() throws InterruptedException {
        //Espera para tomar el transporte
        aero.tomarTransporte();
        System.out.println("Pasajero " + id + " se subio al transporte");
        //Espera que el conductor le avise que ya se puede bajar
        aero.bajarseTransporte(pasaje);
        //Llego a la terminal 
        System.out.println("Pasajero " + id + " se bajo en la Terminal " + pasaje.getLetraTerminal());
    }

    private void embarcar() throws InterruptedException {
        //Genera si el pasajero quiere ir al freeshop o no
        Random random = new Random();
        boolean freeshop = random.nextBoolean();
        if (freeshop) {
            irFreeshop();
        } else {
            System.out.println("Pasajero " + id + " no quiere ir al freeshop ");
        }
        //Espera a que sea la hora para embarcar 
        System.out.println("Pasajero " + id + " esperando a embarcar hasta las " + pasaje.getVuelo().getHoraDespegue());
        pasaje.getVuelo().esperarEmbarcar();
        System.out.println("Pasajero " + id + " despego");
    }

    private void irFreeshop() throws InterruptedException {
        Terminal terminal = pasaje.getTerminal();
        if (aero.hayTiempoFreeShop(pasaje.getTerminal(), pasaje.getVuelo().getHoraDespegue())) {
            terminal.pasarFreeShop();
            System.out.println("Pasajero " + id + " esta en el freeshop");
            Thread.sleep(6000);
            terminal.cajero();
            System.out.println("Pasajero " + id + " fue atendido en el cajero y esta saliendo del freeshop");
            terminal.dejarFreeShop();
        } else {
            System.out.println("Pasajero " + id + " no tiene tiempo para ir al freeshop ");
        }
    }
}
