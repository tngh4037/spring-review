package hello.core.itemservice.web.validation;

import hello.core.itemservice.domain.item.Item;
import hello.core.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    // @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        // 검증 로직 (필드 검증)
        if (!StringUtils.hasText(item.getItemName())) {
            // 스프링은 필드 단위의 오류에 대해서 FieldError 를 제공한다. ( 따라서 필드에 오류가 있으면 FieldError 객체를 생성해서 bindingResult 에 담아두면 된다.)
            // "item": @ModelAttribute 로 선언된 객체의 이름
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1_000_000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("quantity", "price", "수량은 최대 9,999까지 허용합니다."));
        }

        // 특정 필드가 아닌 복합 룰 검증 (글로벌 검증)
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10_000) {
                // 스프링은 글로벌 단위의 오류에 대해서 ObjectError 를 제공한다.
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000이상 이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로 이동
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            // model.addAttribute("errors", bindingResult); // bindingResult 는 자동으로 뷰에 같이 넘어간다. model에 안담아도 됨. (스프링이 자동으로 처리함)
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        // 검증 로직 (필드 검증)
        if (!StringUtils.hasText(item.getItemName())) {
            // 스프링은 필드 단위의 오류에 대해서 FieldError 를 제공한다. ( 따라서 필드에 오류가 있으면 FieldError 객체를 생성해서 bindingResult 에 담아두면 된다.)
            // "item": @ModelAttribute 로 선언된 객체의 이름

            // bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다."));
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수입니다.")); // codes, arguments: defaultMessage 를 메시지화 해서 대체할 수 있는 방법
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1_000_000) {
            // bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            // bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999까지 허용합니다."));
        }

        // 특정 필드가 아닌 복합 룰 검증 (글로벌 검증)
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10_000) {
                // 스프링은 글로벌 단위의 오류에 대해서 ObjectError 를 제공한다.
                // bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000이상 이어야 합니다. 현재 값 = " + resultPrice));
                bindingResult.addError(new ObjectError("item", null, null, "가격 * 수량의 합은 10,000이상 이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로 이동
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            // model.addAttribute("errors", bindingResult); // bindingResult 는 자동으로 뷰에 같이 넘어간다. model에 안담아도 됨. (스프링이 자동으로 처리함)
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

// Failed to convert property value of type java.lang.String to required type java.lang.Integer for property price; For input string: "qqq"
// 불친절하다.
// 이런 것들을 포함해서 오류메시지를 어떻게 관리할지, 메시지를 관리하는 방법에 대해서도 편리한 매커니즘을 제공한다.

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

