package com.example.StyleSphere.api.models;

import javax.xml.crypto.Data;

public class DataChange<T> {
    private T data;
    private ChangeType ChangeType;

    public DataChange(){}

    public DataChange( DataChange.ChangeType changeType,T data) {
        this.data = data;
        ChangeType = changeType;
    }

    public enum ChangeType{
        INSERT,
        UPDATE,
        DELETE,
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DataChange.ChangeType getChangeType() {
        return ChangeType;
    }

    public void setChangeType(DataChange.ChangeType changeType) {
        ChangeType = changeType;
    }
}
