package FoodStore.FoodStore.Controller;

import FoodStore.FoodStore.Entity.*;
import FoodStore.FoodStore.Repository.*;
import FoodStore.FoodStore.Service.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ShopController {
    @Autowired
    UserService userService;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    Category_ProductService category_productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    FavoriteService favoriteService;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductsService productsService;

    @Autowired
    CartService cartService;
    @Autowired
    HttpSession session;
    @Autowired
    private Category_ProductRepository category_ProductRepository;
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillService billService;

    @GetMapping("/home")
    public String home(Model model) throws Exception {
        List<Category> categoryList = categoryService.getAllCategory();

        List<Category_Product> category_products1 = category_productService.getAllCategory_ProductByCategoryId(1);
        List<Category_Product> category_products2 = category_productService.getAllCategory_ProductByCategoryId(3);

        List<Products> productsList1 = productsService.getTop10ProductsByCategory(category_products1);
        List<Products> productsList2 = productsService.getTop10ProductsByCategory(category_products2);

        model.addAttribute("ProductsList1", productsList1);
        model.addAttribute("ProductsList2", productsList2);

        if (categoryList != null)
            model.addAttribute("categoryList", categoryList);
        return "index";
//        return "redirect:/shop-item";
    }

    @GetMapping("/")
    public String home2(Model model) {
//        return "redirect:/shop-item";

        return "redirect:/home";
    }

    @GetMapping("blog")
    public String blog(Model model) {
        return "blog";
    }

    @GetMapping("blog-details")
    public String blogDetail(Model model) {

        return "blog-details";
    }


    @GetMapping("contact")
    public String contact(Model model) {
        return "contact";
    }

    @GetMapping("shop-details")
    public String shopdetails(Model model) {
        if (session.getAttribute("productsId") != null) {
            int procductsId = Integer.parseInt(session.getAttribute("productsId").toString());
            Products products = productsService.getProductsById(procductsId);
            model.addAttribute("productsName", products.getProductsName());
            model.addAttribute("productsDetail", products);
        }
        return "shop-details";
    }

    @GetMapping("shop-details/{id}")
    public String shopdetails2(@PathVariable int id, Model model) {
        session.setAttribute("productsId", id);

        return "redirect:/shop-details";
    }

    @GetMapping("shop-item/category/{id}")
    public String shopgrid(@PathVariable int id, Model model) {
        List<Category> categoryList = categoryService.getAllCategory();
        if (categoryList != null) {
            model.addAttribute("categoryList", categoryList);
            session.setAttribute("categoryList", categoryList);
        }
        session.setAttribute("categoryId", id);
        return "redirect:/shop-item/category/{id}/page=1";
    }

    @GetMapping("shop-item/category/{id}/page={number}")
    public String categoryPage(@PathVariable int id,
                               @PathVariable int number,
                               Model model,
                               @RequestParam("sort_type") Optional<Integer> sort_type) {
        List<Category> categoryList = categoryService.getAllCategory();
        if (categoryList != null) {
            model.addAttribute("categoryList", categoryList);
            session.setAttribute("categoryList", categoryList);
        }
        session.removeAttribute("ProductsList");
        if (number - 1 < 0) {
            return "redirect:/shop-item/category/{id}/page=1";
        }
        if (sort_type.isEmpty()) {
            if (session.getAttribute("sort_type") == null) {
                session.setAttribute("sort_type", Optional.of(0));
            }
            sort_type = (Optional<Integer>) session.getAttribute("sort_type");

        }
        session.setAttribute("sort_type", sort_type);
        Pageable pageable = PageRequest.of(number - 1, 6);
        System.out.println(session.getAttribute("sort_type").toString());
        if (sort_type.equals(Optional.of(1))) {
            pageable = PageRequest.of(number - 1, 6, Sort.by("productsPrice").ascending());
        } else if (sort_type.equals(Optional.of(2))) {
            pageable = PageRequest.of(number - 1, 6, Sort.by("productsName").ascending());
        }

        System.out.println(pageable.first());
        List<Category_Product> category_products = category_productService.getAllCategory_ProductByCategoryId(id);
        Page<Products> productsPage = productsRepository.findByProductCategoryIn(category_products, pageable);


        long totalProc = productsPage.getTotalElements();
        model.addAttribute("totalProducts", totalProc);
        model.addAttribute("ProductsList", productsPage);
        model.addAttribute("thisIsShowCategory", true);

        session.removeAttribute("search_input");

        return "shop-grid";
    }

    @GetMapping("shop-item")
    public String shopgrid2(Model model) {
        List<Category> categoryList = categoryService.getAllCategory();
        if (categoryList != null) {
            model.addAttribute("categoryList", categoryList);
            session.setAttribute("categoryList", categoryList);
        }
        return "redirect:/shop-item/1";
    }

    @GetMapping("shop-item/{id}")
    public String shopgrid3(@PathVariable int id, Model model, @RequestParam("sort_type") Optional<Integer> sort_type) {
//        if(s)
        List<Category> categoryList = categoryService.getAllCategory();
        if (categoryList != null) {
            model.addAttribute("categoryList", categoryList);
            session.setAttribute("categoryList", categoryList);
        }
        if (id - 1 < 0) {
            return "redirect:/shop-item/1";
        }
        if (sort_type.isEmpty()) {
            if (session.getAttribute("sort_type") == null) {
                session.setAttribute("sort_type", Optional.of(0));
            }
            sort_type = (Optional<Integer>) session.getAttribute("sort_type");

        }
        session.setAttribute("sort_type", sort_type);
        Pageable pageable = PageRequest.of(id - 1, 6);
        System.out.println(session.getAttribute("sort_type").toString());
        if (sort_type.equals(Optional.of(1))) {
            pageable = PageRequest.of(id - 1, 6, Sort.by("productsPrice").ascending());
        } else if (sort_type.equals(Optional.of(2))) {
            pageable = PageRequest.of(id - 1, 6, Sort.by("productsName").ascending());
        }
        System.out.println(session.getAttribute("sort_type"));
        if(session.getAttribute("sort_type").equals(Optional.of(1))){
            pageable = PageRequest.of(id - 1, 6, Sort.by("productsPrice").ascending());
        }else if(session.getAttribute("sort_type").equals(Optional.of(2))){
            pageable = PageRequest.of(id - 1, 6, Sort.by("productsName").ascending());
        }
        Page<Products> productsPage = productsRepository.findAll(pageable);


        model.addAttribute("totalProducts", productsPage.getTotalElements());
        model.addAttribute("ProductsList", productsPage);
        model.addAttribute("thisIsShowAll", true);

        return "shop-grid";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @ModelAttribute(name = "search_input") String procName,
                         @RequestParam("sort_type") Optional<Integer> sort_type) {

        List<Category> categoryList = categoryService.getAllCategory();
        if (categoryList != null) {
            model.addAttribute("categoryList", categoryList);
            session.setAttribute("categoryList", categoryList);
        }

        if (sort_type.isEmpty()) {
            if (session.getAttribute("sort_type") == null) {
                session.setAttribute("sort_type", Optional.of(0));
            }
            sort_type = (Optional<Integer>) session.getAttribute("sort_type");

        }
        session.setAttribute("sort_type", sort_type);
        Pageable pageable = PageRequest.of(0, 6);
        System.out.println(session.getAttribute("sort_type").toString());
        if (sort_type.equals(Optional.of(1))) {
            pageable = PageRequest.of(0, 6, Sort.by("productsPrice").ascending());
        } else if (sort_type.equals(Optional.of(2))) {
            pageable = PageRequest.of(0, 6, Sort.by("productsName").ascending());
        }
        List<Category_Product> category_products = category_productService.getAllProductOfCategoryByCategoryName(procName);
        Page<Products> productsList = productsService.getAllProductsByNameAndCategory(procName.trim(), category_products, pageable);
        long totalProc = productsList.getTotalElements();
        model.addAttribute("totalProducts", totalProc);
        model.addAttribute("search_input", procName);
        model.addAttribute("ProductsList", productsList);
        model.addAttribute("search_input", procName);
        model.addAttribute("thisIsSearch", true);

        session.setAttribute("search_input", procName);


//
        return "shop-grid";
//        return "redirect:/search/1?search_input={name}";

    }
    @GetMapping("/search/{id}/search_input={search_input}")
    public String searchPage(Model model,
                             @PathVariable int id,
                             @PathVariable String search_input,
                             @RequestParam("sort_type") Optional<Integer> sort_type) {

        List<Category> categoryList = categoryService.getAllCategory();
        if (categoryList != null) {
            model.addAttribute("categoryList", categoryList);
            session.setAttribute("categoryList", categoryList);
        }
        if (id - 1 < 0) {
            return "redirect:/search/1";
        }

        if (sort_type.isEmpty()) {
            if (session.getAttribute("sort_type") == null) {
                session.setAttribute("sort_type", Optional.of(0));
            }
            sort_type = (Optional<Integer>) session.getAttribute("sort_type");

        }
        session.setAttribute("sort_type", sort_type);
        Pageable pageable = PageRequest.of(0, 6);
        System.out.println(session.getAttribute("sort_type").toString());
        if (sort_type.equals(Optional.of(1))) {
            pageable = PageRequest.of(0, 6, Sort.by("productsPrice").ascending());
        } else if (sort_type.equals(Optional.of(2))) {
            pageable = PageRequest.of(0, 6, Sort.by("productsName").ascending());
        }
        List<Category_Product> category_products = category_productService.getAllProductOfCategoryByCategoryName(search_input);
        Page<Products> productsList = productsService.getAllProductsByNameAndCategory(search_input.trim(), category_products, pageable);
        long totalProc = productsList.getTotalElements();

        model.addAttribute("totalProducts", totalProc);
        model.addAttribute("search_input", search_input);
        model.addAttribute("ProductsList", productsList);
        model.addAttribute("search_input", search_input);
        model.addAttribute("thisIsSearch", true);

        return "shop-grid";
    }

    @GetMapping("shoping-cart")
    public String shoppingcart(Model model) {
        if (session.getAttribute("account") != null) {
            User u = (User) session.getAttribute("account");
            List<Cart> userCart = cartService.getAllCartByUserId(u.getUserId());
            session.setAttribute("userCart", userCart);
            model.addAttribute("userCart", userCart);
        } else {
            return "redirect:/home";
        }
        return "shoping-cart";
    }

    @GetMapping("login")
    public String login(Model model) {
        return "LoginSignUp";
    }

    @GetMapping("favorite")
    public String fav(Model model) {
        List<Category> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        User user = (User) session.getAttribute("account");

        if (user != null) {
            List<Favorite> favoriteList = favoriteService.getAllFavotieProcByUserId(user.getUserId());
            model.addAttribute("favoriteList", favoriteList);
        }

        return "favorite";
    }

    @GetMapping("/my-order")
    public String myOrder(Model model) {
        User u = (User) session.getAttribute("account");
        if (u != null) {
            int pn = 0;
            if(session.getAttribute("order-page") != null)
                pn = (int) session.getAttribute("order-page");

            List<Bill> bills = billRepository.findByUser_UserId(u.getUserId());
            Pageable pageable = PageRequest.of(pn, 4, Sort.by("billId").descending());
            Page<Bill> billsPage = billRepository.findByUser_UserId(u.getUserId(), pageable);
            if(bills != null){
                if (billsPage == null){
                    return "redirect:/my-order/0";
                }
            }

            model.addAttribute("bills", billsPage);
            session.removeAttribute("order-page");

        }
        return "my-order";
    }
    @GetMapping("my-order/{id}")
    public String orp(@PathVariable int id){
        session.setAttribute("order-page", id);
        return "redirect:/my-order";
    }

    @GetMapping("/invoice")
    public String invoice(Model model) {
        int id = 0;
        if (session.getAttribute("billId") != null) {
            id = Integer.parseInt(session.getAttribute("billId").toString());
        }
        System.out.println(id);
        User user = (User) session.getAttribute("account");

        if (user != null) {
            Bill bill = billService.getBillOfUserAndBillId(user.getUserId(), id);
            if (bill != null) {
                model.addAttribute("billId", bill.getBillId());
                model.addAttribute("bill", bill);
            }
        }
        return "invoice";
    }

    @GetMapping("/invoice/{id}")
    public String invoiceid(@PathVariable int id) {
        session.setAttribute("billId", id);
        return "redirect:/invoice";
    }
}
