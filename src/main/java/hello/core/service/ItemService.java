package hello.core.service;

import hello.core.domain.Item;
import hello.core.repository.ItemSearchCond;
import hello.core.repository.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

// 서비스 계층에 인터페이스를 도입하는 경우는 사실 그렇게 많지는 않다.
//  ㄴ 인터페이스를 도입한다는 것: 미래에 구현체를 바꿀 가능성이 있을 때 도입한다.
public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);
}
