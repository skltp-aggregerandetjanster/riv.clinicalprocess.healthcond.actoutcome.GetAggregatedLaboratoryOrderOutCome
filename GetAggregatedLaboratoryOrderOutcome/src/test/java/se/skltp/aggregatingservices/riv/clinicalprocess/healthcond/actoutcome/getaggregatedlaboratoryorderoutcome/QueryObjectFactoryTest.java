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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.GetLaboratoryOrderOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v4.ObjectFactory;
import riv.clinicalprocess.healthcond.actoutcome._4.PersonIdType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.test.producer.TestProducerDb;


public class QueryObjectFactoryTest {

	private static final ObjectFactory OF = new ObjectFactory();
	private QueryObjectFactoryImpl testObject = new QueryObjectFactoryImpl();

	@Before
	public void beforeTest(){
		testObject.setEiCategorization("und-kkm-ure");
		testObject.setEiServiceDomain("riv:clinicalprocess:healthcond:actoutcome");
	}

	@Test
	public void findContentIsCreatedWithCorrectValues() throws Exception{

		//Create request
		GetLaboratoryOrderOutcomeType request = new GetLaboratoryOrderOutcomeType();

		PersonIdType personIdType = new PersonIdType();
		personIdType.setId(TestProducerDb.TEST_RR_ID_ONE_HIT);
		personIdType.setType("1.2.752.129.2.1.3.1");
		request.setPatientId(personIdType);

		request.setSourceSystemHSAId(TestProducerDb.TEST_LOGICAL_ADDRESS_1);
		//End create request

		//Create FindContent request
		QueryObject queryObject = testObject.createQueryObject(createNode(request));
		FindContentType findContent = queryObject.getFindContent();

		//Expected to be set with values
		assertEquals("und-kkm-ure", findContent.getCategorization());
		assertEquals(TestProducerDb.TEST_RR_ID_ONE_HIT, findContent.getRegisteredResidentIdentification());
		assertEquals("riv:clinicalprocess:healthcond:actoutcome", findContent.getServiceDomain());

		//Expected to be null
		assertNull(findContent.getLogicalAddress());
		assertNull(findContent.getSourceSystem());
		assertNull(findContent.getBusinessObjectInstanceIdentifier());
		assertNull(findContent.getClinicalProcessInterestId());
		assertNull(findContent.getDataController());
		assertNull(findContent.getMostRecentContent());
		assertNull(findContent.getOwner());

	}

	private Node createNode(GetLaboratoryOrderOutcomeType request) throws Exception{

		// Since the class GetLaboratoryOrderOutcomeType don't have an @XmlRootElement annotation
        // we need to use the ObjectFactory to add it.
		JAXBElement<GetLaboratoryOrderOutcomeType> requestJaxb = OF.createGetLaboratoryOrderOutcome(request);

		//Node to carry marshalled content
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document node = db.newDocument();

        //Marshall
		JAXBContext jc = JAXBContext.newInstance(GetLaboratoryOrderOutcomeType.class);
		Marshaller marshaller = jc.createMarshaller();
		marshaller.marshal(requestJaxb, node);
		return node;
	}
}
