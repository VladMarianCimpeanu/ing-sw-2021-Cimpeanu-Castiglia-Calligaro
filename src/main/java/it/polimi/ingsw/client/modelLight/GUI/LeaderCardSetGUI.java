package it.polimi.ingsw.client.modelLight.GUI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.modelLight.CLI.LeaderCardCLI;
import it.polimi.ingsw.client.modelLight.LeaderCardSetView;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LeaderCardSetGUI extends LeaderCardSetView {
    protected ArrayList<LeaderCardGUI> cards;

    public LeaderCardSetGUI() {
        cards = new ArrayList<>();
        String srcPath = "/leaderCards.json";
        Reader reader = new InputStreamReader(this.getClass().getResourceAsStream(srcPath), StandardCharsets.UTF_8);
        Gson converter = new Gson();
        Type cardsType = new TypeToken<ArrayList<LeaderCardGUI>>(){}.getType();
        cards = converter.fromJson(reader, cardsType);
    }

    @Override
    public void show() {

    }

    @Override
    public void update(ArrayList<Integer> idS) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public int getIDfromIndex(int index) {
        return 0;
    }

    @Override
    public void activate(int id) {

    }

    @Override
    public void dumpMessage(String message) {

    }

    public ArrayList<LeaderCardGUI> getCards() {
        return new ArrayList<>(cards);
    }
}
