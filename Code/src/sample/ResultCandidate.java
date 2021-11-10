package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;

public class ResultCandidate {
    private SimpleIntegerProperty electionNo;
    private SimpleStringProperty name;
    private SimpleIntegerProperty votes;
    private Label label;

    public ResultCandidate(int electionNo, String name, int votes) {
        this.electionNo = new SimpleIntegerProperty(electionNo);
        this.name = new SimpleStringProperty(name);
        this.votes = new SimpleIntegerProperty(votes);
        this.label = new Label("");

    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public int getElectionNo() {
        return electionNo.get();
    }

    public void setElectionNo(int electionNo) {
        this.electionNo = new SimpleIntegerProperty(electionNo);
    }

    public int getVotes() {
        return votes.get();
    }

    public void setVotes(int votes) {
        this.votes = new SimpleIntegerProperty(votes);
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
