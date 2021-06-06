package it.polimi.ingsw.server.controller.states;

/**
 * Identify all the possible errors that can be sent to the client when something goes wrong
 */
public enum ErrorMessage{
    invalidCommand,
    invalidLeaderCardID,
    invalidDevelopmentCardID,
    invalidShelf,
    invalidResource,
    invalidDeck,
    invalidLevel,
    notEnoughSpace,
    existingResource,
    requirementsNotSatisfied,
    invalidMarketPosition,
    invalidDirection,
    noSuchDevelopmentCard,
    wrongLevel,
    notEnoughResources,
    nullResource,
    nullPosition,
    wrongSizeInput,
    productionUsed,
    productionAlreadyStarted,
    generic,
    invalidNickname,
    usedNickname,
    expectedLogin,
    invalidJson,
    invalidMode,
    notYouTurn,
    invalidStorage,
}
