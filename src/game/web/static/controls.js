var kS = {
    "w": false,
    "a": false,
    "s": false,
    "d": false
};

function handleEvent(event, toSet){
    if(event.key === "w" || event.key === "ArrowUp"){
        if(kS["w"] !== toSet){
            kS["w"] = toSet;
            socket.emit("keyStates", JSON.stringify(kS));
        }
    }else if(event.key === "a" || event.key === "ArrowLeft"){
        if(kS["a"] !== toSet){
            kS["a"] = toSet;
            socket.emit("keyStates", JSON.stringify(kS));
        }
    }else if(event.key === "s" || event.key === "ArrowDown"){
        if(kS["s"] !== toSet){
            kS["s"] = toSet;
            socket.emit("keyStates", JSON.stringify(kS));
        }
    }else if(event.key === "d" || event.key === "ArrowRight"){
        if(kS["d"] !== toSet){
            kS["d"] = toSet;
            socket.emit("keyStates", JSON.stringify(kS));
        }
    }
}

document.addEventListener("keydown", function (event) {handleEvent(event, true)},false);
document.addEventListener("keyup", function (event) {handleEvent(event, false)},false);
