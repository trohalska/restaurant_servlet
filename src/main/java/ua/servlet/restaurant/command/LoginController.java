package ua.servlet.restaurant.command;

import javax.servlet.http.HttpServletRequest;

public class LoginController implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        System.out.println(name + " " + pass);


        if ( name == null || name.equals("")
                || pass == null || pass.equals("") ){
            return "/login.jsp";
        }
        // TODO go to Service
        return "/login.jsp";
    }
}
