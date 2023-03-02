Ext.define("DataGenerator.ui.MapToolbar", {
    extend: 'Ext.toolbar.Toolbar',
    id: 'map-toolbar',
    enableOverflow: true,
    items: [
        {
            xtype: 'button',
            text: 'Create Route',
            id: 'btn-create-route',
            enableToggle: true,
            margin: '5 5 5 5',

            toggleHandler: function (button, pressed) {
                var app = DataGenerator.getApplication();
                if (pressed) {
                    app.creatingRoute = true;
                    Ext.getCmp('btn-create-route').setText("Finish");

                    app.disableControls([Ext.getCmp('btn-create-route')]);

                    app.currentPolyLine = new google.maps.Polyline(app.polyOptions);
                    app.currentPolyLine.setMap(app.map);
                    google.maps.event.addListener(app.map, 'click', app.addLatLng);
                }
                else {
                    Ext.getCmp('btn-create-route').setText("Create Route");
                    app.creatingRoute = false;
                    app.enableControls();

                    google.maps.event.clearListeners(app.map, 'click');

                    var path = app.currentPolyLine.getPath();

                    if (path.length > 1) {
                        app.polyLines.push(app.currentPolyLine);
                        app.polyLinesMarkers.push(app.currentMarkers);
                        app.addRoute(path);
                    }

                    app.currentPolyLine = null;
                    app.currentMarkers = [];
                }
            }
        },
        {
            xtype: 'button',
            text: 'Edit Route',
            id: 'btn-edit-route',
            enableToggle: true,
            margin: '5 5 5 5',

            toggleHandler: function (button, pressed) {

                var app = DataGenerator.getApplication();
                if (app.polyLines.length <= 0 || app.selectedPolyLineIndex < 0) {
                    Ext.getCmp('btn-edit-route').setPressed(false);
                    return;
                }

                var selectedPolyLine = app.polyLines[app.selectedPolyLineIndex];

                if (pressed) {
                    Ext.getCmp('btn-edit-route').setText("Finish Editing");
                    if (selectedPolyLine != null) {
                        app.clearSelectedMarkers(app.selectedPolyLineIndex);
                        app.disableControls([Ext.getCmp('btn-edit-route')]);
                        selectedPolyLine.setEditable(true);
                    }
                }
                else {
                    Ext.getCmp('btn-edit-route').setText("Edit Route");
                    if (selectedPolyLine != null) {
                        selectedPolyLine.setEditable(false);
                        app.setRoutePoints(app.selectedPolyLineIndex, selectedPolyLine.getPath());
                        app.addSelectedMarkers(app.selectedPolyLineIndex);
                        app.enableControls();
                    }
                }
            }
        },
        {
            xtype: 'button',
            id: 'btn-remove-route',
            text: 'Remove Route',
            margin: '5 5 5 5',

            handler: function () {
                var app = DataGenerator.getApplication();

                if (app.selectedPolyLineIndex < 0) return;

                app.removePolyLine(app.selectedPolyLineIndex);

                app.options.routes.splice(app.selectedPolyLineIndex, 1);

                for (var i = 0; i < app.options.routes.length; i++) {
                    app.options.routes[i].id = i + 1;
                }

                Ext.data.StoreManager.lookup('pointsStore').removeAll();
                Ext.data.StoreManager.lookup('routesStore').loadData(app.options.routes);

                if (app.selectedPolyLineIndex >= 0 && app.selectedPolyLineIndex < app.options.routes.length) {
                    var points = app.options.routes[app.selectedPolyLineIndex].points;
                    Ext.data.StoreManager.lookup('pointsStore').loadData(points);
                }
            }
        },
        {
            xtype: 'button',
            id: 'btn-clear-routes',
            text: 'Clear All Routes',
            margin: '5 5 5 5',

            handler: function () {
                var app = DataGenerator.getApplication();

                if (app.options.routes.length <= 0) return;

                app.clearPolyLines();
                app.options.routes = [];

                Ext.data.StoreManager.lookup('pointsStore').removeAll();
                Ext.data.StoreManager.lookup('routesStore').removeAll();
            }
        },
        {
            xtype: 'button',
            text: 'Create WiFi',
            id: 'btn-create-wifi',
            enableToggle: true,
            margin: '5 5 5 5',

            toggleHandler: function (button, pressed) {
                var app = DataGenerator.getApplication();
                if (pressed) {
                    Ext.getCmp('btn-create-wifi').setText("Finish");
                    app.creatingWiFi = true;
                    app.disableControls([Ext.getCmp('btn-create-wifi')]);

                    google.maps.event.addListener(app.map, 'click', app.addLatLng);
                }
                else {
                    Ext.getCmp('btn-create-wifi').setText("Create WiFi");
                    app.creatingWiFi = false;
                    app.enableControls();

                    google.maps.event.clearListeners(app.map, 'click');
                }
            }
        },
        {
            xtype: 'button',
            id: 'btn-remove-wifi',
            text: 'Remove WiFi',
            margin: '5 5 5 5',

            handler: function () {
                var app = DataGenerator.getApplication();
                var row = Ext.getCmp('wifi-grid').getSelectionModel().getSelection()[0];
                app.options.wifi.splice(row.id - 1, 1);
                app.currentWiFi.setMap(null);
                app.currentWiFi = null;
                app.WiFiMarkers.splice(row.id - 1, 1);
                refreshWiFiIds();
                loadWiFiStore();
            }

        }
    ]
});
