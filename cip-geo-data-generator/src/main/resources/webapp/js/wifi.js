function createWiFi()
{
    var app = DataGenerator.getApplication();
    var wifiStore = Ext.create('DataGenerator.store.WiFiZoneStore');
    var wifiGrid = Ext.create('DataGenerator.ui.WiFiGrid', {
        renderTo: Ext.get('WiFi'),
        store: wifiStore
    });

    wifiStore.on('refresh', function(){
        var routesPanel = Ext.getCmp('routes-panel');
        wifiGrid.setWidth(routesPanel.getWidth());
        wifiGrid.getView().setWidth(routesPanel.getWidth());
        wifiGrid.getView().refresh();
    });
}

function refreshWiFiIds()
{
    var app = DataGenerator.getApplication();
    // Set WiFi ids
    for (var i = 0; i < app.options.wifi.length; i++) {
        app.options.wifi[i].id = i + 1;
    }
    app.index = app.options.wifi.length;
}

function loadWiFiStore()
{
    var app = DataGenerator.getApplication();
    var wifiStore = Ext.data.StoreManager.lookup('wifiZoneStore');
    var wifiGrid = Ext.getCmp('wifi-grid');

    wifiGrid.getSelectionModel().clearSelections();
    var records = wifiStore.data.items,
    i = records.length;
    while (i--) {
        wifiStore.remove( records[i] );
    }
    for (var i = 0; i < app.options.wifi.length; i++) {
        wifiStore.add(Ext.create('DataGenerator.model.WiFiModel', {
            id: app.options.wifi[i].id,
            lat: app.options.wifi[i].lat,
            lng: app.options.wifi[i].lng,
            radius: 0.001916654,
            whenCrossing: app.options.wifi[i].whenCrossing,
            timeSpent: app.options.wifi[i].timeSpent
        }));
    }
}
function createWifiMarkers()
{
    var app = DataGenerator.getApplication();

    app.WiFiMarkers = [];
    for (var i = 0; i < app.options.wifi.length; i++)
    {
        // Add the circle for WiFi.
        var circle = new google.maps.Circle(app.optionsWiFi);
        circle.setMap(app.map);
        circle.setCenter(new google.maps.LatLng(app.options.wifi[i].lat,app.options.wifi[i].lng));
        app.WiFiMarkers.push(circle);
    }
}
function removeAllWiFiMarks()
{
    var app = DataGenerator.getApplication();
    if(app.WiFiMarkers!=undefined && app.WiFiMarkers!=null)
    {
        for (var i = 0; i < app.WiFiMarkers.length; i++)
        {
            app.WiFiMarkers[i].setMap(null);
        }
    }
    app.WiFiMarkers = [];
    if(app.currentWiFi!=null) app.currentWiFi.setMap(null);
    app.currentWiFi=null;
}