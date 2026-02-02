package hello.core.MemberInfo;

import hello.core.Member;

public interface MemberService{
    void join(Member member);
    Member findMember(Long memberId);
}
