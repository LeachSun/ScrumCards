var webUrl = "http://192.168.1.23:5005/";
var webSocketUrl = "ws:///192.168.1.23:6006/";
function Msg(type, content) {

    this.type = type;
    if (content != undefined && content != null) {
        this.content = content;
    }
}

function MemberRegMsg(nickname) {
    this.nickname = nickname;
}
function MemberSubmitMsg(point) {
    this.point = point;
}