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
public class PuestoAtencion {

    private int id;
    private int pasActual;
    private int pasMax;
    private Semaphore atencion = new Semaphore(1, true);
    private Semaphore mutex = new Semaphore(1);
    private Hall hall = new Hall();

    public PuestoAtencion(int id, int pasMax) {
        this.id = id;
        this.pasActual = 0;
        this.pasMax = pasMax;
    }

    public boolean llegarPuestoAtencion() throws InterruptedException {
        boolean paso = false;
        //El pasajero intenta entrar a la fila del puesto de atencion
        mutex.acquire();
        if (pasActual < pasMax && !(hall.genteEsperando())) {
            //Pudo pasar a la fila del puesto de atencion
            paso = true;
            pasActual++;
        }
        mutex.release();
        return paso;
    }

    public void esperarSerAtendido(int numPas) throws InterruptedException {
        atencion.acquire();
        System.out.println("PUESTO DE ATENCION:  atendiendo al Pasajero " + numPas);
        mutex.acquire();
        pasActual--;
        mutex.release();
    }

    public void terminarAtencion(Pasaje pasaje) throws InterruptedException {
        Thread.sleep(12000);
        asignarTerminal(pasaje);
        atencion.release();
    }

    private void asignarTerminal(Pasaje pasaje) {
        /*Se determina la terminal y el puerto de embarque con un calculo matematico dependiendo del cod-vuelo
           y la aerolinea para que todos los puestos de atencion den resultado correcto*/

        //Obtiene el numero de la aerolinea y hace calculo para obtener la terminal
        String aerolinea = (pasaje.getVuelo().getAerolinea()).substring(9);
        int auxAero = Integer.parseInt(aerolinea) % 3;
        //Obtiene el numero del codigo de vuelo y hace calculo para obtener puerta de embarque
        String codVuelo = (pasaje.getVuelo().getCodVuelo()).substring(9);
        int auxVuelo = Integer.parseInt(codVuelo) % 7+1;
        switch (auxAero) {
            case 0:
                pasaje.setLetraTerminal('A');
                pasaje.setPuertaEmbarque(auxVuelo);
                break;
            case 1:
                pasaje.setLetraTerminal('B');
                pasaje.setPuertaEmbarque(auxVuelo + 7);
                break;
            case 2:
                pasaje.setLetraTerminal('C');
                pasaje.setPuertaEmbarque(auxAero + 14);
                break;
        }
    }

    public void esperarEnHall(int id) throws InterruptedException {
        hall.esperar(id);
    }
    
    public void avisarEnHall() {
        hall.avisarPasajeros();
    }
}
