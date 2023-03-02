Ext.define("DataGenerator.store.NonLocationOutputStore", {
    extend: 'Ext.data.Store',
    storeId:'nonLocationOutputStore',
    fields:['id',  'fileName'],
    //model:'DataGenerator.model.Output',
    reload: function(){
        Ext.getCmp('non-location-outputs-grid').getView().refresh();
    }//,
    //load:function (records, operation, success) {
    //    var rowIndex = this.find('id', record.getId());
    //    grid.getView().select(rowIndex);
    //}
    //,
    //refresh: function(){
    //    var optionsPanel = Ext.getCmp('options-panel');
    //    fileGrid.setWidth(routesPanel.getWidth());
    //    fileGrid.getView().setWidth(routesPanel.getWidth());
    //    fileGrid.getView().refresh();
    //}
});