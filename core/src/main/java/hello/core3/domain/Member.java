package hello.core3.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/*
- @Entit`: JPA 엔티티임을 나타냄
- @Table(name = "members"): 테이블명 지정
- @Id, `@GeneratedValue`: 기본키 자동 생성
- @Column: 컬럼 제약조건 설정 (nullable, unique, length)
- @PrePersist: 저장 전에 실행되는 메서드 (가입일시 자동 설정)
*/
@Entity
@Table(name="members")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String pwd;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(name = "regi_dtm")
    private LocalDateTime localDateTime;

    @PrePersist
    public void prePersist(){
        localDateTime = LocalDateTime.now();
    }
}
