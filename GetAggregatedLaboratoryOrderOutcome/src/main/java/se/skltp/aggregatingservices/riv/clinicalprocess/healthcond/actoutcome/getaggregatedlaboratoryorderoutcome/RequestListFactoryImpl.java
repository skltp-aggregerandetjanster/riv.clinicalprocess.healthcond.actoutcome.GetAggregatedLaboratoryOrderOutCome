package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedlaboratoryorderoutcome;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.ThreadSafeSimpleDateFormat;

import riv.clinicalprocess.healthcond.actoutcome.getlaboratoryorderoutcomeresponder.v3.GetLaboratoryOrderOutcomeType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.riv.itintegration.engagementindex.v1.EngagementType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.RequestListFactory;

public class RequestListFactoryImpl implements RequestListFactory {

    private static final Logger log = LoggerFactory.getLogger(RequestListFactoryImpl.class);
    private static final ThreadSafeSimpleDateFormat timestampDateFormat = new ThreadSafeSimpleDateFormat("YYYYMMDDhhmmss");

    /**
     * Filtrera svarsposter från i EI (ei-engagement) baserat parametrar i GetLaboratoryOrderOutcome requestet (req). Följande villkor måste
     * vara sanna för att en svarspost från EI skall tas med i svaret:
     *
     * 1. req.getSourceSystemHSAId == null 
     *    or 
     *    req.getSourceSystemHSAId == "" 
     *    or 
     *    req.getSourceSystemHSAId == ei-engagement.logicalAddress
     *
     * Svarsposter från EI som passerat filtreringen grupperas på fältet sourceSystem 
     * samt postens fält logicalAddress (= PDL-enhet) samlas i listan careUnitId per varje sourceSystem
     *
     * Ett anrop görs per funnet sourceSystem med följande värden i anropet:
     *
     * 1. logicalAddress = sourceSystem (systemadressering) 
     * 2. request = originalRequest (ursprungligt anrop från konsument)
     */
    @Override
    public List<Object[]> createRequestList(QueryObject qo, FindContentResponseType src) {

        GetLaboratoryOrderOutcomeType originalRequest = (GetLaboratoryOrderOutcomeType) qo.getExtraArg();
        
        Date requestFromDate = null;
        Date requestToDate = null;
        if (originalRequest.getTimePeriod() != null) {
            requestFromDate = parseTimestamp(originalRequest.getTimePeriod().getStart());
            requestToDate = parseTimestamp(originalRequest.getTimePeriod().getEnd());
        }
        
        String requestCareUnit = originalRequest.getSourceSystemHSAId();

        FindContentResponseType eiResp = (FindContentResponseType) src;
        List<EngagementType> inEngagements = eiResp.getEngagement();

        log.info("Got {} hits in the engagement index", inEngagements.size());

        Map<String, List<String>> sourceSystem_pdlUnitList_map = new HashMap<String, List<String>>();

        for (EngagementType engagement : inEngagements) {
            // Filter
            if (isBetween(requestFromDate, requestToDate, engagement.getMostRecentContent())) {
                if (isPartOf(requestCareUnit, engagement.getLogicalAddress())) {
                    // Add pdlUnit to source system
                    log.debug("Add source system: {} for PDL unit: {}", engagement.getSourceSystem(), engagement.getLogicalAddress());
                    addPdlUnitToSourceSystem(sourceSystem_pdlUnitList_map, engagement.getSourceSystem(), engagement.getLogicalAddress());
                }
            }
        }

        // Prepare the result of the transformation as a list of request-payloads,
        // one payload for each unique logical-address (e.g. source system since we are using systemaddressing),
        // each payload built up as an object-array according to the JAX-WS signature for the method in the service interface
        List<Object[]> reqList = new ArrayList<Object[]>();

        for (Entry<String, List<String>> entry : sourceSystem_pdlUnitList_map.entrySet()) {
            String sourceSystem = entry.getKey();
            log.info("Calling source system using logical address {} for subject of care id {}", 
                      sourceSystem, originalRequest.getPatientId().getId());
            GetLaboratoryOrderOutcomeType request = originalRequest;
            Object[] reqArr = new Object[] { sourceSystem, request };
            reqList.add(reqArr);
        }
        log.debug("Transformed payload: {}", reqList);
        return reqList;
    }

    Date parseTimestamp(String timestamp) {
        try {
            if (timestamp == null || timestamp.length() == 0) {
                return null;
            } else {
                return timestampDateFormat.parse(timestamp);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    boolean isBetween(Date fromRequestDate, Date toRequestDate, String mostRecentContentTimestamp) {
        if (mostRecentContentTimestamp == null) {
            log.error("mostRecentContent - timestamp string is null");
            return true;
        }
        if (StringUtils.isBlank(mostRecentContentTimestamp)) {
            log.error("mostRecentContent - timestamp string is blank");
            return true;
        }
        log.debug("Is {} between {} and ", new Object[] {mostRecentContentTimestamp, fromRequestDate, toRequestDate});
        try {
            Date mostRecentContent = timestampDateFormat.parse(mostRecentContentTimestamp);
            if (fromRequestDate != null && fromRequestDate.after(mostRecentContent)) {
                return false;
            }
            if (toRequestDate != null && toRequestDate.before(mostRecentContent)) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    boolean isPartOf(String careUnitId, String careUnit) {
        log.debug("Check careunit {} equals expected {}", careUnitId, careUnit);
        if (StringUtils.isBlank(careUnitId))
            return true;
        return careUnitId.equals(careUnit);
    }

    void addPdlUnitToSourceSystem(Map<String, List<String>> sourceSystem_pdlUnitList_map, String sourceSystem, String pdlUnitId) {
        List<String> careUnitList = sourceSystem_pdlUnitList_map.get(sourceSystem);
        if (careUnitList == null) {
            careUnitList = new ArrayList<String>();
            sourceSystem_pdlUnitList_map.put(sourceSystem, careUnitList);
        }
        careUnitList.add(pdlUnitId);
    }
}
