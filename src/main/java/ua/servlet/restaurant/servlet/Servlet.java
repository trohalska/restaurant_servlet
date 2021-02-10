package ua.servlet.restaurant.servlet;

import ua.servlet.restaurant.command.*;
import ua.servlet.restaurant.command.ExceptionController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("logout", new LogOutController());
        commands.put("login", new LoginController());
        commands.put("registration", new RegistrationController());
        commands.put("exception" , new ExceptionController());

        commands.put("manager/getAll", new GetAllLogins());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        System.out.println(path);
        path = path.replaceAll(".*/app/" , "");  // ".*/app/((manager|customer)/)?"
        System.out.println(path);

//        response.getWriter().print("Hello from servlet");

        Command command = commands.getOrDefault(path,
                (r)->"/index.jsp");
        System.out.println(command.getClass().getName());

        String page = command.execute(request);

        System.out.println(request.getServletContext().getAttribute("loggedUsers"));

        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", "/restaurant"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}