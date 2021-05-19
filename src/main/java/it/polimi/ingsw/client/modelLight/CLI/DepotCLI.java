package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.DepotView;

public class DepotCLI extends DepotView {
    @Override
    public void show() {
        //On Intellij Shell length of Resources greater than normal characters
        if(quantity[0] != 0)
            System.out.println("         "+resources[0].escape()+ Resource.RESET);
        else System.out.println("  ");
        System.out.println("        ═══");
        System.out.print("      ");
        for(int i = 0; i<quantity[1]; i++)
            System.out.print(resources[1].escape()+Resource.RESET+"     ");
        System.out.print("\n     ");
        for(int i = 0; i<2; i++)
            System.out.print("═══   ");
        System.out.print("\n   ");
        for(int i = 0; i<quantity[2]; i++)
            System.out.print(resources[2].escape()+"     "+Resource.RESET);
        System.out.print("\n  ");
        for(int i = 0; i<3; i++)
            System.out.print("═══   ");
        System.out.println();
    }
}
