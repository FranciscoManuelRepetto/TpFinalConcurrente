/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

/**
 *
 * @author Usuario
 */
public class Pasaje {
    
    private Vuelo vuelo;
    private PuestoAtencion puestoAtencion;
    private char letraTermi;
    private Terminal terminal;
    private int puertaEmbarque;

    public Pasaje(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }
    
    public PuestoAtencion getPuestoAtencion() {
        return puestoAtencion;
    }

    public void setPuestoAtencion(PuestoAtencion puestoAtencion) {
        this.puestoAtencion = puestoAtencion;
    }

    public char getLetraTerminal() {
        return letraTermi;
    }

    public void setLetraTerminal(char letraTermi) {
        this.letraTermi = letraTermi;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public int getPuertaEmbarque() {
        return puertaEmbarque;
    }

    public void setPuertaEmbarque(int puertaEmbarque) {
        this.puertaEmbarque = puertaEmbarque;
    }
    
}
