/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import vn.edu.ptit.hcm.zoroBlogs.validator.DisplayNameExist;
import vn.edu.ptit.hcm.zoroBlogs.validator.EmailExist;
import vn.edu.ptit.hcm.zoroBlogs.validator.FieldMatch;
import vn.edu.ptit.hcm.zoroBlogs.validator.ValidPassword;

/**
 *
 * @author zoroONE01
 */
@FieldMatch(first = "password", second = "confirmPassword", message = "Mật khẩu xác nhận không chính xác")
@Data
public class UserRegistrationDto {

    @NotEmpty(message = "Hãy nhập Email")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$", message = "Email không hợp lệ")
    @Size(max = 50, message = "Email tối đa 50 ký tự")
    @EmailExist
    private String email;

    @NotEmpty(message = "Hãy nhập mật khẩu")
//    @Size(min = 8, max = 100, message = "Mật khẩu phải từ 8 đến 100 ký tự")
    @ValidPassword
    private String password;

    @NotEmpty(message = "Hãy xác nhận lại mật khẩu")
    private String confirmPassword;

    @NotEmpty(message = "Hãy nhập tên hiển thị")
    @Size(max = 50, message = "Tên hiển thị tối đa 50 ký tự")
    @DisplayNameExist
    private String displayName;

    @NotEmpty(message = "Hãy chọn ngày sinh")
    private String dateOfBirth;

    private int gender;

}
