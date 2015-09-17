package ngdemo.service;

import ngdemo.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class UserService {

    public User getDefaultUser() {
            return new User();
    }
}
