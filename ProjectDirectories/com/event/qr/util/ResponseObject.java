package com.event.qr.util;

public class ResponseObject<T> {

private  int responseCode;
private  String responseDesc;
T responseData;

public String getResponseDesc() {
return responseDesc;
}

public  ResponseObject<T> setResponseDesc(String responseDesc) {
this.responseDesc = responseDesc;
return this;
}

public int getResponseCode() {
return responseCode;
}

public  ResponseObject<T> setResponseCode(int responseCode) {
this.responseCode = responseCode;
return this;
}


public T getResponseData() {
return responseData;
}

public void setResponseData(T responseData) {
this.responseData = responseData;
}

}
