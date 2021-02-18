package ua.servlet.restaurant.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.command.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    Logger logger = LogManager.getLogger(Command.class);
    private final Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("login", new LoginController());
        commands.put("registration", new RegistrationController());
        commands.put("logout", new LogOutController());
        commands.put("menu", new MainController());                                     // get

        commands.put("customer/basket", new BasketController());                        // get
        commands.put("customer/basket/add", new BasketAddController());                 // post
        commands.put("customer/basket/delete", new BasketDeleteController());           // delete
        commands.put("customer/basket/delete_all", new BasketDeleteAllController());    // delete

        commands.put("customer/orders", new OrdersController());                        // get
        commands.put("customer/orders/create", new OrdersCreateController());           // post
        commands.put("customer/orders/payment", new OrdersPaymentController());         // get
        commands.put("customer/orders/payment/pay", new OrdersPaymentPayController());  // post

        commands.put("manager/orders/confirm", new OrdersConfirmController());          // post
        commands.put("manager/orders_manager", new OrdersManagerController());          // get
        commands.put("manager/dishes_manager", new DishesManagerController());          // get


        commands.put("manager/dish/create", new DishCreateController());                // get
        commands.put("manager/dish/create/do", new DishCreateDoController());           // post
        commands.put("manager/dish/update", new DishUpdateController());                // get
        commands.put("manager/dish/update/do", new DishUpdateDoController());           // post
        commands.put("manager/dish/delete", new DishDeleteController());                // delete

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        logger.info(path);
        path = path.replaceAll(".*/app/" , ""); // .*/app/((manager|customer)/)?
        logger.info(path);

        Command command = commands.getOrDefault(path, (r)->"/index.jsp");
        String page = command.execute(request);

        logger.info(command.getClass().getName());
        logger.info(request.getSession().getServletContext().getAttribute("loggedUsers"));

        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", "app"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}