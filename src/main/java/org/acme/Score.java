package org.acme;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

public class Score {

    // The number of holes played per round
    public static final int HOLES = 18;

    // The players currentHole
    @ProtoField(number = 1, defaultValue = "0")
    int currentHole = 0;

    // Name of the player
    @ProtoField(number = 2)
    String playerName;

    // players unique Id
    @ProtoField(number = 3)
    String playerId;

    // The actual scoreCard
    int[] card = new int[HOLES];

    // The course player is playing on.
    @ProtoField(number = 4)
    String course = "St.Andrews Links";

    // the courseCard; the expected handicap
    @ProtoField(number = 5)
    int[] courseCard = {4,4,4,4,5,4,4,3,4,4,3,4,4,5,4,4,4,4};

    // Used with Json serialization
    public Score() {
    }

    @ProtoFactory
    public Score(String playerName, String playerId, String course, int[] courseCard, int currentHole) {
        if(playerName == null || playerName.equals(""))
            throw new IllegalArgumentException("Player name cannot be null "+playerName);
        else {
            this.playerName = playerName;
            this.playerId = playerId;
            this.course = course;
            this.courseCard = courseCard;
            this.currentHole = currentHole;
        }
    }


    public Score(String playerName, String playerId) {
        if(playerName == null || playerName.equals(""))
            throw new IllegalArgumentException("Player name cannot be null "+playerName);
        else {
            this.playerName = playerName;
            this.playerId = playerId;
        }
    }

    public int getCurrentHole() {
        return currentHole;
    }

    public void setCurrentHole(int currentHole) {
        this.currentHole = currentHole;
    }

    public void addScore(int score){
        card[currentHole] = score;
        currentHole++;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getTotalScore(){
        int score = 0;
        int courseScore = 0;
        for(int i=0; i<currentHole; i++) {
            score = score + card[i];
            courseScore = courseScore + courseCard[i];
        }

        return score-courseScore;
    }

    public String getPlayerId() { return playerId; }

    public void setPlayerId(String playerId) { this.playerId = playerId; }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int[] getCourseCard() {
        return courseCard;
    }

    public void setCourseCard(int[] courseCard) {
        this.courseCard = courseCard;

    }

        public int[] getCard() {
        return card;
    }

    public void setCard(int[] card) {
        this.card = card;

        if(card.length < HOLES)
            currentHole = card.length + 1;
    }
}