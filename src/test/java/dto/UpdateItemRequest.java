package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UpdateItemRequest {
    private List<Item> items;

    @Data
    @AllArgsConstructor
    @Builder
    public static class Item {
        private String skuId;
        private int quantity;
        private String itemId;
    }
}
