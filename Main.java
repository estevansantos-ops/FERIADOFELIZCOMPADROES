import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 1. SINGLETON: Termostato Central

class TermostatoCentral {
    private static TermostatoCentral instance;
    private int temperaturaIdeal = 23;

    private TermostatoCentral() {} 

    public static TermostatoCentral getInstance() {
        if (instance == null) {
            instance = new TermostatoCentral();
        }
        return instance;
    }
    
    public int getTemperaturaIdeal() { return temperaturaIdeal; }
}

// INTERFACE

interface Climatizador {
    void processarAr();
    void desligar();
}

class ArCondicionadoModerno implements Climatizador {
    public void processarAr() { System.out.println("AC: Resfriando o ambiente."); }
    public void desligar() { System.out.println("AC: Desligado."); }
}