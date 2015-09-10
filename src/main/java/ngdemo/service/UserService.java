package ngdemo.service;

import ngdemo.domain.User;

public class UserService {

    public User getDefaultUser() {
        User user = new User();
        user.setFirstName("André Melo");
        user.setLastName("");
        return user;
    }
}
