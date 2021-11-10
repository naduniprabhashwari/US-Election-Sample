package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.RadioButton;


public class BallotCandidate {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private RadioButton vote;

    public BallotCandidate(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.vote = new RadioButton("Click to Vote");

    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public RadioButton getVote() {
        return vote;
    }

    public void setVote(RadioButton vote) {
        this.vote = vote;
    }
}