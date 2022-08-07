package sprint.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sprint.server.domain.Member;
import sprint.server.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional //기본적으로 트렌젝션 안에서 되어야함
    public Long join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Member findById(Long id){
        return memberRepository.findById(id).get();
    }

    public void isMemberExistById(Long sourceMemberId, String message) {
        if (!memberRepository.existsById(sourceMemberId)) {
            throw new IllegalArgumentException(message);
        }
    }
}
