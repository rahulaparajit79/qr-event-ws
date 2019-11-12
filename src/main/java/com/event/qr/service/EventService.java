package com.event.qr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.event.qr.util.ResponseObject;
import com.event.qr.util.ResponseCodes;
import com.event.qr.util.ResponseList;
import com.event.qr.model.Event;
import com.event.qr.dao.EventDAO;

import java.util.ArrayList;

@Service
public class EventService { 

@Autowired
EventDAO eventdao;

public ResponseObject<Event> saveEvent(Event event) { 

 ResponseObject<Event> responseObject = new ResponseObject<>();
if(eventdao.saveEvent(event)){

responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
responseObject.setResponseDesc("Record has been saved successfully");
responseObject.setResponseData(event);

return responseObject;}else{

event = null;

responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
responseObject.setResponseDesc("Failed to save record");
responseObject.setResponseData(event);

return responseObject;}

}


public ResponseList<Event> getAllEvents() {

ResponseList<Event> responseList = null;ArrayList<Event> eventList =null;

eventList = eventdao.getAllEvents();

if(eventList.isEmpty()){

responseList = new ResponseList<>();
responseList.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
responseList.setResponseDesc("Failed to get all records");
responseList.setList(eventList);
}else{

responseList = new ResponseList<>();
responseList.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
responseList.setResponseDesc("All records fetched successfully");
responseList.setList(eventList);
}
return responseList;
}


public ResponseObject<Event> getEventById(int id) { 

 ResponseObject<Event> responseObject = new ResponseObject<>();Event event = null;

event = eventdao.getEventById(id);

if(event!= null){

responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
responseObject.setResponseDesc("Record found");
responseObject.setResponseData(event);

return responseObject;
}else{

event = null;

responseObject.setResponseCode(ResponseCodes.NOT_FOUND.getResponseCode());
responseObject.setResponseDesc("Record not found ");
responseObject.setResponseData(event);

return responseObject;}

}


public ResponseObject<Event> updateEvent(Event event) { 

 ResponseObject<Event> responseObject = new ResponseObject<>();if(eventdao.updateEvent(event)){

responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
responseObject.setResponseDesc("Record updated successfully");
responseObject.setResponseData(event);

return responseObject;

}else{

responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
responseObject.setResponseDesc("Failed to update record");
responseObject.setResponseData(event);

return responseObject;

}
}


public ResponseObject<Event> deleteEventById(int id) { 

 ResponseObject<Event> responseObject = new ResponseObject<>();

if(eventdao.deleteEventById(id)){

responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
responseObject.setResponseDesc("Record deleted successfully");
responseObject.setResponseData(null);

return responseObject;

}else{

responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
responseObject.setResponseDesc("Failed to delete record");
responseObject.setResponseData(null);

return responseObject;

}

}


}