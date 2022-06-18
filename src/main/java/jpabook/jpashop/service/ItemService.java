package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service //서비스는 지금 리포지토리에 위임만 하는 클래스. 과연 필요할지는 생각을 해 보자!
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional //readonly=true면 저장 안되는데, 각자 트랜잭셔널 해줘야됨! 트랜잭셔널 디폴트는 readonly=false
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }


    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
