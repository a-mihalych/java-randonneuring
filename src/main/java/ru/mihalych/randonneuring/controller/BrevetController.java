package ru.mihalych.randonneuring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mihalych.randonneuring.model.Brevet;
import ru.mihalych.randonneuring.service.BrevetService;

import java.util.List;

@RestController
@RequestMapping("/brevets")
public class BrevetController {

    private final BrevetService brevetService;

    @Autowired
    public BrevetController(BrevetService brevetService) {
        this.brevetService = brevetService;
    }

    @GetMapping
    public List<Brevet> brevets() {
        return brevetService.brevets();
    }

    @GetMapping("/{id}")
    public Brevet brevet(@PathVariable Integer id) {
        return brevetService.brevet(id);
    }

    @PostMapping
    public Brevet createBrevet(@RequestBody Brevet brevet) {
        return brevetService.createBrevet(brevet);
    }

    @PutMapping
    public Brevet caveBrevet(@RequestBody Brevet brevet) {
        return brevetService.caveBrevet(brevet);
    }
}
