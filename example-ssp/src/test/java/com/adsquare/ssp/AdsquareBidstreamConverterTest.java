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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import org.apache.avro.file.DataFileStream;
import org.junit.Assert;
import org.junit.Test;

import com.adsquare.delivery.events.DataEvent;
import com.adsquare.delivery.io.DataEventDeserializer;
import com.adsquare.delivery.io.EventUtils;

import lombok.Cleanup;

public class AdsquareBidstreamConverterTest {

	@Test
	public void testConverter() throws URISyntaxException, IOException {
		final URL sampleFile = AdsquareBidstreamConverterTest.class.getResource("/sample_bid_requests.txt");
		final String avroFileName = new SampleSSPConverter().convertLogFile(sampleFile.toURI());

		@Cleanup
		DataFileStream<DataEvent> isStream = DataEventDeserializer.readFromAvroFile(avroFileName);
		Assert.assertNotNull(isStream);
		
		DataEvent event = DataEvent.newBuilder().build();
		Optional<DataEvent> normalizedAndCheckedDataEvent = EventUtils.normalizeAndCheckDataEvent(event);
		
	}
}