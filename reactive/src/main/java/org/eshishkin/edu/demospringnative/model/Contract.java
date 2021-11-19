package org.eshishkin.edu.demospringnative.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Contract implements Serializable {
    private String contract;
    private String customer;
    private BigDecimal debt;
    private BigDecimal fine;
    private BigDecimal commission;
    private BigDecimal otherCharges;
    private LocalDate created;
}
