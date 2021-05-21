package it.polimi.ingsw.client.MessageFromServer;

public enum ErrorMessage {
    invalidCommand("You are not allowed to perform this action"),
    invalidLeaderCardID("Not such leader card..."),
    invalidDevelopmentCardID("Not such development card ..."),
    invalidShelf("You can not use this shelf"),
    invalidResource(""),
    invalidDeck("You can not use this deck."),
    invalidLevel("You can not access to this level."),
    notEnoughSpace("You have not enough space."),
    existingResource("You have a different resource in the specified shelf, try again."),
    requirementsNotSatisfied("You do not satisfy the requirements needed for the activation."),
    invalidMarketPosition("There is not such position in the market."),
    invalidDirection("The direction given for the market has not been recognized: try again with 'row' or 'column"),
    noSuchDevelopmentCard("No such card exists on the grid."),
    wrongLevel("You can not buy a development card of such level."),
    notEnoughResources("You have not enough resources to perform this action."),
    nullResource("Cannot resolve the given resource."),
    nullPosition("Cannot resolve the position."),
    wrongSizeInput("The number of resources is incorrect."),
    productionUsed("You have already used this production on this turn."),
    productionAlreadyStarted("You have already started to produce something else."),
    generic("Something wrong happened."),
    invalidNickname("You are not allowed to use this nickname."),
    usedNickname("There is already someone else who is using this nickname, try again,"),
    expectedLogin("Wrong command, you must login before."),
    invalidJson("Something strange happened... Please notify the developers."),
    invalidMode("This game can not support di game mode."),
    notYouTurn("This is not your turn, wait yours."),
    invalidStorage("You have tried to access to a not existing storage.");

    private final String caption;

    ErrorMessage(String caption){
        this.caption = caption;
    }

    public String getCaption(){
        return caption;
    }
}
