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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Node;

import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.QueryObjectFactory;

public class QueryObjectFactoryImpl implements QueryObjectFactory {

	private static final Logger log = LoggerFactory.getLogger(QueryObjectFactoryImpl.class);
	private static final JaxbUtil ju = new JaxbUtil(GetLaboratoryOrderOutcomeType.class);

	private String eiServiceDomain;
	public void setEiServiceDomain(String eiServiceDomain) {
		this.eiServiceDomain = eiServiceDomain;
	}

	@SuppressWarnings("unused")
	private String eiCategorization;
	public void setEiCategorization(String eiCategorization) {
		this.eiCategorization = eiCategorization;
	}

	/**
	 * Transformerar GetLaboratoryOrderOutcome request till EI FindContent request enligt:
	 *
	 * 1. patientId --> registeredResidentIdentification
	 * 2. "riv:clinicalprocess:healthcond:actoutcome" --> serviceDomain
	 * 3. "und-kkm-ure" --> categorization
	 */
	@Override
	public QueryObject createQueryObject(Node node) {

		GetLaboratoryOrderOutcomeType request = (GetLaboratoryOrderOutcomeType)ju.unmarshal(node);

		if (log.isDebugEnabled()) log.debug("Transformed payload for pid: {}", request.getPatientId().getId());

		FindContentType fc = new FindContentType();
		fc.setRegisteredResidentIdentification(request.getPatientId().getId());
		fc.setServiceDomain(eiServiceDomain);

		//TKB 4.1	Uppdatering av engagemangsindex
		//Infomängd enl. Tjänstekontrakt	Värde på Categorization
		// GetMaternityMedicalHistory	utr-mtr
		// GetReferralOutcome	und-kon-ure
		//*GetLaboratoryOrderOutcome	und-kkm-ure
		// GetECGOutcome	und-ekg-ure
		// GetImagingOutcome	und-bdi-ure
		fc.setCategorization(eiCategorization);

		QueryObject qo = new QueryObject(fc, request);

		return qo;
	}
}
