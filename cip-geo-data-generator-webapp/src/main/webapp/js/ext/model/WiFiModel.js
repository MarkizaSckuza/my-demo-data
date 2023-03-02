Ext.define('DataGenerator.model.WiFiModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name:'id',type:"int"},
        {name:'lat',type:"number"},
        {name:'lng',type:"number"},
        {name: 'radius', type: 'number'},
        {name:'whenCrossing',type:"int"},
        {name:'timeSpent',type:"int"}
       ]
//
//    // Override to keep the last 10 prices in the trend field
//    set: function(fieldName, value) {
//        if (fieldName === 'price') {
//            this.callParent([{
//                price: value,
//                trend: this.addToTrend(fieldName.price)
//            }]);
//        }
//        else {
//            if (typeof fieldName !== 'string' && 'price' in fieldName) {
//                fieldName.trend = this.addToTrend(fieldName.price);
//            }
//            this.callParent(arguments);
//        }
//    },
//
//    // Override to keep the last 10 prices in the trend field
//    addToTrend: function(value) {
//        var trend = this.data.trend.concat(value);
//
//        if (trend.length > 10) {
//            Ext.Array.splice(trend, 0, trend.length - 10);
//        }
//        return trend;
//    }
});