package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.modele.Semestre;
import com.tpinf3055.foft.modele.UniteEnseignement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniteEnseignementRepository extends JpaRepository<UniteEnseignement, Integer> {

    @Query("select ue.code from UniteEnseignement ue ")
    List<UniteEnseignement> getCodeUniteEnseignement();

    @Query("select ue from UniteEnseignement ue inner join Semestre  se on  se.id = ue.semestre.id   inner join Niveau ni on  ni.id =ue.niveau.id  where se.id =:id_semestre and ni.id=:id_niveau")
   List<UniteEnseignement> getAllBySemestreAndNiveau(@Param("id_semestre") Integer id_semestre, @Param("id_niveau") Integer id_niveau);




}
