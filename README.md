## Poker game on sockets created by Bartosz Kruczek ##

Consists of modules:

* poker-client representing client side of app
* poker-server representing server side of app
* poker-common containing utility classes

This project is a functioning Poker game that can be played by up to 4 players.

### The rules are as follows: ###
* You give Server Your name
* Server gives You, a player, cards
* You can see Your cards and then the betting round begins
* During betting round You can bet fold or check. Also, if bet was previously made You can raise the bet by given amount
* Every player has its own token account
* The game's pot is filled with tokens from players accounts
* After betting You can swap up to 4 cards. Then another betting round begins which is handled same as the previous one
* Finally, the winner of the round is chosen. After that the next round begins.


### How to play the game ###
To play the game You need to first run the poker-server jar built in poker-server module Next create up to 4 players and
set their names All other instructions will be shown in the terminal so make sure to follow them for the best experience.
### Communication protocol ###
The communication protocol is made in a way - that the server, when it's built up, waits for players. 
If the player count equals 2 or more - the game may be started. 
The protocol is built with Java IO and streams. 



