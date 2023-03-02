Ext.define("DataGenerator.ui.FilesGrid", {
    extend: 'Ext.grid.Panel',
    title: 'Option files',
    id: 'files-grid',
    store: Ext.data.StoreManager.lookup('scenariosListStore'),
    columns: [
        { text: 'File name', dataIndex: 'fileName',width: '70%'},
        {
            xtype:'actioncolumn',
            width:24,
            items: [{
                icon: 'img/delete.png',
                tooltip: 'Delete',
                handler: function (grid, rowIndex, colIndex) {
                    var app = DataGenerator.getApplication();
//                    var rec = grid.getStore().getAt(rowIndex);
//                    var id = rec.get('id');
                    app.removeFile(rowIndex);
                }
            }]
        },
        {
            xtype:'actioncolumn',
            width:24,
            items: [{
                icon: 'img/open.png',
                tooltip: 'Open',
                handler: function (grid, rowIndex, colIndex) {
                    var rec = grid.getStore().getAt(rowIndex);
                    var app = DataGenerator.getApplication();
                    Ext.Ajax.request({
                        url: '/api/optionsload',
                        method: 'POST',
                        jsonData : rec.get('fileName'),
                        success: app.onOptionsLoaded
                    });



                }
            }]
        }
    ],
    height: 400,
    viewConfig: {forceFit: true}
});