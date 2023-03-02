Ext.define("DataGenerator.store.StepsStore", {
    extend: 'Ext.data.Store',
    storeId:'stepsStore',
    fields:['step',  'steps'],
    reload: function(){
        Ext.getCmp('steps-grid').getView().refresh();
    }
    //,
    //refresh: function(){
    //    var optionsPanel = Ext.getCmp('options-panel');
    //    fileGrid.setWidth(routesPanel.getWidth());
    //    fileGrid.getView().setWidth(routesPanel.getWidth());
    //    fileGrid.getView().refresh();
    //}
});