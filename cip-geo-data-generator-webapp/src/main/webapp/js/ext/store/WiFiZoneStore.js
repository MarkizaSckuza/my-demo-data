Ext.define("DataGenerator.store.WiFiZoneStore", {
    extend: 'Ext.data.ArrayStore',
    storeId:'wifiZoneStore',
    model:'DataGenerator.model.WiFiModel',
    reload: function(){
        Ext.getCmp('wifi-grid').getView().refresh();
    }

});
