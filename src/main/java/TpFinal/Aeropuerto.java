/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 *
 * @author Usuario
 */
public class Aeropuerto {

    private AtomicReferenceArray vuelos; //Arreglo con todos los vuelos cargados
    private ConcurrentHashMap horariosVuelos = new ConcurrentHashMap(); //Hash que relaciona como clave horarios con los vuelos para controlar embarques
    private PuestoInforme puestoInfo;  
    private AvisosGuardia avisosGu = new AvisosGuardia(); //Recurso compartido entre pasajero y guardia para avisar que hay lugar en la fila
    private Transporte transporte = new Transporte(3);//Transporte utilizado para ir a las terminales
    private ConcurrentHashMap terminales = new ConcurrentHashMap();//Hash que relaciona como clave las letras de terminales con el objeto
    private Semaphore semHora = new Semaphore(1);
    private int horaActual;//Para saber si el aeropuerto esta abierto y para los embarques

    public Aeropuerto(int cantidadAero) {
        //Le llega por parametro la cantidad de aerolineas  
        horaActual = 12;
        cargarVuelos(cantidadAero);//Por cada aerolonia crea un vuelo
        crearEmpleados();
        cargarTerminales();
    }

    private void cargarVuelos(int cantidadAero) {
        //Carga los vuelos*/
        vuelos = new AtomicReferenceArray(cantidadAero);
        for (int i = 0; i < cantidadAero; i++) {
            int aux = i + 1;
            int horario = (int) (Math.random() * 24);
            Vuelo vuelo = new Vuelo("Cod-Vuelo" + aux, "Aerolinea" + aux, horario);
            vuelos.set(i, vuelo);
            //Pone en listas  todos los vuelos segun su horario mapeado a un hash para controlar los embarques
            if (horariosVuelos.get(horario) != null) {
                //Si hay una lista en ese horario, agrega el vuelo a la lista
                ((ArrayList) horariosVuelos.get(horario)).add(vuelo);
            } else {
                //Si no hay una lista en ese horario, la crea y agrega el vuelo
                ArrayList arreglo = new ArrayList();
                arreglo.add(vuelo);
                horariosVuelos.put(horario, arreglo);
            }
        }
        puestoInfo = new PuestoInforme(cantidadAero);
    }

    private void crearEmpleados() {
        //Crea al guardia de los puestos de atencion
        Thread guardia = new Thread(new Guardia(avisosGu));
        guardia.start();

        //Crea al conductor del transporte a las terminales
        Thread conductor = new Thread(new Conductor(transporte));
        conductor.start();
    }

    private void cargarTerminales() {
        int capMax = 3;
        terminales.put('A', new Terminal(capMax));
        terminales.put('B', new Terminal(capMax));
        terminales.put('C', new Terminal(capMax));
    }

    public boolean abierto() throws InterruptedException {
        //Retorna si el aeropuerto esta abierto o no
        semHora.acquire();
        boolean abierto = (horaActual >= 6 && horaActual <= 22);
        semHora.release();
        return abierto;
    }

    public Pasaje generarPasaje() {
        //Genera un numero random para elegir el vuelo
        int num = (int) (Math.random() * vuelos.length());
        //Crea el pasaje y lo devuelve
        return new Pasaje((Vuelo) vuelos.get(num));
    }

    public void llegarPuestoInforme(Pasaje pasaje, int numPas) {
        puestoInfo.preguntarPuestoAten(pasaje, numPas);
    }

    public void avisarGuardia(PuestoAtencion puesto) throws InterruptedException {
        avisosGu.guardarPendiente(puesto);
    }

    public void tomarTransporte() throws InterruptedException {
        transporte.esperaBarrera();
    }

    public void bajarseTransporte(Pasaje pasaje) throws InterruptedException {
        char letraTer = pasaje.getLetraTerminal();
        transporte.esperaTerminal(letraTer);
        pasaje.setTerminal((Terminal) terminales.get(letraTer));
    }

    public boolean hayTiempoFreeShop(Terminal terminal, int horaVuelo) throws InterruptedException {
        //Metodo que retorna si hay tiempo suficiente para que el pasajero pase al freeshop
        boolean hayTiempo = false;
        semHora.acquire();
        int genteEspe = terminal.calcularTiempo();
        /*Para obtener cuanto tiempo queda para embarcar se calcula
            cuanto falta para el embarque mas las personas esperando para entrar en el freeshop*/
        int aux = horaActual - horaVuelo - genteEspe / 4; //Divide por 4 porque esas personas entran en el freeshop en una hora
        if (aux >= 2) {
            hayTiempo = true;
        }
        terminal.dejarCalcularTiempo();
        semHora.release();
        return hayTiempo;
    }

    public int actualizarHora() throws InterruptedException {
        semHora.acquire();
        horaActual = (horaActual + 1) % 24;
        return horaActual;
    }

    public void avisarAVuelos() {
        ArrayList list;
        //Avisa a los vuelos de la hora anterior que ya no hay que embarcar
        if (horariosVuelos.get(horaActual - 1) != null) {
            list = (ArrayList) horariosVuelos.get(horaActual - 1);
            for (int i = 0; i < list.size(); i++) {
                ((Vuelo) list.get(i)).noEsHoraDeEmbarcar();
            }
        }
        //Avisa a los vuelos de la hora actual que hay que embarcar
        if (horariosVuelos.get(horaActual) != null) {
            list = (ArrayList) horariosVuelos.get(horaActual);
            for (int i = 0; i < list.size(); i++) {
                ((Vuelo) list.get(i)).esHoraDeEmbarcar();
            }
        }
        semHora.release();
    }

}
