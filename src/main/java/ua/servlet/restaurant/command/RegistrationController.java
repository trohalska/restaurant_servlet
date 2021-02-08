package ua.servlet.restaurant.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.servlet.restaurant.model.Logins;
import ua.servlet.restaurant.service.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RegistrationController implements Command {
    private RegistrationService registrationService;
    public RegistrationController() {
        this.registrationService = new RegistrationService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");

        try {
//            String json = request.getParameter("data");
            String json = getBody(request);
//            System.out.println(json);
            Logins logins = new ObjectMapper().readValue(json, Logins.class);
            System.out.println(logins.toString());
            registrationService.registrationCustomer(logins);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
//        System.out.println(login + " " + pass + " " + email);
//        registrationService.registrationCustomer(Logins.builder()
//                .setLogin(login)
//                .setPassword(pass)
//                .setEmail(email)
//                .build());



        return "/registration.jsp";
    }

    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}
