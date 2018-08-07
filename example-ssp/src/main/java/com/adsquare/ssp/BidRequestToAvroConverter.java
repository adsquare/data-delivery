/*
 * Copyright (c) 2017 adsquare GmbH
 * Fritz Richter <fritz@adsquare.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.adsquare.ssp;

import java.util.Date;
import java.util.Optional;

import com.adsquare.delivery.events.ConnectionType;
import com.adsquare.delivery.events.DataEvent;
import com.adsquare.delivery.events.DataEvent.Builder;
import com.adsquare.delivery.io.EventUtils;
import com.google.openrtb.OpenRtb.BidRequest;
import com.google.openrtb.OpenRtb.LocationType;

/**
 * Supporting class, which extracts relevant fields from a sample
 * {@link BidRequest} and propagates the data into the {@link DataEvent} object.
 * In this example, the google open-source library
 * https://github.com/google/openrtb is being used as an example to read in
 * bidrequests represented as JSON. Of course the source could be any other
 * format e.g. logfiles as well.
 * 
 * @author Fritz Richter
 *
 */
public class BidRequestToAvroConverter {

	/**
	 * 
	 * @param request
	 *            the input bid request
	 * @return optional a {@link DataEvent} if the validation was successfull
	 *         (all relevant paremters being set)
	 */
	public Optional<DataEvent> convertJSON(BidRequest request) {

		if (request == null) {
			System.err.println("could not parse bidrequest, is null");
			return Optional.empty();
		}

		if (request.getDevice() == null) {
			// no device information (no geo, no ifa), skip processing
			System.err.println("no device information (no geo, no ifa), skip processing");
			return Optional.empty();
		}

		/*
		 * extract relevant fields from the BidRequest and fill the DataEvent
		 * properties
		 */
		final Builder builder = DataEvent.newBuilder();
		builder.setAdId(request.getDevice().getIfa());
		builder.setAdOptOut(request.getDevice().getDnt());
		builder.setAppBundle(request.getApp().getBundle());
		builder.setCarrierName(request.getDevice().getCarrier());
		builder.setConnectionType(convertConnectionType(request.getDevice().getConnectiontype()));
		builder.setDeviceManufacturer(request.getDevice().getMake());
		builder.setDeviceModel(request.getDevice().getModel());
		builder.setHorizontalAccuracy((double) request.getDevice().getGeo().getAccuracy());
		builder.setIdType(parseIDType(request));
		builder.setIp(request.getDevice().getIp());
		builder.setLocationMethod(convertLocationType(request.getDevice().getGeo().getType()));
		builder.setOs(request.getDevice().getOs());
		builder.setPublisherName(request.getSite().getPublisher().getName());
		builder.setUrl(request.getSite().getPage());
		builder.setUtcTimestamp(new Date().getTime());
		builder.setLatitude(request.getDevice().getGeo().getLat());
		builder.setLongitude(request.getDevice().getGeo().getLon());

		// normalize and check the event
		return EventUtils.normalizeAndCheckDataEvent(builder.build());
	}

	/**
	 * Tries to extract the ID Type (idfa or aaid) by applying a very basic
	 * check of the device brand ('make'). This method is just a simple example,
	 * there are better algorithms to determine the device type
	 * 
	 * @param request
	 *            the input bidrequest
	 * @return the IDType or null
	 */
	private static String parseIDType(BidRequest request) {
		/*
		 * please note, this method is just a simple example, there are better
		 * algorithms to determine the device type
		 */
		boolean apple = request.getDevice().getMake().trim().toLowerCase().contains("apple");
		boolean maidPresent = request.getDevice().getIfa() != null;
		return maidPresent ? (apple ? "idfa" : "aaid") : null;
	}

	/**
	 * Converts the {@link com.google.openrtb.OpenRtb.ConnectionType} of the bid
	 * request to the {@link ConnectionType}, which is required by the
	 * {@link DataEvent} class.
	 * 
	 * @param connectionType
	 *            the input from the bid request
	 * @return the equivalent {@link ConnectionType} or null
	 */
	private static ConnectionType convertConnectionType(com.google.openrtb.OpenRtb.ConnectionType connectionType) {
		if (connectionType == null) {
			return null;
		}
		switch (connectionType) {
		case CELL_2G:
		case CELL_3G:
		case CELL_4G:
		case CELL_UNKNOWN:
			return ConnectionType.cellular;
		case ETHERNET:
		case WIFI:
			return ConnectionType.wifi;
		default:
			return null;
		}

	}

	/**
	 * Converts the {@link LocationType} of the bid request to the
	 * LocationMethod as String, which is required by the {@link DataEvent} class.
	 * 
	 * @param locationType
	 *            the input from the bid request
	 * @return the equivalent LocationMethod as String or null
	 */
	private static String convertLocationType(LocationType locationType) {
		if (locationType == null) {
			return null;
		}
		switch (locationType) {
		case GPS_LOCATION:
			return "gps";
		case IP:
			return "ip";
		case USER_PROVIDED:
			return "user";
		default:
			return null;
		}
	}
}