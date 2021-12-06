# ClientServer_Algorithm

### Introduzione al progetto

## Panoramica
#Scopo del programma: 
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
Specificando un UserName, online in quel momento, e aggiungendo un messaggio si puo communicare con altri utenti.
#possibili comandi sono:
**/UserName(/s): messaggio** -> invia *messaggio* a tutti gli utenti specificati, se uno o piu utenti non vengono trovati viene segnato *UserName* not fount, ed il messaggio viene inviato solo agli utenti trovati
/listaUtenti0 -> 
 


### Incarichi del server
Attendere richieste di connessone da parte del server.
 Quando avviene la connessione con un client avvia un thread della classe ServerThread
   
### Spiegazione delle classi

## Supporti 
