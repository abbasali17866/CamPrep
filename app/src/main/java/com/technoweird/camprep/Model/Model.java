package com.technoweird.camprep.Model;

public class Model {
        String id;
        String startwith;
        String word;
        String definition;
        String synonym;
        String antonym;
        String example;

        public Model(){}


        public Model(String word, String definition, String synonym, String antonym, String example){
            this.word = word;
            this.definition=definition;
            this.synonym = synonym;
            this.antonym = antonym;
            this.example = example;
        }


    public Model(String id, String startwith, String word, String definition, String synonym, String antonym, String example) {
        this.id = id;
        this.startwith = startwith;
        this.word = word;
        this.definition=definition;
        this.synonym = synonym;
        this.antonym = antonym;
        this.example = example;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartwith() {
        return startwith;
    }

    public void setStartwith(String startwith) {
        this.startwith = startwith;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDefinition(String definition){
            this.definition=definition;
    }

    public String getDefinition(){
            return definition;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getAntonym() {
        return antonym;
    }

    public void setAntonym(String antonym) {
        this.antonym = antonym;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
