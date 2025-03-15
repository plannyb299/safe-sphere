package com.pada.safe_sphere.controller;

import com.pada.safe_sphere.service.CounsellingServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class CounsellingController {

    private final CounsellingServices counsellingServices;

    @PostMapping("/process/{userId}")
    public String processChat(@PathVariable String userId, @RequestBody String input) {
        return counsellingServices.processChat(userId, input);
    }
}