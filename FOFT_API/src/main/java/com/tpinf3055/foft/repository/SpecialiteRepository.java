package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite,Integer> {

}
