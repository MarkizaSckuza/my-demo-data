function listeners() {

    var routesStore = Ext.data.StoreManager.lookup('routesStore');
    var pointsStore = Ext.data.StoreManager.lookup('pointsStore');
    var routesGrid = Ext.getCmp('routes-grid');
    var pointsGrid = Ext.getCmp('points-grid');

    var pointsGrid = Ext.getCmp('routes-panel');

    routesStore.on('refresh', function(){
        var routesPanel = Ext.getCmp('routes-panel');
        routesGrid.setWidth(routesPanel.getWidth());
        routesGrid.getView().setWidth(routesPanel.getWidth());

        routesGrid.getView().refresh();
    });

    pointsStore.on('refresh', function(){
        var routesPanel = Ext.getCmp('routes-panel');
        pointsGrid.setWidth(routesPanel.getWidth());
        pointsGrid.getView().setWidth(routesPanel.getWidth());
        pointsGrid.getView().refresh();
    });
}