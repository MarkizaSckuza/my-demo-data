function createUi() {
    var app = DataGenerator.getApplication();
    var tabs = new Ext.create("DataGenerator.ui.MainTab",{
    });

    Ext.create('DataGenerator.ui.Viewport', {
        items: [tabs],
        layout: 'fit'
    });



    var routesPanel = Ext.create('DataGenerator.ui.RoutesPanel', { renderTo: Ext.get('routesPanel') });//
    var optionsPanel = Ext.create('DataGenerator.ui.OptionsPanel', { renderTo: Ext.get('optionsPanel') });//

    var mapEditor = Ext.create('DataGenerator.ui.Map', { renderTo: Ext.get('mapEditor') });//
    Ext.create('DataGenerator.ui.MapToolbar', { renderTo: Ext.get('map-toolbar') });

    var routesGrid = Ext.create('DataGenerator.ui.RoutesGrid', {
        renderTo: Ext.get('routes'),
        store: Ext.data.StoreManager.lookup('routesStore')
    });

    var pointsGrid = Ext.create('DataGenerator.ui.PointsGrid', {
        renderTo: Ext.get('points'),
        store: Ext.data.StoreManager.lookup('pointsStore')
    });


    var fileGrid = Ext.create('DataGenerator.ui.FilesGrid', {
        renderTo: Ext.get('files-list'),
        store: Ext.data.StoreManager.lookup('scenariosListStore')
    });

    createWiFi();


    routesGrid.getSelectionModel().on('selectionchange', app.onRouteSelected);
    pointsGrid.getSelectionModel().on('selectionchange', app.onPointSelected);

    Ext.create('DataGenerator.ui.Commands', {
        renderTo: Ext.get('commands')
    });

    Ext.create('DataGenerator.ui.Options', {
        renderTo: Ext.get('options')
    });


    routesPanel.setWidth(500);

    app.initializeMap();

    Ext.getCmp('current-file').setText(app.options.fileName);
    Ext.getCmp('file-name').setValue(app.options.fileName);

    var optionsTab = tabs.getActiveTab();

    createJobs(tabs);

    tabs.setActiveTab('scenario-management');

}