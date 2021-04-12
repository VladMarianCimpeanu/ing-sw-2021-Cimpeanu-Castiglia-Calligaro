package it.polimi.ingsw;

import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.leaderCards.*;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class JsonToLeaderCard {
    private static ArrayList<LeaderCard> leaderCardsSet;
    private JsonToLeaderCard(){
    }

    public static ArrayList<LeaderCard> readLeaderCards() throws IOException {
        if(leaderCardsSet == null)
            leaderCardsSet = parseLeaderCards();
        return leaderCardsSet;
    }

    private static ArrayList<LeaderCard> parseLeaderCards() throws IOException {
        ArrayList<LeaderCard> cards = new ArrayList<>();
        File file = new File("C:\\Users\\Nicola\\Documents\\GitHub\\ing-sw-2021-Cimpeanu-Castiglia-Calligaro\\src\\main\\java\\leadercards.json");
        InputStream in = new FileInputStream(file);
        JsonReader reader = new JsonReader(new InputStreamReader(in));

        String current_type = null;
        Resource resource = null;
        ArrayList<Requirement> requirements;
        int victoryPointsAmount = 0;
        String type_req = null;

        reader.beginArray();
        while(reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String next = reader.nextName();
                switch (next) {
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
                        switch (current_type) {
                            case "market":
                                cards.add(new MarketSkill(requirements, resource, victoryPointsAmount));
                                break;
                            case "depot":
                                cards.add(new DepotSkill(requirements, resource, victoryPointsAmount));
                                break;
                            case "buycard":
                                cards.add(new BuyCardSkill(requirements, resource, victoryPointsAmount));
                                break;
                            case "production":
                                cards.add(new ProductionSkill(requirements, resource, victoryPointsAmount));
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
                reader.nextName();
                int numberResources = reader.nextInt();
                if(numberResources < 0) throw new NoSuchElementException("Negative values of numberResources are not allowed. Value found is: "+numberResources);
                reader.nextName();
                Resource resource =Resource.valueOf(reader.nextString());       //throws its own IllegalArgumentException
                requirements.add(new ResourceRequirement(numberResources, resource));
            }else if(type.equals("development")){
                reader.nextName();
                int numberCards = reader.nextInt();
                if(numberCards < 0) throw new NoSuchElementException("Negative values of numberCards are not allowed. Value found is: "+numberCards);
                reader.nextName();
                int levelCard = reader.nextInt();
                if (levelCard < 0 || levelCard > 3) throw new NoSuchElementException("Value out of Range[1,3] of levelCard. Value found is: "+levelCard);
                reader.nextName();
                Color color = Color.valueOf(reader.nextString());   //throws its own IllegalArgumentException
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
