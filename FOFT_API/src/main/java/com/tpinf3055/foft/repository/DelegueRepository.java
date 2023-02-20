package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.Delegue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DelegueRepository extends JpaRepository<Delegue, Integer> {

    Optional<Delegue> findByEmailAndPassword(String email, String password);
    Optional<Delegue> findByEmail(String email);

}
