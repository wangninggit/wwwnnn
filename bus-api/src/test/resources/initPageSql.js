var fs = require("fs");
var pageArr = JSON.parse(fs.readFileSync("./module.json"));
var all = "";
pageArr.forEach(function (it) {
    var t= parseInt(Date.now());
    var sql = `INSERT INTO \`page\` VALUES (${it.id}, '${it.name}', '${it.label}','${it.description}', ${t}, ${t});\n`
    all = all + sql;
});

fs.writeFileSync("page.sql", all);

