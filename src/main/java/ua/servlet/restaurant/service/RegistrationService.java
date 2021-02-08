package ua.servlet.restaurant.service;

import ua.servlet.restaurant.model.RoleType;
import ua.servlet.restaurant.repository.LoginsRepo;
import ua.servlet.restaurant.model.Logins;

import java.util.List;

public class RegistrationService {
    private final LoginsRepo loginsRepo;
    public RegistrationService() {
        loginsRepo = new LoginsRepo();
    }

    public void registrationCustomer(Logins user) {
        Logins reg = loginsRepo.insert(user, RoleType.ROLE_CUSTOMER).orElse(null);

        assert reg != null;
        System.err.println(reg.toString());
    }

    public void get() {
        System.err.println(loginsRepo.get("ww"));
    }

    public List<Logins> getAll() {
        return loginsRepo.getAll();
    }


}
