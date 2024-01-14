const backendUrl = 'http://localhost:8080';

// Funzione per inviare una richiesta POST per generare il risultato del gioco
//come parametro prende l'oggetto GameInformation di gameInformation.js
export function generateResult(gameInformation) {
    return fetch(backendUrl+'/play', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(gameInformation),
        credentials: 'include',
    })
        .then(response => {
          if (!response.ok) { //gestisce errori HTTP in base al codice di stato della risposta.
              console.log("response not ok"+response.status);
          }
          return response.json();
        })
        .catch( error => {
            console.error("Internal Server Error", error);
        }); // il blocco .catch gestisce errori di rete e di parsing JSON
}

// Funzione per inviare una richiesta POST per ottenere il saldo
export function getBalance(token){
    return fetch(backendUrl+'/getBalance', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({token: token}),
        credentials: 'include',
    })
        .then(response => {
            if (!response.ok) { //gestisce errori HTTP in base al codice di stato della risposta.
                console.log("response not ok" + response.status);
            }
            return response.json();  //ritorna un number (saldo utente)
        })
}

// Dichiarazione dell'interfaccia GameResult
/*
interface GameResult {
    result: string[];
    balance: number;
}
*/
