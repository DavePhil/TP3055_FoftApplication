package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Entity
@DynamicUpdate
public class UniteEnseignement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "UniteEnseignement_id", referencedColumnName = "id")
    private List<Fiche> fiche;


}
