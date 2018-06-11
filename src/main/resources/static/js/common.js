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


function hasClass(elements, cName) {
    return !!elements.className.match(new RegExp("(\\s|^)" + cName + "(\\s|$)"));
}

function addClass(elements, cName) {
    elements.className += " " + cName;
}

function removeClass(elements, cName) {
    elements.className = elements.className.replace(new RegExp("(\\s|^)" + cName + "(\\s|$)"), " ");
}

function setCookie(c_name,value,expiredays) {
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+expiredays)
    document.cookie=c_name+ "=" +escape(value)+
        ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}
function getCookie(c_name) {
    if (document.cookie.length>0)
    {
        c_start=document.cookie.indexOf(c_name + "=")
        if (c_start!=-1)
        {
            c_start=c_start + c_name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))
        }
    }
    return ""
}