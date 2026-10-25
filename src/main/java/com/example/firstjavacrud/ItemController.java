package com.example.firstjavacrud;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final Map<Long, Item> items = new LinkedHashMap<>();
    private long nextId = 1;

    public ItemController() {
        items.put(nextId, new Item(nextId++, "Java"));
        items.put(nextId, new Item(nextId++, "GitHub"));
    }

    // READ: Get all items
    @GetMapping
    public Collection<Item> getAllItems() {
        return items.values();
    }

    // READ: Get one item
    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        Item item = items.get(id);

        if (item == null) {
            throw new NoSuchElementException("Item not found");
        }

        return item;
    }

    // CREATE: Add an item
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        Item newItem = new Item(nextId++, item.getName());
        items.put(newItem.getId(), newItem);
        return newItem;
    }

    // UPDATE: Change an item
    @PutMapping("/{id}")
    public Item updateItem(
            @PathVariable Long id,
            @RequestBody Item updatedItem) {

        if (!items.containsKey(id)) {
            throw new NoSuchElementException("Item not found");
        }

        Item item = new Item(id, updatedItem.getName());
        items.put(id, item);
        return item;
    }

    // DELETE: Remove an item
    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        if (items.remove(id) == null) {
            throw new NoSuchElementException("Item not found");
        }

        return "Item deleted successfully";
    }
}