package com.example.myapplication;

public class Bigram{

    /* The two characters represented by the bigram */
    private char first;
    private char second;

    /**
     * Constrictor
     */
    public Bigram(char first, char second){
        this.first = first;
        if(first!=second){
            this.second=second;
        }else{
            this.second = 'X';
        }
    }

    /**
     * @return the first character of the bigram
     */
    public char getFirst(){
        return this.first;
    }

    /**
     * @return the second character of the bigram
     */
    public char getSecond(){
        return this.second;
    }

    /**
     * @return string version
     */
    public String toString(){
        return Character.toString(this.first) + Character.toString(this.second);
    }


}