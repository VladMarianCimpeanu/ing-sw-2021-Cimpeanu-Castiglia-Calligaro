package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.Message;
import it.polimi.ingsw.model.leaderCards.LeaderCard;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ){

        ArrayList<String> parametri = new ArrayList<>();
        parametri.add("Ciao");
        parametri.add("Nick");

        Message message = new Message("VladCiRinuncia", parametri);
        Gson gson = new Gson();
        String outputDiGoogle = gson.toJson(message);
        System.out.println(outputDiGoogle);
    }
}

