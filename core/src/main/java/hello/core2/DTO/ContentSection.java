package hello.core2.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

// 2. 수집된 데이터 조각
@Getter
@AllArgsConstructor
@ToString
public class ContentSection {
    private String type;
    private String content;
}
