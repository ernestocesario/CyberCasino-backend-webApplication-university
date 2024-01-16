import {addBalance,substractBalance} from '../js/profileService.js';

const alertMessageGenericError = 'Errore interno. Si prega di riprovare.';
// Ottenere i parametri dall'URL
const urlParams = new URLSearchParams(window.location.search);
const operationType = urlParams.get('operationType');

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

document.getElementById('confirmButton').addEventListener('click', validateCardDetails);

function validateCardDetails() {
    var cardNumber = document.getElementById('cardNumber').value;
    var cardHolder = document.getElementById('cardHolder').value;
    var expiryDate = document.getElementById('expiryDate').value;
    var cvv = document.getElementById('cvv').value;
    var amount = document.getElementById('amount').value;

    // Controlli semplificati per la simulazione
    if (!isValidCardNumber(cardNumber)) {
        alert('Il Numero di Carta deve essere composto da 16 cifre.');
        return;
    }

    if (!isValidCardHolder(cardHolder)) {
        alert('Il proprietario della carta deve essere composto da sole lettere.');
        return;
    }

    if (!isValidFormat(expiryDate)) {
        alert('La data di scadenza deve essere nel formato MM/YYYY e il mese deve essere un mese valido.');
        return;
    }

    if (!isFutureExpiryDate(expiryDate)) {
        alert('Carta scaduta.');
        return;
    }

    if (!isValidCVV(cvv)) {
        alert('Il CVV deve essere composto da 3 cifre.');
        return;
    }

    if (!isValidAmount(amount)) {
        alert('La Quantità deve essere un numero maggiore di 0.');
        return;
    }

    if (operationType === 'preleva') {
        substractBalance(token, amount).then(r => {
            console.log(r);
            if (!r) alert('Il saldo non è sufficiente per effettuare il prelievo.');
            else {
                alert('I Dati della Carta sono Validi! Cash Prelevati: ' + amount);
            }
        });
        window.opener.location.reload();
        //window.close();
    }
    else if (operationType === 'deposita') {
        addBalance(token,amount).then(r => {
            if (!r) alert(alertMessageGenericError)
            return r;
        });
        alert('I Dati della Carta sono Validi! Cash Depositati (CI BALLIAMO LA FRESCA): ' + amount);
        window.opener.location.reload();
        window.close();
    }
    else {
        alert(alertMessageGenericError);
        return;
    }
}

function isValidCardNumber(cardNumber) {
    // Simulazione di un controllo di validità del numero di carta di credito
    return /^\d{16}$/.test(cardNumber);
}

function isValidCardHolder(cardHolder) {
    // Simulazione di un controllo di validità del nome del titolare della carta
    return /^[a-zA-Z ]+$/.test(cardHolder);
}

function isValidFormat(expiryDate) {
    // Simulazione di un controllo di validità della data di scadenza
    // Nell'esempio, verifichiamo che sia nel formato MM/YYYY
    var parts = expiryDate.split('/');
    var month = parseInt(parts[0], 10);
    var year = parseInt(parts[1], 10);

    var isValidMonth = month >= 1 && month <= 12;
    var isValidYear = !isNaN(year) && parts[1].length === 4;

    var isValidExpiryDate = parts.length === 2 && isValidMonth && isValidYear;

    return isValidExpiryDate;
}

function isFutureExpiryDate(expiryDate) {
    var parts = expiryDate.split('/');
    if (parts.length !== 2) {
        return false;
    }

    var currentYear = new Date().getFullYear();
    var currentMonth = new Date().getMonth() + 1;

    var month = parseInt(parts[0], 10);
    var year = parseInt(parts[1], 10);

    // Controllo se la data è nel futuro
    return !(year < currentYear || (year === currentYear && month < currentMonth));
}

function isValidCVV(cvv) {
    // Simulazione di un controllo di validità del CVV
    return /^\d{3}$/.test(cvv);
}

function isValidAmount(amount) {
    // Simulazione di un controllo di validità dell'importo
    // Nell'esempio, verifichiamo che sia un numero positivo
    return !isNaN(parseFloat(amount)) && parseFloat(amount) > 0;
}
