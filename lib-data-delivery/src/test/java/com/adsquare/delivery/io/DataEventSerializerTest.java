package com.adsquare.delivery.io;

import java.io.IOException;

import org.junit.Test;

import com.adsquare.delivery.events.DataEvent;

import avro.shaded.com.google.common.collect.Maps;

public class DataEventSerializerTest {
    @Test
    public void testApiEventSerialization() throws IOException {
    	DataEvent e = new DataEvent();
    	e.setAdId("idfa-1");
    	e.setAdOptOut(false);
    	
    	final byte[] bytes = DataEventSerializer.toBytes(e);
    	final DataEvent e2 = DataEventDeserializer.fromBytes(bytes);

    	assert e2.equals(e);
    }

   

}
