function createJobs(tabs)
{
    var jobsPanel = Ext.create('DataGenerator.ui.jobs.JobManagementPanel');//,{items:[outputsForm,outputsGrid]}
    var jobsTab = tabs.add({
        xtype: 'container',
        title: 'Jobs management',
        autoScroll: true,
        fullscreen: true,
        id: 'jobs-management',
        //items: [routesPanel, optionsPanel, mapEditor]
        layout:'fit',
        items: [jobsPanel]
    });
    tabs.setActiveTab('jobs-management');
    Ext.create('DataGenerator.ui.NonLocationOutputManagmentForm', {renderTo: Ext.get('NonlocationOutputParametrsForm')});//
    Ext.create('DataGenerator.ui.LocationOutputManagmentForm', {renderTo: Ext.get('locationOutputParametrsForm')});//
    var jobManagmentGrid = Ext.create('DataGenerator.ui.JobsManagmentGrid', { renderTo: Ext.get('jobsManagerGrid') });//
    var stepsGrid = Ext.create('DataGenerator.ui.StepsManagmentGrid', { renderTo: Ext.get('stepsManagerGrid') });
    var nonLocationOutputGrid = Ext.create('DataGenerator.ui.NonLocationOutputsGrid', { renderTo: Ext.get('nonLocationOutputGrid') });
    var scenariosGrid = Ext.create('DataGenerator.ui.ScenariosGrid', { renderTo: Ext.get('scenariosGrid') });

    var nonLocationOutputForm = Ext.get('non-location-output-field-set');


    stepsGrid.nonLocationOutputForm= nonLocationOutputForm;
    stepsGrid.locationOutputForm= Ext.get('location-output-field-set');
    stepsGrid.nonLocationOutputGrid=nonLocationOutputGrid;
    stepsGrid.scenariosGrid=scenariosGrid;

    jobManagmentGrid.nonLocationOutputForm = nonLocationOutputForm;
    jobManagmentGrid.locationOutputForm = stepsGrid.locationOutputForm;
    jobManagmentGrid.stepsGrid = stepsGrid;
    jobManagmentGrid.nonLocationOutputGrid = nonLocationOutputGrid;
    jobManagmentGrid.scenariosGrid = scenariosGrid;

    nonLocationOutputGrid.nonLocationOutputForm = nonLocationOutputForm;



};


