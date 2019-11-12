package com.event.qr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.event.qr.util.ResponseObject;
import com.event.qr.util.ResponseCodes;
import com.event.qr.util.ResponseList;
import com.event.qr.model.User;
import com.event.qr.dao.UserDAO;

import java.util.ArrayList;

@Service
public class UserService { 

@Autowired
UserDAO userdao;

public ResponseObject<User> saveUser(User user) { 

 ResponseObject<User> responseObject = new ResponseObject<>();
if(userdao.saveUser(user)){

responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
responseObject.setResponseDesc("Record has been saved successfully");
responseObject.setResponseData(user);

return responseObject;}else{

user = null;

responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
responseObject.setResponseDesc("Failed to save record");
responseObject.setResponseData(user);

return responseObject;}

}


public ResponseList<User> getAllUsers() {

ResponseList<User> responseList = null;ArrayList<User> userList =null;

userList = userdao.getAllUsers();

if(userList.isEmpty()){

responseList = new ResponseList<>();
responseList.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
responseList.setResponseDesc("Failed to get all records");
responseList.setList(userList);
}else{

responseList = new ResponseList<>();
responseList.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
responseList.setResponseDesc("All records fetched successfully");
responseList.setList(userList);
}
return responseList;
}


public ResponseObject<User> getUserById(int id) { 

 ResponseObject<User> responseObject = new ResponseObject<>();User user = null;

user = userdao.getUserById(id);

if(user!= null){

responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
responseObject.setResponseDesc("Record found");
responseObject.setResponseData(user);

return responseObject;
}else{

user = null;

responseObject.setResponseCode(ResponseCodes.NOT_FOUND.getResponseCode());
responseObject.setResponseDesc("Record not found ");
responseObject.setResponseData(user);

return responseObject;}

}


public ResponseObject<User> updateUser(User user) { 

 ResponseObject<User> responseObject = new ResponseObject<>();if(userdao.updateUser(user)){

responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
responseObject.setResponseDesc("Record updated successfully");
responseObject.setResponseData(user);

return responseObject;

}else{

responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
responseObject.setResponseDesc("Failed to update record");
responseObject.setResponseData(user);

return responseObject;

}
}


public ResponseObject<User> deleteUserById(int id) { 

 ResponseObject<User> responseObject = new ResponseObject<>();

if(userdao.deleteUserById(id)){

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