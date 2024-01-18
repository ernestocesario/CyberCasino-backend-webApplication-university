import {generateResult, getBalance} from "../js/gameService.js";
import {GameInformation} from "../js/gameInformation.js";
import {GameType} from "../js/gameType.js";

const urlParams = new URLSearchParams(window.location.search);
let token;
if(urlParams.has('token')) {
  token = urlParams.get('token');
  console.log('Token found: ' + token);
}else {
  alert("Non siamo riusciti a verificare il tuo account, verrai reindirizzato alla home");
  window.location.href = "http://localhost:4200";
}

var vm = new Vue({
  el: '#app',
  data: {
    gather: true,
    funds: 0,
    state: "Press the cards to start a game",
    symbols: [
      {label: "spades",symbol: "♠" },
      {label: "hearts",symbol: "♥" },
      {label: "diamonds",symbol: "♦" },
      {label: "clubs",symbol: "♣" }
    ],
    cards:  [
      {
        id: 1,label: "spades",open: true
      },
      {
        id: 2,label: "hearts", open: false
      },
      {
        id: 3,label: "clubs", open: false
      },
      {
        id: 4,label: "diamonds", open: false
      }
    ],
    question: null,
    mode: "",
    count: 0
    
  },
  methods: {
    shuffle(){
      let newOrder = [1,2,3,4].sort((a,b)=> Math.random()>0.5?1:-1)
      this.cards.forEach((card,cid)=> card.id=newOrder[cid])
    },
    turnAll(state){
      this.cards.forEach(card => card.open=state)
    },
    startGame(){
      getBalance(token).then(
          value => {
            this.funds = parseInt(value);
            console.log("balance:"+funds);
          }
      );
      var amount = parseInt(document.getElementById('amount').value);
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
      if (funds < amount){
        alert('Not enough funds.');
      }
      this.cards = [
        {
          id: 1,label: "spades",open: true
        },
        {
          id: 2,label: "hearts", open: false
        },
        {
          id: 3,label: "clubs", open: false
        },
        {
          id: 4,label: "diamonds", open: false
        }
      ]
      this.mode=""
      this.question = this.symbols[parseInt(Math.random()*4)]
      this.turnAll(false)
      this.gather = true
      this.state="Ready..."
      setTimeout(()=> {
        this.gather=false
        this.state="Your mission is .."
      },1000)
      setTimeout(()=> {
        this.turnAll(true)
        this.state="Find "+this.question.label + this.question.symbol + "!"
      },2000)
      setTimeout(()=> {
        this.turnAll(false)
        this.state="Get ready..."
      },4000)
      this.count=0
      setTimeout(()=>{
        let startShuffle= () => {
          this.shuffle()
          console.log("Shuffle! "+this.count)
          if( this.count++ < 6){
            setTimeout(startShuffle,300)
          }else{
            this.state="Please pick out "+ this.question.label + this.question.symbol
            this.mode="Answer"
          }
        }
        startShuffle()
      },6000)
      
    },
    getSymbol(label){
      let result = this.symbols.find(s => s.label == label)
      return result?result.symbol:label
    },
    getCard(label){
      return this.cards.find(card=> card.label==label)
    },
    openCard(card){
      //in questo caso a differenza della roulette gameInformation me lo gestisco in questo modo:
      // in bet ci metto la puntata effettuata, e in betOn ci metto il cavallo su cui ho puntato
      //nella roulette essendo tutto piu complicato, bet non lo utilizzavo, e tenevo tutto in betOn che era
      // un array di oggetti "Bet" che conteneva il numero su cui si puntava e la puntata effettuata e il tipo


      if(this.mode== "Answer"){
        var amount = parseInt(document.getElementById('amount').value);
        var betOn = [card.id];
        console.log("betOn "+betOn);
        console.log("amount "+amount);
        var gameInformation = GameInformation.create(token, GameType.GUESS_THE_CARD, amount, betOn, "");
        var gameInformation = generateResult(gameInformation)
            .then( GameResult => {
              console.log("result "+GameResult.result);
              console.log("balance "+GameResult.balance);
              var winningCard = GameResult.result.toString();
              console.log("typeWinningHorse "+ typeof winningHorse);

              card.open =!card.open
              var winnerCard = this.getCard(this.question.label)
              var labelToSwitch = winnerCard.label
              var prova = this.cards.find(card=> card.id==GameResult.result)
              winnerCard.label = prova.label
              prova.label = labelToSwitch
              if(card.id == GameResult.result){
                this.state="You get the "+this.question.label+this.question.symbol+"!!!"

              } /*
              else if(card.label == this.question.label) {
                console.log(" BARARE: card.label "+card.label);
                card.label = "hearts";
              } */
              else{
                //if (winnerCard.id !== GameResult.result){

                //}
                this.state="You lose!!!"
                setTimeout(() => {
                  let card = this.getCard(this.question.label)
                  card.open=true
                },1000)
              }
              setTimeout(() => {
                this.startGame()
              },3000)

              //funds = GameResult.balance;
            })
      }else{
        this.startGame()
      }
    }
  },
  mounted(){
    this.startGame()
  }
  
})