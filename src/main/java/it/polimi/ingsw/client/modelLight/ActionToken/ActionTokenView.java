package it.polimi.ingsw.client.modelLight.ActionToken;

public abstract class ActionTokenView {
    public abstract void show(int id);

    protected ActionToken getActionToken(int id){
        for(ActionToken token: ActionToken.values())
            if(token.getId() == id) return token;
        return null;
    }
}
