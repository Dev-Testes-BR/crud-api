package br.com.vinicius.crud.repositories;

import br.com.vinicius.crud.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
