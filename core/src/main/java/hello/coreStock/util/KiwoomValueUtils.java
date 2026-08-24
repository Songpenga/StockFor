package hello.coreStock.util;

public class KiwoomValueUtils {

    private KiwoomValueUtils() {
    }

    // 키움이 간헐적으로 거래량을 int32 -1(에러/데이터없음 센티널)이 unsigned로
    // 잘못 캐스팅된 2^32-1로 내려주는 경우가 있어, 이 값일 때만 "데이터 없음"으로 처리
    private static final long CORRUPT_VOLUME_SENTINEL = 4294967295L;

    public static Long parseVolume(String raw) {
        long value = Long.parseLong(raw);
        return value == CORRUPT_VOLUME_SENTINEL ? null : value;
    }
}
