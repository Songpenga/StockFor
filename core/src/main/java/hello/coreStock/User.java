package hello.coreStock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false, unique = true, length = 50)
    private String id; // 로그인 아이디

    @Column(nullable = false)
    private String pw; // 로그인 비밀번호

    @Column(name = "pinpw")
    private String pinpw; // 매수/매도 실행용 PIN 비밀번호
}
