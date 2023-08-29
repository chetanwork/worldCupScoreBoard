#Live Football World Cup Score Board

#Task
Java Library which will show the score of Live footballs

#Requirements
You are working in a sports data company, and we would like you to develop a new Live Football World Cup Scoreboard
library that shows all the ongoing matches and their scores.

The scoreboard supports the following operations:
1. New Match can be added on scoreboard with initial score of 0 - 0 and Match contain two team to start the match 
   a. Home team
   b. Away team
2. Update Score of in progress match which should receive a pair of absolute scores (HomeTeam & AwayTeam)
3. When match is finished the Match should be removed from scoreboard.
4. Matches on scoreboard should be ordered by their total score. When two matches total scores are same
   then match should be ordered by the most recently started match in the scoreboard.

#Test Cases
Test cases of this Library has been added in the ScoreBoardTest class. Located at -> src/test/java/live.scoreboard

#Implementation 
Implementation include below classes
 1) Match -> This class holds the information about the current match
 2) FootballTeam -> This class holds the information about teams playing a match
 3) ScoreBoard -> This is an interface that contains the contract.
 4) ScoreBoardImpl -> This class contains logic of adding Matches, update score, order matches and show on scoreboard.
 5) GeneralException -> Exception that needs to be thrown in any uncertain situations.

#Setup code
You need java 17 installed on your machine with Maven 3.8+

