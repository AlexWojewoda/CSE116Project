const electron = require('electron');
const app = electron.app;
const BrowserWindow = electron.BrowserWindow;

var mainWindow = null;
var prefWindow = null;

app.on('window-all-closed', function(){
    if(process.platform !== 'darwin'){
        app.quit();
    }
});

app.on('ready', function(){
    mainWindow = new BrowserWindow({width: 800, height: 600});
    prefWindow = new BrowserWindow({width: 800, height: 600});
    mainWindow.loadURL('http://localhost:8080/');
    prefWindow.loadURL('http://localhost:8080/');
    // mainWindow.webContents.openDevTools();
    mainWindow.on('closed', function(){
        mainWindow = null;
    });
});