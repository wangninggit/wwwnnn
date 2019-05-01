# 华生基因商业数据服务分析系统后端项目

## 包含子项目

* [API](./bus-api)

## SQL文件

在根目录下的`hs_bus.sql`.

修改`longtext`类型最大长度为1G方法：
> 1. 执行命令`set global max_allowed_packet = 1024*1024*1024;`
> 2. 关闭命令行窗口执行命令`show variables like 'max_allo%';`查看设置是否生效

## 编译

在项目根目录运行 `mvn package`

## 运行

### API

#### 测试环境

    java -jar bus-api-1.0.jar

要改变端口号的话

    java -jar bus-api-1.0.jar --server.port=8000 

#### 生产环境

    java -jar bus-api-1.0.jar --spring.profiles.active=prod

要改变端口号的话

    java -jar bus-api-1.0.jar --spring.profiles.active=prod --server.port=8000

### Engine


#### 测试环境

    java -jar bus-engine-1.0.jar 

#### 生产环境

    java -jar bus-engine-1.0.jar --spring.profiles.active=prod


## 健康检查

### API

- Path：/api/actuator/health
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/actuator/health
```

返回成功示例   

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	{
        "status": "UP",
        "details": {
            "diskSpace": {
                "status": "UP",
                "details": {
                    "total": 121123069952,
                    "free": 52990783488,
                    "threshold": 10485760
                }
            },
            "db": {
                "status": "UP",
                "details": {
                    "database": "MySQL",
                    "hello": 1
                }
            }
        }
    }
```

返回字段解释：

- status 整体运行状态（UP：正常；DOWN：不正常），各检测项中任何一项为DOWN，整体状态即为DOWN
- details 健康明细
  - diskSpace 磁盘使用信息
    - status 运行状态（UP：正常；DOWN：不正常）
    - details 明细
      - total 总共大小
      - free 空闲大小
      - threshold 阈值；剩余空间小于该值时认为不健康
  - db 数据库连接信息
    - status 运行状态（UP：正常；DOWN：不正常）
    - details 明细
      - database 数据库名称
      - hello 运行是否正常（1：正常；其他：不正常）

## 华生测试接口

* tokenUrl: http://hdas.hsgene.cn/api/sync/token
* dataUrk: http://hdas.hsgene.cn/api/sync/data
* AK：cd71ca0e5b17457db5f1aa6f6db4d6ff
* SK：fb52f9314ae1404090a5a28ee3985e44

