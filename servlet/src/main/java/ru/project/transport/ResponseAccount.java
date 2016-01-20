package ru.project.transport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "response")
public class ResponseAccount {

    @XmlElement(name = "result-code", required = true)
    private int resultCode;
    @XmlElement(name = "bal", required = true)
    private BigDecimal balance;


    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
