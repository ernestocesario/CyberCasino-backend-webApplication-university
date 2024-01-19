import {generateResult, getBalance, getToken} from "../js/gameService.js";
import {GameInformation} from "../js/gameInformation.js";
import {GameType} from "../js/gameType.js";

// Ottenere i parametri dall'URL
const urlParams = new URLSearchParams(window.location.search);
let token = getToken(urlParams);
if (token==null){
  alert('We were unable to verify your account, you will be redirected to the login page.');
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
      document.getElementById('shuffleCardsSound').play();
    },
    turnAll(state){
      this.cards.forEach(card => card.open=state)
    },
    startGame(){
      getBalance(token).then(
          value => {
            this.funds = parseInt(value);
          }
      );
      document.getElementById('restartSound').play();
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
      document.getElementById('flipCardSound').play();
      if(this.mode== "Answer"){
        var amount = parseInt(document.getElementById('amount').value);
        var betOn = [card.id];
        var gameInformation = GameInformation.create(token, GameType.GUESS_THE_CARD, amount, betOn, "");
        var gameInformation = generateResult(gameInformation)
            .then( GameResult => {
              var winningCard = GameResult.result.toString();

              card.open =!card.open

              var winnerCard = this.getCard(this.question.label)
              var labelToSwitch = winnerCard.label
              var prova = this.cards.find(card=> card.id==GameResult.result)
              winnerCard.label = prova.label
              prova.label = labelToSwitch
              if(card.id == GameResult.result){
                document.getElementById('winSound').play();
                this.state="WIN! You get the "+this.question.label+this.question.symbol+"!!!"
              }
              else {
                document.getElementById('loseSound').play();
                this.state="YOU LOSE!"
              setTimeout(() => {
                let card = this.getCard(this.question.label)
                card.open=true
              },1000)
              }
              setTimeout(() => {
                this.startGame()
              },3000)
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