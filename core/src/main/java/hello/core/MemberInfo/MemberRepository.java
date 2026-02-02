package hello.core.MemberInfo;

import hello.core.Member;

public interface MemberRepository {

    void save(Member member);
    Member findbyId(long memberID);
}
