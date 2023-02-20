package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.Salle;
import com.tpinf3055.foft.modele.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeanceRepository extends JpaRepository<Seance, Integer> {

}
