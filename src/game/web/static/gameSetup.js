var socket = io.connect({transports: ['websocket']});
socket.on('gameState', parseGameState);

const tileSize = 30;

var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
ctx.globalCompositeOperation = 'source-over';
let interval = setInterval(parseGameState, 10);
function parseGameState(event) {
    const gameState = JSON.parse(event);

    drawGameBoard(gameState['gridSize']);
    let n = 0;

    for (let player of gameState['players']) {
        n += 1;
        placeCircle(player['x'], player['y'], player['id'] === socket.id ? 'red' : 'blue', 2.0);
        placeSquare(gameState['bases'][n]['x'], gameState['bases'][n]['y'], '#ffffff');
        if(player["id"] === socket.id) {
            ctx.font = "20px Arial";
            ctx.fillStyle = "black";
            ctx.textAlign = "center";
            ctx.fillText("Points: " + player["pts"].toString(), canvas.width - 50, canvas.height - 10);
        }
        if(player["pts"] === gameState["goal"]){
            if(player["id"] === socket.id) {
                ctx.font = "50px Arial";
                ctx.fillStyle = "green";
                ctx.textAlign = "center";
                ctx.fillText("GAME OVER", canvas.width / 2, canvas.height / 2);
                ctx.fillText("YOU WIN", canvas.width / 2, (canvas.height / 2) + 50);
                alert("YOU WIN");
                document.location.reload();
                clearInterval(interval);
            }else{
                ctx.font = "50px Arial";
                ctx.fillStyle = "green";
                ctx.textAlign = "center";
                ctx.fillText("GAME OVER", canvas.width / 2, canvas.height / 2);
                ctx.fillText("YOU LOSE", canvas.width / 2, (canvas.height / 2) + 50);
                alert("YOU LOSE");
                document.location.reload();
                clearInterval(interval);
            }
        }
    }
}

function drawGameBoard(gridSize) {

    const gridWidth = gridSize['x'];
    const gridHeight = gridSize['y'];

    ctx.clearRect(0, 0, gridWidth * tileSize, gridHeight * tileSize);

    canvas.setAttribute("width", gridWidth * tileSize);
    canvas.setAttribute("height", gridHeight * tileSize);

    ctx.strokeStyle = '#bbbbbb';
    for (let j = 0; j <= gridWidth; j++) {
        ctx.beginPath();
        ctx.moveTo(j * tileSize, 0);
        ctx.lineTo(j * tileSize, gridHeight * tileSize);
        ctx.stroke();
    }
    for (let k = 0; k <= gridHeight; k++) {
        ctx.beginPath();
        ctx.moveTo(0, k * tileSize);
        ctx.lineTo(gridWidth * tileSize, k * tileSize);
        ctx.stroke();
    }

}

function placeSquare(x, y, color) {
    ctx.fillStyle = color;
    ctx.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
    ctx.strokeStyle = 'black';
    ctx.strokeRect(x * tileSize, y * tileSize, tileSize, tileSize);
}

function placeCircle(x, y, color, size) {
    ctx.fillStyle = color;
    ctx.beginPath();
    ctx.arc(x * tileSize,
        y * tileSize,
        size / 10.0 * tileSize,
        0,
        2 * Math.PI);
    ctx.fill();
    ctx.strokeStyle = 'black';
    ctx.stroke();
}
