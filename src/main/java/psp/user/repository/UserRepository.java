package psp.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import psp.user.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    @Query(
            value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM users u WHERE u.email = :email AND u.id <> :excludedUserId",
            nativeQuery = true)
    boolean existsByEmailAndNotExcludedUserId(@Param("email") String email, @Param("excludedUserId") Long excludedUserId);

    @Query(
            value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM users u WHERE u.username = :username AND u.id <> :excludedUserId",
            nativeQuery = true)
    boolean existsByUsernameAndNotExcludedUserId(@Param("username") String username, @Param("excludedUserId") Long excludedUserId);

    @Query(
            value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM users u WHERE u.phone = :phone AND u.id <> :excludedUserId",
            nativeQuery = true)
    boolean existsByPhoneAndNotExcludedUserId(@Param("phone") String phone, @Param("excludedUserId") Long excludedUserId);

}