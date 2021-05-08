package it.polimi.ingsw;

import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.NoCardException;
import it.polimi.ingsw.model.leaderCards.*;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class is used to read from a JSON File all the information about the leader cards needed to play and create a list of them
 * Singleton approach is being used for the purpose reading the File only the first time that the list of cards is required and saving them in the related private attribute
 */

public class JsonToLeaderCard {
    private static ArrayList<LeaderCard> leaderCardsSet = null;
    private JsonToLeaderCard(){
    }

    public static ArrayList<LeaderCard> readLeaderCards() throws IOException {
        if(leaderCardsSet == null)
            leaderCardsSet = parseLeaderCards();
        return new ArrayList<>(leaderCardsSet);
    }

    /**
     * @param ID specified leaderCard's ID
     * @return the leaderCard with the specified ID; if no leaderCard has the specified ID, it returns null.
     */
    public static LeaderCard getLeaderCard(int ID) throws NoCardException {
        if(leaderCardsSet == null) {
            try {
                leaderCardsSet = parseLeaderCards();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (ID > 0 && ID < 17) {
            return leaderCardsSet.get(ID - 1);
        }
        throw new NoCardException();
    }

    private static ArrayList<LeaderCard> parseLeaderCards() throws IOException {
        ArrayList<LeaderCard> cards = new ArrayList<>();
        File file = new File("src/jsonSources/leaderCards.json");
        InputStream in = new FileInputStream(file);
        JsonReader reader = new JsonReader(new InputStreamReader(in));

        String current_type;
        Resource resource = null;
        ArrayList<Requirement> requirements;
        int victoryPointsAmount = 0;
        String type_req = null;
        int ID = 0;

        reader.beginArray();
        while(reader.hasNext()) {
            current_type = null;
            reader.beginObject();
            while (reader.hasNext()) {
                String next = reader.nextName();
                switch (next) {
                    case "ID":
                        ID = reader.nextInt();
                        break;
                    case "type":
                        current_type = reader.nextString();
                        break;
                    case "resource":
                        resource = Resource.valueOf(reader.nextString());   //throws its own IllegalArgumentException
                        break;
                    case "victoryPoints":
                        victoryPointsAmount = reader.nextInt();
                        if(victoryPointsAmount < 0) throw new NoSuchElementException("Negative values of victoryPoints are not allowed. Value found is: "+victoryPointsAmount);
                        break;
                    case "type_req":
                        type_req = reader.nextString();
                        break;
                    case "requirements":
                        requirements = readRequirement(reader, type_req);
                        if(current_type == null) throw new NoSuchElementException("Type of the leader card cannot be identified");
                        switch (current_type) {
                            case "market":
                                cards.add(new MarketSkill(requirements, resource, victoryPointsAmount, ID));
                                break;
                            case "depot":
                                cards.add(new DepotSkill(requirements, resource, victoryPointsAmount, ID));
                                break;
                            case "buyCard":
                                cards.add(new BuyCardSkill(requirements, resource, victoryPointsAmount, ID));
                                break;
                            case "production":
                                cards.add(new ProductionSkill(requirements, resource, victoryPointsAmount, ID));
                                break;
                            default:
                                throw new NoSuchElementException(next + " is an undefined type for LeaderCard");
                        }
                        break;
                    default:
                        throw new NoSuchElementException(next + " is an undefined field for LeaderCard");
                }
            }
            reader.endObject();
        }
        reader.endArray();
        reader.close();
        return cards;
    }

    private static ArrayList<Requirement> readRequirement(JsonReader reader, String type) throws IOException {
        ArrayList<Requirement> requirements = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()){
            reader.beginObject();
            if(type.equals("resource")){
                int numberResources = -1;
                Resource resource = null;
                while(reader.hasNext()) {
                    String next = reader.nextName();
                    switch (next){
                        case "numberResource":
                            numberResources = reader.nextInt();
                            if (numberResources < 0)
                                throw new NoSuchElementException("Negative values of numberResources are not allowed. Value found is: " + numberResources);
                            break;
                        case "resource":
                            resource = Resource.valueOf(reader.nextString());       //throws its own IllegalArgumentException
                            break;
                        default:
                            throw new NoSuchElementException(next + " is an undefined field for LeaderCard");
                    }
                }
                if(numberResources == -1) throw new NoSuchElementException("There was no numberResources field in the requirement description");
                if(resource == null) throw new NoSuchElementException("There was no resource field in the requirement description");
                requirements.add(new ResourceRequirement(numberResources, resource));
            }else if(type.equals("development")){
                int numberCards = -1;
                int levelCard = -1;
                Color color = null;
                while(reader.hasNext()) {
                    String next = reader.nextName();
                    switch(next){
                        case "numberCards":
                            numberCards = reader.nextInt();
                            if (numberCards < 0)
                                throw new NoSuchElementException("Negative values of numberCards are not allowed. Value found is: " + numberCards);
                            break;
                        case "levelCard":
                            levelCard = reader.nextInt();
                            if (levelCard < 0 || levelCard > 3)
                                throw new NoSuchElementException("Value out of Range[1,3] of levelCard. Value found is: " + levelCard);
                            break;
                        case "color":
                            color = Color.valueOf(reader.nextString());   //throws its own IllegalArgumentException
                            break;
                        default:
                            throw new NoSuchElementException(next + " is an undefined field for LeaderCard");
                    }
                }
                if(numberCards == -1) throw new NoSuchElementException("There was no numberCards field in the requirement description");
                if(levelCard == -1) throw new NoSuchElementException("There was no levelCard field in the requirement description");
                if(color == null) throw new NoSuchElementException("There was no color field in the requirement description");
                requirements.add(new DevelopmentRequirement(numberCards, levelCard, color));
            }else{
                throw new NoSuchElementException(type + " is an undefined field for type of requirement");
            }
            reader.endObject();
        }
        reader.endArray();
        return requirements;
    }
}
