package com.tpinf3055.foft.repository;

import com.tpinf3055.foft.modele.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

    @Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

/*
    @Query("select admin from Admin admin where admin.email =: email and admin.password =: password")
    Admin findAdminByEmailAndPassWordQuery(@Param("email") String email,
                                           @Param("password") String password);
*/
          Admin findByEmailAndPassword(String email, String password);
}
