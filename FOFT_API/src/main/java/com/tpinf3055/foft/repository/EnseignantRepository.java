package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.modele.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant, Integer> {

    Optional<Enseignant> findByEmailAndPassword(String email, String password);
    Optional<Enseignant> findByEmail(String email);
}
