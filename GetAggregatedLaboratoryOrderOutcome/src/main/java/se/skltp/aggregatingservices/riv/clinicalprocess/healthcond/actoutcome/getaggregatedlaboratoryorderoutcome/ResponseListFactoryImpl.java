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
package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedlaboratoryorderoutcome;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.ObjectFactory;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;

public class ResponseListFactoryImpl implements ResponseListFactory {

	private static final Logger log = LoggerFactory.getLogger(ResponseListFactoryImpl.class);
	private static final JaxbUtil jaxbUtil = new JaxbUtil(GetLaboratoryOrderOutcomeResponseType.class, ProcessingStatusType.class);
	private static final ObjectFactory OF = new ObjectFactory();

	@Override
	public String getXmlFromAggregatedResponse(QueryObject queryObject, List<Object> aggregatedResponseList) {
		GetLaboratoryOrderOutcomeResponseType aggregatedResponse = new GetLaboratoryOrderOutcomeResponseType();

	    for (Object object : aggregatedResponseList) {
	    	GetLaboratoryOrderOutcomeResponseType response = (GetLaboratoryOrderOutcomeResponseType)object;
			aggregatedResponse.getLaboratoryOrderOutcome().addAll(response.getLaboratoryOrderOutcome());
		}
	    
	    /*
        aggregatedResponse.setResult(new ResultType());
        aggregatedResponse.getResult().setResultCode(ResultCodeEnum.INFO);
        aggregatedResponse.getResult().setLogId("NA");
	     */
	    
		String subjectOfCareId = queryObject.getFindContent().getRegisteredResidentIdentification();
    	log.info("Returning {} aggregated laboratory order outcome for subject of care id {}", aggregatedResponse.getLaboratoryOrderOutcome().size() ,subjectOfCareId);

        // Since the class GetRequestActivitiesResponseType doesn't have an @XmlRootElement annotation we need to use the ObjectFactory to add it.
        return jaxbUtil.marshal(OF.createGetLaboratoryOrderOutcomeResponse(aggregatedResponse));
	}
}
