package psp.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import psp.user.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {

    public User findByEmail(String email);

    public User findByUsername(String username);

    public User findByPhone(String phone);

}