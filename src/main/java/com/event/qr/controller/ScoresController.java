package com.event.qr.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.event.qr.util.ResponseObject;
import com.event.qr.util.ResponseList;

import com.event.qr.service.ScoresService;
import com.event.qr.model.Scores;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;



@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
@RequestMapping("/scores")
public class ScoresController{ 

@Autowired
ScoresService scoresservice;

@PostMapping("/add")
public ResponseObject<Scores> saveScores(@RequestBody Scores scores){

return scoresservice.saveScores(scores);
}

@GetMapping("/getbyid")
public ResponseObject<Scores> getScoresById(@RequestParam(value="id",required=true) int id){

return scoresservice.getScoresById(id);


}

@GetMapping("/listall")
public ResponseList<Scores> getScoresListAll(){ 

return scoresservice.getAllScoress();
}

@PutMapping("/update")
public ResponseObject<Scores>  updateScores(@RequestBody Scores scores){ 

return scoresservice.updateScores(scores);
}

@DeleteMapping("/delete")
public ResponseObject<Scores> deleteScoresById(@RequestParam(value="id",required=true) int id){

return scoresservice.deleteScoresById(id);

}

}