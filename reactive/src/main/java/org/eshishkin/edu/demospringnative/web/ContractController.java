package org.eshishkin.edu.demospringnative.web;

import lombok.RequiredArgsConstructor;
import org.eshishkin.edu.demospringnative.model.Contract;
import org.eshishkin.edu.demospringnative.model.CustomerSummary;
import org.eshishkin.edu.demospringnative.service.ContractService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    public Flux<Contract> getContractsForCustomer(@RequestParam(name = "customer") String customer) {
        return contractService.getContractsForCustomer(customer);
    }

    @GetMapping("/{contractId}")
    public Mono<Contract> getContract(@PathVariable("contractId") String contract) {
        return contractService.getContract(contract);
    }

    @GetMapping("/summary/{customer}")
    public Mono<CustomerSummary> getSummary(@PathVariable("customer") String customer) {
        return contractService.getSummary(customer);
    }

    @PostMapping("/random")
    public Mono<Void> generateTestData(@RequestParam("size") int size) {
        return contractService.generateTestData(size);
    }
}
