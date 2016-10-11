/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


if (window.WebSocket) {
    var ws = new WebSocket("ws://localhost:8080/mod250_auction/push");
    ws.onmessage = function(event) {
        if(window["updateListOfAucitons"]){
            window["updateListOfAucitons"]();
            
            console.log("jeej");
        }
        else{
            $('#thisdiv').load(document.URL +  ' #thisdiv');
            console.log("lol");
        }
        
    };
}
else {
    // Bad luck. Browser doesn't support it. Consider falling back to long polling.
    // See http://caniuse.com/websockets for an overview of supported browsers.
    // There exist jQuery WebSocket plugins with transparent fallback.
}
