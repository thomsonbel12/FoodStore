package FoodStore.FoodStore.Controller;

import FoodStore.FoodStore.Entity.Cart;
import FoodStore.FoodStore.Entity.Favorite;
import FoodStore.FoodStore.Entity.Products;
import FoodStore.FoodStore.Entity.User;
import FoodStore.FoodStore.Repository.FavoriteRepository;
import FoodStore.FoodStore.Service.CartService;
import FoodStore.FoodStore.Service.UserService;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
//import sun.jvm.hotspot.gc.parallel.PSYoungGen;

@Controller
public class LoginSignUpController {
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @Autowired
    HttpSession session;
    @Autowired
    private FavoriteRepository favoriteRepository;

    @PostMapping("/loginACC")
    public String login(@ModelAttribute(name = "login-email") String email,
                        @ModelAttribute(name = "pass") String pass, Model model, HttpServletRequest request) {

        if (session.getAttribute("email") != null) {
            email = (String) session.getAttribute("email");
            model.addAttribute("login_email", email);
        }
        User u = userService.getUserByEmail(email);


        if (u != null) {
            if (u.getUserPass().trim().equals(pass.trim())) {
                session.setAttribute("account", u);
                List<Cart> lsCart = cartService.getAllCartByUserId(u.getUserId());
                List<Favorite> favoriteList = favoriteRepository.findByUser_UserId(u.getUserId());
                double totalCartMoney = 0;
                for (Cart ca : lsCart) {
                    totalCartMoney += ca.getProductsQuantity() * ca.getProducts().getProductsPrice();
                }
                session.setAttribute("totalCartMoney", totalCartMoney);
                session.setAttribute("cart", lsCart);
                session.setAttribute("favorite", favoriteList);
//                session.setAttribute("favorite", u.getFavorite());
                return "redirect:/home";
            } else {
                model.addAttribute("error", "This Email or Password is not VALID");
                return "LoginSignUp";
            }

        } else {
            model.addAttribute("error", "This Email or Password is not VALID");
            System.out.println("Nope");
            return "LoginSignUp";
        }

    }

    @PostMapping("signupACC")
    public String signup(Model model, @ModelAttribute(name = "email") String email,
                         @ModelAttribute(name = "pass") String pass,
                         @ModelAttribute(name = "passCom") String passCom,
                         @ModelAttribute(name = "userName") String username,
                         @ModelAttribute(name = "Address") String address) {
        User u = userService.getUserByEmail(email);
        if (u == null) {
            if (pass.equals(passCom)) {
                User newU = new User();
                newU.setUserEmail(email);
                newU.setUserPass(pass);
                newU.setUserAddress(address);
                newU.setUserName(username);
                model.addAttribute("login-email", email);
                session.setAttribute("email", email);
                userService.saveUser(newU);
                return "redirect:/login";
            } else {
                model.addAttribute("error", "Password confirm ERROR");
//                return "LoginSignUp";
            }
        } else {
            model.addAttribute("login-email", email);
            session.setAttribute("email", email);
            model.addAttribute("error", "This Email was REGISTERED");
            model.addAttribute("login_email", email);
//            return "LoginSignUp";
        }
        return "LoginSignUp";
    }

    @GetMapping("/signout")
    public String signOut(Model model) {
        session.removeAttribute("account");
        return "redirect:home";
    }
}
