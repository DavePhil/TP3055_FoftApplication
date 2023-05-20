package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Data
@Entity
@DynamicUpdate
public class UniteEnseignement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private Semestre semestre;
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private Niveau niveau;
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private Specialite specialite;

//    @OneToMany(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "UniteEnseignement_id", referencedColumnName = "id")
//    private List<Fiche> fiche;


}
