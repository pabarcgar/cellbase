package org.opencb.cellbase.build.transform

import org.opencb.cellbase.build.transform.formats.clinical.ClinvarPublicSet
import org.opencb.cellbase.core.serializer.CellBaseSerializer
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.file.Paths

/**
 * Created by lcruz on 23/10/14.
 */
class ClinVarParserTest extends Specification {

    @Shared
    def clinvarAssembly37Parser
    @Shared
    def clinvarAssembly38Parser
    @Shared
    List<ClinvarPublicSet> serializedVariantsAssembly37
    @Shared
    List<ClinvarPublicSet> serializedVariantsAssembly38

    def setupSpec() {
        // custom test serializer that adds the assembly 37 serialized variants to a list
        def assembly37Serializer = Mock(CellBaseSerializer)
        serializedVariantsAssembly37 = new ArrayList<ClinvarPublicSet>()
        assembly37Serializer.serialize(_) >> { ClinvarPublicSet arg -> serializedVariantsAssembly37.add(arg) }
        // custom test serializer that adds the assembly 38 serialized variants to a list
        def assembly38Serializer = Mock(CellBaseSerializer)
        serializedVariantsAssembly38 = new ArrayList<ClinvarPublicSet>()
        assembly38Serializer.serialize(_) >> { ClinvarPublicSet arg -> serializedVariantsAssembly38.add(arg) }

        def clinvarXmlFile = Paths.get(VariantEffectParserTest.class.getResource("/clinvar_v19_test.xml").toURI())

        clinvarAssembly37Parser = new ClinVarParser(clinvarXmlFile, ClinVarParser.GRCH37_ASSEMBLY, assembly37Serializer)
        clinvarAssembly38Parser = new ClinVarParser(clinvarXmlFile, ClinVarParser.GRCH38_ASSEMBLY, assembly38Serializer)
    }

    def "Parse"() {
        when:
        clinvarAssembly37Parser.parse()
        clinvarAssembly38Parser.parse()
        then: "serialize 3 variants"
        serializedVariantsAssembly37.size() == 3
        serializedVariantsAssembly38.size() == 3
    }

    @Unroll
    def "parsed variant has assembly 37 coordinates #chr:#startAssembly37-#endAssembly37, assembly 38 coordinates  #chr:#startAssembly38-#endAssembly38 and genotype #ref/#alt"() {
        expect:
        serializedVariantsAssembly37[variantNumber].chromosome == chr
        serializedVariantsAssembly38[variantNumber].chromosome == chr
        serializedVariantsAssembly37[variantNumber].start == startAssembly37
        serializedVariantsAssembly37[variantNumber].end == endAssembly37
        serializedVariantsAssembly38[variantNumber].start == startAssembly38
        serializedVariantsAssembly38[variantNumber].end == endAssembly38
        serializedVariantsAssembly37[variantNumber].reference == ref
        serializedVariantsAssembly37[variantNumber].alternate == alt
        serializedVariantsAssembly38[variantNumber].reference == ref
        serializedVariantsAssembly38[variantNumber].alternate == alt

        where:
        variantNumber || chr  | startAssembly37 | endAssembly37 | startAssembly38 | endAssembly38 | ref | alt
        0             || "12" | 2795019         | 2795019       | 2685853         | 2685853       | "C" | "T"
        1             || "14" | 24709794        | 24709794      | 24240588        | 24240588      | "G" | "-"
        2             || "4"  | 187120195       | 187120196     | 186199041       | 186199042     | "A" | "AA"
    }
}