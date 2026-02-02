package hello.core3.Service;

import hello.core3.Dto.TestDto;
import hello.core3.Entity.TestEntity;
import hello.core3.Repository.TestJdbcRepository;
import hello.core3.Repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//DTO를 Entity로 변환하여 저장
@Service
@RequiredArgsConstructor //생성자주입
public class TestService {
    private final TestRepository testRepository;

    @Transactional
    public void savePost(TestDto testDto){
        TestEntity testentity = new TestEntity(testDto.getTitle(), testDto.getContent());
        testRepository.save(testentity);
    }
}
