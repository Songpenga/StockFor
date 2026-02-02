package hello.core2.Service;

import ch.qos.logback.core.joran.conditional.ElseAction;
import hello.core2.DTO.InterviewDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SpringTestInterviewService {

    public List<InterviewDto> crawlSpringBasicData() {
        List<InterviewDto> resultList = new ArrayList<>();

        try {
            //1.URL List
            //List<String> urllist = Arrays.asList()
//            List<CrawlTarget> targets = List.of(
//                    new CrawlContent("https://chung-develop.tistory.com/145#google_vignette",
//                                    ".contents_style","춍춍블로그_tistory")
//            );
//
                        Document doc = Jsoup.connect("https://rongscodinghistory.tistory.com/category/%EA%B0%9C%EB%B0%9C/%EC%9B%B9%EA%B0%9C%EB%B0%9C%20%EB%A9%B4%EC%A0%91%EC%A7%88%EB%AC%B8").get();
            //System.out.println(doc);
            Elements elements = doc.select(".contents_style");//게시글 리스트 선택

            for (Element el : elements) {
                String rawTitle = el.select("p").text();
                String link = el.select("a").attr("href");
                String summary = el.select(".summary").text();

                //1.가공예시 : 제목 앞뒤 공백 제거 및 dto 담기

                System.out.println(rawTitle);
                System.out.println(link);
                System.out.println(summary);

                InterviewDto dto = InterviewDto.builder()
                        .title(rawTitle.trim())
                        .url(link)
                        .content(summary)
                        .build();

                resultList.add(dto);



            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return resultList;
    }
}
