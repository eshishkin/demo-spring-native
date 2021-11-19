package org.eshishkin.edu.demospringnative.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.eshishkin.edu.demospringnative.exception.ResourceNotFoundException;
import org.eshishkin.edu.demospringnative.model.Contract;
import org.eshishkin.edu.demospringnative.model.CustomerSummary;
import org.eshishkin.edu.demospringnative.model.CustomerSummary.ContractSummary;
import org.eshishkin.edu.demospringnative.persistence.model.ContractEntity;
import org.eshishkin.edu.demospringnative.persistence.repo.ContractRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {
    private static final int MAX_CONTRACT_LENGTH = 30;
    private static final int MAX_CONTRACT_FOR_CUSTOMER = 5;
    private static final int MAX_DEBT_VALUE = 1000000;

    private final ContractRepository contractRepository;

    public Mono<Contract> getContract(String contract) {
        return contractRepository.findByContract(contract)
                .map(this::convert)
                .switchIfEmpty(Mono.error(() -> new ResourceNotFoundException("Contract not found: " + contract)));
    }

    public Flux<Contract> getContractsForCustomer(String customer) {
        return contractRepository.findAllByCustomer(customer).map(this::convert);
    }

    public Mono<CustomerSummary> getSummary(String customer) {
        return contractRepository.findAllByCustomer(customer)
                .map(contract -> {
                    var total = decimal(contract.getDebt())
                            .add(decimal(contract.getFine()))
                            .add(decimal(contract.getCommission()))
                            .add(decimal(contract.getOtherCharges()));

                    return ContractSummary.of(contract.getContract(), total);
                })
                .collectList()
                .map(contracts -> CustomerSummary.of(
                        customer,
                        contracts.stream().map(ContractSummary::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add),
                        contracts
                ));
    }

    public Mono<Void> generateTestData(int size) {
        if (size <= 0 || size > 10000) {
            throw new RuntimeException("Size must be in [1, 10000] range");
        }

        var random = new Random();
        var records = IntStream.range(0, size)
                .flatMap(i -> IntStream.range(1, MAX_CONTRACT_FOR_CUSTOMER).map(j -> i))
                .mapToObj(i -> {
                    ContractEntity entity = new ContractEntity();
                    entity.setContract(RandomStringUtils.randomAlphabetic(MAX_CONTRACT_LENGTH).toUpperCase());
                    entity.setCustomer(String.valueOf(i));
                    entity.setDebt(new BigDecimal(Math.abs(random.nextInt(MAX_DEBT_VALUE))));
                    entity.setFine(new BigDecimal(Math.abs(random.nextInt(MAX_DEBT_VALUE))));
                    entity.setCommission(new BigDecimal(Math.abs(random.nextInt(MAX_DEBT_VALUE))));
                    entity.setOtherCharges(new BigDecimal(Math.abs(random.nextInt(MAX_DEBT_VALUE))));
                    entity.setCreated(LocalDate.now());

                    return entity;
                })
                .collect(Collectors.toList());

        return contractRepository.saveAll(records)
                .collectList()
                .doOnNext(saved -> log.info("Generated contracts {}", saved.size()))
                .then();
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
