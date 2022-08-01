package kr.co.tbell.labeling.solutionbackend.user.repository;

import kr.co.tbell.labeling.solutionbackend.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query(
            value = "SELECT * FROM user WHERE user_name LIKE %:name% AND user_email LIKE %:email% AND user_state LIKE %:state%",
            countQuery = "SELECT COUNT(user_idx) FROM user WHERE user_name LIKE %:name% AND user_email LIKE %:email% AND user_state LIKE %:state%",
            nativeQuery = true
    )
    Page<Users> findAllSearch(@Param("name") String name, @Param("email") String email, @Param("state") String state, Pageable pageable);
}
