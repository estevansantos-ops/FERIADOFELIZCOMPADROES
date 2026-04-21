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

// 2. ADAPTER: Ar-Condicionado de Janela Antigo

class ArDeJanelaAntigo {
    public void acionarCompressor() { System.out.println("AC Antigo: BRRRRR (Compressor ligado)."); }
    public void cortarEnergia() { System.out.println("AC Antigo: Desligado na tomada."); }
}

class AdapterACAntigo implements Climatizador {
    private ArDeJanelaAntigo acLegado;

    public AdapterACAntigo(ArDeJanelaAntigo ac) { this.acLegado = ac; }
    public void processarAr() { acLegado.acionarCompressor(); }
    public void desligar() { acLegado.cortarEnergia(); }
}

// 3. PROXY: Controle de Janela/Energia + Logs

class ProxyEconomiaEnergia implements Climatizador {
    private Climatizador acReal;
    private boolean janelaFechada;

    public ProxyEconomiaEnergia(Climatizador ac, boolean janelaFechada) {
        this.acReal = ac;
        this.janelaFechada = janelaFechada;
    }

    private void registrarLog(String comando, String resultado) {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        System.out.println("[LOG | " + dataHora + "] Comando: " + comando + " | Status: " + resultado);
    }

    public void processarAr() {
        if (janelaFechada) {
            registrarLog("Ligar AC", "PERMITIDO - Requisitos de energia atendidos.");
            System.out.println("[Proxy] Liberando energia elétrica para o AC...");
            acReal.processarAr();
        } else {
            registrarLog("Ligar AC", "BLOQUEADO - Desperdício de energia (Janela aberta).");
            System.out.println("[Proxy] ERRO: Comando ignorado. Feche a janela para economizar energia!");
        }
    }

    public void desligar() {
        registrarLog("Desligar AC", "EXECUTADO");
        acReal.desligar();
    }
}

// 4. FACTORY: Criação de Climatizadores

class ClimatizadorFactory {
    public static Climatizador criar(String tipo) {
        if (tipo.equals("MODERNO")) {
            return new ArCondicionadoModerno();
        }
        if (tipo.equals("ANTIGO_PROTEGIDO")) {
            ArDeJanelaAntigo antigo = new ArDeJanelaAntigo();
            AdapterACAntigo adapter = new AdapterACAntigo(antigo);
            return new ProxyEconomiaEnergia(adapter, true); // true = simulando janela fechada
        }
        return null;
    }
}

// 5. DECORATOR: Tratamento do Ar

abstract class TratamentoArDecorator implements Climatizador {
    protected Climatizador wrapper;
    public TratamentoArDecorator(Climatizador wrapper) { this.wrapper = wrapper; }
    public void processarAr() { wrapper.processarAr(); }
    public void desligar() { wrapper.desligar(); }
}

class FiltroAntiAlergicoDecorator extends TratamentoArDecorator {
    public FiltroAntiAlergicoDecorator(Climatizador wrapper) { super(wrapper); }
    public void processarAr() {
        super.processarAr();
        System.out.println("+ [Filtro HEPA] Removendo poeira e ácaros do ar.");
    }
}

// 6. FACADE: Painel Simples do Celular

class AplicativoClimatizacaoFacade {
    private Climatizador arDoQuarto;

    public AplicativoClimatizacaoFacade() {
        // Factory cria o ar antigo, passa pelo adapter, protege com o proxy
        Climatizador baseAC = ClimatizadorFactory.criar("ANTIGO_PROTEGIDO");
        
        // Decorator adiciona o filtro anti-alérgico
        this.arDoQuarto = new FiltroAntiAlergicoDecorator(baseAC);
    }

    public void ativarModoDormir() {
        System.out.println("\n--- Ativando Modo Dormir ---");
        int temp = TermostatoCentral.getInstance().getTemperaturaIdeal();
        System.out.println("Buscando temperatura ideal do termostato: " + temp + "°C");
        arDoQuarto.processarAr();
    }
    
    public void desativarSistema() {
        System.out.println("\n--- Desativando Sistema ---");
        arDoQuarto.desligar();
    }
}