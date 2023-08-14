package ru.goncharenko.deck.controller

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/local")
@ConditionalOnProperty(
    prefix = "local-controller",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = false,
)
class LocalController {
    @GetMapping("me")
    fun me(): Authentication? {
        return SecurityContextHolder.getContext().authentication
    }
}