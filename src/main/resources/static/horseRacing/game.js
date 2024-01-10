import {generateResult, getBalance} from "../js/gameService.js";
import {GameInformation} from "../js/gameInformation.js";
import {GameType} from "../js/gameType.js";

//parte per ottenere il token da URL
const urlParams = new URLSearchParams(window.location.search);
let token;
if(urlParams.has('token')) {
	token = urlParams.get('token');
	console.log('Token found: ' + token);
}else {
	console.log('Token not found');
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

//initializeAll();
function initializeAll(){
	document.getElementById('funds').innerText = funds;

	//Start the function when the document loaded
	//document.addEventListener("DOMContentLoaded", function(event) {

		var horse1 = new Horse('horse1', 20, 4);
		var horse2 = new Horse('horse2', 20, 8);
		var horse3 = new Horse('horse3', 20, 12);
		var horse4 = new Horse('horse4', 20, 16);
		//Event listener to the Start button
		document.getElementById('start').onclick = function(){
			amount = parseInt(document.getElementById('amount').value);

			alert('amount: ' + amount);
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

			//num_lap = parseInt(document.getElementById('num_lap').value);
			num_lap = 1;
			bethorse = parseInt(document.getElementById('bethorse').value);

			if (funds < amount){
				alert('Not enough funds.');
			}
			/*
            else if (num_lap <= 0){
                alert('Number of lap must be greater than 0.');
            }*/
			else{
				/*Started the game*/
				document.getElementById('raceSound').pause();
				document.getElementById('raceSound').currentTime = 0;
				document.getElementById('raceSound').play();

				this.disabled = true;/*Disable the start button*/
				var tds = document.querySelectorAll('#results .result');//Get all cells of result table.
				for (var i = 0; i < tds.length; i++) {
					tds[i].className = 'result';//Reset the result.
				}

				var winningHorse = 'horse3';
				document.getElementById('funds').innerText = funds;
				results = [];//Results array is to save the horse numbers when the race is finished.
				horse1.run();
				horse2.run();
				horse3.run();
				horse4.run();


				switch (winningHorse){
					case 'horse1':
						horse1.vincente = true;
						//alert('Horse 1 wins!')
						break;
					case 'horse2':
						horse2.vincente = true;
						//alert('Horse 2 wins!')
						break;
					case 'horse3':
						horse3.vincente = true;
						//alert('Horse 3 wins!')
						break;
					case 'horse4':
						horse4.vincente = true;
						//alert('Horse 4 wins!')
						break;
				}
			}
		}
	//});
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

		//Push the horse number to results array, according the the results array, we know the order of race results
		results.push(this.number);

		//Win horse
		if (results.length == 1){
			//If win horse is the bet horse, then add the fund
			if (this.number == bethorse){
				funds += amount;
			}else{
				funds -= amount;
			}
			document.getElementById('funds').innerText = funds;
		}else if (results.length == 4){
			//All horse arrived, enable again the Start Button
			document.getElementById('start').disabled = false;
		}
	}
}

