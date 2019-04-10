package io.github.axtuki1.ngbuilder.system;

public class ThemeData {

    private String theme, genre, key;
    private double bonusPer;
    private int bonusAdd, difficulty;

    public ThemeData(String genre, String theme, int difficulty, double bonusPer, int bonusAdd){
        this.theme = theme;
        if( genre == null ){
            this.genre = "未設定";
        } else {
            if( genre.equalsIgnoreCase("") ){
                this.genre = "未設定";
            } else {
                this.genre = genre;
            }
        }
        this.difficulty = difficulty;
        this.bonusPer = bonusPer;
        this.bonusAdd = bonusAdd;
    }

    public String getKey() {
        return key;
    }

    public String getTheme() {
        return theme;
    }

    public String getGenre() {
        return genre;
    }

    public double getBonusPer() {
        return bonusPer;
    }

    public int getBonusAdd() {
        return bonusAdd;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setBonusAdd(int bonusAdd) {
        this.bonusAdd = bonusAdd;
    }

    public void setBonusPer(double bonusPer) {
        this.bonusPer = bonusPer;
    }
}
