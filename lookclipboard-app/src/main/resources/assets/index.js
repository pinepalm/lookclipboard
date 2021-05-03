/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 19:28:51
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-03 23:46:20
 * @Description: index.js
 */
function addRecord(recordJson) {
    var record = JSON.parse(recordJson);
    document.getElementById("parsed").innerHTML = record.content;
    document.getElementById("raw").innerHTML = recordJson;
}

function testSetOpacity() {
    settingsService.setOpacity(0.5);
}

function testSetAlwaysOnTop() {
    settingsService.setAlwaysOnTop(true);
}

function testGetAllRecords() {
    var condition = {}
    var recordsCount = clipboardService.getRecordsCountByCondition(JSON.stringify(condition));
    document.getElementById("testGetAllRecords").innerHTML = recordsCount;
}