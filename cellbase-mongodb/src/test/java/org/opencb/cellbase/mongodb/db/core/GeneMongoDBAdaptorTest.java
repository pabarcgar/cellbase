package org.opencb.cellbase.mongodb.db.core;

import com.mongodb.BasicDBObject;
import org.junit.Test;
import org.opencb.cellbase.core.db.api.core.GeneDBAdaptor;
import org.opencb.cellbase.mongodb.GenericMongoDBAdaptorTest;
import org.opencb.datastore.core.QueryOptions;
import org.opencb.datastore.core.QueryResult;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by fjlopez on 08/10/15.
 */
public class GeneMongoDBAdaptorTest extends GenericMongoDBAdaptorTest {

    public GeneMongoDBAdaptorTest() { super(); }

    @Test
    public void testGetStatsById() throws Exception {
        GeneDBAdaptor geneDBAdaptor = dbAdaptorFactory.getGeneDBAdaptor("hsapiens", "GRCh37");
        QueryResult queryResult = geneDBAdaptor.getStatsById("BRCA2", new QueryOptions());
        Map resultDBObject = (Map) queryResult.getResult().get(0);
        assertEquals((String)resultDBObject.get("chromosome"),"13");
        assertEquals((String)resultDBObject.get("name"),"BRCA2");
        assertTrue((Integer)resultDBObject.get("start") == 32973805);
        assertTrue((Integer) resultDBObject.get("length") == 84195);
        assertTrue((Integer) resultDBObject.get("length") == 84195);
        assertEquals((String)resultDBObject.get("id"),"ENSG00000139618");
        Map clinicalDBObject = (Map)resultDBObject.get("clinicalVariantStats");
        Map soSummaryDBObject = (Map)clinicalDBObject.get("soSummary");
        assertTrue((Integer)((Map) soSummaryDBObject.get("SO:0001583")).get("count")==3121);
        assertTrue((Integer)((Map) soSummaryDBObject.get("SO:0001822")).get("count")==72);
        assertTrue((Integer)((Map) soSummaryDBObject.get("SO:0001627")).get("count")==226);
        Map clinicalSignificanceDBObject = (Map)clinicalDBObject.get("clinicalSignificanceSummary");
        assertTrue((Integer)clinicalSignificanceDBObject.get("Benign")==362);
        assertTrue((Integer) clinicalSignificanceDBObject.get("Likely pathogenic")==35);
        assertTrue((Integer) clinicalSignificanceDBObject.get("conflicting data from submitters")==279);

    }
}