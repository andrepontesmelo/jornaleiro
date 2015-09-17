package ngdemo.repositories.impl.mock;

import ngdemo.domain.User;
import ngdemo.repositories.contract.DummyRepository;

public class DummyMockRepositoryImpl extends GenericMockRepository<User> implements DummyRepository {

    @Override
    public User getDefaultUser() {
        User user = new User();
        user.atribuiNome("JonFromREST");
        user.setLastName("DoeFromREST");
        return user;
    }
}
