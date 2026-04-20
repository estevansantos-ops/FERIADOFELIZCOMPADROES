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
