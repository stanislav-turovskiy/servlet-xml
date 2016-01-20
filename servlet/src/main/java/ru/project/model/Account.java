package ru.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Account {
    @Id
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ")
    //@SequenceGenerator(name="SEQ", sequenceName="SEQ", allocationSize=100)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int agentId;
    private BigDecimal balance;

    public Account(int agentId, BigDecimal balance) {
        this.agentId = agentId;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
