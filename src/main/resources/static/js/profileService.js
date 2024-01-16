const backendUrl = 'http://localhost:8080';
export function addBalance(token, amount){
    return fetch(backendUrl+'/deposit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({token: token, amount: amount}),
        credentials: 'include',
    })
        .then(response => {
            if (!response.ok) { //gestisce errori HTTP in base al codice di stato della risposta.
                console.log("response not ok" + response.status);
            }
            return response.json();
        })
}

export function substractBalance(token, amount){
    return fetch(backendUrl+'/withdraw', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({token: token, amount: amount}),
        credentials: 'include',
    })
        .then(response => {
            if (!response.ok) { //gestisce errori HTTP in base al codice di stato della risposta.
                console.log("response not ok" + response.status);
            }
            return response.json();
        })
}
