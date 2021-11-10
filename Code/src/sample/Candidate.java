package sample;

import javafx.beans.property.SimpleStringProperty;

public class Candidate {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty NIC;
    private final SimpleStringProperty telNo;
    private final SimpleStringProperty email;
    private final SimpleStringProperty address;
    private final SimpleStringProperty street;
    private final SimpleStringProperty city;
    private final SimpleStringProperty State;
    private final SimpleStringProperty postcode;
    private final SimpleStringProperty DOB;
    private final SimpleStringProperty citizen;
    private final SimpleStringProperty Pname;
    private final SimpleStringProperty Pcolor;
    private final SimpleStringProperty Pnumber;
    private final SimpleStringProperty Psymbol;
    private final SimpleStringProperty deposit;



    Candidate(String id, String name, String nic, String telNo, String email, String address, String street, String city, String state, String postcode, String dob, String citizen, String pname, String pcolor, String pnumber, String psymbol, String deposit){
        this.ID = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.NIC = new SimpleStringProperty(nic);
        this.telNo = new SimpleStringProperty(telNo);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.street = new SimpleStringProperty(street);
        this.city = new SimpleStringProperty(city);
        this.State = new SimpleStringProperty(state);
        this.postcode = new SimpleStringProperty(postcode);
        this.DOB = new SimpleStringProperty(dob);
        this.citizen = new SimpleStringProperty(citizen);
        this.Pname = new SimpleStringProperty(pname);
        this.Pcolor = new SimpleStringProperty(pcolor);
        this.Pnumber = new SimpleStringProperty(pnumber);
        this.Psymbol = new SimpleStringProperty(psymbol);
        this.deposit = new SimpleStringProperty(deposit);
    }


    public String getID() {
        return ID.get();
    }

    public SimpleStringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNIC() {
        return NIC.get();
    }

    public SimpleStringProperty NICProperty() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC.set(NIC);
    }

    public String getTelNo() {
        return telNo.get();
    }

    public SimpleStringProperty telNoProperty() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo.set(telNo);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getStreet() {
        return street.get();
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getState() {
        return State.get();
    }

    public SimpleStringProperty stateProperty() {
        return State;
    }

    public void setState(String state) {
        this.State.set(state);
    }

    public String getPostcode() {
        return postcode.get();
    }

    public SimpleStringProperty postcodeProperty() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode.set(postcode);
    }

    public String getDOB() {
        return DOB.get();
    }

    public SimpleStringProperty DOBProperty() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB.set(DOB);
    }

    public String getCitizen() {
        return citizen.get();
    }

    public SimpleStringProperty citizenProperty() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen.set(citizen);
    }

    public String getPname() {
        return Pname.get();
    }

    public SimpleStringProperty pnameProperty() {
        return Pname;
    }

    public void setPname(String pname) {
        this.Pname.set(pname);
    }

    public String getPcolor() {
        return Pcolor.get();
    }

    public SimpleStringProperty pcolorProperty() {
        return Pcolor;
    }

    public void setPcolor(String pcolor) {
        this.Pcolor.set(pcolor);
    }

    public String getPnumber() {
        return Pnumber.get();
    }

    public SimpleStringProperty pnumberProperty() {
        return Pnumber;
    }

    public void setPnumber(String pnumber) {
        this.Pnumber.set(pnumber);
    }

    public String getPsymbol() {
        return Psymbol.get();
    }

    public SimpleStringProperty psymbolProperty() {
        return Psymbol;
    }

    public void setPsymbol(String psymbol) {
        this.Psymbol.set(psymbol);
    }

    public String getDeposit() {
        return deposit.get();
    }

    public SimpleStringProperty depositProperty() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit.set(deposit);
    }
}
