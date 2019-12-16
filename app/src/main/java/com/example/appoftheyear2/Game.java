package com.example.appoftheyear2;

public class Game {
    String Name;
    String Genre;
    int Score;
    String Status;
    float HoursPlayed;

    public Game(String NameIn, String GenreIn, int ScoreIn, float HoursPlayedIn, String StatusIn){
        Name = NameIn;
        Genre = GenreIn;
        Score = ScoreIn;
        Status = StatusIn;
        HoursPlayed = HoursPlayedIn;
    }

    public String getName(){
        return Name;
    }

    public String getGenre(){
        return Genre;
    }

    public int getScore(){
        return Score;
    }

    public void UpdateName(String NameIn){
        Name = NameIn;
    }

    public void UpdateGenre(String GenreIn){
        Genre = GenreIn;
    }

    public void UpdateScore(int ScoreIn){
        Score = ScoreIn;
    }
}
