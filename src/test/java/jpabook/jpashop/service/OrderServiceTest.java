package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문 () throws Exception{
        //given
        Member member = createMember();

        Book book = createBook("시골 jpa",10000,10);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(),orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

    }

    private Book createBook(String name,int orderPrice,int orderQuantity ) {
        Book book = new Book();
        book.setName(name);;
        book.setPrice(orderPrice);
        book.setStockQuantity(orderQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가","123-123"));
        em.persist(member);
        return member;
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);

        int orderCount = 11;
        //when
        orderService.order(member.getId(), item.getId(), orderCount);
        //then
        fail("재고 수량 부족 에외 발생!");
    }


    @Test
    public void 주문취소 () throws Exception{
        //given
        Member member = createMember();
        Book item = createBook("시골 jpa",10000,10);

        int orderCount =2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.CancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL이다", OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 함",10,item.getStockQuantity());
    }

    @Test
    public void 재고수량초과() throws Exception{
        //given

        //when

        //then
    }
}