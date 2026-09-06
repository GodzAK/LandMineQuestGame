# LandMineQuest

## About the Game
LandMineQuest is a two-player Java quiz game. Players move across a numbered board by rolling a dice and try to reach position 100 first.

Some spaces on the board are landmines. When a player lands on one, they must answer a Java programming question. A correct answer moves the player forward, while an incorrect answer moves the player backward.

The game combines luck, competition, and Java learning in a fun board-game style.

## How to Play
1. Start the game and enter the names of the two players.
2. Player 1 rolls the dice by clicking **Roll Dice**.
3. The player moves forward by the number shown on the dice.
4. If the player lands on a landmine space, answer the Java question.
5. A correct answer moves the player forward 3 spaces.
6. An incorrect answer moves the player backward 3 spaces.
7. Players take turns rolling the dice.
8. The first player to reach position 100 wins the game.

## How to Start the Game

**Option 1 - Run the jar file**

```bash
java -jar LandMineQuest.jar
```

**Option 2 - Build from source**

```bash
cd src
javac -d out *.java
copy *.jpg *.png *.wav out
cd out
java Welcome
```

This opens the Welcome screen. From there, click **Rules & Start** to see the rules, then **Start Game** to begin.

## Game Features
- Two-player gameplay
- 10 by 10 game board
- Random dice rolls, with a rolling dice animation
- Players move smoothly across the board instead of jumping instantly
- Java programming questions on landmine spaces
- Custom question screen with clickable answer cards
- Images and sound effects
- Play Again or Exit option after winning
- Consistent color theme across all screens

## Built With
- Java
- Java Swing and AWT for the graphical interface
- Java2D (Graphics2D) for custom-drawn board cells, dice, and animations
- Java Sound for audio effects

## Project Structure
| File | Purpose |
|---|---|
| `Welcome.java` | Start/intro screen |
| `Menu.java` | Rules screen |
| `GameFrame.java` | Main game window and turn logic |
| `BoardPanel.java` | Board rendering and player movement |
| `DicePanel.java` | Dice rendering and roll animation |
| `QuestionDialog.java` | Landmine question popup |
| `WinDialog.java` | Win screen |
| `QuestionBank.java` | All Java trivia questions |
| `Theme.java` | Shared colors and fonts |
| `RoundedButton.java` | Reusable styled button |
| `SoundManager.java` | Sound effect playback |

## Purpose
This game was created to make learning Java more enjoyable. Players can review important Java topics while playing a competitive game with a friend.

## Possible Future Updates
- Support for more than two players or a versus-computer mode
- More question categories beyond core Java
- Saved high scores
