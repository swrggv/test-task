package com.example.demo.services;

import com.example.demo.models.Person;
import com.example.demo.reposotories.PersonRepository;
import com.ibm.icu.text.Transliterator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final WebClient webClient;
    private static final String URI = "https://api.agify.io/?name=";
    private static final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

    @Override
    @Transactional
    public Person findPersonAge(String name) {
        if (!isCorrectName(name)) {
            throw new IllegalArgumentException("Допустимы только символы кириллицы");
        }
        if (!personRepository.existsByName(name)) {
            String transliteratedName = transliterateName(name);
            Person person = getRequestToApi(transliteratedName);
            person.setName(name);
            personRepository.save(person);
            return person;
        }
        personRepository.increaseViews(name);
        return personRepository.findByName(name);
    }

    private boolean isCorrectName(String name) {
        return name.matches("[а-яёА-ЯЁ]+");
    }

    private String transliterateName(String name) {
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        return toLatinTrans.transliterate(name);
    }

    private Person getRequestToApi(String name) {
        return webClient.get()
                .uri(URI + name)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

    @Override
    @Transactional
    public void addPeopleFromFile() {
        List<Person> peopleToSave = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : readFile().entrySet()) {
            Person person = new Person(entry.getKey(), entry.getValue());
            peopleToSave.add(person);
        }
        personRepository.saveAll(peopleToSave);
    }

    @Override
    public Person getOldestName() {
        return personRepository.findOldestPerson();
    }

    @Override
    public Person getStatistic(String name) {
        return personRepository.findByName(name);
    }

    private Map<String, Integer> readFile() {
        Map<String, Integer> data;
        try (BufferedReader reader = new BufferedReader(new FileReader("name", StandardCharsets.UTF_8))) {
            data = reader.lines().map(x -> x.split("_"))
                    .collect(Collectors.toMap(x -> x[0], x -> Integer.parseInt(x[1])));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
