Ext.define("DataGenerator.store.ScenariosForStep", {
    extend: 'Ext.data.Store',
    storeId:'scenariosForStep',
    model:'DataGenerator.model.FileNameModel',
    reload: function(){
        Ext.getCmp('scenarios-grid').getView().refresh();
    },
    refresh: function(){
        var optionsPanel = Ext.getCmp('options-panel');
        fileGrid.setWidth(routesPanel.getWidth());
        fileGrid.getView().setWidth(routesPanel.getWidth());
        fileGrid.getView().refresh();
    }
});