package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddItemRequest {
    private List<Item> items;

    @Data
    @AllArgsConstructor
    @Builder
    public static class Item {
        private String skuId;
        private int quantity;
    }
}