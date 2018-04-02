var webSocketUrl = "ws:///127.0.0.1:6006/";
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