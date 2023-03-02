Ext.define("DataGenerator.store.LocationOutputStore", {
    extend: 'Ext.data.Store',
    storeId:'locationOutputStore',
    fields:[ 'fileName'],
//    model:'DataGenerator.model.OutputModel',
    reload: function(){
        Ext.getCmp('location-based-outputs-combobox').getView().refresh();
    }//,
    //constructor: function(config) {
    //    // applyIf means only copy if it doesn't exist
    //    Ext.applyIf(config, {
    //        proxy: this.createProxy()
    //    });
    //    this.callParent([config]);
    //}
});