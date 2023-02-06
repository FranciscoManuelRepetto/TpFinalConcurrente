/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TpFinal;

/**
 *
 * @author Usuario
 */
public class Vuelo {
    
    private String codVuelo;
    private String aerolinea;
    private int horaDespegue;
    private boolean horaDeEmbarcar;

    public Vuelo(String codVuelo, String aerolinea,int horaDespegue) {
        this.codVuelo = codVuelo;
        this.aerolinea = aerolinea;
        this.horaDespegue = horaDespegue;
        this.horaDeEmbarcar = false;
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public String getAerolinea() {
        return aerolinea;
    }
    
    public int getHoraDespegue(){
        return horaDespegue;
    }

    public synchronized void esperarEmbarcar() throws InterruptedException {
        while(!horaDeEmbarcar){
            wait();
        }
    }
    
    public synchronized void esHoraDeEmbarcar(){
        horaDeEmbarcar = true;
        notifyAll();
    }
    
    public synchronized void noEsHoraDeEmbarcar(){
        horaDeEmbarcar = false;
    }
    
}
