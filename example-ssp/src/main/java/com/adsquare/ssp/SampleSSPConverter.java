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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import com.adsquare.delivery.events.DataEvent;
import com.adsquare.delivery.io.DataEventSerializer;
import com.google.openrtb.OpenRtb.BidRequest;
import com.google.openrtb.json.OpenRtbJsonFactory;
import com.google.openrtb.json.OpenRtbJsonReader;

/**
 * Sample converter for SSPs to understand the data preparation phase
 */
public class SampleSSPConverter {
	final BidRequestToAvroConverter converter = new BidRequestToAvroConverter();
	final OpenRtbJsonReader jsonReader = OpenRtbJsonFactory.create().newReader();

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// check arguments
		if (args == null || args.length != 0) {
			System.err.println("please specifiy exaclty one parameter with the path of a file, containing json bidrequests per line");
			System.exit(1);
		}
		new SampleSSPConverter().convertLogFile(new File(args[0]).toURI());
	}

	public String convertLogFile(URI logFile) throws IOException {
		final Function<String, Optional<DataEvent>> convert = (json) -> {
			BidRequest request = null;
			try {
				request = jsonReader.readBidRequest(json);
			} catch (IOException e) {
				return Optional.empty();
			}
			return converter.convertJSON(request);
		};

		// iterate over all logfile lines
		try (Stream<String> stream = Files.lines(Paths.get(logFile))) {
			// and convert into a list of (validated and normalized) avro events
			final Stream<DataEvent> eventStream = stream.map(convert).filter(optional -> optional.isPresent()).map(optional -> optional.get());
			// persist all events into avro container file
			File export = new File(logFile.getPath() + ".avro");
			DataEventSerializer.writeToFile(eventStream, export);
			/*
			 * TODO for the SSP: upload this avro file to your AWS S3 bucket,
			 * which adsquare can access: filename should be like:
			 * 
			 * "s3://<your_s3_bucket>/somepath/2017-01-01_bidstream_us.avro"
			 */

			return export.getAbsolutePath();
		}
	}
}