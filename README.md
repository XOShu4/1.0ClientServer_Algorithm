# ClientServer_Algorithm

### Introduzione al progetto

## Panoramica
#### Scopo del programma: 
Interfacciamento di piu client implementando algoritmo di crittografia RSA.

## Creato con 
- Java

## Partecipanti del gruppo 
- **Galantini Corrado**
- **Donzelli Lorenzo**
- **Aprea Mario**       

### Spiegazione generale del funzionamneto del programma
I client si connettono ad un server mediante socket. 
Verra' quindi richiesto UserName identificativo.  
#### possibili comandi sono:  
* **/UserName(/s): messaggio** -> invia *messaggio* a tutti gli utenti specificati. Se uno o piu utenti non vengono trovati viene segnato *UserName* not fount, ed il messaggio viene inviato solo agli utenti trovati;  
* **/listaUtenti** -> elenca tutti gli utenti online.
*  **/SendToAll: messaggio** ->invia *messaggio* a tutti gli utenti online
*  **/EXIT** ->  chiude la connessione con il server  
   
#### L'interfaccia utente e' suddivisa in due terminali:   
Il primo dedito a ricevere i comandi dello user. Stampa eventuali errori di sintassi del comando e in caso di utenti non trovati;   
Il secondo si occupa solamente della stampa dei messaggi ricevuti.

 
### Server
Attendere richieste di connessone da parte dei client.    
Quando avviene la connessione con un client avvia un thread della classe ServerThread che gestira' i comandi degli utenti  
   
### Client
Classe adibita all'interfacciamento utente.  
Gestisce la prima fase log in, la ricezione messaggi utente, la criptazione dei messaggi, l'invio dei messaggi criptati al ServerThread e l'invio della chiave pubblica al server.  
Inoltre avvia e comunica con ClientReader.

### RsaKey


## Supporti 
