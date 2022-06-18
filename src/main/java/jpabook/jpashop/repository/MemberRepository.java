package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

     //이거 있으면 em을 주입해 줌. spring data jpa는 Autowired를 em injection 주입해 줌줌
   private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }
    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
        //jpql은 entity 객체를 대상으로 쿼리를 날림
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name =: name", Member.class).
                setParameter("name",name).
                getResultList();
    }
}
