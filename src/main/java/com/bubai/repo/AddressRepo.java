package com.bubai.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bubai.model.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

}
