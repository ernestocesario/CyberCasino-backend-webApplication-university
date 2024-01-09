export const GameInformation = {
    create: function(sessionToken, gameType, bet, additionalInfo) {
        return {
            sessionToken: sessionToken,
            gameType: gameType,
            bet: bet,
            additionalInfo: additionalInfo,
        };
    },
};