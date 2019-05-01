var fs = require("fs");
var permissionArr = JSON.parse(fs.readFileSync("./permission.json"));
var roleArr = JSON.parse(fs.readFileSync("./role.json"));

var perLength = permissionArr.length;
var all = "";
var index = 1;
var t= parseInt(Date.now());
var fPers = [15,16,27,28];
//admin
roleArr.forEach(function (r, rIndex) {
    if (r.name == "admin") {
        for(var i = 1; i <= perLength; i +=1) {
            var sql = `INSERT INTO \`role_permission\` VALUES (${index}, ${rIndex + 1}, ${i}, ${t}, ${t});\n`;
            all = all + sql;
            index = index + 1;
        }
    }
    if (r.name == "boss") {
        for(var i = 1; i <= 28; i +=1) {
            var sql = `INSERT INTO \`role_permission\` VALUES (${index}, ${rIndex + 1}, ${i}, ${t}, ${t});\n`;
            all = all + sql;
            index = index + 1;
        }
    }
    if (r.name == "finance") {
        for(var i = 0; i < fPers.length; i +=1) {
            var sql = `INSERT INTO \`role_permission\` VALUES (${index}, ${rIndex + 1}, ${fPers[i]}, ${t}, ${t});\n`;
            all = all + sql;
            index = index + 1;
        }
    }
});

fs.writeFileSync("role-permission.sql", all);
