package hello.coreStock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_log")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "access_ip", nullable = false, length = 45)
    private String accessIp;

    @Column(name = "access_time", nullable = false)
    private LocalDateTime accessTime;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;
}
