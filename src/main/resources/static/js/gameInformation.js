export const GameInformation = {
    create: function(sessionToken, gameType, bet, betOn, additionalInfo) {
        return {
            sessionToken: sessionToken,  //string
            gameType: gameType,   //enum GameType
            bet: bet,  //number della bet che ho fatto
            betOn: betOn,  //array diObjects, me lo gestisco come voglio (come array di betObj della roulette)
            additionalInfo: additionalInfo,  // servono per le slot, oltre a type.SLOT devo dire se .PremiumSlot o .MinerSlor ecc
        };
    },
};