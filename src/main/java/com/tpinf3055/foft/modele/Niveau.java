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
public class Niveau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @OneToMany
    private Set<UniteEnseignement> uE ;


    public Niveau(Integer id, String code, Set<UniteEnseignement> uE) {
        super();
        this.id = id;
        this.code = code;
        this.uE = uE;
    }

    public Niveau() {
        super();
    }


}
