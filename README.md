# ClientServer_Algorithm

### Introduzione al progetto

## Panoramica
#### Scopo del programma: 
Interfacciamento di piu client implementando algoritmo di crittografia RSA.

## Creato con 
- Java

## Partecipanti del gruppo 
- **Galantini Corrado**
- **Sanseverino Lorenzo**
- **Panichi Leonardo**      
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

## Classi
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
  
La funzione getMessage ritorna il messaggio nel formato *"[" + time + "] " + Sender + "@:" + message*.  

### MsgBox
Classe che gestisce la mappa di ArrayList di *Message*.
I metodi predisposti sono:
1. **addUser**-> prende come parametro *userName* e controlla se il nome e' gia presente, se non lo e' verifica se rispetta il formato richiesto, nel caso aggiunge l'utente.
2. **remuveUser**-> prende come parametro un u*serName* e lo rimuove dalla mappa. Metodo usato alla disconnessione dei client.
3. **addMsg**-> inserisce un messaggio(*Message*) nel ArrayList del utente specificato. returna *not fount* nel caso di utente non trovato.
4. **readBox**-> se ci sono messaggi per l utente allora legge la prima cella dell ArrayList occupata e la rimuove.

### ServerThread
Avviato da Server si occupa dell'interfacciamento tra client e MsgBox.   
Memorizza, legendoli da client, le cliavi publiche associandoli ai rispettivi userName.  
Smista i possibili comandi inseriti nel server e si occupa principalmente di inviare le chiavi richieste ai client e inserire i messaggi criptati  
nella msgBox.  
Avvia ServerThreadReader. 

### ServerThreadReader 
legge i messaggi in msgBox e li scrive al ClientReader.

### ClientReader
Classe che si occupa di smistare tutti i messaggi inviati da server e o serverThread secondo protocollo.
Si occupa in oltre di decriptare i messaggi ricevuti e di avviare Terminal ed il relativo nuovo terminale.

### Terminal
Classe che gestisce il nuovo cmd.  
La porta socket viene condivisa tramite file contenente un numero incrementale.  
Gestisce tutti i messaggi che sono indirizzati al Client dal ServerThread.


## Codice, Protocolli ed Esempi

