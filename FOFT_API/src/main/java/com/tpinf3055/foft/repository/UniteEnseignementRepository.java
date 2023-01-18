package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.UniteEnseignement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniteEnseignementRepository extends JpaRepository<UniteEnseignement, Integer> {

    @Query("select ue.code from UniteEnseignement ue  ")
    List<UniteEnseignement> getCodeUniteEnseignement();
}
