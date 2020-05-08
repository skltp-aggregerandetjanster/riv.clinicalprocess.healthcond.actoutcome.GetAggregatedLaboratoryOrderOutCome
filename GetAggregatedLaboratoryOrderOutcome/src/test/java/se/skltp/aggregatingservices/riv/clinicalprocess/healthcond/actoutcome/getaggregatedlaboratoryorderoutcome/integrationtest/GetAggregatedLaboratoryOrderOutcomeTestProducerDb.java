/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedlaboratoryorderoutcome.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.ThreadSafeSimpleDateFormat;

import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome._4.AccessControlHeaderType;
import riv.clinicalprocess.healthcond.actoutcome._4.ContactInformationType;
import riv.clinicalprocess.healthcond.actoutcome._4.HeaderType;
import riv.clinicalprocess.healthcond.actoutcome._4.IIType;
import riv.clinicalprocess.healthcond.actoutcome._4.LaboratoryOrderOutcomeBodyType;
import riv.clinicalprocess.healthcond.actoutcome._4.LaboratoryOrderOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome._4.OrgUnitType;
//import riv.clinicalprocess.healthcond.actoutcome._4.ResultType;
import se.skltp.agp.test.producer.TestProducerDb;

public class GetAggregatedLaboratoryOrderOutcomeTestProducerDb extends TestProducerDb {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedLaboratoryOrderOutcomeTestProducerDb.class);
	private static final ThreadSafeSimpleDateFormat df = new ThreadSafeSimpleDateFormat("YYYYMMDDhhmmss");

	@Override
	public Object createResponse(Object... responseItems) {
		log.info("Creating a response with {} items", responseItems.length);
		GetLaboratoryOrderOutcomeResponseType response = new GetLaboratoryOrderOutcomeResponseType();
		for (int i = 0; i < responseItems.length; i++) {
			response.getLaboratoryOrderOutcome().add((LaboratoryOrderOutcomeType)responseItems[i]);
		}

		/*
		ResultType result = new ResultType();
		result.setResultCode(ResultCodeEnum.INFO);
		result.setLogId(UUID.randomUUID().toString());
		result.setMessage("Ett meddelande till anvÃ¤ndaren");
		response.setResult(result);
		*/
		log.info("response.toString:" + response.toString());

		return response;
	}

	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {

        log.debug("Created LaboratoryOrderOutcomeType for logical-address {}, registeredResidentId {} and businessObjectId {}",
				new Object[] {logicalAddress, registeredResidentId, businessObjectId});

		LaboratoryOrderOutcomeType labOrderOutcome = new LaboratoryOrderOutcomeType();

		HeaderType header = new HeaderType();
		labOrderOutcome.setHeader(header);
		LaboratoryOrderOutcomeBodyType body = new LaboratoryOrderOutcomeBodyType();
		labOrderOutcome.setBody(body);

		IIType systemId = new IIType();
		systemId.setRoot("1.2.3");
		systemId.setExtension(logicalAddress);
		header.setSourceSystemId(systemId);
		
		ContactInformationType contactinfo = new ContactInformationType();
		contactinfo.setText("Testvägen 3, 12345 GLOO");
		body.setContactInformation(contactinfo);

		OrgUnitType orgUnit = new OrgUnitType();
		orgUnit.setName("Organisation 1");
		IIType id = new IIType();
		id.setRoot(logicalAddress);
		id.setExtension("1.2.3");
		orgUnit.setId(id);
		body.setRecipientUnit(orgUnit);
		
		AccessControlHeaderType ac = new AccessControlHeaderType();
		header.setAccessControlHeader(ac);
		
		IIType patient = new IIType();
		patient.setExtension(registeredResidentId);
		patient.setRoot("1.2.752.129.2.1.3.1");
		ac.setOriginalPatientId(patient);

		ac.setApprovedForPatient(true);

		//Body start
		body.setText("Test av GLOO");

		//Body end

		//response.setCareUnit(logicalAddress);
		//response.setSubjectOfCareId(registeredResidentId);
		//response.setSenderRequestId(businessObjectId);

		return labOrderOutcome;
	}
}
