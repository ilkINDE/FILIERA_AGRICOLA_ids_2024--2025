# 🌾 Piattaforma di Digitalizzazione e Valorizzazione della Filiera Agricola Locale

## 📌 Descrizione del Progetto

Questa piattaforma nasce con l’obiettivo di valorizzare i prodotti agricoli locali, promuovere il territorio comunale e garantire la tracciabilità completa della filiera agroalimentare. Attraverso un sistema digitale, gli attori della filiera possono caricare, visualizzare e condividere informazioni relative alla produzione, trasformazione e distribuzione dei prodotti tipici. La piattaforma consente inoltre l’organizzazione di eventi come fiere, visite guidate e degustazioni, favorendo l’interazione tra produttori e cittadini.

## 🛠️ Tecnologie Utilizzate

| Tecnologia          | Descrizione                                                                 |
|---------------------|------------------------------------------------------------------------------|
| **Java**            | Linguaggio di programmazione principale                                     |
| **Spring Boot**     | Framework per lo sviluppo di applicazioni web e RESTful API                 |
| **H2 Database**     | Database relazionale in-memory per test e sviluppo                          |

## 👥 Attori Principali del Sistema

- **Produttore**: Carica informazioni sui metodi di coltivazione e certificazioni, vende nel marketplace.
- **Trasformatore**: Documenta i processi di trasformazione e collega i prodotti ai produttori locali.
- **Distributore di Tipicità**: Vende prodotti singoli o in pacchetti gastronomici.
- **Curatore**: Verifica e approva i contenuti prima della pubblicazione.
- **Animatore della Filiera**: Organizza eventi, fiere e visite alle aziende.
- **Acquirente**: Acquista prodotti e partecipa agli eventi.
- **Utente Generico**: Consulta contenuti informativi sulla qualità e provenienza dei prodotti.
- **Gestore della Piattaforma**: Amministra autorizzazioni e accrediti.
- **Sistemi Social**: Ricevono contenuti condivisi dagli utenti.
- **Sistema OSM**: Fornisce mappe interattive per visualizzare i punti della filiera.

## 🎯 Design Pattern Utilizzati

- **Observer Pattern**  
  Utilizzato per notificare automaticamente gli attori interessati (es. venditori invitati a un evento) quando si verificano cambiamenti nel sistema.

- **State Pattern**  
  Implementato in tre contesti distinti:
  - Stato dell’**invito** (in attesa, accettato, rifiutato)
  - Stato della **richiesta del ruolo** (in attesa, approvata, rifiutata)
  - Stato del **prodotto** (approvato, rifiutato, in bozza, in revisione)

## 📂 Struttura del Progetto

```text
src/
├── main/
│   ├── java/
│   │   └── unicam.filiera_agricola_ids_20242025
|   |       ├── DTO              # Data Transfer Objects
│   │       ├── controllers/     # Contiene i controller REST
│   │       ├── services/        # Logica di business
│   │       ├── models/          # Entità e classi di dominio
│   │       └── repositoriy/     # Interfacce per l’accesso ai dati
    └── resources/

```
## 🗺️ Funzionalità Principali

- Tracciabilità completa del ciclo produttivo
- Visualizzazione dei punti della filiera su mappa interattiva
- Gestione e approvazione dei contenuti
- Marketplace per la vendita dei prodotti
- Organizzazione e prenotazione eventi
- Condivisione contenuti sui social

## 📌 Note Finali

Il progetto è stato sviluppato seguendo i vincoli tecnici richiesti, con particolare attenzione alla modularità, alla tracciabilità e all’estensibilità del sistema. È pensato per essere facilmente integrabile con interfacce grafiche e sistemi esterni.
