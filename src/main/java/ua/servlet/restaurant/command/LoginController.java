package ua.servlet.restaurant.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LoginController implements Command {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        System.out.println(name + " " + pass);

        logger.info(name + " " + pass);


        if ( name == null || name.equals("")
                || pass == null || pass.equals("") ){
            return "/login.jsp";
        }
        // TODO go to Service
        return "/login.jsp";
    }

//    private TeacherService teacherService ;
//
//    public LoginTeacherCommand(TeacherService teacherService) {
//        this.teacherService = teacherService;
//    }
//
//    @Override
//    public String execute(HttpServletRequest request) {
//        String name = request.getParameter("name");
//        String pass = request.getParameter("pass");
//        if( name == null || name.equals("") || pass == null || pass.equals("")  ){
//            return "/login.jsp";
//        }
//        Optional<Teacher> teacher = teacherService.login(name);
//        if( teacher.isPresent() && teacher.get().getPassHash()
//                == pass.hashCode()){
//            request.getSession().setAttribute("teacher" , teacher.get());
//            logger.info("Teacher "+ name+" logged successfully.");
//            return "/WEB-INF/studentlist.jsp";
//
//        }
//        logger.info("Invalid attempt of login user:'"+ name+"'");
//        return "/login.jsp";
//    }

}
