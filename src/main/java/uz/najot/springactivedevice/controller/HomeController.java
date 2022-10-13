package uz.najot.springactivedevice.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String getHome(){
        return "Hello you are entered "+ SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
