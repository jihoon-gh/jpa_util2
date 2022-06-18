package jpabook.jpashop.service;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//이거 쓰면 public methods는 트랜잭션에 걸려 들어감
@RequiredArgsConstructor //final이 있는 친구들로 constructor 만들고, constructor가 하나면 @Autowired 자동주입
public class MemberService {

     //spring이 spring bean에 있는 repository 찾아서 등록해줌
    private final MemberRepository memberRepository;


    //회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member){
        List<Member> findMembers =  memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    //
}
