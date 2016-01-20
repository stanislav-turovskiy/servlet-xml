package ru.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agent {
    @Id
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ")
    //@SequenceGenerator(name="SEQ", sequenceName="SEQ", allocationSize=100)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String phone;
    private String password;

    public Agent(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
