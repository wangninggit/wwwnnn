var fs = require("fs");
var permissionArr = JSON.parse(fs.readFileSync("./permission.json"));
var all = "";
permissionArr.forEach(function (it, index) {
    var t= parseInt(Date.now());
    var sql = `INSERT INTO \`permission\` VALUES (${index+1}, ${it.page}, '${it.name}', '${it.label}', '${it.type}', NULL, NULL, ${it.sort}, ${t}, ${t});\n`
    all = all + sql;
});

fs.writeFileSync("permission.sql", all);

