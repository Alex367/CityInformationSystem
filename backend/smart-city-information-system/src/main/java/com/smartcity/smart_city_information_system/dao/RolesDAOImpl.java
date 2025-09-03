package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Members;
import com.smartcity.smart_city_information_system.entity.Roles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RolesDAOImpl implements RolesDAO{

    private EntityManager entityManager;

    @Autowired
    public RolesDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Roles> findAllUsers(String adminId) {
        TypedQuery<Roles> theQuery = entityManager.createQuery(
                "select r FROM Roles r WHERE r.member.user_id <> :adminId", Roles.class
        ).setParameter("adminId", adminId);

        return theQuery.getResultList();
    }

    @Override
    public String deleteUser(String id) {
//        System.out.println(id);
        int roleId = Integer.parseInt(id);
        Roles roles = entityManager.find(Roles.class, roleId);
//        String deletedUsername = roles.getMember().getUser_id();

//        Members member = entityManager.find(Members.class, deletedUsername);
        Members member = roles.getMember();
        String deletedUsername = member.getUser_id();
        entityManager.remove(member);

        return deletedUsername;
    }
}
