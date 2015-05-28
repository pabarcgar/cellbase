package org.opencb.cellbase.mongodb.db

import com.mongodb.DBCollection
import org.opencb.biodata.models.variation.GenomicVariant
import org.opencb.cellbase.core.common.core.CellbaseConfiguration
import org.opencb.cellbase.core.lib.DBAdaptorFactory
import org.opencb.cellbase.core.lib.api.variation.ClinicalDBAdaptor
import org.opencb.datastore.core.QueryOptions
import org.opencb.datastore.mongodb.MongoDataStore
import spock.lang.Specification

/**
 * Created by parce on 15/04/15.
 */
class ClinicalMongoDBAdaptorGroovyTest extends Specification {

    static ClinicalMongoDBAdaptor clinicalAdaptor
    void setup() {
        //def mongoDataStore = Mock(MongoDataStore)
        //def clinicalCollection = Mock(DBCollection)
        //mongoDataStore.getCollection("clinical") >> { return clinicalCollection }
        CellbaseConfiguration config = new CellbaseConfiguration()
        config.addSpeciesConnection("hsapiens", "GRCh37", "mlocalhost", "cellbase_hsapiens_grch37_v3", 27017, "mongo", "", "", 10, 10)
        config.addSpeciesAlias("hsapiens", "hsapiens")
        DBAdaptorFactory dbAdaptorFactory = new MongoDBAdaptorFactory(config)
        clinicalAdaptor = dbAdaptorFactory.getClinicalDBAdaptor("hsapiens", "GRCh37")
//        def mongoDataStore = new MongoDataStore()
//        clinicalAdaptor = new ClinicalMongoDBAdaptor("", "", mongoDataStore)
    }

    void cleanup() {
    }

    def "GetAllByGenomicVariantList"() {
        clinicalAdaptor.getAllByGenomicVariantList(new ArrayList<GenomicVariant>(), new QueryOptions())
        clinicalAdaptor.getAllByGenomicVariantListOld(new ArrayList<GenomicVariant>(), new QueryOptions())
    }
}
