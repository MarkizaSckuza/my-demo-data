Ext.define("DataGenerator.store.ScenariosListStore", {
    extend: 'Ext.data.Store',
    storeId:'scenariosListStore',
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
