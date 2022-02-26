/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.ptit.hcm.zoroBlogs.dto.UserRegistrationDto;
import vn.edu.ptit.hcm.zoroBlogs.entity.CustomUserDetails;
import vn.edu.ptit.hcm.zoroBlogs.entity.Role;
import vn.edu.ptit.hcm.zoroBlogs.entity.Account;
import vn.edu.ptit.hcm.zoroBlogs.entity.Profile;
import vn.edu.ptit.hcm.zoroBlogs.repository.RoleRepository;
import vn.edu.ptit.hcm.zoroBlogs.repository.AccountRepository;
import vn.edu.ptit.hcm.zoroBlogs.repository.ProfileRepository;
import vn.edu.ptit.hcm.zoroBlogs.utils.EncrytedPasswordUtils;

/**
 *
 * @author zoroONE01
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // đầu tiên mình query xuống database xem có user  đó không  
        Account user = accountRepository.findByEmail(email);
        System.out.println(email);
        //Nếu không tìm thấy Account thì mình thông báo lỗi
        if (user == null) {
            System.out.println("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }

        // Khi đã có user rồi thì mình query xem user đó có những quyền gì (Admin hay Account)
        // [ROLE_USER, ROLE_ADMIN,..]
        List<Role> listRole = roleRepository.findAll();

        // Dựa vào list quyền trả về mình tạo đối tượng GrantedAuthority  của spring cho quyền đó
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (listRole != null) {
            listRole.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).forEachOrdered(authority -> {
                // ROLE_USER, ROLE_ADMIN,..
                grantList.add(authority);
            });
        }

        //Cuối cùng mình tạo đối tượng UserDetails của Spring và mình cung cấp cá thông số như tên , password và quyền
        // Đối tượng userDetails sẽ chứa đựng các thông tin cần thiết về user từ đó giúp Spring Security quản lý được phân quyền như ta đã
        // cấu hình trong bước 4 method configure
        CustomUserDetails userDetails = new CustomUserDetails(user, grantList);
        System.out.println(userDetails.toString());
        return userDetails;
    }

    @Override
    public Account save(UserRegistrationDto registration) {
        Account account = new Account();
        Profile profile = new Profile();

        account.setEmail(registration.getEmail());
        account.setPassword(EncrytedPasswordUtils.encrytePassword(registration.getPassword()));

        profile.setDisplayName(registration.getDisplayName());
        try {
            profile.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(registration.getDateOfBirth()));
        } catch (ParseException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        profile.setGender(registration.getGender());

        account.setRole(roleRepository.findAllById(1));

        profile.setAccount(account);
        account.setProfile(profile);

        profileRepository.save(profile);
        accountRepository.save(account);
        
        return account;
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Profile findByDisplayName(String name) {
        return profileRepository.findByDisplayName(name);
    }

}
