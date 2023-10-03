package FoodStore.FoodStore.Controller;

import FoodStore.FoodStore.Entity.*;
import FoodStore.FoodStore.Repository.CategoryRepository;
import FoodStore.FoodStore.Repository.Category_ProductRepository;
import FoodStore.FoodStore.Repository.ProductsRepository;
import FoodStore.FoodStore.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ProductsController {
    @Autowired
    HttpSession session;
    @Autowired
    ProductsService productsService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    Category_ProductService category_productService;
    @Autowired
    CartService cartService;
    @Autowired
    BillService billService;
    @Autowired
    UserService userService;
    @Autowired
    Bill_DetailService bill_detailService;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private Category_ProductRepository category_ProductRepository;



    @GetMapping("/deleteCart/{id}")
    public String deleteCart(@PathVariable int id, Model model) {
//        System.out.println(id);
        cartService.deleteCartByCartId(id);

        User u = (User) session.getAttribute("account");
        List<Cart> lsCart = cartService.getAllCartByUserId(u.getUserId());

        double totalCartMoney = 0;
        for (Cart ca : lsCart) {
            totalCartMoney += ca.getProductsQuantity() * ca.getProducts().getProductsPrice();
        }

        session.setAttribute("totalCartMoney", totalCartMoney);
        session.setAttribute("cart", lsCart);
        return "redirect:/shoping-cart";
    }

    @GetMapping("/unlike/{id}")
    public String unlike(@PathVariable int id, Model model, HttpServletRequest request) {
        User u = (User) session.getAttribute("account");
        if (u != null) {
            favoriteService.deleteFavoriteById(id);
            List<Favorite> favoriteList = favoriteService.getAllFavotieProcByUserId(u.getUserId());
            session.setAttribute("favorite", favoriteList);

        }
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("addToCart/{productId}")
    public String addToCart(Model model, @PathVariable int productId, HttpServletRequest request) throws Exception {
        addToCart(productId, 1);
        return "redirect:" + request.getHeader("Referer");

    }

    @PostMapping("/addToCart")
    public String addToCartWithQuantity(@ModelAttribute(name = "productQuantity") int productQuantity, Model model, HttpServletRequest request) {
        int productsId = Integer.parseInt(session.getAttribute("productsId").toString());
        addToCart(productsId, productQuantity);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/addToFavorite/{productId}")
    public String addToFav(@PathVariable int productId, Model model, HttpServletRequest request) {
        User u = (User) session.getAttribute("account");
        Products products = productsService.getProductsById(productId);
        if (u != null) {
            List<Favorite> favoriteList = favoriteService.getAllFavotieProcByUserId(u.getUserId());
            Favorite favorite = new Favorite();
            int flag = 0;
            for (Favorite favorite1 : favoriteList) {
                if (favorite1.getProducts().getProductsId() == productId) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                favorite.setProducts(products);
                favorite.setUser(u);
                favoriteService.saveFavorite(favorite);
                favoriteList = favoriteService.getAllFavotieProcByUserId(u.getUserId());

                session.setAttribute("favorite", favoriteList);
            }
        } else {
//            return "redirect"
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/updateCart")
    public String updateCart(Model model, HttpServletRequest request) {
        List<Cart> lsCart = (List<Cart>) session.getAttribute("cart");
        User u = (User) session.getAttribute("account");
        if (u != null) {
            int cartI = 0;
            for (Cart ca : lsCart) {
                int newQuantity = Integer.parseInt(request.getParameter("productsQuantity" + cartI));
                ca.setProductsQuantity(newQuantity);
                cartService.saveCart(ca);

                cartI++;
            }
            lsCart = cartService.getAllCartByUserId(u.getUserId());

            double totalCartMoney = 0;
            for (Cart ca : lsCart) {
                totalCartMoney += ca.getProductsQuantity() * ca.getProducts().getProductsPrice();
            }
            session.setAttribute("totalCartMoney", totalCartMoney);
            session.setAttribute("cart", lsCart);
        } else {
            return "redirect:/home";

        }

        return "redirect:/shoping-cart";
    }

    public void addToCart(int productsId, int productQuantity) {
//        System.out.println(productQuantity);
        User u = (User) session.getAttribute("account");
        Products products = productsService.getProductsById(productsId);
        if (u != null) {
            List<Cart> lsCart = cartService.getAllCartByUserId(u.getUserId());
            Cart cart = new Cart();
            int test = 0;
            for (Cart c : lsCart) {
                if (c.getProducts().getProductsId() == productsId) {
                    test = 1;
                    break;
                }
            }
            if (test == 0) {
//                Cart cart1 = new Cart();
                cart.setProductsQuantity(productQuantity);
                cart.setProducts(products);
                cart.setUser(u);
                cartService.saveCart(cart);
            } else {
                for (Cart ca : lsCart) {
                    if (ca.getProducts().equals(products)) {
                        ca.setProductsQuantity(ca.getProductsQuantity() + productQuantity);
                        cartService.saveCart(ca);
                    }
                }
            }


            lsCart = cartService.getAllCartByUserId(u.getUserId());

            double totalCartMoney = 0;
            for (Cart ca : lsCart) {
                totalCartMoney += ca.getProductsQuantity() * ca.getProducts().getProductsPrice();
            }
            session.setAttribute("totalCartMoney", totalCartMoney);
            session.setAttribute("cart", lsCart);
        }
    }

    @GetMapping("/checkout")
    public String checkout(Model model) throws Exception {
//        @SuppressWarnings("")
        User u = (User) session.getAttribute("account");

        if (u != null) {
            List<Cart> lsCart = cartService.getAllCartByUserId(u.getUserId());
            Bill bill = new Bill();
            lsCart = cartService.getAllCartByUserId(u.getUserId());
            double totalCartMoney = 0;
            for (Cart ca : lsCart) {
                totalCartMoney += ca.getProductsQuantity() * ca.getProducts().getProductsPrice();
            }
            session.setAttribute("totalCartMoney", totalCartMoney);
            session.setAttribute("cart", lsCart);
            return "checkout";
        } else {
            return "redirect:/home";

        }
    }

    @PostMapping("/checkout")
    public String check(Model model,
                        @ModelAttribute(name = "username") String username,
                        @ModelAttribute(name = "country") String country,
                        @ModelAttribute(name = "address") String address,
                        @ModelAttribute(name = "phone") String phone,
                        @ModelAttribute(name = "email") String email,
                        @ModelAttribute(name = "note") String note,
                        @RequestParam(value = "payment", defaultValue = "false") boolean paymentChecked) {
        User u = (User) session.getAttribute("account");

        if (u != null) {
            List<Cart> lsCart = cartService.getAllCartByUserId(u.getUserId());
            System.out.println(lsCart);
            if(lsCart.size() == 0){
                return "/checkout";
            }
            Bill bill = new Bill();
            long m = System.currentTimeMillis();
            Date checkoutDate = new java.sql.Date(m);

            u.setUserName(username);
            u.setUserAddress(address);
            bill.setUser(u);
            bill.setAddress(address);
            bill.setNote(note);
            bill.setStatus(false);
            bill.setPhone(phone);
            bill.setDateCheckout(checkoutDate);
            bill.setCountry(country);

            userService.saveUser(u);
            billService.saveBill(bill);

            session.setAttribute("newBill", bill);
            if (paymentChecked) {
                return "redirect:/payment";
            }
        } else {
            return "redirect:/home";

        }

        return "redirect:/checkout";
    }

    @GetMapping("/payment")
    public String payment(Model model) {
        User u = (User) session.getAttribute("account");
        if (u != null) {
            Bill newBill = (Bill) session.getAttribute("newBill");
//            List<Bill_Detail> bill_details = new ArrayList<>() ;
            List<Cart> lsCart = (List<Cart>) session.getAttribute("cart");
            double totalBillMoney = 0;
            for (Cart ca : lsCart) {
                Bill_Detail bill_detail = new Bill_Detail();
                bill_detail.setProducts(ca.getProducts());
                bill_detail.setProductsQuantity(ca.getProductsQuantity());
                bill_detail.setBill(newBill);

                totalBillMoney += ca.getProducts().getProductsPrice() * ca.getProductsQuantity();
                bill_detailService.saveBill_Details(bill_detail);

                cartService.deleteCartByCartId(ca.getCartId());
            }
            newBill.setTotalMoney(totalBillMoney);
            billService.saveBill(newBill);
//            System.out.println(newBill);


            lsCart = cartService.getAllCartByUserId(u.getUserId());
            double totalCartMoney = 0;
            for (Cart ca : lsCart) {
                totalCartMoney += ca.getProductsQuantity() * ca.getProducts().getProductsPrice();
            }

            session.setAttribute("totalCartMoney", totalCartMoney);
            session.setAttribute("cart", lsCart);
            session.setAttribute("billId", newBill.getBillId());
//            int id = newBill.getBillId();

            return "redirect:/invoice";
//            return "redirect:/home";
        } else {
            return "redirect:/home";
        }
    }


}
