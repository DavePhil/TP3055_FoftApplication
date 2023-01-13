package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant, Integer> {

}
