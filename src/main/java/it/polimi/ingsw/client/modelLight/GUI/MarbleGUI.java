package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Marble;
import it.polimi.ingsw.client.Shape;

public class MarbleGUI {
    private String url;
    private Shape shape;

    public MarbleGUI(Marble marble, Shape shape) {
        this.url = marble.url();
        this.shape = shape;
    }

    public String getUrl() {
        return url;
    }

    public Shape getShape() {
        return shape;
    }
}
