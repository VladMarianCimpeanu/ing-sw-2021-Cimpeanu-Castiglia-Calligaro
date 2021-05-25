package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.Color;
import it.polimi.ingsw.client.Marble;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.LeaderCardView;

import java.util.ArrayList;

public class LeaderCardCLI extends LeaderCardView {
    private int ID;
    private String type;
    private Resource resource;
    private int victoryPoints;
    private String type_req;
    private ArrayList<RequirementCLI> requirements;
    private boolean activated;

    @Override
    public void show() {
    }

    public String[] generateAscii(){
        String[] leadercard = new String[7];
        if(activated)
            leadercard[0] = (Color.BLUE.escape() + "╔═════════════════╗  " + Color.RESET);
        else
            leadercard[0] = ("╔═════════════════╗  ");
        int size = 0;
        String row;
        if(activated)
            row = Color.BLUE.escape() + "║" + Color.RESET + "req ";
        else
            row = "║req ";
        for(RequirementCLI r: requirements){
            row += r.toString();
            size += r.size();
        }
        for(; size < 13; size++){
            row += " ";
        }
        if(activated)
            row += (Color.BLUE.escape() +  "║  " + Color.RESET);
        else
            row += "║  ";
        leadercard[1] = (row);
        if(activated)
            leadercard[2] = (Color.BLUE.escape() + "║" + Color.RESET + "VP " + victoryPoints + "             " + Color.BLUE.escape() + "║  " + Color.RESET);
        else
            leadercard[2] = ("║VP " + victoryPoints + "             ║  ");
        String effect;
        if(activated) {
            effect = Color.BLUE.escape() + "║" + Color.RESET + "effect " + type + "                 ";
            effect = effect.substring(0, 27);
            effect += Color.BLUE.escape() + "║  " + Color.RESET;
        }
        else{
            effect = "║effect " + type + "                 ";
            effect = effect.substring(0,18);
            effect += "║  ";
        }

        leadercard[3] = (effect);
        String[] effectString = generateEffect(activated);
        leadercard[4] = effectString[0];
        leadercard[5] = effectString[1];
        if(activated)
            leadercard[6] = (Color.BLUE.escape() + "╚═════════════════╝  " + Color.RESET);
        else
            leadercard[6] = ("╚═════════════════╝  ");
        return leadercard;
    }

    private String[] generateEffect(Boolean activated){
        if(activated){
            String r1 = Color.BLUE.escape() + "║                 ║  " + Color.RESET;
            String r2 = "";
            if(type.equals("buyCard")){
                r2 = Color.BLUE.escape() + "║" + Color.RESET + "       -1" + resource.escape() + Resource.RESET + Color.BLUE.escape() + "       ║  " + Color.RESET;
            }else if(type.equals("depot")){
                r2 = Color.BLUE.escape() + "║" + Color.RESET + "     [" + resource.escape() + "] [" + resource.escape() + Resource.RESET +  "]     " + Color.BLUE.escape() + "║  " + Color.RESET;
            }else if(type.equals("market")){
                r2 = Color.BLUE.escape() + "║" + Color.RESET + "      " + "@" + " = " + resource.escape() + Resource.RESET + Color.BLUE.escape() + "      ║  " + Color.RESET;
            }else{
                r1 = Color.BLUE.escape() + "║" + Color.RESET + "in " + "1" + resource.escape() + Resource.RESET + Color.BLUE.escape() + "            ║  " + Color.RESET;
                r2 = Color.BLUE.escape() + "║" + Color.RESET + "out 1\u001B[31mX" + Color.RESET + " 1" + Resource.FAITH.escape() + Resource.RESET + Color.BLUE.escape() + "        ║  " + Color.RESET;
            }
            String[] rows = new String[2];
            rows[0] = r1;
            rows[1] = r2;
            return rows;
        }
        String r1 = "║                 ║  ";
        String r2 = "";
        if(type.equals("buyCard")){
            r2 = "║       -1" + resource.escape() + Resource.RESET + "       ║  ";
        }else if(type.equals("depot")){
            r2 = "║     [" + resource.escape() + "] [" + resource.escape() + Resource.RESET +  "]     ║  ";
        }else if(type.equals("market")){
            r2 = "║      " + "@" + " = " + resource.escape() + Resource.RESET + "      ║  ";
        }else{
            r1 = "║in " + "1" + resource.escape() + Resource.RESET + "            ║  ";
            r2 = "║out 1\u001B[31mX" + Color.RESET + " 1" + Resource.FAITH.escape() + Resource.RESET + "        ║  ";
        }
        String[] rows = new String[2];
        rows[0] = r1;
        rows[1] = r2;
        return rows;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void activate(){
        activated = true;
    }
}
