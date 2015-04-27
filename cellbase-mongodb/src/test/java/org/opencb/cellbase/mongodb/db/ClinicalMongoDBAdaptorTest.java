/*
 * Copyright 2015 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.cellbase.mongodb.db;

import com.google.common.base.Splitter;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.Test;
import org.opencb.biodata.models.feature.Region;
import org.opencb.cellbase.core.CellBaseConfiguration;
import org.opencb.cellbase.core.common.core.CellbaseConfiguration;
import org.opencb.cellbase.core.lib.DBAdaptorFactory;
import org.opencb.cellbase.core.lib.api.variation.ClinicalDBAdaptor;
import org.opencb.cellbase.core.lib.api.variation.VariantAnnotationDBAdaptor;
import org.opencb.datastore.core.QueryOptions;
import org.opencb.datastore.core.QueryResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ClinicalMongoDBAdaptorTest {

    @Test
    public void testGetAllByRegionList() throws Exception {
//        try {
//            CellbaseConfiguration config = new CellbaseConfiguration();
            CellBaseConfiguration cellBaseConfiguration = new CellBaseConfiguration();

//            config.addSpeciesAlias("hsapiens", "hsapiens");

            DBAdaptorFactory dbAdaptorFactory = new MongoDBAdaptorFactory(cellBaseConfiguration);

            ClinicalDBAdaptor clinicalDBAdaptor = dbAdaptorFactory.getClinicalDBAdaptor("hsapiens", "GRCh37");
            QueryOptions queryOptions = new QueryOptions("include", "clinvarList");
//            List<QueryResult> clinicalQueryResultList = clinicalDBAdaptor.getAllClinvarByRegionList(Arrays.asList(new Region("3", 550000, 1166666)), queryOptions);
//            List<QueryResult> queryResultList = new ArrayList<>();
//            for (QueryResult clinvarQueryResult : clinicalQueryResultList) {
//                QueryResult queryResult = new QueryResult();
//                queryResult.setId(clinvarQueryResult.getId());
//                queryResult.setDbTime(clinvarQueryResult.getDbTime());
//                queryResult.setNumResults(clinvarQueryResult.getNumResults());
//                BasicDBList basicDBList = new BasicDBList();
//
//                for (BasicDBObject clinicalRecord : (List<BasicDBObject>) clinvarQueryResult.getResult()) {
//                    if (clinicalRecord.containsKey("clinvarList")) {
//                        for (BasicDBObject clinvarRecord : (List<BasicDBObject>) clinicalRecord.get("clinvarList")) {
//                            basicDBList.add(clinvarRecord);
//                        }
//                    }
//                }
//                queryResult.setResult(basicDBList);
//                queryResultList.add(queryResult);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
    }

//    @Test
//    public void testGetClinvarById() throws Exception {
//
////        CellbaseConfiguration config = new CellbaseConfiguration();
//        CellBaseConfiguration cellBaseConfiguration = new CellBaseConfiguration();
//
////        config.addSpeciesAlias("hsapiens", "hsapiens");
//
//        DBAdaptorFactory dbAdaptorFactory = new MongoDBAdaptorFactory(cellBaseConfiguration);
//
//        ClinicalDBAdaptor clinicalDBAdaptor = dbAdaptorFactory.getClinicalDBAdaptor("hsapiens", "GRCh37");
//
////        clinicalDBAdaptor.getAllClinvarByIdList(Splitter.on(",").splitToList("RCV000091359"), new QueryOptions());
//    }

    @Test
    public void testGetAll() {

//        CellbaseConfiguration config = new CellbaseConfiguration();
        CellBaseConfiguration cellBaseConfiguration = new CellBaseConfiguration();

        try {
            cellBaseConfiguration = CellBaseConfiguration
                        .load(CellBaseConfiguration.class.getClassLoader().getResourceAsStream("configuration.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DBAdaptorFactory dbAdaptorFactory = new MongoDBAdaptorFactory(cellBaseConfiguration);

        ClinicalDBAdaptor clinicalDBAdaptor = dbAdaptorFactory.getClinicalDBAdaptor("hsapiens", "GRCh37");
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addToListOption("include", "clinvar");
//        queryOptions.add("phenotype", "ALZHEIMER DISEASE 2, DUE TO APOE4 ISOFORM");
//        queryOptions.addToListOption("phenotype", "ALZHEIMER");
        queryOptions.addToListOption("phenotype", "alzheimer");
//        queryOptions.addToListOption("phenotype", "diabetes");
//        queryOptions.addToListOption("region", new Region("3", 550000, 1166666));
//        queryOptions.addToListOption("region", new Region("1", 550000, 1166666));
//        queryOptions.addToListOption("gene", "APOE");
//        queryOptions.addToListOption("significance", "Likely_pathogenic");
//        queryOptions.addToListOption("review", "REVIEWED_BY_PROFESSIONAL_SOCIETY");
//        queryOptions.addToListOption("type", "Indel");
//        queryOptions.addToListOption("so", "missense_variant");
//        queryOptions.addToListOption("rs", "rs429358");
//        queryOptions.addToListOption("rcv", "RCV000019455");
        queryOptions.add("limit", 100);

//        ((List<String>) queryOptions.get("include")).remove(0);

        QueryResult queryResult = clinicalDBAdaptor.getAll(queryOptions);
        int a;
        a = 1;


    }

    @Test
    public void testGetPhenotypeGeneRelations() throws Exception {

        CellBaseConfiguration cellBaseConfiguration = new CellBaseConfiguration();

        try {
            cellBaseConfiguration = CellBaseConfiguration
                    .load(CellBaseConfiguration.class.getClassLoader().getResourceAsStream("configuration.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DBAdaptorFactory dbAdaptorFactory = new MongoDBAdaptorFactory(cellBaseConfiguration);

        ClinicalDBAdaptor clinicalDBAdaptor = dbAdaptorFactory.getClinicalDBAdaptor("hsapiens", "GRCh37");
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addToListOption("include", "clinvar");
        queryOptions.addToListOption("include", "cosmic");
        List<QueryResult> queryResultList = clinicalDBAdaptor.getPhenotypeGeneRelations(queryOptions);
        int a;
        a=1;

    }
}