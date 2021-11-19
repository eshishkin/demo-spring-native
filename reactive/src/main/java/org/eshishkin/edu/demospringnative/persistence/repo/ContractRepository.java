package org.eshishkin.edu.demospringnative.persistence.repo;

import org.eshishkin.edu.demospringnative.persistence.model.ContractEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContractRepository extends ReactiveMongoRepository<ContractEntity, String> {

    Flux<ContractEntity> findAllByCustomer(String customer);

    Mono<ContractEntity> findByContract(String customer);
}
