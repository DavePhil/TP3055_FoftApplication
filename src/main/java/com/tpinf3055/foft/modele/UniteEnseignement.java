package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
public class UniteEnseignement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    @ManyToOne
    private Niveau niveau;
    @ManyToOne
    private Specialite specialite;
    @ManyToOne
    private Salle salle;
    @ManyToOne
    private Enseignant enseignant;


}
