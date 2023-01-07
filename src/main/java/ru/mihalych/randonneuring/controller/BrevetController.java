package ru.mihalych.randonneuring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mihalych.randonneuring.model.Brevet;
import ru.mihalych.randonneuring.service.BrevetService;

import java.util.List;

@RestController
@RequestMapping("/brevets")
@RequiredArgsConstructor
public class BrevetController {

    private final BrevetService brevetService;

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
    public Brevet saveBrevet(@RequestBody Brevet brevet) {
        return brevetService.saveBrevet(brevet);
    }
}
