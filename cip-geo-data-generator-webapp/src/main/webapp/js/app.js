Ext.Loader.setConfig({
    enabled: true,
    paths: {
        DataGenerator: 'js/ext',
        wifi: 'js/WiFi'
    }
});

Ext.define('DataGenerator.app.Application', {
    extend: 'Ext.app.Application',

    id: 'data-generator',
    name: 'DataGenerator',

    //controllers:['DataGenerator.controller.JobsManagmentController'],

    currentFilename: "options.json",

    map: {},

    selectedPolyLineIndex: -1,
    selectedWiFiIndex: -1,
    selectedPolyLinePointIndex: -1,
    creatingRoute: false,
    creatingWiFi: false,
    currentPolyLine: new google.maps.Polyline(),
    polyLines: [],
    currentMarkers: [],
    polyLinesMarkers: [],
    WiFiMarkers: [],
    WiFi: [],
    currentWiFi: new google.maps.Circle(),

    selectedPointShape: {
        path: google.maps.SymbolPath.CIRCLE,
        strokeColor: '#CD0D0D',
        fillColor: '#CD0D0D',
        fillOpacity: 1,
        scale: 4
    },

    poiShapes: [
        {
            path: 'M -4,0 0,-4 4,0 0,4 z',
            strokeColor: '#CD0D0D',
            fillColor: '#CD0D0D',
            fillOpacity: 1
        },
        {
            path: 'M -4,-4 4,-4 4,4 -4,4 z',
            strokeColor: '#0DCD0D',
            fillColor: '#0DCD0D',
            fillOpacity: 1
        },
        {
            path: 'M -4,-4 4,-4 4,4 -4,4 z',
            strokeColor: '#CD0DCD',
            fillColor: '#CD0DCD',
            fillOpacity: 1
        },
        {
            path: 'M -4,-4 4,-4 4,4 -4,4 z',
            strokeColor: '#1e90ff',
            fillColor: '#1e90ff',
            fillOpacity: 1
        }
    ],


    polyOptions: {
        strokeColor: '#CD0D0D',
        strokeOpacity: 0.5,
        strokeWeight: 3
    },

    selectedPolyOptions: {
        strokeColor: '#CD0D0D',
        strokeOpacity: 0.5,
        strokeWeight: 6
    },

    optionsWiFi: {
        strokeColor: '#0000ff',
        strokeOpacity: 0.2,
        strokeWeight: 2,
        fillColor: '#0000ff',
        fillOpacity: 0.2,
        radius: 213.36
    },
    optionsWiFiSelected: {
        strokeColor: '#FF0000',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#FF0000',
//        fillOpacity: 0.35,
        radius: 213.36
    },
    options: {
        "pedestrians": {"count": 0, "speed": 0},
        "bicyclists": {"count": 0, "speed": 0},
        "cars": {"count": 0, "speed": 0},
        "routes": [],
        "wifi": [],
        "bounds": new google.maps.LatLngBounds(0, 0, 0, 0),
        "routesCenter": new google.maps.LatLng(0, 0),
        "fileName": 'options.json',
        'beginOnlyFromSpecialPoints': false,
        'fluctuation': 0.0

    },
    fileNames: {
        "fileName": []

    },

    jobs: {
        "steps": [],
        "fileName": ""
    },

    fileToLoad: {
        "fileName": ""
    },

    step: {
        id: 0,
        scenarios: [],
        outputs: []
    },

    output: {
        path: "",
        partitionType: "",
        separator: "",
        factoryName: "",
        description: "",
        location: false
    },

    launch: function () {
        Ext.QuickTips.init();
        Ext.Loader.setPath('DataGenerator', '/js/ext');

        var index = 0;
        var defaultWiFiRadius = 0.001916654;

        Ext.create('DataGenerator.controller.JobsManagmentController');

        createStores();
        createUi();
        listeners();
        populateStore();

        //var app = DataGenerator.getApplication();

    },

    getPoiShape: function (index) {
        var app = DataGenerator.getApplication();
        if (index == undefined || index < 0 || index >= app.poiShapes.length) return app.poiShapes[0];
        return app.poiShapes[index];
    },

    setRoutePoints: function (routeIndex, path) {
        var app = DataGenerator.getApplication();
        var points = app.pathToPoints(path);
        app.options.routes[routeIndex].points = points;
        Ext.data.StoreManager.lookup('routesStore').loadData(app.options.routes);
        Ext.data.StoreManager.lookup('pointsStore').loadData(points);
    },

    addRoute: function (path) {
        var app = DataGenerator.getApplication();
        app.options.routes.push({"id": app.polyLines.length, "points": app.pathToPoints(path)});
        Ext.data.StoreManager.lookup('routesStore').loadData(app.options.routes);
    },

    pathToPoints: function (path) {
        var points = [];

        for (var i = 0; i < path.length; i++) {
            var pathPoint = path.getAt(i);

            if (pathPoint.poiType == undefined) pathPoint.poiType = 0;

            points.push({
                "id": i + 1,
                "lat": pathPoint.lat(),
                "lng": pathPoint.lng(),
                "poiType": pathPoint.poiType
            });
        }

        return points;
    },

    initializeMap: function () {
        var mapOptions = {
            zoom: 16,
            center: new google.maps.LatLng(37.445832, -122.1601447)
        };
        var app = DataGenerator.getApplication();

        app.map = new google.maps.Map(document.getElementById('map'), mapOptions);
    },

    addLatLng: function (event) {
        var app = DataGenerator.getApplication();

        if (app.creatingRoute && !app.creatingWiFi) {
            var path = app.currentPolyLine.getPath();
            var position = event.latLng;
            position.poiType = 0;
            path.push(position);
            var marker = new google.maps.Marker({
                position: position,
                icon: app.getPoiShape(0),
                draggable: false,
                map: app.map
            });

            app.currentMarkers.push(marker);
        } else if (!app.creatingRoute && app.creatingWiFi) {
            app.index = app.index + 1;
            var wifiStore = Ext.data.StoreManager.lookup('wifiZoneStore');
            wifiStore.add(Ext.create('DataGenerator.model.WiFiModel', {
                id: app.index,
                lat: event.latLng.lat(),
                lng: event.latLng.lng(),
                radius: app.defaultWiFiRadius,
                whenCrossing: 0,
                timeSpent: 2
            }));

            // Add the circle for WiFi.
            var circle = new google.maps.Circle(app.optionsWiFi);
            circle.setMap(app.map);
            circle.setCenter(event.latLng);
            app.WiFiMarkers.push(circle);
        } else {
            window.alert("Don't know what to create!");
        }
    },


    clearSelectedMarkers: function (selectedIndex) {
        var app = DataGenerator.getApplication();
        app.polyLinesMarkers[selectedIndex].forEach(function (marker) {
            marker.setMap(null);
        });
        app.polyLinesMarkers[selectedIndex] = [];
    },

    addSelectedMarkers: function (selectedIndex) {
        var app = DataGenerator.getApplication();
        var polyLine = app.polyLines[selectedIndex];
        var path = polyLine.getPath();
        path.forEach(function (point) {
            var marker = new google.maps.Marker({
                position: point,
                icon: app.getPoiShape(point.poiType),
                draggable: false,
                map: app.map
            });
            app.polyLinesMarkers[selectedIndex].push(marker);
        });
    },

    clearPolyLines: function () {
        var app = DataGenerator.getApplication();

        app.polyLines.forEach(function (polyLine) {
            polyLine.setMap(null);
        });
        app.polyLines = [];

        app.polyLinesMarkers.forEach(function (lineMarkers) {
            lineMarkers.forEach(function (marker) {
                marker.setMap(null);
            });
        });
        app.polyLinesMarkers = [];
    },

    addPolyLines: function (routes) {
        if (routes == null || routes.length <= 0) return;
        var app = DataGenerator.getApplication();

        routes.forEach(function (route) {
            app.addPolyLine(route);
        });
    },

    addPolyLine: function (route) {
        if (route.points == null || route.points.length <= 0) return;

        var app = DataGenerator.getApplication();

        var mapObjects = app.createMapObjects(route);

        app.polyLines.push(mapObjects.polyLine);
        app.polyLinesMarkers.push(mapObjects.markers);
    },

    refreshPolyLine: function (index) {
        var app = DataGenerator.getApplication();
        app.polyLines[index].setMap(null);
        app.polyLines[index] = null;

        var currentMarkers = app.polyLinesMarkers[index];
        currentMarkers.forEach(function (marker) {
            marker.setMap(null);
        });
        app.polyLinesMarkers[index] = null;

        var mapObjects = app.createMapObjects(app.options.routes[index]);

        app.polyLines[index] = mapObjects.polyLine;
        app.polyLinesMarkers[index] = mapObjects.markers;
    },

    createMapObjects: function (route) {
        if (route.points == null || route.points.length <= 0) return null;
        var app = DataGenerator.getApplication();

        var currentPolyLine = new google.maps.Polyline(app.polyOptions);
        var path = currentPolyLine.getPath();
        var currentMarkers = [];

        route.points.forEach(function (point) {
            var position = new google.maps.LatLng(point.lat, point.lng);
            position.poiType = point.poiType;
            path.push(position);
            var marker = new google.maps.Marker({
                position: position,
                icon: app.getPoiShape(point.poiType),
                draggable: false,
                map: app.map
            });
            currentMarkers.push(marker);
        });

        currentPolyLine.setMap(app.map);
        return {"polyLine": currentPolyLine, "markers": currentMarkers};
    },

    removePolyLine: function (index) {
        var app = DataGenerator.getApplication();
        app.polyLines[index].setMap(null);

        app.polyLines.splice(index, 1);
        app.clearSelectedMarkers(index);
        app.polyLinesMarkers.splice(index, 1);
    },


    bindData: function (data) {
        var app = DataGenerator.getApplication();
        debugger;
        Ext.getCmp('pedestrians-count').setValue(data.pedestrians.count == null ? 10 : data.pedestrians.count);
        Ext.getCmp('pedestrians-speed').setValue(data.pedestrians.speed == null ? 3 : data.pedestrians.speed);
        Ext.getCmp('bicyclists-count').setValue(data.bicyclists.count == null ? 10 : data.bicyclists.count);
        Ext.getCmp('bicyclists-speed').setValue(data.bicyclists.speed == null ? 20 : data.bicyclists.speed);
        Ext.getCmp('cars-count').setValue(data.cars.count == null ? 10 : data.cars.count);
        Ext.getCmp('cars-speed').setValue(data.cars.speed == null ? 45 : data.cars.speed);

        app.options.startTime = Ext.Date.parse(app.options.startTime, "c");
        Ext.getCmp('start-date').setValue(app.options.startTime == null ? new Date() : app.options.startTime);
        Ext.getCmp('start-time').setValue(app.options.startTime == null ? new Date() : app.options.startTime);
        Ext.getCmp('fluctuation').setValue(app.options.fluctuation == null ? 0.0 : app.options.fluctuation);
        Ext.getCmp('current-file').setText(app.options.fileName);
        Ext.getCmp('file-name').setValue(app.options.fileName);
        Ext.getCmp('track-interval').setValue(data.trackingInterval == null ? 2 : data.trackingInterval);

        if (data.routes == null) return;

        Ext.data.StoreManager.lookup('routesStore').loadData(data.routes);

        app.clearPolyLines();
        app.addPolyLines(data.routes);

        if (app.selectedPolyLineIndex >= 0 && data.routes[app.selectedPolyLineIndex] != undefined) {
            app.selectRouteByIndex(app.selectedPolyLineIndex);

            if (app.selectedPolyLinePointIndex >= 0 &&
                data.routes[app.selectedPolyLineIndex].points[app.selectedPolyLinePointIndex] != undefined) {
                app.polyLinesMarkers[app.selectedPolyLineIndex][app.selectedPolyLinePointIndex].setIcon(app.selectedPointShape);
            }
        }

        if (data.routes == undefined || data.routes == null || data.routes.length <= 0) return;
        if (app.options.wifi != undefined && app.options.wifi !== null) {
            loadWiFiStore();
            createWifiMarkers();
        }
        app.calculateBoundsAndCenter(data);

        app.map.setCenter(data.routesCenter);
        var mapElement = Ext.get('map');
        var zoom = app.getBoundsZoomLevel(data.bounds, {width: mapElement.getWidth(), height: mapElement.getHeight()});
        app.map.setZoom(zoom);
    },

    calculateBoundsAndCenter: function (options) {

        var left = Number.MAX_VALUE;
        var right = -Number.MAX_VALUE;
        var bottom = Number.MAX_VALUE;
        var top = -Number.MAX_VALUE;

        for (var r = 0; r < options.routes.length; r++) {
            var route = options.routes[r];
            for (var c = 0; c < route.points.length; c++) {
                var coord = route.points[c];
                if (coord.lng < left) left = coord.lng;
                if (coord.lng > right) right = coord.lng;
                if (coord.lat < bottom) bottom = coord.lat;
                if (coord.lat > top) top = coord.lat;
            }
        }
        var lat = bottom + (top - bottom) / 2;
        var lng = left + (right - left) / 2;

        options.bounds = JSON.stringify(new google.maps.LatLngBounds(
            new google.maps.LatLng(bottom, left),
            new google.maps.LatLng(top, right)
        ));
        options.routesCenter = JSON.stringify(new google.maps.LatLng(lat, lng))
    },

    getBoundsZoomLevel: function (bounds, mapDim) {
        var WORLD_DIM = {height: 256, width: 256};
        var ZOOM_MAX = 21;

        function latRad(lat) {
            var sin = Math.sin(lat * Math.PI / 180);
            var radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
            return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
        }

        function zoom(mapPx, worldPx, fraction) {
            return Math.floor(Math.log(mapPx / worldPx / fraction) / Math.LN2);
        }

        var ne = bounds.getNorthEast();
        var sw = bounds.getSouthWest();

        var latFraction = (latRad(ne.lat()) - latRad(sw.lat())) / Math.PI;

        var lngDiff = ne.lng() - sw.lng();
        var lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;

        var latZoom = zoom(mapDim.height, WORLD_DIM.height, latFraction);
        var lngZoom = zoom(mapDim.width, WORLD_DIM.width, lngFraction);

        return Math.min(latZoom, lngZoom, ZOOM_MAX);
    },

    updateOptionsObject: function () {
        var app = DataGenerator.getApplication();

        app.options.pedestrians.count = Ext.getCmp('pedestrians-count').getValue();
        app.options.pedestrians.speed = Ext.getCmp('pedestrians-speed').getValue();
        app.options.bicyclists.count = Ext.getCmp('bicyclists-count').getValue();
        app.options.bicyclists.speed = Ext.getCmp('bicyclists-speed').getValue();
        app.options.cars.count = Ext.getCmp('cars-count').getValue();
        app.options.cars.speed = Ext.getCmp('cars-speed').getValue();
        app.options.fluctuation = Ext.getCmp('fluctuation').getValue();
        var index = 1;
        app.options.wifi = [];
        Ext.data.StoreManager.lookup('wifiZoneStore').each(function (rec) {
                app.options.wifi.push({
                    id: index,
                    lat: rec.data.lat,
                    lng: rec.data.lng,
                    whenCrossing: rec.data.whenCrossing,
                    radius: rec.data.radius,
                    timeSpent: rec.data.timeSpent
                });
                index = index + 1;
            }
        );

        var startDate = Ext.getCmp('start-date').getValue();
        var startTime = Ext.getCmp('start-time').getValue();
        startDate.setHours(startTime.getHours(), startTime.getMinutes(), startTime.getSeconds());

        app.options.startTime = startDate.toJSON();
        app.options.trackingInterval = Ext.getCmp('track-interval').getValue();
        app.options.fileName = Ext.getCmp('file-name').getValue();
    },

    deselectAllPoints: function () {
        var app = DataGenerator.getApplication();
        for (var l = 0; l < app.polyLines.length; l++) {
            var polyLine = app.polyLines[l];
            var path = polyLine.getPath();
            for (var p = 0; p < path.length; p++) {
                var point = path.getAt(p);
                app.polyLinesMarkers[l][p].setIcon(app.getPoiShape(point.poiType));
            }
        }
    },

    onRouteSelected: function (sm, selection) {
        var app = DataGenerator.getApplication();

        var item = selection[0];
        var routeId = item.data.id;
        app.selectedPolyLineIndex = routeId - 1;
        app.selectRouteByIndex(app.selectedPolyLineIndex);
    },

    selectRouteByIndex: function (index) {
        var app = DataGenerator.getApplication();
        app.deselectAllPolyLines();
        app.selectPolyLine(index);

        var points = app.options.routes[index].points;
        Ext.data.StoreManager.lookup('pointsStore').loadData(points);
    },

    selectPolyLine: function (index) {
        var app = DataGenerator.getApplication();
        var selectedPolyLine = app.polyLines[index];
        selectedPolyLine.setOptions(app.selectedPolyOptions);
    },

    deselectAllPolyLines: function () {
        var app = DataGenerator.getApplication();
        app.polyLines.forEach(function (item) {
            item.setOptions(app.polyOptions);
        });
        app.deselectAllPoints();
    },

    onPointSelected: function (sm, selection) {
        //Highlight point
        var app = DataGenerator.getApplication();
        app.deselectAllPoints();

        selection.forEach(function (item) {
            var pointId = item.data.id;
            app.selectedPolyLinePointIndex = pointId - 1;
            app.polyLinesMarkers[app.selectedPolyLineIndex][app.selectedPolyLinePointIndex].setIcon(app.selectedPointShape);
        });
    },

    removePoint: function (id) {
        debugger;
        var app = DataGenerator.getApplication();
        var route = app.options.routes[app.selectedPolyLineIndex];
        route.points.splice(id - 1, 1);
        for (var i = 0; i < route.points.length; i++) {
            route.points[i].id = i + 1;
        }
        Ext.data.StoreManager.lookup('pointsStore').loadData(route.points);
        app.refreshPolyLine(app.selectedPolyLineIndex);
    },

    removeFile: function (id) {
        debugger;
        var app = DataGenerator.getApplication();
        var file = app.fileNames[id];
        app.fileNames.splice(id, 1);
        Ext.data.StoreManager.lookup('scenariosListStore').loadData(app.fileNames);
        Ext.Ajax.request({
            url: '/api/files',
            method: 'DELETE',
            params: file.fileName
//            ,
//            success: app.onFilesLoaded()
        });

    },


    disableControls: function (except) {
        var app = DataGenerator.getApplication();
        app.enableControls(false);

        if (except == undefined) return;

        except.forEach(function (item) {
            item.enable();
        });
    },

    enableControls: function (state) {
        if (state == undefined) state = true;

        if (state) {
            Ext.getCmp('btn-create-route').enable();
            Ext.getCmp('btn-create-wifi').enable();
            Ext.getCmp('btn-edit-route').enable();
            Ext.getCmp('btn-remove-route').enable();
            Ext.getCmp('btn-remove-wifi').enable();
            Ext.getCmp('btn-clear-routes').enable();
            Ext.getCmp('routes-grid').enable();
            Ext.getCmp('points-grid').enable();
        }
        else {
            Ext.getCmp('btn-create-route').disable();
            Ext.getCmp('btn-create-wifi').disable();
            Ext.getCmp('btn-edit-route').disable();
            Ext.getCmp('btn-remove-route').disable();
            Ext.getCmp('btn-remove-wifi').disable();
            Ext.getCmp('btn-clear-routes').disable();
            Ext.getCmp('routes-grid').disable();
            Ext.getCmp('points-grid').disable();
        }


    },

    saveOptions: function () {
        var app = DataGenerator.getApplication();
        app.updateOptionsObject();

        Ext.Ajax.request({
            url: '/api/options',
            method: 'POST',
            jsonData: app.options,
            success: function () {
                Ext.toast({
                    html: 'Generator Options Saved',
                    title: 'Status',
                    width: 200,
                    align: 'br'
                });
            }
        });
    },

    saveAsOptions: function () {

        var app = DataGenerator.getApplication();
        app.updateOptionsObject();

        Ext.Ajax.request({
            url: '/api/options',
            method: 'POST',
            jsonData: app.options,
            success: function () {
                Ext.toast({
                    html: 'Generator Options Saved',
                    title: 'Status',
                    width: 200,
                    align: 'br'
                });
                Ext.data.StoreManager.lookup('wifiZoneStore').commitChanges();
                Ext.data.StoreManager.lookup('routesStore').commitChanges();
                Ext.data.StoreManager.lookup('scenariosListStore').commitChanges();
            }
        });


        Ext.getCmp('current-file').setText(app.options.fileName);
    },


    openOptions: function () {
        if (!(window.File && window.FileReader && window.FileList && window.Blob)) {
            alert('The File APIs are not fully supported in this browser.');
            return;
        }

        var fileField = $('#options-file');

        $(fileField).on('change', function (changeEvent) {

            var files = changeEvent.target.files;
            if (files == null || files.length <= 0) return;
            var file = files[0];

            var reader = new FileReader();

            $(reader).on('load', function (loadEvent) {

                var contents = loadEvent.target.result;
                var app = DataGenerator.getApplication();
                app.options = Ext.JSON.decode(contents);
                app.bindData(app.options);
                app.currentFilename = file.name;
                Ext.getCmp('current-file').setText(file.name);
            });
            reader.readAsText(file);
        });
        fileField.click();
    }

});

Ext.application('DataGenerator.app.Application');
