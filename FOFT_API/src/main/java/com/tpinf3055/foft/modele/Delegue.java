package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.Set;

@Data
@Entity
@DynamicUpdate
public class Delegue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String email;
    private String matricule;
    private String password;
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private Specialite specialite;
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private Niveau niveau;
    private String photo;




    public Delegue() {
        super();
        password = "1234";
    }
}
