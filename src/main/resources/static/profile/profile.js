// Ottenere i parametri dall'URL
const urlParams = new URLSearchParams(window.location.search);

let allLoaded = false;
let token = '';
let transactionsLoaded = 0;
let matchesLoaded = 0;
document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    if (!urlParams.has('token')) {
        window.location.href = 'http://localhost:4200/';
    }
    else {
        allLoaded = true;
        //get token from url params
        token = urlParams.get('token');
        //get number of transactions from url params
        transactionsLoaded = urlParams.get('transactionsToShow');
        //get number of matches from url params
        matchesLoaded = urlParams.get('matchesToShow');

        var btnLoadMoreTransactions = document.getElementById('btnLoadMoreTransaction');
        btnLoadMoreTransactions.addEventListener('click', viewMoreTransactions);

        var btnLoadMoreMatches = document.getElementById('btnLoadMoreMatches');
        btnLoadMoreMatches.addEventListener('click', viewMoreMatches);

        var btnResetView = document.getElementById('btnResetView');
        btnResetView.addEventListener('click', resetView);
    }
});

resetView = function () {
    if(!allLoaded){
        return;
    }
    allLoaded = false;
    transactionsLoaded = 5;
    matchesLoaded = 5;
    reloadPage();
}

viewMoreTransactions = function () {
    if (!allLoaded) {
        return;
    }
    allLoaded = false;
    transactionsLoaded *= 2;
    reloadPage();
}

viewMoreMatches = function () {
    if (!allLoaded) {
        return;
    }
    allLoaded = false;
    matchesLoaded *= 2;
    reloadPage();
}

reloadPage = function () {
    window.location.href = 'http://localhost:8080/profile?token=' + token + '&transactionsToShow=' + transactionsLoaded + '&matchesToShow=' + matchesLoaded;
}