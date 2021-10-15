package org.eshishkin.edu.demospringnative.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.eshishkin.edu.demospringnative.exception.ResourceNotFoundException;
import org.eshishkin.edu.demospringnative.model.Contract;
import org.eshishkin.edu.demospringnative.model.CustomerSummary;
import org.eshishkin.edu.demospringnative.model.CustomerSummary.ContractSummary;
import org.eshishkin.edu.demospringnative.persistence.model.ContractEntity;
import org.eshishkin.edu.demospringnative.persistence.repo.ContractRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ContractService {
    private static final int MAX_CONTRACT_LENGTH = 30;
    private static final int MAX_CUSTOMER_LENGTH = 5;
    private static final int MAX_DEBT_VALUE = 1000000;

    private final ContractRepository contractRepository;

    public Contract getContract(String contract) {
        return contractRepository.findByContract(contract)
                .map(this::convert)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found: " + contract));
    }

    public List<Contract> getContractsForCustomer(String customer) {
        return contractRepository.findAllByCustomer(customer).stream()
                .map(this::convert)
                .collect(toList());
    }

    public CustomerSummary getSummary(String customer) {
        var contracts = contractRepository.findAllByCustomer(customer)
                .stream()
                .map(contract -> {
                    var total = decimal(contract.getDebt())
                            .add(decimal(contract.getFine()))
                            .add(decimal(contract.getCommission()))
                            .add(decimal(contract.getOtherCharges()));

                    return ContractSummary.of(contract.getContract(), total);
                })
                .collect(toList());


        return CustomerSummary.of(
                customer,
                contracts.stream().map(ContractSummary::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add),
                contracts
        );
    }

    public List<Contract> generateTestData(int size) {
        if (size <= 0 || size > 100) {
            throw new RuntimeException("Size must be in [1, 100] range");
        }

        var random = new Random();
        var records = IntStream.range(0, size)
                .mapToObj(i -> {
                    ContractEntity entity = new ContractEntity();
                    entity.setContract(RandomStringUtils.randomAlphabetic(MAX_CONTRACT_LENGTH).toUpperCase());
                    entity.setCustomer(RandomStringUtils.randomAlphabetic(MAX_CUSTOMER_LENGTH).toUpperCase());
                    entity.setDebt(new BigDecimal(Math.abs(random.nextInt(MAX_DEBT_VALUE))));
                    entity.setFine(new BigDecimal(Math.abs(random.nextInt(MAX_DEBT_VALUE))));
                    entity.setCommission(new BigDecimal(Math.abs(random.nextInt(MAX_DEBT_VALUE))));
                    entity.setOtherCharges(new BigDecimal(Math.abs(random.nextInt(MAX_DEBT_VALUE))));
                    entity.setCreated(LocalDate.now());

                    return entity;
                })
                .collect(Collectors.toList());

        return contractRepository.saveAll(records)
                .stream()
                .map(this::convert)
                .collect(toList());
    }

    private Contract convert(ContractEntity entity) {
        Contract contract = new Contract();
        contract.setContract(entity.getContract());
        contract.setCustomer(entity.getCustomer());
        contract.setDebt(entity.getDebt());
        contract.setFine(entity.getFine());
        contract.setCommission(entity.getCommission());
        contract.setOtherCharges(entity.getOtherCharges());
        contract.setCreated(entity.getCreated());


        return contract;
    }

    private BigDecimal decimal(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}
