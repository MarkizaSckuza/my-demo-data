function createStores() {
    var app = DataGenerator.getApplication();
    Ext.create('DataGenerator.store.RoutesStore', { data: app.options} );
    Ext.getCmp('routes-panel');

    Ext.create('DataGenerator.store.PointsStore');

    Ext.create('DataGenerator.store.ScenariosListStore');

    Ext.create('Ext.data.Store', {
        storeId:'poiStore',
        fields: ['id', 'name'],
        data : [
            {"id": 0, "name": "No Type"},
            {"id": 1, "name": "Begin"},
            {"id": 2, "name": "Stay for 1 minute"},
            {"id": 3, "name": "Stay if WiFi"}
        ]
    });
    Ext.create('DataGenerator.store.JobsStore');
    Ext.create('DataGenerator.store.StepsStore');
    Ext.create('DataGenerator.store.LocationOutputStore');
    Ext.create('DataGenerator.store.NonLocationOutputStore');
    Ext.create('DataGenerator.store.ScenariosForStep');
    Ext.create('DataGenerator.view.JobsViewModel');

}