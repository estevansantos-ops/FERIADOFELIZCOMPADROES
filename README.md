# Sistema de Climatização Inteligente (Smart HVAC)

Sistema simulado de automação residencial focado no controle de ar-condicionado e ventilação. O projeto demonstra a aplicação prática de Padrões de Projeto Orientados a Objetos (GoF) em um cenário realista, priorizando código limpo, flexível e de fácil manutenção.

## Padrões de Projeto Implementados

A arquitetura combina seis padrões, cada um resolvendo um problema específico de design:

- **Singleton (`TermostatoCentral`)**: garante uma única instância das configurações globais da residência (como a temperatura ideal padrão) e serve como ponto de acesso global.
- **Factory (`ClimatizadorFactory`)**: encapsula a criação dos diferentes tipos de aparelhos (modernos, legados protegidos, etc.), desacoplando o cliente das classes concretas.
- **Adapter (`AdapterACAntigo`)**: permite que equipamentos legados, com métodos incompatíveis, operem sob a interface padrão do sistema.
- **Proxy (`ProxyEconomiaEnergia`)**: intercepta chamadas ao ar-condicionado base, adicionando verificação de pré-condições (janelas abertas) e auditoria (logs com timestamp e ID do usuário) de forma transparente.
- **Decorator (`TratamentoArDecorator`, `FiltroAntiAlergicoDecorator`)**: adiciona responsabilidades aos equipamentos em tempo de execução — por exemplo, acoplar um filtro HEPA ao fluxo de ar — sem explosão de subclasses.
- **Facade (`AplicativoClimatizacaoFacade`)**: oferece uma interface unificada de alto nível (o "aplicativo do usuário") que esconde a composição de fábricas, proxies, adaptadores e decoradores.

## Como Executar

Projeto em Java puro, sem dependências externas.

1. Clone o repositório.
2. Navegue até o diretório raiz.
3. Compile: `javac Main.java`
4. Execute: `java Main`

## Exemplo de Saída

Ao acionar o "Modo Dormir" através da Fachada, o console apresenta a coordenação entre os padrões:

```
--- Ativando Modo Dormir ---
Buscando temperatura ideal do termostato: 23°C
[LOG | 19/04/2026 20:20:00] Usuário: AppUser | Comando: Ligar AC | Status: PERMITIDO - Requisitos de energia atendidos.
[Proxy] Liberando energia elétrica para o AC...
AC Antigo: BRRRRR (Compressor ligado).
[Filtro HEPA] Removendo poeira e ácaros do ar.
```

## Arquitetura

O princípio central é a **separação de responsabilidades**: o ar-condicionado não sabe que está sendo auditado, o filtro ignora se o aparelho é moderno ou legado, e o usuário final não lida com a complexidade do hardware — tudo é operado por um comando único da Fachada.
