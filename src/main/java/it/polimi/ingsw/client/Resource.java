package it.polimi.ingsw.client;

/**
 * enum representing resources to the client.
 */
public enum Resource {
    FAITH("\u001B[31m┼", "images/punchboard/faith.png"),
    COIN("\u001B[33m©", "images/punchboard/coin.png"),
    SHIELD("\u001B[34m█", "images/punchboard/shield.png"),
    STONE("\u001B[90m░", "images/punchboard/stone.png"),
    SERVANT("\u001B[95m§", "images/punchboard/servant.png");
    public static final String RESET = "\u001B[0m";

    private final String escape;
    private final String url;

    Resource(String escape, String url) {
        this.escape = escape;
        this.url = url;
    }

    /**
     * Return the representation of the resource as a string.
     * @return a string formed by two escape characters: the first one indicates the color of the resource, whereas
     * the second represent the symbol used to represent the resource.
     */
    public String escape(){
        return escape;
    }

    /**
     * gives the image of the resource.
     * @return the path of the pic representing the resource, starting from resources directory.
     */
    public String url(){
        return url;
    }

    /**
     * given a string, it calculates how many spaces it needs to append in order to have a standard width for
     * the representation inside a dumped box
     * @param content
     * @return
     */
    private static int calculateSpace(String content){
        int size = "┌──────────────┐".length() - 2;
        return size - content.length() + Resource.COIN.escape.length();
    }

    /**
     * creates a string containing 'n' desired strings
     * @param times specified number of spaces that the string should contains.
     * @return returns a string containing 'times' spaces.
     */
    private static String multiplySpaces(int times){
        String space = "";
        for(int i = 0; i < times; i++)
            space += " ";
        return space;
    }

    /**
     * displays a legend showing how the resources are represented in CLI.
     */
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