# ClientServer_Algorithm

### Introduzione al progetto

## Panoramica
### Scopo del programma: 
##### Interfacciamento di piu client implementando algoritmo di crittografia RSA.

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

##
## Codice, Protocolli ed Esempi
```java
out.println("/@/Key/@/" + s);
```
Invia al ClientReader demarcandola con il messaggio di protocollo */@/Key/@/*.
```java
out.println("/@/Key/@/ STOP");        
```
Dice al Client di uscire dalla sezione di attesa ricezione delle chiavi.

```java
out.println(userToSend + "/@/Key/@/<-not found");
```
Specifica al Client quali utenti non sono stati trovati
```java
Pattern.matches("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$, userName)) 
```
Controlla se userName rispetta lo standard deciso: solo trattini alti o bassi interni al nome.

# Ulteriori informazioni
## Build with
- Java version 17

##  Traguardi
- [x] In caso di utente inesistente notificare il client dell'errore.
- [x] Evitare la presenza di più client con lo stesso nome collegati contemporaneamente.
- [x] Gestita fase di Exit dei client con annesso cancellazione userName dalla mappa.
- [ ] Programma funzionante su MacOs  (￣▼￣)

## License
Copyright (c) 22 Giugno anno 0, 2021, SafJNest and/or its affiliates. All rights reserved. SAFJNEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.

## Contacts

### Galantini Corrado
- galantini.corrado@istitutomontani.edu.it
- Git <a href="https://github.com/XOShu4%22%3EXOShu4</a> 
- Discord Mario Giordano#3698
   
### Lorenzo Sanseverino
- lorenzosanseverino2003@gmail.com
- Git <a href="https://github.com/NeutronSun%22%3ENeutronSun</a> 
- Discord Sun#7606.

### Panichi Leonardo
- panichileonardo4@gmail.com
- Git <a href="https://github.com/Leon412%22%3ELeon412</a> 
- Discord Leon_#7949
