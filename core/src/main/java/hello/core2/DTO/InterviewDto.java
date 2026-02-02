package hello.core2.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class InterviewDto {

    private String title;
    private String url;
    private String content;
    private String source;
    private String date;
    private String tags;

}
