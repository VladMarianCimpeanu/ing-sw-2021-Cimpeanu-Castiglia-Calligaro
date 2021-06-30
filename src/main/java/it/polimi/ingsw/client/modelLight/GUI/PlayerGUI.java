package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.modelLight.PlayerView;

public class PlayerGUI extends PlayerView {

    public PlayerGUI(String nickname) {
        this.nickname = nickname;
        leaderCards = new LeaderCardSetGUI();
        decks = new DevelopmentCardDecksGUI();
        strongbox = new StrongboxGUI();
        depot = new DepotGUI();
        popeMeetings = new boolean[3];
        for(int i = 0; i<3; i++)
            popeMeetings[i] = false;

        if(nickname.equals(GUI.getClient().getNickname())){
            GUI.getGamePanel().addGameboard((DevelopmentCardDecksGUI) decks);
            GUI.getGamePanel().addGameboard((StrongboxGUI) strongbox);
            GUI.getGamePanel().addGameboard((DepotGUI) depot);
            GUI.getGamePanel().addGameboard((LeaderCardSetGUI)leaderCards);
        }
    }

    @Override
    public void dumpPlayer(String player, String objectUpdated) {
        String row;
        if(player.equals(nickname))
            row = ("Your");
        else
            row = (player);
        GUI.print(row + "'s "+ objectUpdated +" has changed into:  ");
    }

    @Override
    public void attendPopeMeeting(int victoryPoints) {
        switch(victoryPoints){
            case 2:
                popeMeetings[0] = true;
                break;
            case 3:
                popeMeetings[1] = true;
                break;
            case 4:
                popeMeetings[2] = true;
                break;
            default:
        }
    }
}
