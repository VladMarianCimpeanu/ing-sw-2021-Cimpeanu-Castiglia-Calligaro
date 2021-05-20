package it.polimi.ingsw.client;

//TODO: ask if these escape characters associated with unicode characters will behave in the same way
// in different platforms

/**
 * enum representing resources to the client.
 */
public enum Resource {
    FAITH("\u001B[31m┼"),
    COIN("\u001B[33m©"),
    SHIELD("\u001B[34m█"),
    STONE("\u001B[90m░"),
    SERVANT("\u001B[95m§");
    public static final String RESET = "\u001B[0m";

    private final String escape;

    Resource(String escape) {
        this.escape = escape;
    }

    /**
     * Return the representation of the resource as a string.
     * @return a string formed by two escape characters: the first one indicates the color of the resource, whereas
     * the second represent the symbol used to represent the resource.
     */
    public String escape(){
        return escape;
    }

    private static int calculateSpace(String content){
        int size = "┌──────────────┐".length() - 2;
        return size - content.length() + Resource.COIN.escape.length();
    }

    private static String multiplySpaces(int times){
        String space = "";
        for(int i = 0; i < times; i++)
            space += " ";
        return space;
    }

    public static void showLegend(){
        String upperLegend = "┌──────────────┐";
        System.out.println(upperLegend);
        for(Resource res : Resource.values()){
            String content = "│ " + res.toString() + " " + res.escape;
            System.out.println(content + multiplySpaces(calculateSpace(content)) + RESET + "│ ");
        }
        System.out.println("└──────────────┘");
    }

}



//┐ └ ─ ┘┌ | │