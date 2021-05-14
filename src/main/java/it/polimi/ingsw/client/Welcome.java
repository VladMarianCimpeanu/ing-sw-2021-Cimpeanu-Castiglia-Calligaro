package it.polimi.ingsw.client;

import static it.polimi.ingsw.client.AnsiColor.*;

public class Welcome {
    private static String title1 = "█▀▄▀█ ██      ▄▄▄▄▄      ▄▄▄▄▀ ▄███▄   █▄▄▄▄   ▄▄▄▄▄       ████▄ ▄████      █▄▄▄▄ ▄███▄      ▄   ██   ▄█    ▄▄▄▄▄    ▄▄▄▄▄   ██      ▄   ▄█▄    ▄███▄   ";
    private static String title2 = "█ █ █ █ █    █     ▀▄ ▀▀▀ █    █▀   ▀  █  ▄▀  █     ▀▄     █   █ █▀   ▀     █  ▄▀ █▀   ▀      █  █ █  ██   █     ▀▄ █     ▀▄ █ █      █  █▀ ▀▄  █▀   ▀  ";
    private static String title3 = "█ ▄ █ █▄▄█ ▄  ▀▀▀▀▄       █    ██▄▄    █▀▀▌ ▄  ▀▀▀▀▄       █   █ █▀▀        █▀▀▌  ██▄▄    ██   █ █▄▄█ ██ ▄  ▀▀▀▀▄ ▄  ▀▀▀▀▄   █▄▄█ ██   █ █   ▀  ██▄▄    ";
    private static String title4 = "█   █ █  █  ▀▄▄▄▄▀       █     █▄   ▄▀ █  █  ▀▄▄▄▄▀        ▀████ █          █  █  █▄   ▄▀ █ █  █ █  █ ▐█  ▀▄▄▄▄▀   ▀▄▄▄▄▀    █  █ █ █  █ █▄  ▄▀ █▄   ▄▀ ";
    private static String title5 = "   █     █              ▀      ▀███▀     █                        █           █   ▀███▀   █  █ █    █  ▐                        █ █  █ █ ▀███▀  ▀███▀   ";
    private static String title6 = "  ▀     █                               ▀                          ▀         ▀            █   ██   █                           █  █   ██                ";
    private static String title7 = "       ▀                                                                                          ▀                           ▀           ";

    public static void dump() {
        System.out.println(RED.escape() + title1);
        System.out.println(title2);
        System.out.println(title3);
        System.out.println(title4);
        System.out.println(title5);
        System.out.println(title6);
        System.out.println(title7);
    }
}
