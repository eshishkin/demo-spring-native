package org.eshishkin.edu.demospringnative.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CustomerSummary implements Serializable {
    private String customer;
    private BigDecimal total;
    private List<ContractSummary> contracts;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class ContractSummary implements Serializable {
        private String contract;
        private BigDecimal total;
    }
}
