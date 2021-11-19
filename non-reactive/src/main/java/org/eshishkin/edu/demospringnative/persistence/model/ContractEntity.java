package org.eshishkin.edu.demospringnative.persistence.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document(collection = ContractEntity.COLLECTION_NAME)
public class ContractEntity implements Serializable {
    public static final String COLLECTION_NAME = "Contract";

    private String contract;
    private String customer;
    private BigDecimal debt;
    private BigDecimal fine;
    private BigDecimal commission;
    private BigDecimal otherCharges;
    private LocalDate created;
}
