package com.poly.controller;

import com.poly.model.Account;
import com.poly.service.AccountService;
import com.poly.utils.ParamService;
import com.poly.utils.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("account")
public class AccountController {
    @Autowired
    ParamService paramService;

    @Autowired
    SessionService sessionService;

    @Autowired
    AccountService accountService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        String message = (String) model.asMap().get("message");
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("ACCOUNT", new Account());
        return "register";
    }

    @PostMapping("/SaveOrUpdate")
    public String saveOrUpdate(@Validated @ModelAttribute("ACCOUNT") Account ac, Errors result
            , Model model, @RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult, Exception e) throws IOException {
        if(multipartFile.isEmpty()  || bindingResult.hasErrors() || result.hasErrors()) {
            model.addAttribute("ERROR_PHOTO", "Vui lòng chọn 1 ảnh");
            System.out.println(e);
            return "register";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = "uploads/";
        ac.setPhoto(fileName);
        accountService.save(ac);
        paramService.saveFile(uploadDir, fileName, multipartFile);
        model.addAttribute("ACCOUNT", new Account());
        return "register";
    }

//    @GetMapping("views")
//    public String view(Model model) {
//        model.addAttribute("ACCOUNTS", accountService.findAll());
//        return "admin-accountList";
//    }

    @GetMapping("/forgot-password")
    public String showForgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/change-password")
    public String showChangePassword() {
        return "change-password";
    }

//    @GetMapping("register/{username}")
//    public String edit(@PathVariable("username") String username, Model model) {
//        Optional<Account> account = accountService.findById(username);
//        if (account.isPresent()) {
//            model.addAttribute("ACCOUNT", account.get());
//        } else {
//            model.addAttribute("ACCOUNT", new Account());
//        }
//        return "admin-accountForm";
//    }

//    @GetMapping(value = "views", params = "btnDel")
//    public String del(@RequestParam("username") String username, Model model) {
//        accountService.deleteById(username);
//        return "redirect:/account/views";
//    }

    @PostMapping("/login")
    public String login(Model model) {
        String u = paramService.getString("username", "");
        String p = paramService.getString("password", "");

        try {
            Optional<Account> account = accountService.findById(u);
            if (account.isPresent()) {
                Account acc = account.get();
                if (!acc.getPassword().equals(p)) {
                    model.addAttribute("message", "Mật khẩu không hợp lệ!");
                    return "login";
                } else {
                    String uri = sessionService.get("security-uri"); //Lấy giá trị được lưu trữ trong đối tượng "sessionService" với khóa là "security-uri" và gán cho biến "uri".
                    if (uri != null) { //Kiểm tra xem giá trị của "uri" có khác null hay không. Nếu có, tức là tồn tại giá trị "security-uri" đã được lưu trữ trước đó.
                        return "redirect:" + uri;
                    } else {
                        model.addAttribute("message", "Đăng nhập thành công!");
                        sessionService.set("username", u);
                    }
                }
            } else {
                model.addAttribute("message", "Tên đăng nhập không hợp lệ!");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("message", "Lỗi xảy ra trong quá trình đăng nhập!");
            System.out.println("Lỗi xảy ra trong quá trình đăng nhập!" + e);
            e.printStackTrace(); // In stack trace để kiểm tra lỗi chi tiết
        }
        return "redirect:/product/category/page";
    }

    @GetMapping("logout")
    public String logout() {
        sessionService.remove("username");
        return "login";
    }
}
