package util_methods;

import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static org.junit.Assert.assertEquals;

/**
 * @author malex
 */
public class CoordinatesFromGoogleMapsApiTest {

	@Test
	public void test_01() throws Exception {
		String postcode = "Karnataka, India";
		String latLongs[] = getLatLongPositions(postcode);

		if (latLongs != null) {

			String latitudeLong = latLongs[0];
			String longitudeLong = latLongs[1];
			System.out.println("Latitude: " + latitudeLong + " and Longitude: " + longitudeLong);

			assertEquals("15.3172775", latitudeLong);
			assertEquals("75.7138884", longitudeLong);

		} else {
			throw new Exception("Coordinate search error");
		}
	}


	private final static String XPATH_STATUS = "/GeocodeResponse/status";
	private final static String XPATH_LOCATION_LATITUDE = "//geometry/location/lat";
	private final static String XPATH_LOCATION_LONGITUDE = "//geometry/location/lng";

	private static String[] getLatLongPositions(String address) throws Exception {
		int responseCode;
		String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
		System.out.println("URL : " + api);
		URL url = new URL(api);
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
		httpConnection.connect();
		responseCode = httpConnection.getResponseCode();
		if (responseCode == 200) {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			;
			Document document = builder.parse(httpConnection.getInputStream());
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(XPATH_STATUS);
			String status = (String) expr.evaluate(document, XPathConstants.STRING);
			if (status.equals("OK")) {
				expr = xpath.compile(XPATH_LOCATION_LATITUDE);
				String latitude = (String) expr.evaluate(document, XPathConstants.STRING);
				expr = xpath.compile(XPATH_LOCATION_LONGITUDE);
				String longitude = (String) expr.evaluate(document, XPathConstants.STRING);
				return new String[]{latitude, longitude};
			} else {
				throw new Exception("It was not possible to find the coordinates of the location");
			}
		}
		return null;
	}

}
