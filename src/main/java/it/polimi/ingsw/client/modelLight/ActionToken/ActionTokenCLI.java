package it.polimi.ingsw.client.modelLight.ActionToken;

public class ActionTokenCLI extends ActionTokenView{

    @Override
    public void show(int id) {
        System.out.println(getActionToken(id).getDescription());
    }
}
