package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){ //jpa저장 전 까지 id값이 없음 -> 새로 생성하는 객체
            em.persist(item);
        }else{
            em.merge(item); //merge는 준영속 상태 엔티티를 다시 영속 상태 엔티티로 만들어 준다.
                            //1차캐시, 혹은 없으면 db에서 찾아와서 영속 엔티티의 값을 param 으로 바꿔치기함.
                            //
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}
