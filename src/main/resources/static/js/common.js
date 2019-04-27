
// 提示msg信息
function layerMsg(msg) {
    layer.msg(msg, {time:3000, skin:'layui-bg-red', offset:'50px'});
}

// 按钮定时器
function timer(btn, time) {
    btn.innerText = time <= 0 ? "发送邮件" : "" + time + " 秒";
    var handler = setInterval(function () {
        if (time <= 0) {
            clearInterval(handler);
            btn.className = "login-btn";
            btn.innerText = "发送邮件";
            return false;
        } else {
            btn.innerText = "" + (time--) + " 秒";
        }
    }, 1000);
}

// 提示给定input的id的value的非空白内容
function layerMsgInputValueById(id) {

    var message = $("#" + id).val();
    if (typeof message != "undefined" && message != null && message.length > 0) {
        layerMsg(message);
    }
}

