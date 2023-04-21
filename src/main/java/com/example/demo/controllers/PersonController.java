package com.example.demo.controllers;

import com.example.demo.models.Person;
import com.example.demo.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/app")
@AllArgsConstructor
public class PersonController {
    private PersonService personService;

    @GetMapping
    public String getWelcomePage() {
        return "app";
    }

    @PostMapping("age")
    public String getAge(@RequestParam String name, Model model) {
        Person person = personService.findPersonAge(name);
        model.addAttribute("person", person);
        model.addAttribute("name", name);
        return "age";
    }

    @PostMapping("frequency")
    public String getFrequency() {
        return "frequency";
    }

    @GetMapping("oldest")
    public String getOldest() {
        return "oldest";
    }
}
