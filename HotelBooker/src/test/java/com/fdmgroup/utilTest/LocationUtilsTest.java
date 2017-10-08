package com.fdmgroup.utilTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.util.LocationUtils;

public class LocationUtilsTest {
	
	private static String hatfieldLocationString = "Hatfield, UK";
	private static String hatfieldLatitude = "51.7633660";
	private static String hatfieldLongitude = "-0.2230900";

	@Test
	public void test() {
		String[] latLong = null;
		
		// Attempt to get latitude/longitude
		Exception unexpectedException = null;
		try {
			latLong = LocationUtils.getLatLongPositions(hatfieldLocationString);
		} catch (Exception e) {
			unexpectedException = e;
		}
		assertEquals("Utils got unexpected exception", null, unexpectedException);
		
		// Confirm latitude and longitude is correct
		String latitude = latLong[0];
		String longitude = latLong[1];
		assertEquals("Latitude was incorrect", latitude, hatfieldLatitude);
		assertEquals("Longitude was incorrect", longitude, hatfieldLongitude);
	}

}
