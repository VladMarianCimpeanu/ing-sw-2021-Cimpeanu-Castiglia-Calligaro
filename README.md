# Master of Renaissance: software engineering project

## Features
It was decided to implement all the rules of the game: it is possible to play both multiplayer and singleplayer mode.
Game can be played with both GUI and CLI.

## Advanced features
For this project two advanced features were implemented: resilience to disconnections and mulitple games.
### Multiple games
Multiple games can run simultaneously. When the server starts, it waits for a player to create a waiting room: when a player connects to the server, if there
are no waiting rooms available, it will ask him how many players should play the game and then it will creates a new waiting room. In order to avoid players waiting for the creation
of a waiting room, if there are no waiting rooms available, the server will always ask for the mode to the new connected player, so theoretically there could be more than one 
waiting room: in that case new players will join the oldest waiting room created.
### Resilience to disconnections
If a player crashes or quits, he can rejoin the game using the same nickname used during the last game: while
he is offline, his opponents can keep on playing. If a player crashes during his own turn the server will auto complete his turn.  As multiple games are implemented, 
there can not be more than one player with the same nickname at the same time, even though they may play different games.
Resilience works only after the game creation: if a player crashes while he is in the waiting room, the room will free a space for a new player, so if the same player tries to reconnect
with the same nickname used before, he will not be necessesary able to rejoin the same waiting room.
If all the players that were playing the same game crash or quit, the game will pause, but will not end, so it will restart when at least one player rejoins the game.

## Requirements
For the implementation of this game, Java 15 was used: it is not garanteed that it will work with lower versions. 

Server and GUI client can run on all operative systems, whereas CLI run correctly on Linux or MacOS. For Windows users WSL is suggested in order to be able to play with CLI.

## How to build
//TODO

## How to Play
### Server
To start the server, open the terminal and change your working directory to the one in which Server.jar is saved, then write the following command:

`java -jar Server.jar IP PORT `

where 'IP' is the IP of the machine used to run the server whereas 'PORT' is the port used for the connections acceptance.
### Client
To start the client, open the terminal and change your working directory to the one in which Client.jar is saved, then write the following command:

`java -jar Client.jar IP PORT MODE`

where 'IP' is the IP of the server to connect to, 'PORT' is the port used for the connections acceptance, whereas 'MODE' can be `gui` if you want to play with a graphical user
interface, otherwise `cli`, if you want to go on using the command line.
