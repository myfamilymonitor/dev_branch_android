package com.myfamilymonitor.dao;

import java.io.Serializable;

public class MessageDAO implements Serializable {
    public String PhoneNumber,
            Address,
            Message,
            ReadState, //"0" for have not read sms and "1" for have read sms
            Time,
            MessageType;
}
