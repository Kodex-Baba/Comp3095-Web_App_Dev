package ca.gbc.inventoryservice.controller;

import ca.gbc.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode,
                             @RequestParam Integer quantity) {

        return inventoryService.isInStock(skuCode, quantity);
        // http://Localhost:8085/api/inventory?skuCode=SKU001&quatity=10
    }
}
