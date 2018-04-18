function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function addUrlParam(url, name, value) {
    if (url.indexOf("?") > -1) {
        return url + "&" + name + "=" + value;
    } else {
        return url + "?" + name + "=" + value;
    }
}

function ajaxRequest(url, type, data, successCallback) {
    $.ajax({
        url: url,
        type: type,
        data: data,
        success: function (data) {
            var obj = eval(data);
            if (obj.code == '100') {
                successCallback(obj);
            } else {
                alert("操作失败:(" + obj.code + "): " + obj.msg);
            }
            ;
        },
        error: function () {
            alert("操作失败，请重试！");
        }
    });
}

function CardWebsocket(url, openCallback, msgCallback, closeCallback) {
    this.websocket = new WebSocket(url);
    bind(this.websocket);

    function bind(ws) {

        ws.onopen = function() {
            console.log('connect');
            if (openCallback != undefined) {
                openCallback();
            }
        };

        ws.onmessage = function(e) {
            var msgJson = e.data;
            console.log('rev: ' + msgJson);
            if (msgCallback != undefined) {
                msgCallback(JSON.parse(msgJson));
            }

        };

        ws.onclose = function() {
            console.log('close reconnect');

            if (closeCallback != undefined) {
                closeCallback();
            }

        };
    }

    this.reconnect = function() {
        this.websocket = new WebSocket(url);
        bind(this.websocket);
    };

    this.sendMsg = function(msgObj) {
        if (this.websocket == undefined || this.websocket == null || this.websocket.readyState != 1) {
            alert("未连接");
            return;
        }

        var json = JSON.stringify(msgObj);
        console.log('send: ' + json);
        this.websocket.send(json);
    };

    this.state = function(){
        return this.websocket.readyState;
    }
}

/*
function initWebsocket(url, revCallback) {
    var websocket = new WebSocket(url);

    websocket.onopen = function() {
        console.log('connect');
    };

    websocket.onmessage = function(e) {
        var msgJson = e.data;
        console.log('rev: ' + msgJson);
        if (revCallback != undefined) {
            revCallback(JSON.parse(msgJson));
        }

    };

    websocket.onclose = function() {
        console.log('close reconnect');

    };

    return websocket;
}

function sendmsg(websocket, msgObj) {
    var json = JSON.stringify(msgObj);
    console.log('send: ' + json);
    websocket.onsend(json);
}*/
