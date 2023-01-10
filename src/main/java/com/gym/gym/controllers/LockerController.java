package com.gym.gym.controllers;

import com.gym.gym.dto.LockerDto;
import com.gym.gym.service.LockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("locker")
@RestController
@RequiredArgsConstructor
public class LockerController {


    private final LockerService lockerService;


    @GetMapping
    public List<LockerDto> getAllLockers() {
        return lockerService.getAllLockers();
    }

    @PostMapping
    public LockerDto save(@RequestBody LockerDto lockerDto) {
        return lockerService.save(lockerDto);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteLocker(@PathVariable Long id) {
        lockerService.delete(id);
    }


}
