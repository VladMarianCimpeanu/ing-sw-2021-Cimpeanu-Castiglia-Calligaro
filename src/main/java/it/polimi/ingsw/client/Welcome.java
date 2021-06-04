package it.polimi.ingsw.client;

import static it.polimi.ingsw.client.Color.*;

/**
 * class used to welcome the player when he starts the cli game.
 */
public class Welcome {
    private static final String title1 = "█▀▄▀█ ██      ▄▄▄▄▄      ▄▄▄▄▀ ▄███▄   █▄▄▄▄   ▄▄▄▄▄       ████▄ ▄████      █▄▄▄▄ ▄███▄      ▄   ██   ▄█    ▄▄▄▄▄    ▄▄▄▄▄   ██      ▄   ▄█▄    ▄███▄   ";
    private static final String title2 = "█ █ █ █ █    █     ▀▄ ▀▀▀ █    █▀   ▀  █  ▄▀  █     ▀▄     █   █ █▀   ▀     █  ▄▀ █▀   ▀      █  █ █  ██   █     ▀▄ █     ▀▄ █ █      █  █▀ ▀▄  █▀   ▀  ";
    private static final String title3 = "█ ▄ █ █▄▄█ ▄  ▀▀▀▀▄       █    ██▄▄    █▀▀▌ ▄  ▀▀▀▀▄       █   █ █▀▀        █▀▀▌  ██▄▄    ██   █ █▄▄█ ██ ▄  ▀▀▀▀▄ ▄  ▀▀▀▀▄   █▄▄█ ██   █ █   ▀  ██▄▄    ";
    private static final String title4 = "█   █ █  █  ▀▄▄▄▄▀       █     █▄   ▄▀ █  █  ▀▄▄▄▄▀        ▀████ █          █  █  █▄   ▄▀ █ █  █ █  █ ▐█  ▀▄▄▄▄▀   ▀▄▄▄▄▀    █  █ █ █  █ █▄  ▄▀ █▄   ▄▀ ";
    private static final String title5 = "   █     █              ▀      ▀███▀     █                        █           █   ▀███▀   █  █ █    █  ▐                        █ █  █ █ ▀███▀  ▀███▀   ";
    private static final String title6 = "  ▀     █                               ▀                          ▀         ▀            █   ██   █                           █  █   ██                ";
    private static final String title7 = "       ▀                                                                                          ▀                           ▀           ";

    /**
     * dumps a welcome message.
     */
    public static void dump() {
        System.out.println(BLUE.escape() + title1);
        System.out.println(title2);
        System.out.println(title3);
        System.out.println(title4);
        System.out.println(title5);
        System.out.println(title6);
        System.out.println(title7 + RESET);
    }


}
