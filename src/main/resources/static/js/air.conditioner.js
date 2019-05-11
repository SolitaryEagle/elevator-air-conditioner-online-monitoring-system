//////////////////////////   user common variables defined     /////////////////////////////////////////

var airConditionerDefaultLayerMsgOptions = {time: 3000, skin: 'layui-bg-red', offset: '128px'};


////////////////////////  air-conditioner common fragments defined  ////////////////////////////////////




////////////////////////  air-conditioner common function defined  ////////////////////////////////////

// 地址校验
function checkAddressInfoAndTip() {
    var province = $("#address-province").val().trim();
    if (typeof province == "undefined" || province == null || province.length <= 0) {
        kingLayerMsg('请选择省份!', airConditionerDefaultLayerMsgOptions);
        return false;
    }
    var city = $("#address-city").val().trim();
    if (typeof city == "undefined" || city == null || city.length <= 0) {
        kingLayerMsg('请选择城市!', airConditionerDefaultLayerMsgOptions);
        return false;
    }
    var district = $("#address-district").val().trim();
    if (typeof district == "undefined" || district == null || district.length <= 0) {
        kingLayerMsg('请选择地区!', airConditionerDefaultLayerMsgOptions);
        return false;
    }
    var detail = $("#address-detail").val().trim();
    if (typeof detail != "undefined" && detail != null && detail.indexOf(' ') !== -1) {
        kingLayerMsg('详细地址中不能包含空格!', airConditionerDefaultLayerMsgOptions);
        return false;
    }
    return true;
}
