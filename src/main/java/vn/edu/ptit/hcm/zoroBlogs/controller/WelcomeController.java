/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.controller;

import java.io.File;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.ptit.hcm.zoroBlogs.dto.UserRegistrationDto;
import vn.edu.ptit.hcm.zoroBlogs.entity.Account;
import vn.edu.ptit.hcm.zoroBlogs.entity.Profile;
import vn.edu.ptit.hcm.zoroBlogs.service.UserServiceImpl;

/**
 *
 * @author zoroONE01
 */
@Controller
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public JavaMailSender emailSender;

//    private Account account;
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @ModelAttribute("account")
    public Account account() {
        return new Account();
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) boolean error, Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String getRegister(@RequestParam(value = "success", required = false) boolean success) {
        return "login";
    }

    @PostMapping("/register/processing")
    public String registerUserAccount(@ModelAttribute("user") @Valid final UserRegistrationDto userDto,
            final BindingResult result,
            Errors errors, RedirectAttributes redirectAttributes, Model model, HttpSession session,
            HttpServletRequest request
    ) throws MessagingException {
        Account accountExist = userService.findByEmail(userDto.getEmail());
        Profile profileExist = userService.findByDisplayName(userDto.getDisplayName());
        System.out.println("validate: " + result.hasErrors());
        System.out.println("exist: " + accountExist != null || profileExist != null);
//        validator.validate(userDto, result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", userDto);
            return "redirect:/welcome/register?success=false";
        }
        System.out.println(userDto.toString());
        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
        helper.setTo(userDto.getEmail());
        helper.setSubject("Dang ky tai khoan zoroBlogs thanh cong!");
        helper.setText("Chao mung " + userDto.getDisplayName() + " den voi mang xa hoi noi tieng my lon nhat hanh tinh ^^");
        emailSender.send(message);
        redirectAttributes.addFlashAttribute("account", userService.save(userDto));
        return "redirect:/welcome/login";
    }

}
