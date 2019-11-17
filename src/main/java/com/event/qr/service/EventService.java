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

		String status = validateEvent(event);

		if (!status.equals("OK")) {
			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc(status);
			responseObject.setResponseData(event);

			return responseObject;
		}

		if (eventdao.saveEvent(event)) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record has been saved successfully");
			responseObject.setResponseData(event);

			return responseObject;
		} else {

			event = null;

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Failed to save record");
			responseObject.setResponseData(event);

			return responseObject;
		}

	}

	public ResponseList<Event> getAllEvents() {

		ResponseList<Event> responseList = null;
		ArrayList<Event> eventList = null;

		eventList = eventdao.getAllEvents();

		if (eventList.isEmpty()) {

			responseList = new ResponseList<>();
			responseList.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseList.setResponseDesc("Failed to get all records");
			responseList.setList(eventList);
		} else {

			responseList = new ResponseList<>();
			responseList.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseList.setResponseDesc("All records fetched successfully");
			responseList.setList(eventList);
		}
		return responseList;
	}

	public ResponseObject<Event> getEventById(int id) {

		ResponseObject<Event> responseObject = new ResponseObject<>();
		Event event = null;

		event = eventdao.getEventById(id);

		if (event != null) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record found");
			responseObject.setResponseData(event);

			return responseObject;
		} else {

			event = null;

			responseObject.setResponseCode(ResponseCodes.NOT_FOUND.getResponseCode());
			responseObject.setResponseDesc("Record not found ");
			responseObject.setResponseData(event);

			return responseObject;
		}

	}

	public ResponseObject<Event> updateEvent(Event event) {

		ResponseObject<Event> responseObject = new ResponseObject<>();
		
		String status = validateUpdatedEvent(event);
		if(!status.equals("OK")) {
			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc(status);
			responseObject.setResponseData(event);

			return responseObject;
		}
		
		if (eventdao.updateEvent(event)) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record updated successfully");
			responseObject.setResponseData(event);

			return responseObject;

		} else {

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Failed to update record");
			responseObject.setResponseData(event);

			return responseObject;

		}
	}

	public ResponseObject<Event> deleteEventById(int id) {

		ResponseObject<Event> responseObject = new ResponseObject<>();

		if (eventdao.deleteEventById(id)) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record deleted successfully");
			responseObject.setResponseData(null);

			return responseObject;

		} else {

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Failed to delete record");
			responseObject.setResponseData(null);

			return responseObject;

		}

	}

	private String validateEvent(Event event) {
		String status = "OK";

		if (event.getEventName() == null || event.getEventName().trim().isEmpty()) {
			return status = "Please provide event name.";
		}
		if(eventdao.getEventByEventName(event.getEventName().trim())!=null) {
			return status = "Event with the same name already present.";
		}
		if (event.getEventDesc() == null || event.getEventDesc().trim().isEmpty()) {
			return status = "Please provide event description.";
		}
		if (event.getEventStartTime() == null) {
			return status = "Please provide event start time.";
		}
		if (event.getEventEndTime() == null) {
			return status = "Please provide event end time.";
		}
		if(event.getEventEndTime().before(event.getEventStartTime())) {
			return status = "End time cannot be before start time.";
		}
		if(event.getEventStartTime().after(event.getEventEndTime())) {
			return status = "Start time cannot be after end time.";
		}
		int count = (eventdao.getEventCountBetweenTime(event.getEventStartTime(), event.getEventEndTime()));
		if(count == -1) {
			return status = "Problem checking event between time.";
		}else if(count > 0) {
			return status = "An event is already scheduled between this time span, "
					+ "please choose other time span.";
		}
		return status;
	}
	
	private String validateUpdatedEvent(Event event) {
		
		if(event.getId() == 0) {
			return "Please provide eventId"; 
		}else {
			return validateEvent(event);
		}
		
	}

}