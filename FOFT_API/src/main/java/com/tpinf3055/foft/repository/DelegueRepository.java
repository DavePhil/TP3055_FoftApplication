package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.modele.Programme;
import com.tpinf3055.foft.modele.UniteEnseignement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DelegueRepository extends JpaRepository<Delegue, Integer> {

    Optional<Delegue> findByEmailAndPassword(String email, String password);
    Optional<Delegue> findByEmail(String email);


    @Query("select del from Delegue del inner join Specialite  se on  se.id = del.specialite.id   inner join Niveau ni on  ni.id =del.niveau.id  where se.id =:id_semestre and ni.id=:id_niveau")
    List<Delegue> getAllBySpecialiteAndNiveau(@Param("id_semestre") Integer id_semestre, @Param("id_niveau") Integer id_niveau);
}
