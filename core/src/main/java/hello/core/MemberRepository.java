package hello.core;

public interface MemberRepository {

    void save(Member member);
    Member findbyId(long memberID);
}
