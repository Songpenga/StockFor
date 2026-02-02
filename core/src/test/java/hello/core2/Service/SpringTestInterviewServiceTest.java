package hello.core2.Service;

import hello.core2.DTO.InterviewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {SpringTestInterviewService.class})
class SpringTestInterviewServiceTest {

    @Autowired
    private SpringTestInterviewService springTestInterviewService;

    @Test
    void crawlZip_Test(){
        //1.crawl 실행
        List<InterviewDto> results = springTestInterviewService.crawlSpringBasicData();

        //2.결과실행
        if(results.isEmpty()){
            System.out.println("데이터가 비어있습니다. 선택자(.post-item)을 확인하세요.");
        }else{
            for(InterviewDto dto : results){
                System.out.println("=============");
                System.out.println("title:" + dto.getTitle());
                System.out.println("link:" + dto.getUrl());
            }
        }
    }
}