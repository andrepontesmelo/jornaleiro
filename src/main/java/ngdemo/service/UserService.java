package ngdemo.service;

import ngdemo.dto.PesquisaPessoa;

public class UserService {

    public PesquisaPessoa getDefaultUser() {
            return new PesquisaPessoa();
    }
}
