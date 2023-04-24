package FoodStore.FoodStore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class admin_dashboardController {
	@Autowired
    HttpSession session;

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        return "admin-dashboard/dashboard";
    }

    @GetMapping("/dashboard/product/add")
    public String addproc(Model model){
        return "admin-dashboard/dashboard-add-products";
    }

    @GetMapping("dashboard/order")
    public String myOrder(Model model){
        return "admin-dashboard/dashboard-order";
    }
    @GetMapping("dashboard/income")
    public String income(Model model){
        return "admin-dashboard/dashboard-wallet";
    }
}
