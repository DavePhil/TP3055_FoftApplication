package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Data
@Entity
@DynamicUpdate
public class Specialite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;

    @OneToMany
    private Set<UniteEnseignement> uniteEnseignements;

    public Specialite(Integer id, String code, Set<UniteEnseignement> uniteEnseignements) {
        super();
        this.id = id;
        this.code = code;
        this.uniteEnseignements = uniteEnseignements;
    }

    public Specialite() {
        super();
    }

}
