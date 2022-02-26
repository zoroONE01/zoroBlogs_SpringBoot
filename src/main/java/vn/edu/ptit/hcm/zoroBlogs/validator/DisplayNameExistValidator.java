/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.validator;

import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import vn.edu.ptit.hcm.zoroBlogs.service.UserService;

public class DisplayNameExistValidator implements ConstraintValidator<DisplayNameExist, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(final DisplayNameExist arg0) {

    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext context) {
        return userService.findByDisplayName(t) == null;
    }

}
