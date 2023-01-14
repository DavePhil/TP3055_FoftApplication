package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.UniteEnseignement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniteEnseignementRepository extends JpaRepository<UniteEnseignement, Integer> {

}
