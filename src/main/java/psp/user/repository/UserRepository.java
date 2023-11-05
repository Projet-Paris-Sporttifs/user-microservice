package psp.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import psp.user.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {

    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String email);
    public Optional<User> findByPhone(String email);

}