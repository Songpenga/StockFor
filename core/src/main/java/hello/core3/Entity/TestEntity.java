package hello.core3.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//DB 테이블과 매핑되는 객체(JPA Entity)
@Entity
public class TestEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String title;
    private String content;

    public TestEntity(String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}