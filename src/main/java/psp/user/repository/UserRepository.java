package psp.user.repository;

import org.springframework.data.repository.CrudRepository;
import psp.user.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    public List<User> findByEmail(String email);
    public List<User> findByUsername(String email);
    public List<User> findByPhone(String email);

}