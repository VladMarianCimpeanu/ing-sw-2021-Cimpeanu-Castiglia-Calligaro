package it.polimi.ingsw.client;

import it.polimi.ingsw.server.MultiEchoServer;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.market.Yellow;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.client.Color.*;

public class Welcome {
    private static String title1 = "█▀▄▀█ ██      ▄▄▄▄▄      ▄▄▄▄▀ ▄███▄   █▄▄▄▄   ▄▄▄▄▄       ████▄ ▄████      █▄▄▄▄ ▄███▄      ▄   ██   ▄█    ▄▄▄▄▄    ▄▄▄▄▄   ██      ▄   ▄█▄    ▄███▄   ";
    private static String title2 = "█ █ █ █ █    █     ▀▄ ▀▀▀ █    █▀   ▀  █  ▄▀  █     ▀▄     █   █ █▀   ▀     █  ▄▀ █▀   ▀      █  █ █  ██   █     ▀▄ █     ▀▄ █ █      █  █▀ ▀▄  █▀   ▀  ";
    private static String title3 = "█ ▄ █ █▄▄█ ▄  ▀▀▀▀▄       █    ██▄▄    █▀▀▌ ▄  ▀▀▀▀▄       █   █ █▀▀        █▀▀▌  ██▄▄    ██   █ █▄▄█ ██ ▄  ▀▀▀▀▄ ▄  ▀▀▀▀▄   █▄▄█ ██   █ █   ▀  ██▄▄    ";
    private static String title4 = "█   █ █  █  ▀▄▄▄▄▀       █     █▄   ▄▀ █  █  ▀▄▄▄▄▀        ▀████ █          █  █  █▄   ▄▀ █ █  █ █  █ ▐█  ▀▄▄▄▄▀   ▀▄▄▄▄▀    █  █ █ █  █ █▄  ▄▀ █▄   ▄▀ ";
    private static String title5 = "   █     █              ▀      ▀███▀     █                        █           █   ▀███▀   █  █ █    █  ▐                        █ █  █ █ ▀███▀  ▀███▀   ";
    private static String title6 = "  ▀     █                               ▀                          ▀         ▀            █   ██   █                           █  █   ██                ";
    private static String title7 = "       ▀                                                                                          ▀                           ▀           ";

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
