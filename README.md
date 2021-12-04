# ClientServer_Algorithm

### Introduzione al progetto

## Panoramica
Il seguente file contiene un programma che rappresenta l'invio di messaggi tramite più utenti e gestiti da un Server, crittografati tramite l'algoritmo RSA

## Creato con 
- Java
## Aggiornamenti futuri

## Partecipanti del gruppo 

#### Autore
- **Donzelli Lorenzo**

#### Programmatori
- **Galantini Corrado**
- **Donzelli Lorenzo**
- **Aprea Mario**

### Spiegazione generale del funzionamneto del programma
1. Vengono generate le chavi pubbliche e private, per ogni utente vengono assegnate una coppia di chiavi, questo perché senza esse non può comunicare con gli altri utenti. Le chiavi vengono generate nella classe RsaKey tramte l'algoritmo Rsa. La chiave pubblica deve essere inviata al Server.
2. Invio di messaggi cifrati.
3. Percepire messaggi cifrati.

### Incarichi del server
1. Attendere richieste di connessone da parte del server.
2. Quando avviene la connessione con un client avvia un thread della classe ServerThread
   
### Spiegazione delle classi

## Supporti 
