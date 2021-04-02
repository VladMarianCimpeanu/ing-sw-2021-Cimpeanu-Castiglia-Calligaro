package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MultiplayerTest {
    private Multiplayer game;

    @BeforeEach
    public void init(){
        //create a list of 4 players
        ArrayList<Player> list = new ArrayList<Player>();
        for(int i = 0; i<3; i++)
            list.add(new Player(String.valueOf(i), 0, 0));
        //create a multiplayer game
        game = new Multiplayer(list);
    }

    @Test
    public void checkLeaderCards(){
        //assert that every player has exactly 2 leadercards at the beginning of the game
        for(Player p: game.getPlayers())
            assertTrue(p.getLeaderCards().size() == 2);
    }


}