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


import com.event.qr.service.EventService;
import com.event.qr.model.Event;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;



@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
@RequestMapping("/event")
public class EventController{ 

@Autowired
EventService eventservice;

@PostMapping("/add")
public ResponseObject<Event> saveEvent(@RequestBody Event event){

return eventservice.saveEvent(event);
}

@GetMapping("/getbyid")
public ResponseObject<Event> getEventById(@RequestParam(value="id",required=true) int id){

return eventservice.getEventById(id);


}

@GetMapping("/listall")
public ResponseList<Event> getEventListAll(){ 

return eventservice.getAllEvents();
}

@PutMapping("/update")
public ResponseObject<Event>  updateEvent(@RequestBody Event event){ 

return eventservice.updateEvent(event);
}

@DeleteMapping("/delete")
public ResponseObject<Event> deleteEventById(@RequestParam(value="id",required=true) int id){

return eventservice.deleteEventById(id);

}

}