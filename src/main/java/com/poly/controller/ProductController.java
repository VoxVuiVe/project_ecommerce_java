package com.poly.controller;

import com.poly.model.Product;
import com.poly.service.ProductService;
import com.poly.utils.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    SessionService sessionService;

    @Autowired
    ProductService serviceProduct;

//    @GetMapping("category")
//    public String viewProducts(Model model, @RequestParam("field") Optional<String> field) {
//        Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("price"));
//        List<Product> lsProduct = serviceProduct.findAll(sort);
//
//        if(checkSecurity()) {
//            model.addAttribute("LIST_PRODUCT", lsProduct);
//            return "category";
//        }
//        for (Product product: lsProduct) {
//            System.out.println(product.toString());
//        }
//        model.addAttribute("LIST_PRODUCT", lsProduct);
//        return "redirect:/account/login";
//    }
//
//    @GetMapping("/category/page")
//    public String paginate(Model model, @RequestParam("ls") Optional<Integer> ls) {
//        if(checkSecurity()) {
//            int currentPage = ls.orElse(0); // Sửa giá trị mặc định thành 0 nếu không có giá trị ls
//
//            if (currentPage < 0) { // Kiểm tra nếu currentPage nhỏ hơn 0
//                currentPage = 0; // Đặt currentPage thành 0 để tránh lỗi
//            }
//
//            Pageable pageable = PageRequest.of(currentPage, 9);
//            Page<Product> page = serviceProduct.findAllPage(pageable);
//            model.addAttribute("LIST_PRODUCT", page);
//
//            int totalPages = page.getTotalPages();
//            List<Integer> pageNumbers = new ArrayList<>();
//
//            if (currentPage > 0) { // Kiểm tra nếu currentPage không phải trang đầu tiên
//                pageNumbers.add(currentPage - 1);
//            }
//            pageNumbers.add(currentPage);
//
//            if (currentPage < totalPages - 1) {
//                pageNumbers.add(currentPage + 1);
//            }
//            if (currentPage < totalPages - 2) {
//                pageNumbers.add(currentPage + 2);
//            }
//            String username = sessionService.get("username");
//            if (username != null) {
//                model.addAttribute("username", username);
//            }
//            model.addAttribute("PAGE_NUMBERS", pageNumbers);
//
//            return "category";
//        }
//
//        return "redirect:/account/login";
//    }

    @GetMapping("/category/page")
    public String paginate(Model model, @RequestParam("field") Optional<String> field, @RequestParam("ls") Optional<Integer> ls) {
        if (checkSecurity()) { // Kiểm tra tính bảo mật
            Sort sort = Sort.by(Sort.Direction.ASC, field.orElse("price")); // Sắp xếp theo trường field (mặc định là "price")
            List<Product> lsProduct = serviceProduct.findAll(sort); // Lấy danh sách sản phẩm đã được sắp xếp

            int currentPage = ls.orElse(0); // Trang hiện tại (mặc định là 0)

            if (currentPage < 0) {
                currentPage = 0; // Đảm bảo trang hiện tại không nhỏ hơn 0
            }

            Pageable pageable = PageRequest.of(currentPage, 9, sort); // Tạo đối tượng Pageable cho phân trang, hiển thị 9 sản phẩm trên mỗi trang
            Page<Product> page = serviceProduct.findAllPage(pageable); // Lấy trang sản phẩm dựa trên Pageable đã tạo
            model.addAttribute("LIST_PRODUCT", lsProduct); // Thêm danh sách sản phẩm vào model
            model.addAttribute("LIST_PRODUCT", page); // Thêm trang sản phẩm vào model

            int totalPages = page.getTotalPages(); // Tổng số trang
            List<Integer> pageNumbers = new ArrayList<>(); // Danh sách số trang

            if (currentPage > 0) {
                pageNumbers.add(currentPage - 1); // Trang trước đó (nếu có)
            }
            pageNumbers.add(currentPage); // Trang hiện tại

            if (currentPage < totalPages - 1) {
                pageNumbers.add(currentPage + 1); // Trang kế tiếp (nếu có)
            }
            if (currentPage < totalPages - 2) {
                pageNumbers.add(currentPage + 2); // Trang sau kế tiếp (nếu có)
            }

            String username = sessionService.get("username"); // Lấy tên người dùng từ session
            if (username != null) {
                model.addAttribute("username", username); // Thêm tên người dùng vào model
            }
            model.addAttribute("PAGE_NUMBERS", pageNumbers); // Thêm danh sách số trang vào model

            return "category"; // Trả về view "category" để hiển thị danh sách sản phẩm và phân trang
        }

        return "redirect:/account/login"; // Nếu không đáp ứng điều kiện bảo mật, chuyển hướng đến trang đăng nhập
    }

    @GetMapping
    public boolean checkSecurity() {
        String username = sessionService.get("username");
        if (username != null) {
            return true;
        }
        return false;
    }

    @GetMapping("details")
    public String showDetails(Model model) {
        List<Product> lsProduct = serviceProduct.findAll();
        model.addAttribute("details", lsProduct);
        return "product-details";
    }

}
