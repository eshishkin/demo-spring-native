package org.eshishkin.edu.demospringnative.persistence.repo;

import org.eshishkin.edu.demospringnative.persistence.model.ContractEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends MongoRepository<ContractEntity, String> {

    List<ContractEntity> findAllByCustomer(String customer);

    Optional<ContractEntity> findByContract(String customer);
}
