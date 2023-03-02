function populateStore() {

    Ext.Ajax.request({
        url: '/api/options',
        success: this.onOptionsLoaded
    });

    Ext.Ajax.request({
        url: '/api/files/scenarios',
        method: 'GET',
        success: this.onFilesLoaded
    });

    Ext.Ajax.request({
        url: '/api/outputs/location',
        method: 'GET',
        success: this.onFilesLoaded
    });

    //Ext.Ajax.request({
    //    url: '/api/jobs',
    //    method: 'GET',
    //    success: this.onJobsLoaded
    //});
};
function onJobsLoaded(response) {
    var app = DataGenerator.getApplication();
    app.jobs = Ext.JSON.decode(response.responseText);
    if(app.jobs != undefined && app.jobs !== null) {
        for (var i= 0; i < app.jobs.length; i++) {
            app.jobs[i].id =i;
            if (app.jobs[i].steps != undefined && app.jobs[i].steps !== null) {
                //refresh steps id's;
                for (var j = 0; j < app.jobs[i].steps.length; j++) {
                    app.jobs[i].steps[j].id = j;
                }
            } else {
                app.jobs.steps = [];
            }
        }
    }else {
        app.jobs = [];
    }

    //Ext.data.StoreManager.lookup('jobsStore').loadData(app.jobs);
};
function onOptionsLoaded(response){
    var app = DataGenerator.getApplication();
    app.options = Ext.JSON.decode(response.responseText);

    // Set point ids
    app.options.routes.forEach(function(route){
        if (route.points[0].id == 0) {
            for (var i = 0; i < route.points.length; i++){
                route.points[i].id = i + 1;
            }
        }
    });

    // Set WiFi ids
    if(app.options.wifi != undefined && app.options.wifi !== null) {
        refreshWiFiIds();
    }
    else{
        app.options.wifi = [];
        app.index = 0;
    }
    app.bindData(app.options);
};

function onFilesLoaded(response) {
    var app = DataGenerator.getApplication();
    app.fileNames = Ext.JSON.decode(response.responseText);
    Ext.data.StoreManager.lookup('scenariosListStore').loadData(app.fileNames);
};

