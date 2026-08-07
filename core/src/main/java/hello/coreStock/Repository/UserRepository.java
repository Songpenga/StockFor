package hello.coreStock.Repository;

import hello.coreStock.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 엔티티의 @Id는 no(Long)라 상속받은 findById(Long)와 이름이 겹치지 않도록
    // 로그인 아이디(id 컬럼) 조회는 별도 이름으로 분리
    @Query("SELECT u FROM User u WHERE u.id = :loginId")
    Optional<User> findByLoginId(@Param("loginId") String loginId);
}
