package hello.core3.Repository;

import hello.core3.Dto.TestDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public TestJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(TestDto dto){
        String testsql = "INSERT INTO test_table (title, content) VALUES (?, ?)";
        jdbcTemplate.update(testsql, dto.getTitle(), dto.getContent());
    }
}
