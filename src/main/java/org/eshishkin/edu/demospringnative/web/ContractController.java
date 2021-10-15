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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    public List<Contract> getContractsForCustomer(@RequestParam(name = "customer") String customer) {
        return contractService.getContractsForCustomer(customer);
    }

    @GetMapping("/{contractId}")
    public Contract getContract(@PathVariable("contractId") String contract) {
        return contractService.getContract(contract);
    }

    @GetMapping("/summary/{customer}")
    public CustomerSummary getSummary(@PathVariable("customer") String customer) {
        return contractService.getSummary(customer);
    }


    @PostMapping("/random")
    public List<Contract> generateTestData(@RequestParam("size") int size) {
        return contractService.generateTestData(size);
    }
}
