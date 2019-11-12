package com.event.qr.util;

import java.util.List;

public class ResponseList<T> {

private  int responseCode;
private  String responseDesc;
List<T> list;

public String getResponseDesc() {
return responseDesc;
}

public  void setResponseDesc(String errorDesc) {
this.responseDesc = errorDesc;
}

public int getResponseCode() {
return responseCode;
}

public  void setResponseCode(int errorCode) {
this.responseCode = errorCode;
}


public List<T> getList() {
return list;
}

public void setList(List<T> dataList) {
this.list = dataList;
}

@Override
public String toString() {
return "ResponseList [responseCode=" + responseCode + ", responseDesc=" + responseDesc + ", list=" + list + "]";
}


}
