Ext.define("DataGenerator.store.JobsStore", {
    extend: 'Ext.data.Store',
    storeId:'jobsStore',
    //fields:['fileName','fileNameToShow','id' ],
    reload: function(){
        Ext.getCmp('jobs-grid').getView().refresh();
    }//,
    //refresh: function(){
    //    var optionsPanel = Ext.getCmp('options-panel');
    //    fileGrid.setWidth(routesPanel.getWidth());
    //    fileGrid.getView().setWidth(routesPanel.getWidth());
    //    fileGrid.getView().refresh();
    //}
});