Ext.define("DataGenerator.store.FilesStore", {
    extend: 'Ext.data.Store',
    storeId:'filesStore',
    fields:['id',  'fileName'],
    reload: function(){
        Ext.getCmp('files-grid').getView().refresh();
    },
    refresh: function(){
        var optionsPanel = Ext.getCmp('options-panel');
        fileGrid.setWidth(routesPanel.getWidth());
        fileGrid.getView().setWidth(routesPanel.getWidth());
        fileGrid.getView().refresh();
    }
});
