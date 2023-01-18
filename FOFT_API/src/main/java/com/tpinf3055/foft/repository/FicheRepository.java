package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.Fiche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public interface FicheRepository extends JpaRepository<Fiche, Integer> {

    @Modifying
    @Transactional
    @Query ("update Fiche fiche set fiche.state = ?1 where fiche.id =?2 ")
     int valideOrReject (int state, int id);

    @Modifying
    @Transactional
    @Query("update Fiche fiche set fiche.motif = ?1 where  fiche.id = ?2 ")
    int ajouteMotif(String motif, int id);

    @Modifying
    @Transactional
    @Query("update Fiche fiche set fiche.signatureEnseignant = ?1 where fiche.id = ?2 ")
    int ajouteSignature( MultipartFile signature,  int id);

    Optional<Fiche> findFicheByIdAndState(int id, int state);

    void deleteByIdAndState(int id, int state);




//    @Query("update Fiche set motif =: motif where id=: id")
//     void ajouteMotif (@Param("id") int id,
//                             @Param("motif") String motif);

}
