package com.shankar.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shankar.demo.model.Tutorial;
import com.shankar.demo.repository.TutorialRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired(required = true)
    TutorialRepository repo;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        List<Tutorial> ans = new ArrayList<>();
        if(title!=null){
            ans = repo.findByTitleContaining(title);
        } else {
            ans = repo.findAll();
        }

        return new ResponseEntity<>(ans,HttpStatus.OK);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {

        Optional<Tutorial> ans =  repo.findById(id);
        return new ResponseEntity<Tutorial>(ans.get(),HttpStatus.OK);

    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        Tutorial ans = repo.save(tutorial);
        return new ResponseEntity<>(ans, HttpStatus.CREATED);
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        tutorial.setId(id);
        repo.save(tutorial);
        return new ResponseEntity<>(tutorial, HttpStatus.OK);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        repo.deleteAll();
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        List<Tutorial> ans = repo.findByPublished(true);
        return new ResponseEntity<>(ans,HttpStatus.OK);

    }
}