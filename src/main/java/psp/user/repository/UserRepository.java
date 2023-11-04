package psp.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import psp.user.model.User;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {

    public List<User> findByEmail(String email);
    public List<User> findByUsername(String email);
    public List<User> findByPhone(String email);

}