/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.ptit.hcm.zoroBlogs.dto.UserRegistrationDto;
import vn.edu.ptit.hcm.zoroBlogs.entity.Account;
import vn.edu.ptit.hcm.zoroBlogs.entity.Profile;

/**
 *
 * @author zoroONE01
 */
public interface UserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String email);

    Account save(UserRegistrationDto registration);

    Account findByEmail(String email);

    Profile findByDisplayName(String name);

}
