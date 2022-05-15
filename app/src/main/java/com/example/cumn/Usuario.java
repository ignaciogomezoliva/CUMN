package com.example.cumn;

public class Usuario {
    private String username;
    private String UID;
    private Double score;


    public Usuario(String username, String UID) {
        this.username = username;
        this.UID = UID;
        this.score = null;
    }


    public String getUsername() {
        return username;
    }

    public String getUID() {
        return UID;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public boolean isScoreNull(){
        return score==null;
    }

}
