package com.example.appoftheyear2;

public class Game {
    String Name;
    String Genre;
    int Score;

    public Game(String NameIn, String GenreIn, int ScoreIn){
        Name = NameIn;
        Genre = GenreIn;
        Score = ScoreIn;
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
