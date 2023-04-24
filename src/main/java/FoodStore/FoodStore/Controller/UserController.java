package FoodStore.FoodStore.Controller;

import FoodStore.FoodStore.Entity.User;
import FoodStore.FoodStore.Service.CloudinaryService;
import FoodStore.FoodStore.Service.UserService;
import jakarta.mail.Multipart;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    @Autowired
    CloudinaryService cloudinaryService;
    @GetMapping("/my-profile")
    public String myProfile(Model model){
        User user = (User) session.getAttribute("account");
        if (user == null){
            return "redirect:/login";
        }else{
            model.addAttribute("profileDetail", user);
        }
        return "my-profile";
    }
    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("userName") String username,
                                @ModelAttribute("avatar") MultipartFile avatar,
//                                @ModelAttribute("email") String email,
                                @ModelAttribute("address") String address,
                                HttpServletRequest request){

        User u = (User) session.getAttribute("account");
        if (u != null){
            u.setUserName(username);
//
//            u.setUserEmail(email);
            u.setUserAddress(address);
            if(avatar.isEmpty() == false){
                String uimage = cloudinaryService.uploadFile(avatar);
                u.setUserImage(uimage);
            }
            userService.saveUser(u);
        }else{

            return "redirect:/login";
        }
        return "redirect:" + request.getHeader("Referer");
    }
    @GetMapping("/change-password")
    public String cp(){
        return "redirect:/home";
    }
    @PostMapping("/change-password")
    public ModelAndView updatePass(@ModelAttribute("pass") String pass,
                                   @ModelAttribute("newp") String newp,
                                   @ModelAttribute("repp") String repp,
                                   ModelMap model,
                                   HttpServletRequest request){

        User u = (User) session.getAttribute("account");
        ModelMap newModelMap = new ModelMap();
        if (u != null){
            if(u.getUserPass().equals(pass) == false){
//                model.addAttribute("status_change", false);
                newModelMap.addAttribute("status_change_text", "Your Old Password is not Correct !!");
                newModelMap.addAttribute("profileDetail", u);

                return new ModelAndView("forward:/my-profile",newModelMap);
            }else{
                if(newp.equals(repp) == false){
//                    model.addAttribute("status_change", false);
                    newModelMap.addAttribute("status_change_text", "Password Confirm not correct !!");
                    newModelMap.addAttribute("profileDetail", u);
                    return new ModelAndView("redirect:/my-profile",newModelMap);
                }else {
//                    model.addAttribute("status_change", true);
                    newModelMap.addAttribute("status_change_text", "Password change successfully !!");

                    u.setUserPass(newp);
                    userService.saveUser(u);

                    u = userService.getUserByEmail(u.getUserEmail());
                    newModelMap.addAttribute("profileDetail", u);

                    session.setAttribute("account", u);
                    return new ModelAndView("redirect:/my-profile",newModelMap);
                }
            }
        }else{
            return new ModelAndView("/login");
        }
//        return "redirect:" + request.getHeader("Referer");
    }
}
