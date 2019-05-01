package com.viathink.core.util;

public class EventHandler<T> {
    public void perfectDataAtTheBeginning(T messageDetail){}
    public T perfectDataWhenOldDetailIsNull(T messageDetail){return messageDetail;}
    public T perfectDataInDatelyDetail(T tempDetail, T mergeDetail){return tempDetail;}
}
