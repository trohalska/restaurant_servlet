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

        commands.put("logout", new LogOutController());
        commands.put("login", new LoginController());
        commands.put("registration", new RegistrationController());

        commands.put("menu", new MainController());                             // get

        commands.put("basket", new BasketController());                         // get
        commands.put("basket/add", new BasketAddController());                  // post
        commands.put("basket/delete", new BasketDeleteController());            // delete
        commands.put("basket/delete_all", new BasketDeleteAllController());     // delete

        commands.put("orders", new OrdersController());                         // get
        commands.put("orders/create", new OrdersCreateController());            // post
        commands.put("orders/payment", new OrdersPaymentController());          // get
        commands.put("orders/payment/pay", new OrdersPaymentPayController());   // put
        commands.put("orders/confirm", new OrdersConfirmController());          // put

        commands.put("orders_manager", new OrdersManagerController());          // get

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        logger.info(path);
        path = path.replaceAll(".*/app/((manager|customer)/)?" , "");
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