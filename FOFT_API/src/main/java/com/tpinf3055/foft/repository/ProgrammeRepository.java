package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.Jour;
import com.tpinf3055.foft.modele.Programme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgrammeRepository extends JpaRepository<Programme, Integer> {

    @Query("select prog from Programme prog inner join Jour jour on jour.id= prog.jour.id where jour.id=:jourId")
    List<Programme> findProgrammeByJour(@Param("jourId") int jourId);

    @Query("select prog from Programme prog inner join Jour  jour on jour.id = prog.jour.id inner join UniteEnseignement ue on ue.id = prog.ue.id where ue.specialite.id=:idSpecialite and ue.niveau.id=:idNiveau and ue.semestre.id=:semestreId and jour.id =:jourId")
    List<Programme> findProgrammeByNiveauAndSpecialite(@Param("idSpecialite") int idSpecialite, @Param("idNiveau") int idNiveau, @Param("jourId") int jourId, @Param("semestreId") int semestreId);


}
