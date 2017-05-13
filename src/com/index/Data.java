package com.index;

public class Data implements Comparable<Data>{
    private String name;
    private int score;

    Data() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Data obj) {
        int score = obj.getScore();
        String obj_name = obj.getName();

        if (this.getScore() < score) {
            return -1;
        }

        if (this.getScore() > score) {
            return 1;
        }

        if (this.getScore() == score) {
            return this.getName().compareTo(obj_name);
        }
        return 0;
    }
}
