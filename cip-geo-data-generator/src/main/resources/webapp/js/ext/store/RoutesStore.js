Ext.define("DataGenerator.store.RoutesStore", {
    extend: 'Ext.data.Store',
    storeId:'routesStore',
    fields:['id', 'points'],
    reload: function(){
        Ext.getCmp('routes-grid').getView().refresh();
    }
});