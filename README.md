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

#
### Server
Attendere richieste di connessone da parte dei client.    
Quando avviene la connessione con un client avvia un thread della classe ServerThread che gestira' i comandi degli utenti  
   
### Client
Classe adibita all'interfacciamento utente.  
Gestisce la prima fase log in, la ricezione messaggi utente, la criptazione dei messaggi, l'invio dei messaggi criptati al ServerThread e l'invio della chiave pubblica al server.  
Inoltre avvia e comunica con ClientReader.

### RsaKey
Prende da file p,q,e. 
Il file e' composto da numeri primi, tutti i numeri utilizzati nell'algoritmo vengono segnati con "/".  
Una volta scelti i numeri calcola la chiave pubblica e privata.

### Message
Contiene tutte le informazioni che vanno a caratterizzare un messaggio:
1. **message** -> testo criptato da inviare. 
2. **time** -> ora e minuto in cui e' stato inviato il messaggio.
3. **sender**-> utente che ha inviato il messaggio.
  
La funzione getMessage ritorna il messaggio nel formato "[" + time + "] " + Sender + "@:" + message.
