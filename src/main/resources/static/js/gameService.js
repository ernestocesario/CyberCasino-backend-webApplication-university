const backendUrl = 'http://localhost:8080';

// Funzione per inviare una richiesta POST per generare il risultato del gioco
//come parametro prende l'oggetto GameInformation di gameInformation.js
export function generateResult(gameInformation) {
    //serve per inviare una richiesta POST al backend
    return fetch(backendUrl+'/play', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        //converte l'oggetto in una stringa JSON
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
    //serve per inviare una richiesta POST al backend
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

export function getToken(urlParam){
    //se l'URL contiene il parametro token, ritorna il valore del parametro
    if(urlParam.has('token')){
        return urlParam.get('token');  //return string
    }
    return null; //return null
}
