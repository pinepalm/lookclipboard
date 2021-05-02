/*
 * @Author: Zhe Chen
 * @Date: 2021-04-22 19:28:51
 * @LastEditors: Zhe Chen
 * @LastEditTime: 2021-05-02 17:29:04
 * @Description: file content
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
    var records = JSON.parse(clipboardService.getAllRecords());
    document.getElementById("testGetAllRecords").innerHTML = records.length;
}