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

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.actoutcome.enums._4.ResultCodeEnum;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcome.v4.rivtabp21.GetLaboratoryOrderOutcomeResponderInterface;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeType;
//import riv.clinicalprocess.healthcond.actoutcome._4.ResultType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetLaboratoryOrderOutcomeResponderService", portName = "GetLaboratoryOrderOutcomeResponderPort", targetNamespace = "urn:riv:clinicalprocess:healthcond:actoutcome:GetLaboratoryOrderOutcome:3:rivtabp21", name = "GetLaboratoryOrderOutcomeInteraction")
public class GetAggregatedLaboratoryOrderOutcomeTestProducer implements GetLaboratoryOrderOutcomeResponderInterface {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedLaboratoryOrderOutcomeTestProducer.class);

	private TestProducerDb testDb;
	public void setTestDb(TestProducerDb testDb) {
		this.testDb = testDb;
	}

	@Override
	public GetLaboratoryOrderOutcomeResponseType getLaboratoryOrderOutcome(String logicalAddress, GetLaboratoryOrderOutcomeType request) {
		log.info("### Virtual service for GetLaboratoryOrderOutcome call the source system with logical address: {} and patientId: {}", logicalAddress, request.getPatientId().getExtension());

		GetLaboratoryOrderOutcomeResponseType response = (GetLaboratoryOrderOutcomeResponseType)testDb.processRequest(logicalAddress, request.getPatientId().getExtension());
        if (response == null) {
            log.info("nothing was found - returning an empty GetLaboratoryOrderOutcomeResponse");
        	response = new GetLaboratoryOrderOutcomeResponseType();
        	/*
        	response.setResult(new ResultType());
        	response.getResult().setResultCode(ResultCodeEnum.INFO);
            response.getResult().setLogId("NA");
            */
        } else {
            log.info("response.result.resultCode:null");
        }

        log.info("### Virtual service got {} lab responses in the reply from the source system with logical address: {} and patientId: {}", new Object[] {response.getLaboratoryOrderOutcome().size(), logicalAddress, request.getPatientId().getExtension()});

		return response;
	}
}
