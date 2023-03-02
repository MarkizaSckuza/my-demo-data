Ext.define("DataGenerator.store.PointsStore", {
    extend: 'Ext.data.Store',
    storeId:'pointsStore',
    fields:['id', 'lat', 'lng', 'poiType']
});

