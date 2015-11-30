package com.twilio.verification.repository;

import com.twilio.verification.model.User;

import javax.persistence.NoResultException;

public class UsersRepository extends Repository<User> {
    public UsersRepository() {
        super(User.class);
    }

    public User findByEmail(String email) {
        User user = null;

        try {
            user = (User) em.createQuery(
                    String.format("SELECT u FROM %s u WHERE u.email = :email", entityName))
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }
}
