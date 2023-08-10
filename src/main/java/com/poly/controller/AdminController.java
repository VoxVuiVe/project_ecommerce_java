package com.poly.controller;

import com.poly.dao.ProductDAO;
import com.poly.model.Account;
import com.poly.model.Category;
import com.poly.model.Product;
import com.poly.model.Report;
import com.poly.service.AccountService;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    ParamService paramService;

    @Autowired
    SessionService sessionService;

    @Autowired
    AccountService accountService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductDAO productDAO;

    // checkSecurity
//    @GetMapping
//    public boolean checkSecurity() {
//        String username = sessionService.get("username");
//        if (username != null) {
//            return true;
//        }
//        return false;
//    }
//
//    @GetMapping
//    public String checkSecurity() {
//        String username = sessionService.get("username");
//        Optional<Account> account = accountService.findById(username);
//        if (account.isPresent() && account.get().getAdmin()) {
//            return "redirect:/admin/**";
//        }
//        return "redirect:/product/category/page";
//    }

    // Hiểm thị trang form account
    @GetMapping("account-form")
    public String showAdminAccountForm(Model model) {
        String username = sessionService.get("username");
        Optional<Account> account = accountService.findById(username);

        if(account.get().getUsername().equals("")) { // Khong viet doan if nay se bi loi bi khong lay duoc Username -> null
            return "redirect:/account/login";
        }

        if (account.isPresent() && account.get().getAdmin()) {
            model.addAttribute("ACCOUNT", new Account()); // Gán giá trị mặc định
            return "admin-accountForm";
        }
        return "redirect:/product/category/page";
    }


    @GetMapping("product-form")
    public String showAdminProductForm(Model model) {
        String username = sessionService.get("username");
        Optional<Account> account = accountService.findById(username);

        if(account.get().getUsername().equals("")) { // Kiểm tra đăng nhập
            return "redirect:/account/login";
        }

        if (account.isPresent() && account.get().getAdmin()) {
            model.addAttribute("PRODUCTS", new Product());
            model.addAttribute("CATEGORYS", categoryService.findAll());
            return "admin-productForm";
        }
        return "redirect:/product/category/page";
    }

    @GetMapping("product-list")
    public String showAdminProductList(Model model) {
        String username = sessionService.get("username");
        Optional<Account> account = accountService.findById(username);

        if(account.get().getUsername().equals("")) { // Kiểm tra đăng nhập
            return "redirect:/account/login";
        }

        if (account.isPresent() && account.get().getAdmin()) {
            model.addAttribute("PRODUCTS", productService.findAll());
            return "admin-productList";
        }
        return "redirect:/product/category/page";
    }

    @GetMapping("account-list")
    public String showAdminAccountList(Model model) {
        String username = sessionService.get("username");
        Optional<Account> account = accountService.findById(username);

        if(account.get().getUsername().equals("")) { // Kiểm tra đăng nhập
            return "redirect:/account/login";
        }

        if (account.isPresent() && account.get().getAdmin()) {
            model.addAttribute("ACCOUNTS", accountService.findAll());
            return "admin-accountList";
        }
        return "redirect:/product/category/page";
    }

    @GetMapping("category-form")
    public String showAdminCategoryForm(Model model) {
        String username = sessionService.get("username");
        Optional<Account> account = accountService.findById(username);

        if(account.get().getUsername().equals("")) { // Kiểm tra đăng nhập
            return "redirect:/account/login";
        }

        if (account.isPresent() && account.get().getAdmin()) {
            model.addAttribute("CATEGORYS", new Category());
            return "admin-categoryForm";
        }
        return "redirect:/product/category/page";
    }

    @GetMapping("category-list")
    public String showAdminCategoryList(Model model) {
        String username = sessionService.get("username");
        Optional<Account> account = accountService.findById(username);

        if(account.get().getUsername().equals("")) { // Kiểm tra đăng nhập
            return "redirect:/account/login";
        }

        if (account.isPresent() && account.get().getAdmin()) {
            model.addAttribute("CATEGORYS", categoryService.findAll());
            return "admin-categoryList";
        }
        return "redirect:/product/category/page";
    }


    // Delete Product
    @GetMapping(value = "product-list", params = "btnDel")
    public String delProduct(@RequestParam("id") Integer id, Model model) {
        productService.deleteById(id);
        return "redirect:/admin/product-list";
    }

    // Delete Account
    @GetMapping(value = "account-list", params = "btnDel")
    public String del(@RequestParam("username") String username, Model model) {
        accountService.deleteById(username);
        return "redirect:/admin/account-list";
    }

    // Luu or Update Account
    @PostMapping("/SaveOrUpdateAdmin")
    public String saveOrUpdate(@Validated @ModelAttribute("ACCOUNT") Account ac, Errors result
            , Model model, @RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult, Exception e) throws IOException {
        if(multipartFile.isEmpty()  || bindingResult.hasErrors() || result.hasErrors()) {
            model.addAttribute("ERROR_PHOTO", "Vui lòng chọn 1 ảnh");
            System.out.println(e);
            return "admin-accountForm";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = "uploads/";
        ac.setPhoto(fileName);
        accountService.save(ac);
        paramService.saveFile(uploadDir, fileName, multipartFile);
        model.addAttribute("ACCOUNT", new Account());
        return "admin-accountForm";
    }

    // Luu or Update Category
    @PostMapping("/SaveOrUpdateCategory")
    public String saveOrUpdateCategory(@Validated @ModelAttribute("CATEGORYS") Category category, Errors result
            , Model model, BindingResult bindingResult, Exception e) throws IOException {
        if(category.getId().equals("")) {
            model.addAttribute("ERROR_MESSAGE", "Vui lòng nhập mã danh mục");
            return "admin-categoryForm";
        } else if (category.getName().equals("")) {
            model.addAttribute("ERROR_MESSAGE", "Vui lòng nhập tên danh mục");
            return "admin-categoryForm";
        }
        categoryService.save(category);
        model.addAttribute("CATEGORYS", new Category());
        model.addAttribute("MESSAGE", "Thêm hoặc cập nhật thành công");
        return "admin-categoryForm";
    }

    // Luu or Update PRODUCT
    @PostMapping("/SaveOrUpdateProduct")
    public String saveOrUpdateProduct(@Validated @ModelAttribute("PRODUCTS") Product product, Errors result
            , Model model, @RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult, Exception e) throws IOException {
        // Start Validate
//        if(multipartFile.isEmpty()) { //  || bindingResult.hasErrors() || result.hasErrors()
//            model.addAttribute("ERROR_PHOTO", "Vui lòng chọn 1 ảnh");
//            System.out.println(e);
//            return "admin-productForm";
//        }
//        if (product.getName().equals("")) {
//            model.addAttribute("ERROR_MESSAGE", "Vui lòng điền tên sản phẩm");
//        } else if (product.getPrice().equals("")) {
//            model.addAttribute("ERROR_MESSAGE", "Vui lòng điền giá sản phẩm");
//        } else if (product.getQuantity().equals("")) {
//            model.addAttribute("ERROR_MESSAGE", "Vui lòng số lượng tên sản phẩm");
//        } else if (product.getCreateDate().equals("")) {
//            model.addAttribute("ERROR_MESSAGE", "Vui lòng điền ngày thêm sản phẩm");
//        } else if (product.getCategory().equals("")) {
//            model.addAttribute("ERROR_MESSAGE", "Vui lòng điền loại danh mục sản phẩm");
//        }
        List<String> errorMessages = new ArrayList<>();

        // Kiểm tra ảnh
        if (multipartFile.isEmpty()) {
            model.addAttribute("ERROR_PHOTO", "Vui lòng chọn 1 ảnh");
            System.out.println(e);
            return "admin-productForm";
        }

        // Kiểm tra các trường khác
        if (product.getName().isEmpty()) {
            errorMessages.add("Vui lòng điền tên sản phẩm");
        } else if (product.getPrice() == null) {
            errorMessages.add("Vui lòng điền giá sản phẩm");
        } else if (product.getQuantity() == null) {
            errorMessages.add("Vui lòng số lượng tên sản phẩm");
        } else if (product.getCreateDate() == null) {
            errorMessages.add("Vui lòng điền ngày thêm sản phẩm");
        } else if (product.getCategory() == null) {
            errorMessages.add("Vui lòng điền loại danh mục sản phẩm");
        }

        if (!errorMessages.isEmpty()) {
            model.addAttribute("ERROR_MESSAGE", errorMessages.get(0));
            return "admin-productForm";
        }
        // End validate
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = "uploads/";
        product.setImage(fileName);
        productService.save(product);
        paramService.saveFile(uploadDir, fileName, multipartFile);
        model.addAttribute("PRODUCTS", new Product());
        model.addAttribute("MESSAGE", "Thêm hoặc cập nhật thành công");
        return "admin-productForm";
    }

    // Form Edit Account
    @GetMapping("admin-accountForm/{username}")
    public String edit(@PathVariable("username") String username, Model model) {
        Optional<Account> account = accountService.findById(username);
        if (account.isPresent()) {
            model.addAttribute("ACCOUNT", account.get());
        } else {
            model.addAttribute("ACCOUNT", new Account());
        }
        return "admin-accountForm";
    }

    // Form Edit Product
    @GetMapping("admin-productForm/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            model.addAttribute("PRODUCTS", product.get());
            model.addAttribute("CATEGORYS", categoryService.findAll());
        } else {
            model.addAttribute("PRODUCTS", new Product());
        }
        return "admin-productForm";
    }

    // Edit Category
    @GetMapping("admin-categoryForm/{id}")
    public String editCategory(@PathVariable("id") String id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addAttribute("CATEGORYS", category.get());
        } else {
            model.addAttribute("CATEGORYS", new Category());
        }
        return "admin-categoryForm";
    }

    @GetMapping("/report")
    public String report(Model model) {
        String username = sessionService.get("username");
        Optional<Account> account = accountService.findById(username);

        if(account.get().getUsername().equals("")) { // Kiểm tra đăng nhập
            return "redirect:/account/login";
        }

        if (account.isPresent() && account.get().getAdmin()) {
            List<Report> lsReport = productDAO.getInventoryByCategory();
            model.addAttribute("REPORT", lsReport);
            return "admin-reportList";
        }
        return "redirect:/product/category/page";
    }

}
