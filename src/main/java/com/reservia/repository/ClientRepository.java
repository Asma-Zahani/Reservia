package com.reservia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reservia.entity.Client;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);
}