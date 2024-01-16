import {getBalance} from "../js/gameService.js";

const alertMessageGenericError = 'Errore interno. Si prega di riprovare.';
// Ottenere i parametri dall'URL
const urlParams = new URLSearchParams(window.location.search);

let token;
// Verificare se il parametro "token" è presente nell'URL
if (urlParams.has('token')) {
    // Ottenere il valore del parametro "token"
    token = urlParams.get('token');
    console.log('Token:', token);
} else {
    alert('Non siamo riusciti a verificare il tuo account, verrai reindirizzato alla pagina di login');
    window.location.href = "http://localhost:4200";
}

let value = getBalance(token)
    .then(
        value => {
            document.getElementById('balanceValue').innerText = value;
        });

