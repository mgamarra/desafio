package br.com.challenge.core.data.repository;

import br.com.challenge.core.data.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository extends JpaRepository<Address, Long>, PagingAndSortingRepository<Address, Long> {
}
