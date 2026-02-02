package hello.core3.Repository;

import hello.core3.Entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JPA 버전
@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
