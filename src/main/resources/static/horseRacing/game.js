import {generateResult, getBalance, getToken} from "../js/gameService.js";
import {GameInformation} from "../js/gameInformation.js";
import {GameType} from "../js/gameType.js";

//parte per ottenere il token da URL
const urlParams = new URLSearchParams(window.location.search);
let token= getToken(urlParams);
if (token == null){
	alert("Non siamo riusciti a verificare il tuo account, verrai reindirizzato alla home");
	window.location.href = "http://localhost:4200";
}

var funds;
var num_lap = 1, results = [], bethorse, amount;

getBalance(token).then(
	value => {
		funds = parseInt(value);
		console.log("balance:"+funds);
	    initializeAll();
	}
);

function initializeAll(){
	document.getElementById('funds').innerText = funds;
	var horse1 = new Horse('horse1', 20, 4);  //white
	var horse2 = new Horse('horse2', 20, 8);  //blue
	var horse3 = new Horse('horse3', 20, 12); //green
	var horse4 = new Horse('horse4', 20, 16);  //brown
	//Event listener to the Start button
	document.getElementById('start').onclick = function(){
		amount = parseInt(document.getElementById('amount').value);
		// Check for negative or zero amount
		if (amount <= 0) {
			alert('Please enter a positive bet amount.');
			return;
		}
		// Check for invalid amount (not a number)
		if (isNaN(amount)) {
			alert('Please enter a valid bet amount.');
			return;
		}
		num_lap = 1;
		//esempio: horse+ 1 -->  horse1
		bethorse = "horse"+parseInt(document.getElementById('bethorse').value);
		// la checkBox è fatta di 4 valori:
		// 1 --> horse1 --> white   cavallo bianco
		// 2 --> horse2 --> blue     cavallo blu
		// 3 --> horse3 --> green   cavallo verde
		// 4 --> horse4 --> brown    cavallo marrone
		if (funds < amount){
			alert('Not enough funds.');
		}
		else{
			/*Started the game*/
			document.getElementById('winSound').pause();
			document.getElementById('trumpets').pause();
			document.getElementById('trumpets').currentTime = 0;
			document.getElementById('trumpets').play();
			document.getElementById('raceSound').pause();
			document.getElementById('raceSound').currentTime = 0;
			document.getElementById('raceSound').play();

			this.disabled = true;/*Disable the start button*/
			var tds = document.querySelectorAll('#results .result');//Get all cells of result table.
			for (var i = 0; i < tds.length; i++) {
				tds[i].className = 'result';//Reset the result.
			}
			funds -= amount;
			document.getElementById('funds').innerText = funds; //se arrivo qui vuol dire che potevo fare la puntata

			//in questo caso a differenza della roulette gameInformation me lo gestisco in questo modo:
			// in bet ci metto la puntata effettuata, e in betOn ci metto il cavallo su cui ho puntato
			//nella roulette essendo tutto piu complicato, bet non lo utilizzavo, e tenevo tutto in betOn che era
			// un array di oggetti "Bet" che conteneva il numero su cui si puntava e la puntata effettuata e il tipo
			var betOn = [bethorse];
			console.log("amount "+amount);
			var gameInformation = GameInformation.create(token, GameType.HORSE_RACE, amount, betOn, "");
			var gameInformation = generateResult(gameInformation)
				.then( GameResult => {
					console.log("result "+GameResult.result);
					console.log("balance "+GameResult.balance);
					var winningHorse = GameResult.result.toString();
					console.log("typeWinningHorse "+ typeof winningHorse);
					results = [];//Results array is to save the horse numbers when the race is finished.

					//ogni volta che riinizio la partita metto tutti i cavalli vincenti a false.
					horse1.vincente = false;
					horse2.vincente = false;
					horse3.vincente = false;
					horse4.vincente = false;

					horse1.run();
					horse2.run();
					horse3.run();
					horse4.run();

					switch (winningHorse){
						case 'horse1':
							console.log("horse1 wins");
							horse1.vincente = true;
							break;
						case 'horse2':
							console.log("horse2 wins");
							horse2.vincente = true;
							break;
						case 'horse3':
							console.log("horse3 wins");
							horse3.vincente = true;
							break;
						case 'horse4':
							console.log("horse4 wins");
							horse4.vincente = true;
							break;
					}
					funds = GameResult.balance;
				})
		}
	}
}

/*Create a Javascript Object for a horse with 3 parameters: HTML ID, position x and y*/
function Horse(id, x, y){
	var increaseForRandom = 10;
	var increaseConstant = 9;

	this.element = document.getElementById(id);/*HTML element of the horse*/
	this.speed = Math.random()*increaseForRandom + increaseConstant; /*Initiate a random speed for each horse, the greater speed, the faster horse. The value is between 10 and 20*/
	this.originX = x;/*Original X position*/
	this.originY = y;/*Original Y position*/
	this.x = x; /*Current X*/
	this.y = y; /*Current Y*/
	this.number = parseInt(id.replace(/[\D]/g, '')); /*Number of horse, number will be 1 or 2 or 3 or 4*/
	this.lap = 0; //Current lap of the horse

	this.vincente = false; //If the horse is the winner

	this.moveRight = function(){
		var horse = this;/*Assign horse to this object*/

		/*Use setTimeout to have the delay in moving the horse*/
		setTimeout(function(){
			//Move the horse to right 1vw
			horse.x ++;
			horse.element.style.left = horse.x +'vw';

			//Check if goes through the start line, if horse runs enough number of laps and has pass the start line then stop
			if (horse.lap == num_lap && horse.x > horse.originX + 6){
				horse.arrive();
			}else{
				//Make decision to move Down or not
				//The width of the Down Road is 10wh, then the distance of each horse is 2.5vw (4 horses). The right position of the road is 82.5vw
				//Continue to move right if not reach the point to turn
				if (horse.x < 82.5 - horse.number*2.5){
					horse.speed = Math.random()*increaseForRandom + increaseConstant; //!!!!!!!!!!!!!!!!!!!!!!!!!!!
					horse.moveRight();
				}else{
					//Change HTML class of horse to runDown
					horse.element.className = 'horse runDown';
					//Change the speed, will be random value from 10 to 20
					horse.speed = Math.random()*increaseForRandom + increaseConstant;
					horse.moveDown();
				}
			}

		}, 1000/this.speed);
		/* 1000/this.speed is timeout time*/
	}

	/*Do the same for moveDown, moveLeft, moveUp*/
	this.moveDown = function(){
		var horse = this;
		setTimeout(function(){
			horse.y ++;
			horse.element.style.top = horse.y +'vh';
			if (horse.y < horse.originY + 65){
				horse.moveDown();
			}else{
				horse.element.className = 'horse runLeft';
				horse.speed = Math.random()*increaseForRandom + increaseConstant;
				horse.moveLeft();
			}
		}, 1000/this.speed)
	}
	this.moveLeft = function(){
		var horse = this;
		setTimeout(function(){
			horse.x --;
			horse.element.style.left = horse.x +'vw';
			if (horse.x > 12.5 - horse.number*2.5){
				if (horse.vincente == true) {
					horse.speed = Math.random()*increaseForRandom + increaseConstant*1.7;
				}else{
					horse.speed = Math.random()*increaseForRandom + increaseConstant;
				}
				horse.moveLeft();
			}else{
				horse.element.className = 'horse runUp';
				horse.speed = Math.random() * increaseForRandom + increaseConstant;
				horse.moveUp();
			}
		}, 1000/this.speed)
	}
	this.moveUp = function(){
		var horse = this;
		setTimeout(function(){
			horse.y --;
			horse.element.style.top = horse.y +'vh';
			if (horse.y > horse.originY){
				if (horse.vincente == true) {
					horse.speed = Math.random()*increaseForRandom + increaseConstant* 3.3;
				}
				else{
					horse.speed = Math.random()*increaseForRandom + increaseConstant;
				}
				horse.moveUp();
			}else{
				horse.element.className = 'horse runRight';
				//Nearly finish the lap
				horse.lap ++;
				horse.moveRight();
			}
		}, 1000/this.speed)
	}

	/*Trigger the horse by run*/
	this.run = function(){
		this.element.className = 'horse runRight';
		this.moveRight();
	}
	this.arrive = function(){
		//Stop the horse run by change class to standRight
		this.element.className = 'horse standRight';
		this.lap = 0;//Reset the lap

		/*Show the result*/
		var tds = document.querySelectorAll('#results .result');//Get all table cell to display the result
		//results.length is the current arrive position
		tds[results.length].className = 'result horse'+this.number;//The class of result look like: result horse1...

		//Push the horse number to results array, according the results array, we know the order of race results
		results.push(this.number);
		if (results.length == 1){
			//arrivato il primo cavallo, faccio partire il winSound
			document.getElementById('winSound').pause();
			document.getElementById('winSound').currentTime = 0;
			document.getElementById('winSound').play();

		}

		if (results.length == 4){
			//All horse arrived, enable again the Start Button
			document.getElementById('start').disabled = false;
			document.getElementById('raceSound').pause();
			document.getElementById('funds').innerText = funds;
		}
	}
}

