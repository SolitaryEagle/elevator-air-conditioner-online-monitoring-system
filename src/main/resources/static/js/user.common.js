////////////////////////  user common fragments defined  ////////////////////////////////////

var LOGO_DIV_HTML_USER_MODEL =
    '<div class="login-brand">' +
    '   <img src="/image/kodinger.jpg" th:src="@{/image/kodinger.jpg}" alt="logo">' +
    '</div>';

var FOOTER_DIV_HTML_USER_MODEL =
    '<footer class="login-footer">' +
    '   河海大学常州校区 2015级物联网工程学院 毕业设计' +
    '</footer>';


////////////////////////  user common function defined  ////////////////////////////////////

// 初始化用户模块的LogoDiv
function initUserModelLogoDiv() {
    $("#logo-div-id").replaceWith(LOGO_DIV_HTML_USER_MODEL);
}

// 初始化用户模块的FooterDiv
function initUserModelFooterDiv() {
    $("#footer-div-id").replaceWith(FOOTER_DIV_HTML_USER_MODEL);
}

// 检测注册用户名
function checkUsername(usernameId) {
    var username = document.getElementById(usernameId).value.trim();
    if (username.length === 0) {
        return 1;
    }
    if (username.indexOf(' ') !== -1) {
        return 2;
    }
    if (username.length > 20) {
        return 3;
    }
    if (username.indexOf('@') !== -1) {
        return 4;
    }
    return 0;
}

// 检测注册邮箱
function checkEmail(emailId) {
    var email = document.getElementById(emailId).value.trim();
    var regex = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    return regex.test(email) ? 0 : 1;
}

// 检测注册密码
function checkPassword(passwordId) {
    var password = document.getElementById(passwordId).value;
    if (password.indexOf(' ') !== -1) {
        return 1;
    }
    if (password.length < 8 || password.length > 16) {
        return 2;
    }
    return 0;
}

// 检测重复密码
function checkRepassword(passwordId, repasswordId) {
    var password = document.getElementById(passwordId).value;
    var repassword = document.getElementById(repasswordId).value;
    return password === repassword ? 0 : 1;
}

// 检测电话号码
function checkPhoneNumber(phoneNumberId) {
    var phoneNumber = document.getElementById(phoneNumberId).value.trim();
    var regex = /^1\d{10}$/;
    return regex.test(phoneNumber) ? 0 : 1;
}

// 检测激活码
function checkActivationCode(activationCodeId) {
    var code = document.getElementById(activationCodeId).value.trim();
    var regex = /^\d{6}$/;
    return regex.test(code) ? 0 : 1;
}

/////////////////////////////////////////////////////////////////////////////////////////////

// 检查用户名或邮箱并提示
function checkUsernameOrEmailAndTip(usernameOrEmailId) {
    var result1 = checkUsername(usernameOrEmailId);
    var result2 = checkEmail(usernameOrEmailId);
    if (result1 === 0 || result2 === 0) {
        return true;
    }
    layer.msg("用户名或邮箱有误，请重新输入！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
    return false;
}


// 检测注册用户名并提示
function checkUsernameAndTip(usernameId) {
    var result = checkUsername(usernameId);
    if (result === 1) {
        layer.msg("用户名不能为空！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
        return false;
    }
    if (result === 2) {
        layer.msg("用户名不能包含空格！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
        return false;
    }
    if (result === 3) {
        layer.msg("用户名的长度不能超过20个字符！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
        return false;
    }
    if (result === 4) {
        layer.msg("用户名不能包含@字符！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
        return false;
    }
    return true;
}

// 检测注册邮箱并提示
function checkEmailAndTip(emailId) {
    var result = checkEmail(emailId);
    if (result === 1) {
        layer.msg("邮箱格式不正确！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
        return false;
    }
    return true;
}

// 检测注册密码并提示
function checkPasswordAndTip(passwordId) {
    var result = checkPassword(passwordId);
    if (result === 1) {
        layer.msg("密码不能包含空格！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
        return false;
    }
    if (result === 2) {
        layer.msg("密码长度必须在8到16之间！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
        return false;
    }
    return true;
}

// 检测重复密码并提示
function checkRepasswordAndTip(passwordId, repasswordId) {
    var result = checkRepassword(passwordId, repasswordId);
    if (result === 1) {
        layer.msg("两次密码不一致！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
        return false;
    }
    return true;
}

// 检测电话号码并提示
function checkPhoneNumberAndTip(phoneNumberId) {
    var result = checkPhoneNumber(phoneNumberId);
    if (result === 1) {
        layer.msg("电话号码格式错误！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
        return false;
    }
    return true;
}

// 检测激活码并提示
function checkActivationCodeAndTip(activationCodeId) {
    var result = checkActivationCode(activationCodeId);
    if (result === 1) {
        layer.msg("激活码格式错误！", {time: 3000, skin: 'layui-bg-red', offset: '50px'});
        return false;
    }
    return true;
}

// 按钮发送邮件
function sendEmail(receiverEmailId, btnId) {
    if (checkEmailAndTip(receiverEmailId)) {
        // 发送邮件
        $.get(USER_BASE_MAPPING_V1 + "/activation",
            {receiverEmail: $("#" + receiverEmailId).val()},
            function (result, status, xhr) {
                var obj = JSON.parse(result);
                // 邮件发送成功，添加倒计时！
                if (obj.code === 200) {
                    layer.msg(obj.data, {time: 3000, skin: 'layui-bg-red', offset: '50px'});
                    timer(btnId, 60);
                } else {
                    layer.msg(obj.message, {time: 3000, skin: 'layui-bg-red', offset: '50px'});
                }
            });
    }
}

















