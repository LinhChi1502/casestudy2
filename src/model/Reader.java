package model;

import java.io.Serializable;

public class Reader implements Serializable {
    private static int id = 1000;
    private int readerID;
    private String readerName;
    private String phoneNumber;
    private String email;
    private String address;

    public Reader() {
    }

    public Reader(int readerID, String readerName, String phoneNumber, String email, String address) {
        if (readerID == 0) {
            this.readerID = id++;
        } else {
            this.readerID = readerID;
        }
        this.readerName = readerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public static void setId(int id) {
        Reader.id = id;
    }

    public int getReaderID() {
        return readerID;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void outputInfo() {
        System.out.printf("%-17s%-35s%-30s%-30s%-15s", "readerId = " + readerID, "readerName = " + readerName,
                "phoneNumber = " + phoneNumber, "email = " + email, "address = " + address);
        System.out.println();
    }
}
