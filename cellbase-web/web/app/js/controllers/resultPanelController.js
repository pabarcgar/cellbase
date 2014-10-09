var resultPanelControl = myApp.controller('resultPanelController', ['$scope','mySharedService','Server', function ($scope, mySharedService, Server) {

    $scope.genesAndTranscriptsData=[];
    $scope.genesData=[];
    $scope.paginationData=[];

    //------------------para el pagination--------------------
    $scope.numeroDatosMostrar = 4;
    //limites:
    $scope.firstData = 0;
    $scope.lastData = $scope.numeroDatosMostrar;
    //--------------------------------------------------------


    //obtener los datos que hay en el rango establecido por el paginado, es mas rapido que aplicar un filtro
    $scope.getDataInPaginationLimits = function () {

        $scope.paginationData = [];

        for (i=$scope.firstData; i<$scope.lastData; i++){

            if($scope.genesAndTranscriptsData[i] != null){
                $scope.paginationData.push($scope.genesAndTranscriptsData[i]);
            }
        }

    };


    //indicara los datos que va a mostrar dependiendo del numero de la paginacion
    $scope.setLimits = function (firstData) {
        $scope.firstData = firstData;
        $scope.lastData = firstData + $scope.numeroDatosMostrar;
    };


    $scope.$on('resultsBroadcast', function () {

        $scope.genesAndTranscriptsData = Server.getGenesAndTranscripts(mySharedService.selectedSpecies.shortName, mySharedService.selectedRegions);
        $scope.genesData = Server.getGenes(mySharedService.selectedSpecies.shortName, mySharedService.selectedRegions);

        $scope.getgenesIdAndBiotypes();


//        console.log($scope.genesAndTranscriptsData);
//        console.log($scope.genesData);

        //indicamos que los primeros datos a mostrar son los de la pagina 1
        for (i=0; i<$scope.numeroDatosMostrar; i++){
            if($scope.genesAndTranscriptsData[i] != null){
                $scope.paginationData.push($scope.genesAndTranscriptsData[i]);
            }
        }


        //definimos el pagination
        var numeroDatos = $scope.genesAndTranscriptsData.length;  //21
        var numeroDePaginas = Math.ceil(numeroDatos / $scope.numeroDatosMostrar);


        var options = {
            currentPage: 1,   //en la que nos encontramos inicialmente
            totalPages: numeroDePaginas,
            numberOfPages: 3,  //las que se ven en numero
            size: 'mini',
            onPageClicked: function(e,originalEvent,type,page){
                $scope.setLimits((page-1) * $scope.numeroDatosMostrar);
            }
        }

        $('#pagination').bootstrapPaginator(options);


    });



    $scope.$on('filter', function () {   //obtener la especie elegida en optionsBar

        $scope.genesFilters=mySharedService.genesIdFilter;
        $scope.biotypeFilters = mySharedService.biotypesFilter;

    });

    $scope.genesId = [];
    $scope.biotypes = [];

    $scope.getgenesIdAndBiotypes = function () {

        for(var i in $scope.genesData)
        {
            $scope.genesId.push($scope.genesData[i].id);
            if($scope.biotypes.indexOf($scope.genesData[i].biotype) == -1){
                $scope.biotypes.push($scope.genesData[i].biotype);

            }
        }

        mySharedService.genesIdAndBiotypes($scope.genesId, $scope.biotypes);
    };




    $scope.goToTab = function () {

        $(function () {
            $('#myTab a:first').tab('show')
        })

        $('#myTab a').click(function (e) {
            e.preventDefault()
            $(this).tab('show')
        })


    };

//-------------------------------------------------
    $scope.lastDataShow;

    $scope.showGenesTable = false;

    $scope.showAllGenes = function (index) {

        $scope.showGenesTable = true;

        $scope.showGenePanel = false;
        $scope.showTranscriptPanel = false;
        $scope.showTranscriptsTable = false;
        $scope.showMoreInfoPanel = false;

    };

    $scope.showGenePanel = false;
    $scope.selectedGen;

    $scope.geneSelected = function (geneId) {


        if($scope.lastDataShow != geneId){

            $scope.lastDataShow = geneId;   //nuevo gen
            $scope.showGenePanel = true;    //mostrar panel
            $scope.selectedGen = Server.getGene(mySharedService.selectedSpecies.shortName, geneId).result[0];  //obtener los datos


            $scope.showTranscriptPanel = false;
            $scope.showTranscriptsTable = false;
            $scope.showMoreInfoPanel = false;
        }
        else
        {
            if(!$scope.showGenePanel){  //para que no se muestre cuando ya lo esta
                $scope.showGenePanel = true;    //mostrar panel
                $scope.selectedGen = Server.getGene(mySharedService.selectedSpecies.shortName, geneId).result[0];  //obtener los datos
            }
        }
        $scope.showGenesTable = false;
    };


    $scope.showTranscriptsTable = false;


    $scope.transcriptsSelected = function (geneId) {

        if($scope.lastDataShow != geneId){

            $scope.lastDataShow = geneId;   //nuevo gen
            $scope.showTranscriptsTable = true;
            $scope.selectedTranscripts= Server.getGene(mySharedService.selectedSpecies.shortName, geneId).result[0].transcripts;

            $scope.showTranscriptPanel = false;
            $scope.showGenePanel = false;
            $scope.showMoreInfoPanel = false;
        }
        else
        {
            if(!$scope.showTranscriptsTable){  //para que no se muestre cuando ya lo esta
                $scope.showTranscriptsTable = true;
                $scope.selectedTranscripts= Server.getGene(mySharedService.selectedSpecies.shortName, geneId).result[0].transcripts;
            }
        }
        $scope.showGenesTable = false;


    };


    $scope.showTranscriptPanel = false;
    $scope.selectedTranscript;

    $scope.transcriptSelected = function (geneId, transcriptName) {

        var transcripts;

        if($scope.lastDataShow != geneId){
            $scope.lastDataShow = geneId;   //nuevo gen

            $scope.showTranscriptsTable = false;
            $scope.showGenePanel = false;
            $scope.showMoreInfoPanel = false;
        }

        $scope.showGenesTable = false;

        $scope.showTranscriptPanel = true;
        transcripts = Server.getGene(mySharedService.selectedSpecies.shortName, geneId).result[0].transcripts;

        for(var i in transcripts)
        {
            if(transcripts[i].name == transcriptName ){
                $scope.selectedTranscript = transcripts[i];
            }
        }

    };


    $scope.showMoreInfoPanel = false;
    $scope.selectedExons;
    $scope.selectedTFBS;
    $scope.selectedXrefs;

    $scope.moreInfoSelected = function (geneId, transcriptName) {

        var transcripts;

        if($scope.lastDataShow != geneId){

            $scope.lastDataShow = geneId;   //nuevo gen

            $scope.showTranscriptsTable = false;
            $scope.showTranscriptPanel = false;
            $scope.showGenePanel = false;

        }
        $scope.showGenesTable = false;

        $scope.showMoreInfoPanel = true;
        transcripts = Server.getGene(mySharedService.selectedSpecies.shortName, geneId).result[0].transcripts;

        for(var i in transcripts)
        {
            if(transcripts[i].name == transcriptName ){

                $scope.selectedExons = transcripts[i].exons;
                $scope.selectedTFBS = transcripts[i].tfbs;
                $scope.selectedXrefs = transcripts[i].xrefs;

            }
        }
    };

//}
}]);

resultPanelControl.$inject = ['$scope','mySharedService'];


myApp.factory('Server', function ($http) {
    return {
        getGenesAndTranscripts: function(species, regions) {

            var dataGet;
            var host = 'http://ws-beta.bioinfo.cipf.es/cellbase/rest/v3/'

            $.ajax({
                url: host + species + '/genomic/region/' + regions + '/gene?exclude=transcripts.xrefs,transcripts.exons,transcripts.tfbs&of=json',
                async: false,
                dataType: 'json',
                success: function (data, textStatus, jqXHR) {

                    dataGet=data.response[0].result;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });

            return dataGet;
        },
        getGenes: function(species, regions) {

            var dataGet;
            var host = 'http://ws-beta.bioinfo.cipf.es/cellbase/rest/v3/'

            $.ajax({
                url: host + species + '/genomic/region/' + regions + '/gene?exclude=transcripts&of=json',
                async: false,
                dataType: 'json',
                success: function (data, textStatus, jqXHR) {

                    dataGet=data.response[0].result;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });

            return dataGet;
        },
        getGene: function(species, geneId) {

            var dataGet;
            var host = 'http://ws-beta.bioinfo.cipf.es/cellbase/rest/v3/'

            $.ajax({
                url: host + species + '/feature/gene/' + geneId + '/info?of=json',
//              url: host + species + '/genomic/region/' + regions + '/gene?exclude=transcripts.xrefs,transcripts.exons,transcripts.tfbs&of=json',
                async: false,
                dataType: 'json',
                success: function (data, textStatus, jqXHR) {

                    dataGet = data.response[0];
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });

            return dataGet;
        }
    };
});




//----------tabs-----------------