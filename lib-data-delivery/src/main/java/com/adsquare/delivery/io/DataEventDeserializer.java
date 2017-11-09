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

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import com.adsquare.delivery.events.DataEvent;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Supporting class to deserialize {@link DataEvent}s to various formats
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataEventDeserializer {
	private final static DatumReader<DataEvent> READER = new SpecificDatumReader<>(DataEvent.getClassSchema());

	public static DataEvent fromBytes(final byte[] avroBytes) throws IOException {
		return DataEventDeserializer.fromBytes(READER, avroBytes);
	}

	public static DataFileStream<DataEvent> readFromAvroFile(String fileName) throws IOException {
		return new DataFileReader<>(new File(fileName), READER);
	}

	private final static <T> T fromBytes(final DatumReader<T> reader, final byte[] avroBytes) throws IOException {
		final BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(avroBytes, null);
		return reader.read(null, decoder);
	}
}