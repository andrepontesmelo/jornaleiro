package ngdemo.service;

import ngdemo.dto.MercadoriaResource;
import ngdemo.dto.PessoaResource;

public class UserService {
    public PessoaResource getPessoaResource() {
            return new PessoaResource();
    }
    public MercadoriaResource getMercadoriaResource() { return new MercadoriaResource(); }
}
