package com.norsys.service;


import com.norsys.domain.User;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.logging.Logger;

@Service
public class UserService {

    private Logger log = Logger.getLogger(UserService.class.getName());

    public String getNomComplet(User user) {
        return user.getPrenom() + " " + user.getNom().toUpperCase();
    }

    public User save(User user) {
        log.info("User sauvegardé en base.");

        user.setId(new Random().nextInt());
        return user;
    }

}
