/*
 * Copyright (c) 2017 adsquare GmbH
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

package com.adsquare.delivery.io;

import java.util.Optional;

import com.adsquare.delivery.events.DataEvent;

/**
 * Utility class to work with {@link DataEvent}s which provides static methods
 * for validation and normalization validate and normalize
 */
public class EventUtils {

	/**
	 * @param event
	 * @return true if the event is valid, which means that it should at least
	 *         contain the adId (MAID) or a location
	 */
	public final static boolean isValidEvent(DataEvent event) {
		// event should not be null
		if (event == null) {
			return false;
		}

		boolean containsAdId = event.getAdId() != null && !event.getAdId().trim().isEmpty();
		boolean containsLocation = event.getLatitude() != null && event.getLongitude() != null;

		// if an event does not contain an AdID nor a location, it's invalid
		if (!containsAdId && !containsLocation) {
			return false;
		}

		// otherwise it's a valid event
		return true;
	}

	/**
	 * normalize the given {@link DataEvent}, which trims and lowecases the AdId
	 * (MAID) for example
	 * 
	 * @param input
	 */
	public final static DataEvent normalizeDataEvent(final DataEvent input) {
		DataEvent retVal = DataEvent.newBuilder(input).build();
		retVal.setAdId(input.getAdId() != null ? input.getAdId().trim().toLowerCase() : null);
		retVal.setLongitude(input.getLongitude() != null && input.getLongitude().doubleValue() != 0.0d ? input.getLongitude() : null);
		retVal.setLatitude(input.getLatitude() != null && input.getLatitude().doubleValue() != 0.0d ? input.getLatitude() : null);
		return retVal;
	}

	/**
	 * check and normalize the given {@link DataEvent}, which trims and
	 * lowecases the AdId (MAID) for example
	 * 
	 * @param input
	 *            the source {@link DataEvent}
	 * @return an optional, which is filled with the normalized event or is
	 *         empty for non valid events
	 */
	public final static Optional<DataEvent> normalizeAndCheckDataEvent(final DataEvent input) {
		DataEvent normalizeDataEvent = normalizeDataEvent(input);
		if (isValidEvent(normalizeDataEvent)) {
			return Optional.of(normalizeDataEvent);
		}
		return Optional.empty();
	}
}
