package com.eregister.UserService.Dao;

import com.eregister.UserService.Entity.EregUser;

import java.util.Collection;

/**
 * Created by Szymon on 07.04.2017.
 */
public interface EregUserDao {
    Collection<EregUser> getAllEregUsers();

    Collection<EregUser> getAllEnableEregUsers();

    Collection<EregUser> getAllTeachersEregUsers();

    Collection<EregUser> getAllGuardiansEregUsers();

    Collection<EregUser> getAllStudentsEregUsers();

    EregUser getEregUserById(int id);

    EregUser getEregUserByIdPerson(int id);

    EregUser getEregUserByLogin(String login);

    void removeEregUserById(int id);

    void removeEregUserByLogin(String login);

    void updatePasswordEregUser(String newPassword, String login, String oldPassword);

    void insertEregUser(EregUser eregUser);
}
