package se.skltp.aggregatingservices;

import org.soitoolkit.commons.mule.util.RecursiveResourceBundle;

public class Util {

	private static final RecursiveResourceBundle rb = new RecursiveResourceBundle("GetAggregatedLaboratoryOrderOutcome-4-config");

	public static RecursiveResourceBundle getRecursiveResourceBundle() {
		return rb;
	}
}
