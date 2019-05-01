# 华生基因商业数据服务分析系统API

## API列表

### 用户

#### 用户登录

* Path：/api/login
* Method：POST
* Success Code：201
* Failure Code：400、401、404、500

请求示例：

	POST http://domain.com/api/login
	Body:
		{
			"email": "12306@qq.com",
			"password": "12345678"
		}

请求字段解释：

* `email` 邮箱，必填
* `password` 密码，必填

返回成功示例：
	
	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1SWQiOiIzIiwiZXhwIjoxNTI4NDkyMTAzLCJqdGkiOiIxNDZhZTUyYy0zMTNhLTQ4ZTctODZlYi05NjRlZmM0MTA1OTQifQ.AzW4UZzvcltOTIXrO20Kn1chpWrhf8_C1EFlTXyqUw4"
	    }

返回字段解释：

* `token` token,之后的请求接口都必须在请求头中传该token

#### 用户退出

* Path：/api/logout
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/logout
	Header: 
		x-auth: {登录时返回的token}	

返回成功：

* 删除该token

#### 用户id查询用户

* Path：/api/users/{id}
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/users/1
	Header: 
		x-auth: {登录时返回的token}

Path请求字段解释：

* `id` 用户id，必填

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "activate": false,
	        "createTime": 1528269149000,
	        "deleted": false,
	        "email": "admin@viathink.com",
	        "id": 1,
	        "nickname": "admin",
	        "phoneNumber": "18653889398",
	        "roleIds": [
	            2,
	            1
	        ],
	        "updateTime": 1528269149000
	    }

返回字段解释：
	
* id 用户id
* email 邮箱
* nickname 昵称
* phoneNumber 手机号
* activate 是否启用
* deleted 是否删除(软删除)
* roleIds 用户拥有的角色
	 createTime 创建时间	
* updateTime 更新时间

未授权示例

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "error": 未授权
	    }

#### 用户列表分页

* Path：/api/users
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/users
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"phoneNumber": "18435155200",
			"email": "12306@qq.com",
			"nickname":"xiaoxi",
			"deleted":true,
	        "activate":false,
	        "roleName":"管理",
	        "pageNum":1,
	        "pageSize":10
		}

请求字段解释：

* `phoneNumber` 手机，选填
* `email` 邮箱，选填
* `nickname` 昵称，选填
* `deleted` 是否删除，选填
* `activate` 是否启用，选填
* `roleName` 角色，选填
* `pageNum` 当前页，选填，默认为1
* `pageSize` 页数，选填，默认为10


返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "endRow": 4,
	        "firstPage": 1,
	        "hasNextPage": false,
	        "hasPreviousPage": false,
	        "isFirstPage": true,
	        "isLastPage": true,
	        "lastPage": 1,
	        "list": [
	            {
	                "activate": false,
	                "createTime": 1528269149000,
	                "deleted": false,
	                "email": "lrq@qq.com",
	                "id": 4,
	                "roles": "领导,管理员",
	                "nickname": "lra",
	                "phoneNumber": "18435155883",
	                "updateTime": 1528269149000
	            },
	            {
	                "activate": false,
	                "createTime": 1528269149000,
	                "deleted": false,
	                "email": "finance@viathink.com",
	                "id": 3,
	                "roles": "领导,管理员",
	                "nickname": "finance",
	                "phoneNumber": "18653889398",
	                "updateTime": 1528269149000
	            },
	            {
	                "activate": false,
	                "createTime": 1528269149000,
	                "deleted": false,
	                "email": "boss@viathink.com",
	                "id": 2,
	                "roles": "领导,管理员",
	                "nickname": "boss",
	                "phoneNumber": "18653889398",
	                "updateTime": 1528269149000
	            },
	            {
	                "activate": false,
	                "createTime": 1528269149000,
	                "deleted": false,
	                "email": "admin@viathink.com",
	                "id": 1,
	                "roles": "领导,管理员",
	                "nickname": "admin",
	                "phoneNumber": "18653889398",
	                "updateTime": 1528269149000
	            }
	        ],
	        "navigateFirstPage": 1,
	        "navigateLastPage": 1,
	        "navigatePages": 8,
	        "navigatepageNums": [
	            1
	        ],
	        "nextPage": 0,
	        "pageNum": 1,
	        "pageSize": 10,
	        "pages": 1,
	        "prePage": 0,
	        "size": 4,
	        "startRow": 1,
	        "total": 4
	    }

返回字段解释：
	
* pageNum 当前页
* pageSize 页数
* pages 总页数
* total 总数
	 list 数据数组	
* id 用户id
* email 邮箱
* nickname 昵称
* phoneNumber 手机号
* activate 是否启用
* deleted 是否删除(软删除)
* roles 角色
	 createTime 创建时间	
* updateTime 更新时间

#### 添加用户

* Path：/api/users
* Method：POST
* Success Code：201
* Failure Code：400、401、404、500

请求示例：

	POST http://domain.com/api/users
	Body:
		{
	        "phoneNumber":"18535155600",
	        "email":"lrq5@qq.com",
	        "nickname":"xiaoxi1",
	        "password":"123456",
	        "activate":true,
	        "roleIds":[1,3,6]
	    }

请求字段解释：

* `phoneNumber` 手机，必填
* `email` 邮箱，必填
* `nickname` 昵称，必填
* `activate` 是否启用，必填
* `password` 密码，必填
* `roleIds` 用户角色，必填

返回成功示例：
	
	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "id": 5
	    }

返回字段解释：

* `id` 新添加用户的id

#### 删除用户

* Path：/api/users/{id}
* Method：DELETE
* Success Code：201
* Failure Code：400、401、404、500

请求示例：

	DELETE http://domain.com/api/users/5
	Header: 
		x-auth: {登录时返回的token}

Path请求字段解释：

* `id` 用户id

#### 更新用户

* Path：/api/users/{id}
* Method：PUT
* Success Code：201
* Failure Code：400、401、404、500

请求示例：

	PUT http://domain.com/api/users/5
	Header: 
		x-auth: {登录时返回的token}
	Body:
		{
	        "phoneNumber":"18535155600",
	        "email":"lrq5@qq.com",
	        "nickname":"xiaoxi1",
	        "activate":true,
	        "roleIds":[1,3,6]
	    }

请求字段解释：

* `id` 用户id
* `phoneNumber` 手机，必填
* `email` 邮箱，必填
* `nickname` 昵称，必填
* `activate` 是否启用，必填
* `roleIds` 用户角色，必填

返回成功示例：
	
	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "id": 5
	    }

返回字段解释：

* `id` 更新用户的id

#### 修改密码

* Path：/api/users/{id}/password
* Method：PUT
* Success Code：201
* Failure Code：400、401、404、500

请求示例：

	PUT http://domain.com/api/users/13/password
	Header: 
		x-auth: {登录时返回的token}
	Body:
		{
			"password":"1234567",
			"confirmPassword":"1234567"
		}

请求字段解释：

* `password` 密码，必填
* `confirmPassword` 确认密码，必填

返回成功示例：
	
	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "id": 13
	    }

返回字段解释：

* `id` 更新用户的id

#### 获取当前登录用户详细信息

* Path：/api/users/current
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET https://domain.com/api/users/current
	Header: 
		x-auth: {登录时返回的token}

返回成功示例  

    {
        "activate": false,
        "createTime": 1528269149000,
        "deleted": false,
        "email": "admin@viathink.com",
        "id": 1,
        "nickname": "admin",
        "permissions": {
            "role": {
                "permissions": {
                    "add": true,
                    "get": true,
                    "update": true,
                    "list": true,
                    "delete": false
                },
                "description": "角色管理"
            },
            "report:testing-item-contrast": {
                "permissions": {
                    "query": true,
                    "export": false
                },
                "description": "检测项目对比"
            },
            "report:profits": {
                "permissions": {
                    "query": false,
                    "export": false
                },
                "description": "利润表"
            },
            "permission": {
                "permissions": {
                    "add": false,
                    "get": false,
                    "update": false,
                    "list": false,
                    "delete": false
                },
                "description": "权限管理"
            },
            "query:income-detail": {
                "permissions": {
                    "query": false,
                    "export": false
                },
                "description": "收入明细表"
            },
            "query:province-contrast": {
                "permissions": {
                    "query": false,
                    "export": false
                },
                "description": "省份对比"
            },
            "query:testing-item-contrast": {
                "permissions": {
                    "query": false,
                    "export": false
                },
                "description": "检测项目对比"
            },
            "report:province-contrast": {
                "permissions": {
                    "query": false,
                    "export": false
                },
                "description": "省份对比"
            },
            "report:business-detail": {
                "permissions": {
                    "query": false,
                    "export": false
                },
                "description": "业务明细表"
            },
            "report:income-detail": {
                "permissions": {
                    "query": false,
                    "export": false
                },
                "description": "收入明细表"
            },
            "query:region-contrast": {
                "permissions": {
                    "query": false,
                    "export": false
                },
                "description": "大区对比"
            },
            "query:business-detail": {
                "permissions": {
                    "query": true,
                    "export": true
                },
                "description": "业务明细表"
            },
            "report:region-contrast": {
                "permissions": {
                    "query": false,
                    "export": false
                },
                "description": "大区对比"
            },
            "page": {
                "permissions": {
                    "add": false,
                    "get": false,
                    "update": false,
                    "list": false,
                    "delete": false
                },
                "description": "模块管理"
            },
            "user": {
                "permissions": {
                    "add": false,
                    "get": false,
                    "update": false,
                    "list": false,
                    "delete": false
                },
                "description": "用户管理"
            },
            "query:daily-detail": {
                "permissions": {
                    "query": true,
                    "export": true
                },
                "description": "日统计查询"
            }
        },
        "phoneNumber": "18653889398",
        "roles": [
            {
                "createTime": 1528269419000,
                "id": 2,
                "label": "管理员",
                "name": "admin",
                "updateTime": 1528269419000
            },
            {
                "createTime": 1528269419000,
                "id": 1,
                "label": "领导",
                "name": "boss",
                "updateTime": 1528269419000
            }
        ],
        "updateTime": 1528269149000
    }

### 角色

#### 角色列表分页

* Path：/api/roles
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/roles
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"name": "boss",
			"label": "领导",
	        "pageNum":1,
	        "pageSize":10
		}

请求字段解释：

* `name` 角色名称，选填
* `label` 标签，选填
* `pageNum` 当前页，选填，默认为1
* `pageSize` 页数，选填，默认为10


返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "endRow": 2,
	        "firstPage": 1,
	        "hasNextPage": true,
	        "hasPreviousPage": false,
	        "isFirstPage": true,
	        "isLastPage": false,
	        "lastPage": 2,
	        "list": [
	            {
	                "createTime": 1528269419000,
	                "id": 1,
	                "label": "领导",
	                "name": "boss",
	                "updateTime": 1528269419000
	            },
	            {
	                "createTime": 1528269419000,
	                "id": 2,
	                "label": "管理员",
	                "name": "admin",
	                "updateTime": 1528269419000
	            }
	        ],
	        "navigateFirstPage": 1,
	        "navigateLastPage": 2,
	        "navigatePages": 8,
	        "navigatepageNums": [
	            1,
	            2
	        ],
	        "nextPage": 2,
	        "pageNum": 1,
	        "pageSize": 2,
	        "pages": 2,
	        "prePage": 0,
	        "size": 2,
	        "startRow": 1,
	        "total": 4
	    }

返回字段解释：
	
* pageNum 当前页
* pageSize 页数
* pages 总页数
* total 总数
	 list 数据数组	
* id 角色id
* name 角色名
* label 标签名
	 createTime 创建时间	
* updateTime 更新时间

#### 所有角色（不分页）

* Path：/api/roles/all
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/roles/all
	Header: 
		x-auth: {登录时返回的token}

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		[
	        {
	            "createTime": 1528269419000,
	            "id": 1,
	            "label": "领导",
	            "name": "boss",
	            "updateTime": 1528269419000
	        },
	        {
	            "createTime": 1528269419000,
	            "id": 2,
	            "label": "管理员",
	            "name": "admin",
	            "updateTime": 1528269419000
	        },
	        {
	            "createTime": 1528269419000,
	            "id": 3,
	            "label": "财务",
	            "name": "finance",
	            "updateTime": 1528269419000
	        },
	        {
	            "createTime": 1528856882000,
	            "id": 6,
	            "label": "游客1",
	            "name": "tour1",
	            "updateTime": 1528856882000
	        },
	        {
	            "createTime": 1528865480000,
	            "id": 7,
	            "label": "游客1",
	            "name": "tour1",
	            "updateTime": 1528865480000
	        }
	    ]

返回字段解释：
* id 角色id
* name 角色名
* label 标签名
	 createTime 创建时间	
* updateTime 更新时间

#### 添加角色

* Path：/api/roles
* Method：POST
* Success Code：201
* Failure Code：400、401、404、500

请求示例：

	POST http://domain.com/api/roles
	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "name":"tour",
	        "label":"游客2",
	        "permissions": {
	            "role": {
	                "permissions": {
	                    "add": true,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "角色管理"
	            },
	            "report:testing-item-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "检测项目对比"
	            },
	            "report:profits": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "利润表"
	            },
	            "permission": {
	                "permissions": {
	                    "add": false,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "权限管理"
	            },
	            "query:income-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "收入明细表"
	            },
	            "query:province-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "省份对比"
	            },
	            "query:testing-item-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "检测项目对比"
	            },
	            "report:province-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "省份对比"
	            },
	            "report:business-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "业务明细表"
	            },
	            "report:income-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "收入明细表"
	            },
	            "query:region-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "大区对比"
	            },
	            "query:business-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "业务明细表"
	            },
	            "report:region-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "大区对比"
	            },
	            "page": {
	                "permissions": {
	                    "add": false,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "模块管理"
	            },
	            "user": {
	                "permissions": {
	                    "add": false,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "用户管理"
	            },
	            "query:daily-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "日统计查询"
	            }
	        }
}

请求字段解释：

* `name` 角色名，必填
* `label` 标签名，必填
* `permissions` 权限，必填

返回成功示例：
	
	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "id": 7
	    }

返回字段解释：

* `id` 新添加角色的id

#### 更新角色

* Path：/api/roles/{id}
* Method：PUT
* Success Code：201
* Failure Code：400、401、404、500

请求示例：

	PUT http://domain.com/api/roles/7
	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "name":"tour",
	        "label":"游客2",
	        "permissions": {
	            "role": {
	                "permissions": {
	                    "add": true,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "角色管理"
	            },
	            "report:testing-item-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "检测项目对比"
	            },
	            "report:profits": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "利润表"
	            },
	            "permission": {
	                "permissions": {
	                    "add": false,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "权限管理"
	            },
	            "query:income-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "收入明细表"
	            },
	            "query:province-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "省份对比"
	            },
	            "query:testing-item-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "检测项目对比"
	            },
	            "report:province-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "省份对比"
	            },
	            "report:business-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "业务明细表"
	            },
	            "report:income-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "收入明细表"
	            },
	            "query:region-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "大区对比"
	            },
	            "query:business-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "业务明细表"
	            },
	            "report:region-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "大区对比"
	            },
	            "page": {
	                "permissions": {
	                    "add": false,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "模块管理"
	            },
	            "user": {
	                "permissions": {
	                    "add": false,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "用户管理"
	            },
	            "query:daily-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "日统计查询"
	            }
	        }
}

请求字段解释：

* `id` 角色id
* `name` 角色名，必填
* `label` 标签名，必填
* `permissions` 权限，必填

返回成功示例：
	
	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "id": 7
	    }

返回字段解释：

* `id` 更新角色的id

#### 角色id获取角色信息

* Path：/api/roles/{id}/permissions
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/roles/1/permissions
	Header: 
		x-auth: {登录时返回的token}


返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "id": 1,
	        "label": "领导",
	        "name": "boss",
	        "permissions": {
	            "role": {
	                "permissions": {
	                    "add": true,
	                    "get": true,
	                    "update": true,
	                    "list": true,
	                    "delete": false
	                },
	                "description": "角色管理"
	            },
	            "report:testing-item-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "检测项目对比"
	            },
	            "report:profits": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "利润表"
	            },
	            "permission": {
	                "permissions": {
	                    "add": false,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "权限管理"
	            },
	            "query:income-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "收入明细表"
	            },
	            "query:province-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "省份对比"
	            },
	            "query:testing-item-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "检测项目对比"
	            },
	            "report:province-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "省份对比"
	            },
	            "report:business-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "业务明细表"
	            },
	            "report:income-detail": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "收入明细表"
	            },
	            "query:region-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "大区对比"
	            },
	            "query:business-detail": {
	                "permissions": {
	                    "query": true,
	                    "export": true
	                },
	                "description": "业务明细表"
	            },
	            "report:region-contrast": {
	                "permissions": {
	                    "query": false,
	                    "export": false
	                },
	                "description": "大区对比"
	            },
	            "page": {
	                "permissions": {
	                    "add": false,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "模块管理"
	            },
	            "user": {
	                "permissions": {
	                    "add": false,
	                    "get": false,
	                    "update": false,
	                    "list": false,
	                    "delete": false
	                },
	                "description": "用户管理"
	            },
	            "query:daily-detail": {
	                "permissions": {
	                    "query": true,
	                    "export": true
	                },
	                "description": "日统计查询"
	            }
	        }
}

返回字段解释：
	
* id 角色id
* name 角色名
* label 标签名
	 permissions 角色拥有的权限	

#### 删除角色

* Path：/api/roles/{id}
* Method：DELETE
* Success Code：201
* Failure Code：400、401、404、500

请求示例：

	DELETE http://domain.com/api/roles/5
	Header: 
		x-auth: {登录时返回的token}

Path 请求参数：

* `id` 角色id

### 权限

#### 所有权限

* Path：/api/roles/permissions
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/roles/permissions
	Header: 
		x-auth: {登录时返回的token}


返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "role": {
	            "permissions": {
	                "add": false,
	                "get": false,
	                "update": false,
	                "list": false,
	                "delete": false
	            },
	            "description": "角色管理"
	        },
	        "report:testing-item-contrast": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "检测项目对比"
	        },
	        "report:profits": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "利润表"
	        },
	        "permission": {
	            "permissions": {
	                "add": false,
	                "get": false,
	                "update": false,
	                "list": false,
	                "delete": false
	            },
	            "description": "权限管理"
	        },
	        "query:income-detail": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "收入明细表"
	        },
	        "query:province-contrast": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "省份对比"
	        },
	        "query:testing-item-contrast": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "检测项目对比"
	        },
	        "report:province-contrast": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "省份对比"
	        },
	        "report:business-detail": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "业务明细表"
	        },
	        "report:income-detail": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "收入明细表"
	        },
	        "query:region-contrast": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "大区对比"
	        },
	        "query:business-detail": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "业务明细表"
	        },
	        "report:region-contrast": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "大区对比"
	        },
	        "page": {
	            "permissions": {
	                "add": false,
	                "get": false,
	                "update": false,
	                "list": false,
	                "delete": false
	            },
	            "description": "模块管理"
	        },
	        "user": {
	            "permissions": {
	                "add": false,
	                "get": false,
	                "update": false,
	                "list": false,
	                "delete": false
	            },
	            "description": "用户管理"
	        },
	        "query:daily-detail": {
	            "permissions": {
	                "query": false,
	                "export": false
	            },
	            "description": "日统计查询"
	        }
	    }
}

返回字段解释：
	
* 最外层键 页面名称
* permissions 页面的权限
	 description 页面描述	
	
### 积分系数

#### 修改积分系数

* Path：/api/api/properties/{id}
* Method：PUT
* Success Code：201
* Failure Code：400、401、404、500

请求示例：

	POST http://domain.com/api/properties/hs_integral
	Header: 
		x-auth: {登录时返回的token}
	Body:
		{
	        "typeInt":2
	    }

Path请求字段解释：

* `id` property 的id，必填(积分的默认为`hs_integral`)

请求字段解释：

* `typeInt` 积分系数值，必填

#### 获取积分系数

* Path：/api/api/properties/{id}
* Method：GET
* Success Code：201
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/properties/hs_integral
	Header: 
		x-auth: {登录时返回的token}

Path请求字段解释：

* `id` property 的id，必填(积分的默认为`hs_integral`)

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
			"createTime": 1529377806000,
			"id": "hs_integral",
			"type": "type_int",
			"typeInt": 2,
			"typeVarchar": null,
			"updateTime": 1529998930000
		}

返回字段解释：
	
* id 属性id
* typeInt 积分系数


### Dashboard

#### 首页订单收入、现金收入、财务确认收入、订单量统计（TODO）

* Path：/api/dashboard/dimension-count
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET https://domain.com/api/dashboard/dimension-count
	Header: 
		x-auth: {登录时返回的token}

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        orderIncome: {
	            total: 126560,
	            list: [
	                {
	                    x: "2018-06-01",
	                    y: 10
	                },
	                {
	                    x: "2018-06-02",
	                    y: 20
	                }
	                ...(本周和上一周)
	            ],
	            weekCompare: 12,
	            dayCompare： 11
	        }，
	        cashIncome：{
	            total: 126560,
	            list: [
	                {
	                    x: "2018-06-01",
	                    y: 10
	                },
	                {
	                    x: "2018-06-02",
	                    y: 20
	                }
	                ...(本周和上一周)
	            ],
	            weekCompare: 12,
	            dayCompare： 11
	        }，
	        financeConfirmIncome：{
	            total: 126560,
	            list: [
	               {
	                   x: "2018-06-01",
	                   y: 10
	               },
	               {
	                   x: "2018-06-02",
	                   y: 20
	               }
	               ...(本周和上一周)
	            ],
	            weekCompare: 12,
	            dayCompare： 11
	        },
	        order：{
	            total: 126560,
	            list: [
	               {
	                   x: "2018-06-01",
	                   y: 10
	               },
	               {
	                   x: "2018-06-02",
	                   y: 20
	               }
	               ...(本周和上一周)
	            ],
	            weekCompare: 12,
	            dayCompare： 11
	        }
	    }

#### 销售额趋势图（订单量和订单收入趋势）

按本日、本周、本月、本年统计

* Path：/api/dashboard/order-trend?timeDimension=week
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET https://domain.com/api/dashboard/order-trend?timeDimension=week
	Header: 
		x-auth: {登录时返回的token}

请求参数解释

* `timeDimension` 时间维度，可选`day`、`week`、`month`、`year`，必须

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
	    {
	        timeDimension: "week",
	        list: [
	            {
	                date: "周一"
	                订单数: 100,
	                订单收入：50
	            },
	            {
	                date: "周二"
	                订单数: 150,
	                订单收入：60
	            }
	            ...
	        ]
	    }

返回字段解释：

如果是`timeDimension`是`day`的话，`date`字段开始和结束分别是`0-9`和`18-24`，9点和18之间每小时一个节点；

横坐标示例：

    0-9、10、11、12、13、14、15、16、17、18、19-24

#### 月均销量趋势图（订单量和订单收入趋势）


* Path：/api/dashboard/month/order-trend?timeDimension=year
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET https://domain.com/api/dashboard/month/order-trend?timeDimension=year
	Header: 
		x-auth: {登录时返回的token}

请求参数解释

* `timeDimension` 时间维度，可选`day`、`week`、`month`、`year`，必须,目前只有`year`

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
	   {
			"list": [
				{
					"date": "1月",
					"holidayPlaceAvgCost": 0,
					"holidayPlaceAvgCount": 0,
					"workPlaceAvgCost": 0,
					"workPlaceAvgCount": 0
				},
				{
					"date": "2月",
					"holidayPlaceAvgCost": 17690.91,
					"holidayPlaceAvgCount": 6,
					"workPlaceAvgCost": 19164.71,
					"workPlaceAvgCount": 6
				},
				{
					"date": "3月",
					"holidayPlaceAvgCost": 19411.11,
					"holidayPlaceAvgCount": 6,
					"workPlaceAvgCost": 18222.73,
					"workPlaceAvgCount": 5
				},
				{
					"date": "4月",
					"holidayPlaceAvgCost": 19730,
					"holidayPlaceAvgCount": 6,
					"workPlaceAvgCost": 21450,
					"workPlaceAvgCount": 6
				},
				{
					"date": "5月",
					"holidayPlaceAvgCost": 16311.11,
					"holidayPlaceAvgCount": 5,
					"workPlaceAvgCost": 19286.36,
					"workPlaceAvgCount": 6
				},
				{
					"date": "6月",
					"holidayPlaceAvgCost": 0,
					"holidayPlaceAvgCount": 0,
					"workPlaceAvgCost": 0,
					"workPlaceAvgCount": 0
				},
				{
					"date": "7月",
					"holidayPlaceAvgCost": 0,
					"holidayPlaceAvgCount": 0,
					"workPlaceAvgCost": 0,
					"workPlaceAvgCount": 0
				},
				{
					"date": "8月",
					"holidayPlaceAvgCost": 0,
					"holidayPlaceAvgCount": 0,
					"workPlaceAvgCost": 0,
					"workPlaceAvgCount": 0
				},
				{
					"date": "9月",
					"holidayPlaceAvgCost": 0,
					"holidayPlaceAvgCount": 0,
					"workPlaceAvgCost": 0,
					"workPlaceAvgCount": 0
				},
				{
					"date": "10月",
					"holidayPlaceAvgCost": 0,
					"holidayPlaceAvgCount": 0,
					"workPlaceAvgCost": 0,
					"workPlaceAvgCount": 0
				},
				{
					"date": "11月",
					"holidayPlaceAvgCost": 0,
					"holidayPlaceAvgCount": 0,
					"workPlaceAvgCost": 0,
					"workPlaceAvgCount": 0
				},
				{
					"date": "12月",
					"holidayPlaceAvgCost": 0,
					"holidayPlaceAvgCount": 0,
					"workPlaceAvgCost": 0,
					"workPlaceAvgCount": 0
				}
			],
			"timeDimension": "year"
		}

返回字段解释：

* date 月份
* holidayPlaceAvgCost 节假日平均订单收入
* holidayPlaceAvgCount 节假日平均订单量
* workPlaceAvgCost 工作日平均订单收入
* workPlaceAvgCount 工作日平均订单量

#### 大区订单量排行榜（大区订单量排行榜）

按本日、本周、本月、本年统计

* Path：/api/dashboard/order-count-top?timeDimension=week
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET https://domain.com/api/dashboard/income-top?timeDimension=week
	Header: 
		x-auth: {登录时返回的token}

请求参数解释

* `timeDimension` 时间维度，可选`day`、`week`、`month`、`year`，必须

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
	    {
	        timeDimension: "week",
	        list: [
	            {
	                title: "华中",
	                total: 1000
	            },
	            {
	                title: "华北",
	                total: 900
	            },
	            ...
	        ]
	    }

#### 省份业务明细

按本周、本月、本年统计

* Path：/api/dashboard/income-province?timeDimension=week
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET https://domain.com/api/dashboard/income-province?timeDimension=week
	Header: 
		x-auth: {登录时返回的token}

请求参数解释

* `timeDimension` 时间维度，可选`week`、`month`、`year`，必须

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
	    {
	        timeDimension: "week",
	        names: ["北京","上海"...]
	        list: [
	            {
	                name: "订单收入"
	                北京: 100,
	                上海：50,
	                ...
	            },
	            {
	                name: "现金收入"
	                北京: 100,
	                上海：50,
	                ...
	            },
	            {
	                name: "财务确认收入"
	                北京: 100,
	                上海：50,
	                ...
	            },
	            ...
	        ]
	    }

### 字典

#### 获取大区列表

* Path：/api/dict/regions
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET https://domain.com/api/dict/regions
	Header: 
		x-auth: {登录时返回的token}

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "list": [
	            {
	                "createTime": 1527732664000,
	                "regionId": "1",
	                "regionName": "华北",
	                "updateTime": 1527732664000
	            }
	        ]
	    }

#### 获取省份列表

* Path：/api/dict/provinces
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/dict/provinces
	Header: 
		x-auth: {登录时返回的token}

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "list": [
	            {
	                "createTime": 1527476006000,
	                "provinceId": "1",
	                "provinceName": "山东",
	                "regionId": "1",
	                "updateTime": 1527763378000
	            },
	            {
	                "createTime": 1527489577000,
	                "provinceId": "2",
	                "provinceName": "山西",
	                "regionId": "2",
	                "updateTime": 1527489577000
	            },
	            {
	                "createTime": 1527489577000,
	                "provinceId": "3",
	                "provinceName": "河南",
	                "regionId": "3",
	                "updateTime": 1527489577000
	            }
	        ]
	    }

返回字段解释：
	
* provinceId 省份ID
* provinceName 省份名称

#### 获取检测套餐列表

- Path：/api/dict/testing-items
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/dict/testing-items
Header: 
	x-auth: {登录时返回的token}

```

返回成功示例   

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	{
    "list": [
        {
            "testingAgency": "北京迈基诺",
            "testingAgencyAddress": "北京市海淀区",
            "testingAgencyId": 1002,
            "testingItem": "EGFR基因突变",
            "testingItemId": 3
        },
        {
            "testingAgency": "北京迈基诺",
            "testingAgencyAddress": "北京市海淀区",
            "testingAgencyId": 1002,
            "testingItem": "EGFR基因突变（血浆）",
            "testingItemId": 4
        },
        {
            "testingAgency": "杰傲湃思",
            "testingAgencyAddress": "北京市海淀区",
            "testingAgencyId": 1001,
            "testingItem": "HER2（ERBB2）扩增",
            "testingItemId": 1
        },
        {
            "testingAgency": "所罗门",
            "testingAgencyAddress": "北京市海淀区",
            "testingAgencyId": 1003,
            "testingItem": "KRAS基因突变",
            "testingItemId": 5
        },
        {
            "testingAgency": "所罗门",
            "testingAgencyAddress": "北京市海淀区",
            "testingAgencyId": 1003,
            "testingItem": "KRAS基因突变（血浆）",
            "testingItemId": 6
        },
        {
            "testingAgency": "杰傲湃思",
            "testingAgencyAddress": "北京市海淀区",
            "testingAgencyId": 1001,
            "testingItem": "PIK3CA基因突变",
            "testingItemId": 2
        }
    ]
}

```

返回字段解释：
	

- testingItemId 检测套餐ID
- testingItem 检测套餐名称
- testingAgencyId 检测机构ID
- testingAgency 检测机构名称
- testingAgencyAddress 检测机构地址

### 查询API

#### 业务明细表

* Path：/api/query/business-detail?startDate=1525104000000&endDate=1527731359982&regionId=1&pageNum=2
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET https://domain.com/api/query/business-detail?startDate=1525104000000&endDate=1527731359982&regionId=1&pageNum=2
	Header: 
		x-auth: {登录时返回的token}

search请求参数：

* startDate 开始时间
* endDate 结束时间
* regionId 大区ID
* localStaffName 地服名字
* pageNum 当前页码
* pageSize 每页条数

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "endRow": 11,
	        "firstPage": 1,
	        "hasNextPage": false,
	        "hasPreviousPage": true,
	        "isFirstPage": false,
	        "isLastPage": true,
	        "lastPage": 2,
	        "list": [
	            {
	                "cashIncome": 100,
	                "changeCount": 1,
	                "doctorId": null,
	                "doctorName": "王某",
	                "financeConfirmIncome": 100,
	                "hospitalName": "北医3院",
	                "id": 11,
	                "integralCost": 0,
	                "localStaffRegionId": "1",
	                "localStaffRegionName": "华北",
	                "orderId": "1011",
	                "orderIncome": 100,
	                "orderNumber": "HS0011",
	                "patientAge": 60,
	                "patientGender": "male",
	                "patientIdCard": "370983176606150432",
	                "patientName": "孙某",
	                "testingItemCost": 0,
	                "testingItemConfirmCost": 0,
					"localStaffName":"张某"
	            }
	        ],
	        "navigateFirstPage": 1,
	        "navigateLastPage": 2,
	        "navigatePages": 8,
	        "navigatepageNums": [
	            1,
	            2
	        ],
	        "nextPage": 0,
	        "pageNum": 2,
	        "pageSize": 10,
	        "pages": 2,
	        "prePage": 1,
	        "size": 1,
	        "startRow": 11,
	        "sum": {
	            "cashIncome": 4400,
	            "financeConfirmIncome": 3400,
	            "integralCost": 132,
	            "orderIncome": 6500,
	            "testingItemCost": 2100
	        },
	        "total": 11
	    }

#### 业务明细表导出Excel

- Path：/api/export/query/business-detail
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/export/query/business-detail?startDate=1525104000000&endDate=1527731359982&regionId=1
Header: 
	x-auth: {登录时返回的token}
```

search请求参数：

- startDate 开始时间
- endDate 结束时间
- regionId 大区ID
- localStaffName 地服名字

返回成功示例：

​	excel文件流

返回失败示例

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"
```



#### 日统计查询

* Path：/api/query/daily-detail
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/query/daily-detail
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"startDate": "1527215234000",
			"endDate": "1527215234005",
			"regionId":"1" 
			"localStaffName":"张某"
		}

请求字段解释：

* `startDate` 开始时间，必填
* `endDate` 结束时间，必填
* `regionId` 大区id
* `localStaffName` 地服人员名称，选填


返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "list": [
	            {
	                "cashIncome": 0,
	                "dateStr": "2018-05-25",
	                "financeConfirmIncome": 0,
	                "integralCost": 0,
	                "orderCancelAvgCost": 100,
	                "orderCancelCount": 2,
	                "orderIncome": 180,
	                "orderPlaceAvgCost": 127,
	                "orderPlaceCount": 3,
	                "testingItemCost": 0,
	                "testingItemConfirmCost": 0,
	            }
	        ],
	        "sum": {
	            "cashIncome": 0,
	            "dateStr": null,
	            "financeConfirmIncome": 0,
	            "integralCost": 0,
	            "orderCancelAvgCost": 100,
	            "orderCancelCount": 2,
	            "orderIncome": 180,
	            "orderPlaceAvgCost": 127,
	            "orderPlaceCount": 3,
	            "testingItemCost": 0,
	            "testingItemConfirmCost": 0,
	        }
	    }

返回字段解释：
	
* sum 总计
* orderIncome 订单金额(总计)
* financeConfirmIncome 开票金额(总计)
* integralCost 积分金额(总计)
* cashIncome 到账金额(总计)
* testingItemCost 检验成本(总计)
	 list 数据数组	
* dateStr 日期



#### 日统计明细导出Excel

- Path：/api/export/query/daily-detail
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/export/query/daily-detail
Header: 
	x-auth: {登录时返回的token}
Query:
	{
		"startDate": "1527215234000",
		"endDate": "1527215234005",
		"regionId":"1" 
		"localStaffName":"张某"
	}
```

请求字段解释：

- `startDate` 开始时间，必填
- `endDate` 结束时间，必填
- `regionId` 大区id，选填
- `localStaffName` 地服人员名称，选填

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"
```



#### 大区对比

- Path：/query/region-contrast
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/query/region-contrast?startDate=1000000000000&endDate=1000000000300&regionIds=2,11
Header: 
	x-auth: {登录时返回的token}
```

请求字段解释：

- `startDate` 开始时间，必填；
- `endDate` 结束时间，必填；
- `regionIds`选择的大区ID，选填

返回成功示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
    {
        "sum": {
            "orderCancelAvgCost": 100,
            "orderCancelCount": 2,
            "orderIncome": 180,
            "regionId": "sum",
            "financeConfirmIncome": 0,
            "regionName": "总计",
            "orderPlaceCount": 3,
            "integralCost": 0,
            "cashIncome": 0,
            "orderPlaceAvgCost": 127,
            "testingItemConfirmCost": 0,
            "testingItemCost": 0
        },
        "list": [
            {
                "orderCancelAvgCost": 100,
                "orderCancelCount": 2,
                "orderIncome": 180,
                "regionId": "1",
                "financeConfirmIncome": 0,
                "regionName": "华北",
                "orderPlaceCount": 3,
                "integralCost": 0,
                "cashIncome": 0,
                "orderPlaceAvgCost": 127,
                "testingItemConfirmCost": 0,
                "testingItemCost": 0
            }
        ]
    }
```



#### 大区对比导出Excel

- Path：api/export//query/region-contrast
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/export/query/region-contrast?startDate=1000000000000&endDate=1000000000300&regionIds=2,11
Header: 
	x-auth: {登录时返回的token}
```

请求字段解释：

- `startDate` 开始时间，必填；
- `endDate` 结束时间，必填；
- `regionIds`选择的大区ID，选填

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"
```



#### 省份对比表

* Path：/api/query/province-contrast
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/query/province-contrast
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"startDate": "1527215234000",
			"endDate": "1527215234005",
			"provinceIds":"1,2,3" 
		}

请求字段解释：

* `startDate` 开始时间，必填
* `endDate` 结束时间，必填
* `provinceIds` 省份id，选填

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "sum": {
	            "orderCancelAvgCost": 100,
	            "orderCancelCount": 2,
	            "orderIncome": 180,
	            "financeConfirmIncome": 0,
	            "orderPlaceCount": 3,
	            "integralCost": 0,
	            "provinceName": "总计",
	            "provinceId": "sum",
	            "cashIncome": 0,
	            "orderPlaceAvgCost": 127,
	            "testingItemConfirmCost": 0,
	            "testingItemCost": 0
	        },
	        "list": [
	            {
	                "orderCancelAvgCost": 100,
	                "orderCancelCount": 2,
	                "orderIncome": 180,
	                "financeConfirmIncome": 0,
	                "orderPlaceCount": 3,
	                "integralCost": 0,
	                "provinceName": "山东",
	                "provinceId": "1",
	                "cashIncome": 0,
	                "orderPlaceAvgCost": 127,
	                "testingItemConfirmCost": 0,
	                "testingItemCost": 0
	            }
	        ]
	    }



#### 省份对比导出Excel

- Path：/api/export/query/province-contrast
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/export/query/province-contrast
Header: 
	x-auth: {登录时返回的token}
Query:
	{
		"startDate": "1527215234000",
		"endDate": "1527215234005",
		"provinceIds":"1,2,3" 
	}
```

请求字段解释：

- `startDate` 开始时间，必填
- `endDate` 结束时间，必填
- `provinceIds` 省份id，选填

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"
```



#### 检测项目对比

- Path：/query/testing-item-contrast
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/query/testing-item-contrast?startDate=1000000000000&endDate=1000000000300&testingItemIds=2,1
Header: 
	x-auth: {登录时返回的token}
```

请求字段解释：

- `startDate` 开始时间，必填；
- `endDate` 结束时间，必填；
- `testingItemIds`选择的检测项目ID，选填
- `sortBy`根据哪项进行排序，可选值：`placeCount`(下单数，默认)、`orderIncome`(订单金额)
- `sortMode`排序方式，可选值：`desc`(降序，默认)、`asc`(升序)

返回成功示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
    {
        "sum": {
            "testingItem": "总计",
            "testingItemFinanceConfirmIncome": 0,
            "testingItemOrderIncome": 1500,
            "testingItemRecordCost": 0,
            "testingItemConfirmRecordCost": 0,
            "testingItemCashIncome": 0,
            "testingItemId": "sum"
        },
        "list": [
            {
                "testingItem": "血液套餐1",
                "testingItemFinanceConfirmIncome": 0,
                "testingItemOrderIncome": 1500,
                "testingItemRecordCost": 0,
                "testingItemConfirmRecordCost": 0,
                "testingItemCashIncome": 0,
                "testingItemId": "4001"
            }
        ]
    }
```


#### 检测项目对比导出Excel

- Path：/query/export/testing-item-contrast
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/export/query/testing-item-contrast?startDate=1000000000000&endDate=1000000000300&testingItemIds=2,1
Header: 
	x-auth: {登录时返回的token}
```

请求字段解释：

- `startDate` 开始时间，必填；
- `endDate` 结束时间，必填；
- `testingItemIds`选择的检测项目ID，选填
- `sortBy`根据哪项进行排序，可选值：`placeCount`(下单数，默认)、`orderIncome`(订单金额)
- `sortMode`排序方式，可选值：`desc`(降序，默认)、`asc`(升序)

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"

```



#### 收入明细表

* Path：/api/query/income-detail
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/query/income-detail
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"startDate": "1527215234000",
			"endDate": "1527215234005",
			"pageSize":10
			"pageNum":1
		}

请求字段解释：

* `startDate` 开始时间，必填
* `endDate` 结束时间，必填
* `pageSize` 页数，默认为10
* `pageNum` 当前页，默认为1


返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
			"endRow": 6,
			"firstPage": 1,
			"hasNextPage": false,
			"hasPreviousPage": false,
			"isFirstPage": true,
			"isLastPage": true,
			"lastPage": 1,
			"list": [
				{
					"doctorName": "姜骏烁",
					"hospitalName": "聊城市人民医院",
					"invoices": [
						{
							"createTime": 1531379498000,
							"deleted": false,
							"invoiceAmount": 173363,
							"invoiceBuyerAddress": null,
							"invoiceBuyerBank": null,
							"invoiceBuyerName": "吴美兴",
							"invoiceBuyerTaxPayerNumber": null,
							"invoiceCode": "3100171389",
							"invoiceDate": "2018-01-15",
							"invoiceGoodsCodeVersion": "1",
							"invoiceItemCount": 1,
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemUnitPrice": 173363,
							"invoiceNumber": "43641694",
							"invoicePostTaxAmount": 162962,
							"invoiceSellerAddress": null,
							"invoiceSellerBank": null,
							"invoiceSellerName": null,
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxAmount": 10401,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceTaxRate": 6,
							"invoiceTime": 1516032000000,
							"orderIds": null,
							"updateTime": 1531379498000
						},
						{
							"createTime": 1531379498000,
							"deleted": false,
							"invoiceAmount": 384747,
							"invoiceBuyerAddress": null,
							"invoiceBuyerBank": null,
							"invoiceBuyerName": "赵中锴",
							"invoiceBuyerTaxPayerNumber": null,
							"invoiceCode": "3100171391",
							"invoiceDate": "2018-01-15",
							"invoiceGoodsCodeVersion": "1",
							"invoiceItemCount": 1,
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemUnitPrice": 384747,
							"invoiceNumber": "43641694",
							"invoicePostTaxAmount": 361663,
							"invoiceSellerAddress": null,
							"invoiceSellerBank": null,
							"invoiceSellerName": null,
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxAmount": 23084,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceTaxRate": 6,
							"invoiceTime": 1516028400000,
							"orderIds": null,
							"updateTime": 1531379498000
						},
						{
							"createTime": 1531379499000,
							"deleted": false,
							"invoiceAmount": 144000,
							"invoiceBuyerAddress": null,
							"invoiceBuyerBank": null,
							"invoiceBuyerName": "李启菁",
							"invoiceBuyerTaxPayerNumber": null,
							"invoiceCode": "3100171396",
							"invoiceDate": "2018-01-16",
							"invoiceGoodsCodeVersion": "1",
							"invoiceItemCount": 1,
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemUnitPrice": 144000,
							"invoiceNumber": "43641694",
							"invoicePostTaxAmount": 135360,
							"invoiceSellerAddress": null,
							"invoiceSellerBank": null,
							"invoiceSellerName": null,
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxAmount": 8640,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceTaxRate": 6,
							"invoiceTime": 1516122000000,
							"orderIds": null,
							"updateTime": 1531379499000
						}
					],
					"localStaffName": "秦艺菲",
					"orderId": "GTON-20180103-1",
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180103-1",
					"orderNumber": "GTON-20180103-1",
					"orderPrice": 2600000,
					"patientAge": 83,
					"patientClinicalDiagnosis": "肝癌",
					"patientName": "赵山川",
					"payments": [
						{
							"account": "211324199006140734",
							"id": 1,
							"localStaffPayroll": true,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"payTime": 1514952000000,
							"paymentType": "支付宝",
							"total": 5000,
							"tradeNumber": "2113241990",
							"type": "pay",
							"userName": "张某"
						},
						{
							"account": "",
							"id": 2,
							"localStaffPayroll": false,
							"localStaffPayrollTrans": "否",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"payTime": 1514952000000,
							"paymentType": "支付宝",
							"total": -5000,
							"tradeNumber": "2113241991",
							"type": "refun",
							"userName": ""
						}
					],
					"testings": [
						{
							"createTime": 1530243682000,
							"id": 67,
							"orderId": "GTON-20180103-1",
							"sampleConfirmTime": 1514966400000,
							"samplingTime": 1514966400000,
							"testingAgency": "所罗门",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1003,
							"testingItem": "KRAS基因突变（血浆）",
							"testingItemCost": 1900000,
							"testingItemId": 6,
							"testingItemPrice": 2100000,
							"testingReportNumber": "GTON-20180103-1-411",
							"testingReportUploadTime": 1514970000000,
							"testingReportUrl": "http://todaysoft.com.cn",
							"testingResult": "正常",
							"updateTime": 1530243682000
						},
						{
							"createTime": 1530243682000,
							"id": 68,
							"orderId": "GTON-20180103-1",
							"sampleConfirmTime": 1514966400000,
							"samplingTime": 1514966400000,
							"testingAgency": "北京迈基诺",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1002,
							"testingItem": "EGFR基因突变（血浆）",
							"testingItemCost": 400000,
							"testingItemId": 4,
							"testingItemPrice": 500000,
							"testingReportNumber": "GTON-20180103-1-442",
							"testingReportUploadTime": 1514970000000,
							"testingReportUrl": null,
							"testingResult": "正常",
							"updateTime": 1530243682000
						}
					]
				},
				{
					"doctorName": "金启昭",
					"hospitalName": "北医三院",
					"invoices": [
						{
							"createTime": 1531379500000,
							"deleted": false,
							"invoiceAmount": 553500,
							"invoiceBuyerAddress": null,
							"invoiceBuyerBank": null,
							"invoiceBuyerName": "吴诗琪",
							"invoiceBuyerTaxPayerNumber": null,
							"invoiceCode": "3100171399",
							"invoiceDate": "2018-01-17",
							"invoiceGoodsCodeVersion": "1",
							"invoiceItemCount": 1,
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemUnitPrice": 553500,
							"invoiceNumber": "43641694",
							"invoicePostTaxAmount": 520290,
							"invoiceSellerAddress": null,
							"invoiceSellerBank": null,
							"invoiceSellerName": null,
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxAmount": 33210,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceTaxRate": 6,
							"invoiceTime": 1516194000000,
							"orderIds": null,
							"updateTime": 1531379500000
						},
						{
							"createTime": 1531379500000,
							"deleted": false,
							"invoiceAmount": 3654000,
							"invoiceBuyerAddress": null,
							"invoiceBuyerBank": null,
							"invoiceBuyerName": "钱虹君",
							"invoiceBuyerTaxPayerNumber": null,
							"invoiceCode": "3100171397",
							"invoiceDate": "2018-01-16",
							"invoiceGoodsCodeVersion": "1",
							"invoiceItemCount": 1,
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemUnitPrice": 3654000,
							"invoiceNumber": "43641694",
							"invoicePostTaxAmount": 3434760,
							"invoiceSellerAddress": null,
							"invoiceSellerBank": null,
							"invoiceSellerName": null,
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxAmount": 219240,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceTaxRate": 6,
							"invoiceTime": 1516100400000,
							"orderIds": null,
							"updateTime": 1531379500000
						}
					],
					"localStaffName": "孔祥栋",
					"orderId": "GTON-20180103-2",
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180103-2",
					"orderNumber": "GTON-20180103-2",
					"orderPrice": 2100000,
					"patientAge": 64,
					"patientClinicalDiagnosis": "胰腺癌",
					"patientName": "吴春芗",
					"payments": [
						{
							"account": "370983223724468200",
							"id": 3,
							"localStaffPayroll": true,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"payTime": 1514962800000,
							"paymentType": "微信",
							"total": 6000,
							"tradeNumber": "2113241992",
							"type": "pay",
							"userName": "李某"
						}
					],
					"testings": [
						{
							"createTime": 1530243683000,
							"id": 73,
							"orderId": "GTON-20180103-2",
							"sampleConfirmTime": 1514962800000,
							"samplingTime": 1514962800000,
							"testingAgency": "所罗门",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1003,
							"testingItem": "KRAS基因突变（血浆）",
							"testingItemCost": 1900000,
							"testingItemId": 6,
							"testingItemPrice": 2100000,
							"testingReportNumber": "GTON-20180103-2-519",
							"testingReportUploadTime": 1514966400000,
							"testingReportUrl": "http://todaysoft.com.cn",
							"testingResult": "正常",
							"updateTime": 1530243683000
						}
					]
				},
				{
					"doctorName": "姜博",
					"hospitalName": "北京阜外医院",
					"invoices": [
						{
							"createTime": 1531379503000,
							"deleted": false,
							"invoiceAmount": 372072,
							"invoiceBuyerAddress": null,
							"invoiceBuyerBank": null,
							"invoiceBuyerName": "钱品阎",
							"invoiceBuyerTaxPayerNumber": null,
							"invoiceCode": "3100171414",
							"invoiceDate": "2018-01-19",
							"invoiceGoodsCodeVersion": "1",
							"invoiceItemCount": 1,
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemUnitPrice": 372072,
							"invoiceNumber": "43641694",
							"invoicePostTaxAmount": 349748,
							"invoiceSellerAddress": null,
							"invoiceSellerBank": null,
							"invoiceSellerName": null,
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxAmount": 22324,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceTaxRate": 6,
							"invoiceTime": 1516392000000,
							"orderIds": null,
							"updateTime": 1531379503000
						},
						{
							"createTime": 1531379503000,
							"deleted": false,
							"invoiceAmount": 1240000,
							"invoiceBuyerAddress": null,
							"invoiceBuyerBank": null,
							"invoiceBuyerName": "孙芳",
							"invoiceBuyerTaxPayerNumber": null,
							"invoiceCode": "3100171415",
							"invoiceDate": "2018-01-19",
							"invoiceGoodsCodeVersion": "1",
							"invoiceItemCount": 1,
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemUnitPrice": 1240000,
							"invoiceNumber": "43641694",
							"invoicePostTaxAmount": 1165600,
							"invoiceSellerAddress": null,
							"invoiceSellerBank": null,
							"invoiceSellerName": null,
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxAmount": 74400,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceTaxRate": 6,
							"invoiceTime": 1516348800000,
							"orderIds": null,
							"updateTime": 1531379503000
						}
					],
					"localStaffName": "陈禹西",
					"orderId": "GTON-20180103-6",
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180103-6",
					"orderNumber": "GTON-20180103-6",
					"orderPrice": 4000000,
					"patientAge": 77,
					"patientClinicalDiagnosis": "淋巴癌",
					"patientName": "吴诗琪",
					"payments": [
						{
							"account": "370983495979869950",
							"id": 4,
							"localStaffPayroll": true,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"payTime": 1514966400000,
							"paymentType": "银行转账",
							"total": 8000,
							"tradeNumber": "2113241993",
							"type": "pay",
							"userName": "刘某"
						},
						{
							"account": "370983495979869950",
							"id": 5,
							"localStaffPayroll": true,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"payTime": 1514966400000,
							"paymentType": "微信",
							"total": 2000,
							"tradeNumber": "2113241994",
							"type": "pay",
							"userName": "刘某"
						}
					],
					"testings": [
						{
							"createTime": 1530243685000,
							"id": 113,
							"orderId": "GTON-20180103-6",
							"sampleConfirmTime": 1514952000000,
							"samplingTime": 1514952000000,
							"testingAgency": "北京迈基诺",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1002,
							"testingItem": "EGFR基因突变（血浆）",
							"testingItemCost": 400000,
							"testingItemId": 4,
							"testingItemPrice": 500000,
							"testingReportNumber": "GTON-20180103-6-898",
							"testingReportUploadTime": 1514955600000,
							"testingReportUrl": null,
							"testingResult": "正常",
							"updateTime": 1530243685000
						},
						{
							"createTime": 1530243685000,
							"id": 114,
							"orderId": "GTON-20180103-6",
							"sampleConfirmTime": 1514952000000,
							"samplingTime": 1514952000000,
							"testingAgency": "杰傲湃思",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1001,
							"testingItem": "HER2（ERBB2）扩增",
							"testingItemCost": 1000000,
							"testingItemId": 1,
							"testingItemPrice": 1500000,
							"testingReportNumber": "GTON-20180103-6-812",
							"testingReportUploadTime": 1514955600000,
							"testingReportUrl": null,
							"testingResult": "正常",
							"updateTime": 1530243685000
						},
						{
							"createTime": 1530243685000,
							"id": 115,
							"orderId": "GTON-20180103-6",
							"sampleConfirmTime": 1514952000000,
							"samplingTime": 1514952000000,
							"testingAgency": "所罗门",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1003,
							"testingItem": "KRAS基因突变",
							"testingItemCost": 1800000,
							"testingItemId": 5,
							"testingItemPrice": 2000000,
							"testingReportNumber": "GTON-20180103-6-486",
							"testingReportUploadTime": 1514955600000,
							"testingReportUrl": null,
							"testingResult": "正常",
							"updateTime": 1530243685000
						}
					]
				},
				{
					"doctorName": "姜博",
					"hospitalName": "北京阜外医院",
					"invoices": [
						{
							"createTime": 1530265204000,
							"deleted": false,
							"invoiceAmount": 3300000,
							"invoiceBuyerAddress": null,
							"invoiceBuyerBank": null,
							"invoiceBuyerName": "孙芳",
							"invoiceBuyerTaxPayerNumber": null,
							"invoiceCode": "3200171335",
							"invoiceDate": "2018-01-03",
							"invoiceGoodsCodeVersion": "1",
							"invoiceItemCount": 1,
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemUnitPrice": 3300000,
							"invoiceNumber": "43641694",
							"invoicePostTaxAmount": 3102000,
							"invoiceSellerAddress": "盐城经济技术开发区希望大道南路5号4楼14、15层 0515-6669888",
							"invoiceSellerBank": "中国农业银行盐城开发区科技支行 1041111",
							"invoiceSellerName": "江苏华生基因数据科技股份有限公司",
							"invoiceSellerTaxPayerNumber": "913209005592111",
							"invoiceTaxAmount": 198000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceTaxRate": 6,
							"invoiceTime": 0,
							"orderIds": null,
							"updateTime": 1530265204000
						}
					],
					"localStaffName": "孔祥栋",
					"orderId": "GTON-20180103-5",
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180103-5",
					"orderNumber": "GTON-20180103-5",
					"orderPrice": 3200000,
					"patientAge": 59,
					"patientClinicalDiagnosis": "鼻咽癌",
					"patientName": "孙荣廷",
					"payments": [
						{
							"account": "370983214245431900",
							"id": 6,
							"localStaffPayroll": true,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"payTime": 1514962800000,
							"paymentType": "支付宝",
							"total": 3000,
							"tradeNumber": "2113241995",
							"type": "pay",
							"userName": "苏某"
						}
					],
					"testings": [
						{
							"createTime": 1530243684000,
							"id": 98,
							"orderId": "GTON-20180103-5",
							"sampleConfirmTime": 1514952000000,
							"samplingTime": 1514952000000,
							"testingAgency": "北京迈基诺",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1002,
							"testingItem": "EGFR基因突变（血浆）",
							"testingItemCost": 400000,
							"testingItemId": 4,
							"testingItemPrice": 500000,
							"testingReportNumber": "GTON-20180103-5-326",
							"testingReportUploadTime": 1514955600000,
							"testingReportUrl": null,
							"testingResult": "正常",
							"updateTime": 1530243684000
						},
						{
							"createTime": 1530243684000,
							"id": 99,
							"orderId": "GTON-20180103-5",
							"sampleConfirmTime": 1514952000000,
							"samplingTime": 1514952000000,
							"testingAgency": "杰傲湃思",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1001,
							"testingItem": "PIK3CA基因突变",
							"testingItemCost": 1000000,
							"testingItemId": 2,
							"testingItemPrice": 1200000,
							"testingReportNumber": "GTON-20180103-5-772",
							"testingReportUploadTime": 1514955600000,
							"testingReportUrl": null,
							"testingResult": "正常",
							"updateTime": 1530243684000
						},
						{
							"createTime": 1530243684000,
							"id": 100,
							"orderId": "GTON-20180103-5",
							"sampleConfirmTime": 1514952000000,
							"samplingTime": 1514952000000,
							"testingAgency": "杰傲湃思",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1001,
							"testingItem": "HER2（ERBB2）扩增",
							"testingItemCost": 1000000,
							"testingItemId": 1,
							"testingItemPrice": 1500000,
							"testingReportNumber": "GTON-20180103-5-439",
							"testingReportUploadTime": 1514955600000,
							"testingReportUrl": null,
							"testingResult": "正常",
							"updateTime": 1530243684000
						}
					]
				},
				{
					"doctorName": "金启昭",
					"hospitalName": "北医三院",
					"invoices": [
						{
							"createTime": 1530265194000,
							"deleted": false,
							"invoiceAmount": 3500000,
							"invoiceBuyerAddress": null,
							"invoiceBuyerBank": null,
							"invoiceBuyerName": "赵璇海",
							"invoiceBuyerTaxPayerNumber": null,
							"invoiceCode": "3200171328",
							"invoiceDate": "2018-01-02",
							"invoiceGoodsCodeVersion": "1",
							"invoiceItemCount": 1,
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemUnitPrice": 3500000,
							"invoiceNumber": "43641694",
							"invoicePostTaxAmount": 3290000,
							"invoiceSellerAddress": "盐城经济技术开发区希望大道南路5号4楼14、15层 0515-6669888",
							"invoiceSellerBank": "中国农业银行盐城开发区科技支行 1041111",
							"invoiceSellerName": "江苏华生基因数据科技股份有限公司",
							"invoiceSellerTaxPayerNumber": "913209005592111",
							"invoiceTaxAmount": 210000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceTaxRate": 6,
							"invoiceTime": 0,
							"orderIds": null,
							"updateTime": 1530265194000
						}
					],
					"localStaffName": "陈品如",
					"orderId": "GTON-20180102-2",
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180102-2",
					"orderNumber": "GTON-20180102-2",
					"orderPrice": 500000,
					"patientAge": 77,
					"patientClinicalDiagnosis": "淋巴癌",
					"patientName": "赵婧文",
					"payments": [
						{
							"account": "370983892420013400",
							"id": 7,
							"localStaffPayroll": true,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"payTime": 1514854800000,
							"paymentType": "银行转账",
							"total": 4000,
							"tradeNumber": "2113241996",
							"type": "pay",
							"userName": "刘某"
						}
					],
					"testings": [
						{
							"createTime": 1530243681000,
							"id": 50,
							"orderId": "GTON-20180102-2",
							"sampleConfirmTime": 1514862000000,
							"samplingTime": 1514862000000,
							"testingAgency": "北京迈基诺",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1002,
							"testingItem": "EGFR基因突变（血浆）",
							"testingItemCost": 400000,
							"testingItemId": 4,
							"testingItemPrice": 500000,
							"testingReportNumber": "GTON-20180102-2-892",
							"testingReportUploadTime": 1514865600000,
							"testingReportUrl": null,
							"testingResult": "正常",
							"updateTime": 1530243681000
						}
					]
				},
				{
					"doctorName": "曹杰卿",
					"hospitalName": "北大人民医",
					"invoices": [
						{
							"createTime": 1530265183000,
							"deleted": false,
							"invoiceAmount": 2800000,
							"invoiceBuyerAddress": null,
							"invoiceBuyerBank": null,
							"invoiceBuyerName": "吴易奚",
							"invoiceBuyerTaxPayerNumber": null,
							"invoiceCode": "3200171322",
							"invoiceDate": "2018-01-01",
							"invoiceGoodsCodeVersion": "1",
							"invoiceItemCount": 1,
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemUnitPrice": 2800000,
							"invoiceNumber": "43641694",
							"invoicePostTaxAmount": 2632000,
							"invoiceSellerAddress": "盐城经济技术开发区希望大道南路5号4楼14、15层 0515-6669888",
							"invoiceSellerBank": "中国农业银行盐城开发区科技支行 1041111",
							"invoiceSellerName": "江苏华生基因数据科技股份有限公司",
							"invoiceSellerTaxPayerNumber": "913209005592111",
							"invoiceTaxAmount": 168000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceTaxRate": 6,
							"invoiceTime": 0,
							"orderIds": null,
							"updateTime": 1530265183000
						}
					],
					"localStaffName": "陈品如",
					"orderId": "GTON-20180101-3",
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180101-3",
					"orderNumber": "GTON-20180101-3",
					"orderPrice": 3200000,
					"patientAge": 59,
					"patientClinicalDiagnosis": "胆囊癌",
					"patientName": "钱品妍",
					"payments": [
						{
							"account": "1514786400000",
							"id": 8,
							"localStaffPayroll": true,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"payTime": 1514786400000,
							"paymentType": "支付宝",
							"total": 8000,
							"tradeNumber": "2113241997",
							"type": "pay",
							"userName": "新欧"
						},
						{
							"account": "",
							"id": 9,
							"localStaffPayroll": false,
							"localStaffPayrollTrans": "否",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"payTime": 1514786400000,
							"paymentType": "微信",
							"total": -2000,
							"tradeNumber": "2113241998",
							"type": "refun",
							"userName": ""
						}
					],
					"testings": [
						{
							"createTime": 1530243680000,
							"id": 40,
							"orderId": "GTON-20180101-3",
							"sampleConfirmTime": 1514775600000,
							"samplingTime": 1514775600000,
							"testingAgency": "杰傲湃思",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1001,
							"testingItem": "PIK3CA基因突变",
							"testingItemCost": 1000000,
							"testingItemId": 2,
							"testingItemPrice": 1200000,
							"testingReportNumber": "GTON-20180101-3-9",
							"testingReportUploadTime": 1514779200000,
							"testingReportUrl": null,
							"testingResult": "正常",
							"updateTime": 1530243680000
						},
						{
							"createTime": 1530243680000,
							"id": 41,
							"orderId": "GTON-20180101-3",
							"sampleConfirmTime": 1514775600000,
							"samplingTime": 1514775600000,
							"testingAgency": "所罗门",
							"testingAgencyAddress": "北京市海淀区",
							"testingAgencyId": 1003,
							"testingItem": "KRAS基因突变",
							"testingItemCost": 1800000,
							"testingItemId": 5,
							"testingItemPrice": 2000000,
							"testingReportNumber": "GTON-20180101-3-137",
							"testingReportUploadTime": 1514779200000,
							"testingReportUrl": null,
							"testingResult": "正常",
							"updateTime": 1530243680000
						}
					]
				}
			],
			"navigateFirstPage": 1,
			"navigateLastPage": 1,
			"navigatePages": 8,
			"navigatepageNums": [
				1
			],
			"nextPage": 0,
			"pageNum": 1,
			"pageSize": 10,
			"pages": 1,
			"prePage": 0,
			"size": 6,
			"startRow": 1,
			"total": 6
		}

返回字段解释：
	
* pageNum 当前页
* pageSize 页数
* pages 总页数
* total 总数
* list 数据数组
* orderPrice: 订单金额,
* doctorName 医生名称
* hospitalName 医院名称
* localStaffName 地服人员
* orderId 订单id
* orderNumber 订单编号 
* patientAge 病人年龄
* patientClinicalDiagnosis 临床诊断
* patientName 病人姓名
* orderLogisticsUrl 订单物流信息
* invoices 订单关联的发票信息
* testings 订单关联的检测套餐信息
* payments 缴款信息
* type 支付/还是退款 (pay/refund)
* total 支付或退款金额
* account 支付或退款账号
* userName 支付或退款人姓名
* localStaffPayroll 是否是地服转存(true/false) 
* localStaffPayrollTrans 是否是地服转存(是/否)
* paymentType  支付方式
* orderPayAttorneyUrl 授权委托书
* payTime 支付时间  



#### 收入明细表导出Excel

- Path：/api/export/query/income-detail
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/export/query/income-detail
Header: 
	x-auth: {登录时返回的token}
Query:
	{
		"startDate": "1527215234000",
		"endDate": "1527215234005"
	}
```

请求字段解释：

- `startDate` 开始时间，必填
- `endDate` 结束时间，必填

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"

```



#### 未开票明细表

- Path：/query/order-without-invoice
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/query/order-without-invoice?startDate=1000000000000&endDate=1000000000300
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"startDate": "1000000000000",
			"endDate": "1000000000300",
			"pageSize":10,
			"pageNum":1
		}

```

请求字段解释：

* `startDate` 开始时间，必填；
* `endDate` 结束时间，必填；
* `pageSize` 页数，选填，默认为10
* `pageNum` 当前页，选填，默认为1

返回成功示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
    {
		"endRow": 6,
		"firstPage": 1,
		"hasNextPage": false,
		"hasPreviousPage": false,
		"isFirstPage": true,
		"isLastPage": true,
		"lastPage": 1,
		"list": [
			{
				"balance": 2597400,
				"cashIncome": 2600000,
				"invoices": [
					{
						"createTime": 1531379498000,
						"deleted": false,
						"invoiceAmount": 173363,
						"invoiceBuyerAddress": null,
						"invoiceBuyerBank": null,
						"invoiceBuyerName": "吴美兴",
						"invoiceBuyerTaxPayerNumber": null,
						"invoiceCode": "3100171389",
						"invoiceDate": "2018-01-15",
						"invoiceGoodsCodeVersion": "1",
						"invoiceItemCount": 1,
						"invoiceItemName": "*信息技术服务*基因检测服务费",
						"invoiceItemUnitPrice": 173363,
						"invoiceNumber": "43641694",
						"invoicePostTaxAmount": 162962,
						"invoiceSellerAddress": null,
						"invoiceSellerBank": null,
						"invoiceSellerName": null,
						"invoiceSellerTaxPayerNumber": null,
						"invoiceTaxAmount": 10401,
						"invoiceTaxClassificationCode": "1.1",
						"invoiceTaxRate": 6,
						"invoiceTime": 1516032000000,
						"orderIds": null,
						"updateTime": 1531379498000
					},
					{
						"createTime": 1531379498000,
						"deleted": false,
						"invoiceAmount": 384747,
						"invoiceBuyerAddress": null,
						"invoiceBuyerBank": null,
						"invoiceBuyerName": "赵中锴",
						"invoiceBuyerTaxPayerNumber": null,
						"invoiceCode": "3100171391",
						"invoiceDate": "2018-01-15",
						"invoiceGoodsCodeVersion": "1",
						"invoiceItemCount": 1,
						"invoiceItemName": "*信息技术服务*基因检测服务费",
						"invoiceItemUnitPrice": 384747,
						"invoiceNumber": "43641694",
						"invoicePostTaxAmount": 361663,
						"invoiceSellerAddress": null,
						"invoiceSellerBank": null,
						"invoiceSellerName": null,
						"invoiceSellerTaxPayerNumber": null,
						"invoiceTaxAmount": 23084,
						"invoiceTaxClassificationCode": "1.1",
						"invoiceTaxRate": 6,
						"invoiceTime": 1516028400000,
						"orderIds": null,
						"updateTime": 1531379498000
					},
					{
						"createTime": 1531379499000,
						"deleted": false,
						"invoiceAmount": 144000,
						"invoiceBuyerAddress": null,
						"invoiceBuyerBank": null,
						"invoiceBuyerName": "李启菁",
						"invoiceBuyerTaxPayerNumber": null,
						"invoiceCode": "3100171396",
						"invoiceDate": "2018-01-16",
						"invoiceGoodsCodeVersion": "1",
						"invoiceItemCount": 1,
						"invoiceItemName": "*信息技术服务*基因检测服务费",
						"invoiceItemUnitPrice": 144000,
						"invoiceNumber": "43641694",
						"invoicePostTaxAmount": 135360,
						"invoiceSellerAddress": null,
						"invoiceSellerBank": null,
						"invoiceSellerName": null,
						"invoiceSellerTaxPayerNumber": null,
						"invoiceTaxAmount": 8640,
						"invoiceTaxClassificationCode": "1.1",
						"invoiceTaxRate": 6,
						"invoiceTime": 1516122000000,
						"orderIds": null,
						"updateTime": 1531379499000
					}
				],
				"orderCreateDate": "2018-01-03",
				"orderId": "GTON-20180103-1",
				"orderNumber": "GTON-20180103-1",
				"patientName": "赵山川",
				"payments": [
					{
						"account": "211324199006140734",
						"id": 1,
						"localStaffPayroll": true,
						"localStaffPayrollTrans": "是",
						"orderPayAttorneyUrl": "http://todaysoft.com.cn",
						"payTime": 1514952000000,
						"paymentType": "支付宝",
						"total": 5000,
						"tradeNumber": "2113241990",
						"type": "pay",
						"userName": "张某"
					},
					{
						"account": "",
						"id": 2,
						"localStaffPayroll": false,
						"localStaffPayrollTrans": "否",
						"orderPayAttorneyUrl": "http://todaysoft.com.cn",
						"payTime": 1514952000000,
						"paymentType": "支付宝",
						"total": -5000,
						"tradeNumber": "2113241991",
						"type": "refun",
						"userName": ""
					}
				],
				"testings": [
					{
						"createTime": 1530243682000,
						"id": 67,
						"orderId": "GTON-20180103-1",
						"sampleConfirmTime": 1514966400000,
						"samplingTime": 1514966400000,
						"testingAgency": "所罗门",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1003,
						"testingItem": "KRAS基因突变（血浆）",
						"testingItemCost": 1900000,
						"testingItemId": 6,
						"testingItemPrice": 2100000,
						"testingReportNumber": "GTON-20180103-1-411",
						"testingReportUploadTime": 1514970000000,
						"testingReportUrl": "http://todaysoft.com.cn",
						"testingResult": "正常",
						"updateTime": 1530243682000
					},
					{
						"createTime": 1530243682000,
						"id": 68,
						"orderId": "GTON-20180103-1",
						"sampleConfirmTime": 1514966400000,
						"samplingTime": 1514966400000,
						"testingAgency": "北京迈基诺",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1002,
						"testingItem": "EGFR基因突变（血浆）",
						"testingItemCost": 400000,
						"testingItemId": 4,
						"testingItemPrice": 500000,
						"testingReportNumber": "GTON-20180103-1-442",
						"testingReportUploadTime": 1514970000000,
						"testingReportUrl": null,
						"testingResult": "正常",
						"updateTime": 1530243682000
					}
				]
			},
			{
				"balance": 2100000,
				"cashIncome": 2100000,
				"invoices": [
					{
						"createTime": 1531379500000,
						"deleted": false,
						"invoiceAmount": 553500,
						"invoiceBuyerAddress": null,
						"invoiceBuyerBank": null,
						"invoiceBuyerName": "吴诗琪",
						"invoiceBuyerTaxPayerNumber": null,
						"invoiceCode": "3100171399",
						"invoiceDate": "2018-01-17",
						"invoiceGoodsCodeVersion": "1",
						"invoiceItemCount": 1,
						"invoiceItemName": "*信息技术服务*基因检测服务费",
						"invoiceItemUnitPrice": 553500,
						"invoiceNumber": "43641694",
						"invoicePostTaxAmount": 520290,
						"invoiceSellerAddress": null,
						"invoiceSellerBank": null,
						"invoiceSellerName": null,
						"invoiceSellerTaxPayerNumber": null,
						"invoiceTaxAmount": 33210,
						"invoiceTaxClassificationCode": "1.1",
						"invoiceTaxRate": 6,
						"invoiceTime": 1516194000000,
						"orderIds": null,
						"updateTime": 1531379500000
					},
					{
						"createTime": 1531379500000,
						"deleted": false,
						"invoiceAmount": 3654000,
						"invoiceBuyerAddress": null,
						"invoiceBuyerBank": null,
						"invoiceBuyerName": "钱虹君",
						"invoiceBuyerTaxPayerNumber": null,
						"invoiceCode": "3100171397",
						"invoiceDate": "2018-01-16",
						"invoiceGoodsCodeVersion": "1",
						"invoiceItemCount": 1,
						"invoiceItemName": "*信息技术服务*基因检测服务费",
						"invoiceItemUnitPrice": 3654000,
						"invoiceNumber": "43641694",
						"invoicePostTaxAmount": 3434760,
						"invoiceSellerAddress": null,
						"invoiceSellerBank": null,
						"invoiceSellerName": null,
						"invoiceSellerTaxPayerNumber": null,
						"invoiceTaxAmount": 219240,
						"invoiceTaxClassificationCode": "1.1",
						"invoiceTaxRate": 6,
						"invoiceTime": 1516100400000,
						"orderIds": null,
						"updateTime": 1531379500000
					}
				],
				"orderCreateDate": "2018-01-03",
				"orderId": "GTON-20180103-2",
				"orderNumber": "GTON-20180103-2",
				"patientName": "吴春芗",
				"payments": [
					{
						"account": "370983223724468200",
						"id": 3,
						"localStaffPayroll": true,
						"localStaffPayrollTrans": "是",
						"orderPayAttorneyUrl": "http://todaysoft.com.cn",
						"payTime": 1514962800000,
						"paymentType": "微信",
						"total": 6000,
						"tradeNumber": "2113241992",
						"type": "pay",
						"userName": "李某"
					}
				],
				"testings": [
					{
						"createTime": 1530243683000,
						"id": 73,
						"orderId": "GTON-20180103-2",
						"sampleConfirmTime": 1514962800000,
						"samplingTime": 1514962800000,
						"testingAgency": "所罗门",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1003,
						"testingItem": "KRAS基因突变（血浆）",
						"testingItemCost": 1900000,
						"testingItemId": 6,
						"testingItemPrice": 2100000,
						"testingReportNumber": "GTON-20180103-2-519",
						"testingReportUploadTime": 1514966400000,
						"testingReportUrl": "http://todaysoft.com.cn",
						"testingResult": "正常",
						"updateTime": 1530243683000
					}
				]
			},
			{
				"balance": 4000000,
				"cashIncome": 4000000,
				"invoices": [
					{
						"createTime": 1531379503000,
						"deleted": false,
						"invoiceAmount": 372072,
						"invoiceBuyerAddress": null,
						"invoiceBuyerBank": null,
						"invoiceBuyerName": "钱品阎",
						"invoiceBuyerTaxPayerNumber": null,
						"invoiceCode": "3100171414",
						"invoiceDate": "2018-01-19",
						"invoiceGoodsCodeVersion": "1",
						"invoiceItemCount": 1,
						"invoiceItemName": "*信息技术服务*基因检测服务费",
						"invoiceItemUnitPrice": 372072,
						"invoiceNumber": "43641694",
						"invoicePostTaxAmount": 349748,
						"invoiceSellerAddress": null,
						"invoiceSellerBank": null,
						"invoiceSellerName": null,
						"invoiceSellerTaxPayerNumber": null,
						"invoiceTaxAmount": 22324,
						"invoiceTaxClassificationCode": "1.1",
						"invoiceTaxRate": 6,
						"invoiceTime": 1516392000000,
						"orderIds": null,
						"updateTime": 1531379503000
					},
					{
						"createTime": 1531379503000,
						"deleted": false,
						"invoiceAmount": 1240000,
						"invoiceBuyerAddress": null,
						"invoiceBuyerBank": null,
						"invoiceBuyerName": "孙芳",
						"invoiceBuyerTaxPayerNumber": null,
						"invoiceCode": "3100171415",
						"invoiceDate": "2018-01-19",
						"invoiceGoodsCodeVersion": "1",
						"invoiceItemCount": 1,
						"invoiceItemName": "*信息技术服务*基因检测服务费",
						"invoiceItemUnitPrice": 1240000,
						"invoiceNumber": "43641694",
						"invoicePostTaxAmount": 1165600,
						"invoiceSellerAddress": null,
						"invoiceSellerBank": null,
						"invoiceSellerName": null,
						"invoiceSellerTaxPayerNumber": null,
						"invoiceTaxAmount": 74400,
						"invoiceTaxClassificationCode": "1.1",
						"invoiceTaxRate": 6,
						"invoiceTime": 1516348800000,
						"orderIds": null,
						"updateTime": 1531379503000
					}
				],
				"orderCreateDate": "2018-01-03",
				"orderId": "GTON-20180103-6",
				"orderNumber": "GTON-20180103-6",
				"patientName": "吴诗琪",
				"payments": [
					{
						"account": "370983495979869950",
						"id": 4,
						"localStaffPayroll": true,
						"localStaffPayrollTrans": "是",
						"orderPayAttorneyUrl": "http://todaysoft.com.cn",
						"payTime": 1514966400000,
						"paymentType": "银行转账",
						"total": 8000,
						"tradeNumber": "2113241993",
						"type": "pay",
						"userName": "刘某"
					},
					{
						"account": "370983495979869950",
						"id": 5,
						"localStaffPayroll": true,
						"localStaffPayrollTrans": "是",
						"orderPayAttorneyUrl": "http://todaysoft.com.cn",
						"payTime": 1514966400000,
						"paymentType": "微信",
						"total": 2000,
						"tradeNumber": "2113241994",
						"type": "pay",
						"userName": "刘某"
					}
				],
				"testings": [
					{
						"createTime": 1530243685000,
						"id": 113,
						"orderId": "GTON-20180103-6",
						"sampleConfirmTime": 1514952000000,
						"samplingTime": 1514952000000,
						"testingAgency": "北京迈基诺",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1002,
						"testingItem": "EGFR基因突变（血浆）",
						"testingItemCost": 400000,
						"testingItemId": 4,
						"testingItemPrice": 500000,
						"testingReportNumber": "GTON-20180103-6-898",
						"testingReportUploadTime": 1514955600000,
						"testingReportUrl": null,
						"testingResult": "正常",
						"updateTime": 1530243685000
					},
					{
						"createTime": 1530243685000,
						"id": 114,
						"orderId": "GTON-20180103-6",
						"sampleConfirmTime": 1514952000000,
						"samplingTime": 1514952000000,
						"testingAgency": "杰傲湃思",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1001,
						"testingItem": "HER2（ERBB2）扩增",
						"testingItemCost": 1000000,
						"testingItemId": 1,
						"testingItemPrice": 1500000,
						"testingReportNumber": "GTON-20180103-6-812",
						"testingReportUploadTime": 1514955600000,
						"testingReportUrl": null,
						"testingResult": "正常",
						"updateTime": 1530243685000
					},
					{
						"createTime": 1530243685000,
						"id": 115,
						"orderId": "GTON-20180103-6",
						"sampleConfirmTime": 1514952000000,
						"samplingTime": 1514952000000,
						"testingAgency": "所罗门",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1003,
						"testingItem": "KRAS基因突变",
						"testingItemCost": 1800000,
						"testingItemId": 5,
						"testingItemPrice": 2000000,
						"testingReportNumber": "GTON-20180103-6-486",
						"testingReportUploadTime": 1514955600000,
						"testingReportUrl": null,
						"testingResult": "正常",
						"updateTime": 1530243685000
					}
				]
			},
			{
				"balance": 3200000,
				"cashIncome": 3200000,
				"invoices": [
					{
						"createTime": 1530265204000,
						"deleted": false,
						"invoiceAmount": 3300000,
						"invoiceBuyerAddress": null,
						"invoiceBuyerBank": null,
						"invoiceBuyerName": "孙芳",
						"invoiceBuyerTaxPayerNumber": null,
						"invoiceCode": "3200171335",
						"invoiceDate": "2018-01-03",
						"invoiceGoodsCodeVersion": "1",
						"invoiceItemCount": 1,
						"invoiceItemName": "*信息技术服务*基因检测服务费",
						"invoiceItemUnitPrice": 3300000,
						"invoiceNumber": "43641694",
						"invoicePostTaxAmount": 3102000,
						"invoiceSellerAddress": "盐城经济技术开发区希望大道南路5号4楼14、15层 0515-6669888",
						"invoiceSellerBank": "中国农业银行盐城开发区科技支行 1041111",
						"invoiceSellerName": "江苏华生基因数据科技股份有限公司",
						"invoiceSellerTaxPayerNumber": "913209005592111",
						"invoiceTaxAmount": 198000,
						"invoiceTaxClassificationCode": "1.1",
						"invoiceTaxRate": 6,
						"invoiceTime": 0,
						"orderIds": null,
						"updateTime": 1530265204000
					}
				],
				"orderCreateDate": "2018-01-03",
				"orderId": "GTON-20180103-5",
				"orderNumber": "GTON-20180103-5",
				"patientName": "孙荣廷",
				"payments": [
					{
						"account": "370983214245431900",
						"id": 6,
						"localStaffPayroll": true,
						"localStaffPayrollTrans": "是",
						"orderPayAttorneyUrl": "http://todaysoft.com.cn",
						"payTime": 1514962800000,
						"paymentType": "支付宝",
						"total": 3000,
						"tradeNumber": "2113241995",
						"type": "pay",
						"userName": "苏某"
					}
				],
				"testings": [
					{
						"createTime": 1530243684000,
						"id": 98,
						"orderId": "GTON-20180103-5",
						"sampleConfirmTime": 1514952000000,
						"samplingTime": 1514952000000,
						"testingAgency": "北京迈基诺",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1002,
						"testingItem": "EGFR基因突变（血浆）",
						"testingItemCost": 400000,
						"testingItemId": 4,
						"testingItemPrice": 500000,
						"testingReportNumber": "GTON-20180103-5-326",
						"testingReportUploadTime": 1514955600000,
						"testingReportUrl": null,
						"testingResult": "正常",
						"updateTime": 1530243684000
					},
					{
						"createTime": 1530243684000,
						"id": 99,
						"orderId": "GTON-20180103-5",
						"sampleConfirmTime": 1514952000000,
						"samplingTime": 1514952000000,
						"testingAgency": "杰傲湃思",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1001,
						"testingItem": "PIK3CA基因突变",
						"testingItemCost": 1000000,
						"testingItemId": 2,
						"testingItemPrice": 1200000,
						"testingReportNumber": "GTON-20180103-5-772",
						"testingReportUploadTime": 1514955600000,
						"testingReportUrl": null,
						"testingResult": "正常",
						"updateTime": 1530243684000
					},
					{
						"createTime": 1530243684000,
						"id": 100,
						"orderId": "GTON-20180103-5",
						"sampleConfirmTime": 1514952000000,
						"samplingTime": 1514952000000,
						"testingAgency": "杰傲湃思",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1001,
						"testingItem": "HER2（ERBB2）扩增",
						"testingItemCost": 1000000,
						"testingItemId": 1,
						"testingItemPrice": 1500000,
						"testingReportNumber": "GTON-20180103-5-439",
						"testingReportUploadTime": 1514955600000,
						"testingReportUrl": null,
						"testingResult": "正常",
						"updateTime": 1530243684000
					}
				]
			},
			{
				"balance": 495000,
				"cashIncome": 500000,
				"invoices": [
					{
						"createTime": 1530265194000,
						"deleted": false,
						"invoiceAmount": 3500000,
						"invoiceBuyerAddress": null,
						"invoiceBuyerBank": null,
						"invoiceBuyerName": "赵璇海",
						"invoiceBuyerTaxPayerNumber": null,
						"invoiceCode": "3200171328",
						"invoiceDate": "2018-01-02",
						"invoiceGoodsCodeVersion": "1",
						"invoiceItemCount": 1,
						"invoiceItemName": "*信息技术服务*基因检测服务费",
						"invoiceItemUnitPrice": 3500000,
						"invoiceNumber": "43641694",
						"invoicePostTaxAmount": 3290000,
						"invoiceSellerAddress": "盐城经济技术开发区希望大道南路5号4楼14、15层 0515-6669888",
						"invoiceSellerBank": "中国农业银行盐城开发区科技支行 1041111",
						"invoiceSellerName": "江苏华生基因数据科技股份有限公司",
						"invoiceSellerTaxPayerNumber": "913209005592111",
						"invoiceTaxAmount": 210000,
						"invoiceTaxClassificationCode": "1.1",
						"invoiceTaxRate": 6,
						"invoiceTime": 0,
						"orderIds": null,
						"updateTime": 1530265194000
					}
				],
				"orderCreateDate": "2018-01-02",
				"orderId": "GTON-20180102-2",
				"orderNumber": "GTON-20180102-2",
				"patientName": "赵婧文",
				"payments": [
					{
						"account": "370983892420013400",
						"id": 7,
						"localStaffPayroll": true,
						"localStaffPayrollTrans": "是",
						"orderPayAttorneyUrl": "http://todaysoft.com.cn",
						"payTime": 1514854800000,
						"paymentType": "银行转账",
						"total": 4000,
						"tradeNumber": "2113241996",
						"type": "pay",
						"userName": "刘某"
					}
				],
				"testings": [
					{
						"createTime": 1530243681000,
						"id": 50,
						"orderId": "GTON-20180102-2",
						"sampleConfirmTime": 1514862000000,
						"samplingTime": 1514862000000,
						"testingAgency": "北京迈基诺",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1002,
						"testingItem": "EGFR基因突变（血浆）",
						"testingItemCost": 400000,
						"testingItemId": 4,
						"testingItemPrice": 500000,
						"testingReportNumber": "GTON-20180102-2-892",
						"testingReportUploadTime": 1514865600000,
						"testingReportUrl": null,
						"testingResult": "正常",
						"updateTime": 1530243681000
					}
				]
			},
			{
				"balance": 3200000,
				"cashIncome": 3200000,
				"invoices": [
					{
						"createTime": 1530265183000,
						"deleted": false,
						"invoiceAmount": 2800000,
						"invoiceBuyerAddress": null,
						"invoiceBuyerBank": null,
						"invoiceBuyerName": "吴易奚",
						"invoiceBuyerTaxPayerNumber": null,
						"invoiceCode": "3200171322",
						"invoiceDate": "2018-01-01",
						"invoiceGoodsCodeVersion": "1",
						"invoiceItemCount": 1,
						"invoiceItemName": "*信息技术服务*基因检测服务费",
						"invoiceItemUnitPrice": 2800000,
						"invoiceNumber": "43641694",
						"invoicePostTaxAmount": 2632000,
						"invoiceSellerAddress": "盐城经济技术开发区希望大道南路5号4楼14、15层 0515-6669888",
						"invoiceSellerBank": "中国农业银行盐城开发区科技支行 1041111",
						"invoiceSellerName": "江苏华生基因数据科技股份有限公司",
						"invoiceSellerTaxPayerNumber": "913209005592111",
						"invoiceTaxAmount": 168000,
						"invoiceTaxClassificationCode": "1.1",
						"invoiceTaxRate": 6,
						"invoiceTime": 0,
						"orderIds": null,
						"updateTime": 1530265183000
					}
				],
				"orderCreateDate": "2018-01-01",
				"orderId": "GTON-20180101-3",
				"orderNumber": "GTON-20180101-3",
				"patientName": "钱品妍",
				"payments": [
					{
						"account": "1514786400000",
						"id": 8,
						"localStaffPayroll": true,
						"localStaffPayrollTrans": "是",
						"orderPayAttorneyUrl": "http://todaysoft.com.cn",
						"payTime": 1514786400000,
						"paymentType": "支付宝",
						"total": 8000,
						"tradeNumber": "2113241997",
						"type": "pay",
						"userName": "新欧"
					},
					{
						"account": "",
						"id": 9,
						"localStaffPayroll": false,
						"localStaffPayrollTrans": "否",
						"orderPayAttorneyUrl": "http://todaysoft.com.cn",
						"payTime": 1514786400000,
						"paymentType": "微信",
						"total": -2000,
						"tradeNumber": "2113241998",
						"type": "refun",
						"userName": ""
					}
				],
				"testings": [
					{
						"createTime": 1530243680000,
						"id": 40,
						"orderId": "GTON-20180101-3",
						"sampleConfirmTime": 1514775600000,
						"samplingTime": 1514775600000,
						"testingAgency": "杰傲湃思",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1001,
						"testingItem": "PIK3CA基因突变",
						"testingItemCost": 1000000,
						"testingItemId": 2,
						"testingItemPrice": 1200000,
						"testingReportNumber": "GTON-20180101-3-9",
						"testingReportUploadTime": 1514779200000,
						"testingReportUrl": null,
						"testingResult": "正常",
						"updateTime": 1530243680000
					},
					{
						"createTime": 1530243680000,
						"id": 41,
						"orderId": "GTON-20180101-3",
						"sampleConfirmTime": 1514775600000,
						"samplingTime": 1514775600000,
						"testingAgency": "所罗门",
						"testingAgencyAddress": "北京市海淀区",
						"testingAgencyId": 1003,
						"testingItem": "KRAS基因突变",
						"testingItemCost": 1800000,
						"testingItemId": 5,
						"testingItemPrice": 2000000,
						"testingReportNumber": "GTON-20180101-3-137",
						"testingReportUploadTime": 1514779200000,
						"testingReportUrl": null,
						"testingResult": "正常",
						"updateTime": 1530243680000
					}
				]
			}
		],
		"navigateFirstPage": 1,
		"navigateLastPage": 1,
		"navigatePages": 8,
		"navigatepageNums": [
			1
		],
		"nextPage": 0,
		"pageNum": 1,
		"pageSize": 10,
		"pages": 1,
		"prePage": 0,
		"size": 6,
		"startRow": 1,
		"total": 6
	}

```
返回字段解释：

* balance: 差额,
* cashIncome 到账金额
* orderCreateDate 下单日期
* orderId 订单id
* orderNumber 订单编号 
* patientName 检测人
* invoices 订单关联的发票信息
* testings 订单关联的检测套餐信息
* payments 缴款信息
* type 支付/还是退款 (pay/refund)
* total 支付或退款金额
* account 支付或退款账号
* userName 支付或退款人姓名
* localStaffPayroll 是否是地服转存(true/false) 
* localStaffPayrollTrans 是否是地服转存(是/否)
* paymentType  支付方式
* orderPayAttorneyUrl 授权委托书
* payTime 支付时间



#### 未开票明细表导出Excel

- Path：api/export/query/order-without-invoice
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/export/query/order-without-invoice?startDate=1000000000000&endDate=1000000000300
Header: 
	x-auth: {登录时返回的token}

```

请求字段解释：

- `startDate` 开始时间，必填；
- `endDate` 结束时间，必填；

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"

```

#### 月平均销量表

* Path：/api/query/month/order-trend
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/query/month/order-trend
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"startDateStr": "2018-01",
			"endDateStr": "2018-05",
			"timeRange":"month",
			"pageSize":10,
			"pageNum":1
		}

请求字段解释：

* `startDateStr` 开始时间，必填
* `endDateStr` 结束时间，必填
* `timeRange` 维度，必填，目前只有month
* `pageSize` 页数，选填，默认为10
* `pageNum` 当前页，选填，默认为1


返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "endRow": 3,
	        "firstPage": 1,
	        "hasNextPage": false,
	        "hasPreviousPage": false,
	        "isFirstPage": true,
	        "isLastPage": true,
	        "lastPage": 1,
	        "list": [
	            {
	                "date": "2018-01",
	                "holidayPlaceAvgCost": 20666667,
	                "holidayPlaceAvgCount": 6,
	                "workPlaceAvgCost": 17663636,
	                "workPlaceAvgCount": 5
	            },
	            {
	                "date": "2018-02",
	                "holidayPlaceAvgCost": 17690909,
	                "holidayPlaceAvgCount": 6,
	                "workPlaceAvgCost": 19164706,
	                "workPlaceAvgCount": 6
	            },
	            {
	                "date": "2018-05",
	                "holidayPlaceAvgCost": 16311111,
	                "holidayPlaceAvgCount": 5,
	                "workPlaceAvgCost": 19286364,
	                "workPlaceAvgCount": 6
	            }
	        ],
	        "navigateFirstPage": 1,
	        "navigateLastPage": 1,
	        "navigatePages": 8,
	        "navigatepageNums": [
	            1
	        ],
	        "nextPage": 0,
	        "pageNum": 1,
	        "pageSize": 10,
	        "pages": 1,
	        "prePage": 0,
	        "size": 3,
	        "startRow": 1,
	        "total": 3
	    }

返回字段解释：
	
* pageNum 当前页
* pageSize 页数
* pages 总页数
* total 总数
* list 数据数组
* date 月份
* holidayPlaceAvgCost 节假日平均订单收入
* holidayPlaceAvgCount 节假日平均订单量
* workPlaceAvgCost 工作日平均订单收入
* workPlaceAvgCount 工作日平均订单量


#### 月平均销量表导出Excel

- Path：api/export/query/month/order-trend
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/export/query/month/order-trend
Header: 
	x-auth: {登录时返回的token}
    Query:
        {
            "startDateStr": "2018-01",
            "endDateStr": "2018-05",
            "timeRange":"month"
        }

```

请求字段解释：

* `startDateStr` 开始时间，必填
* `endDateStr` 结束时间，必填
* `timeRange` 维度，必填，目前只有month

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"

```


### 报表API

#### 业务明细表

* Path：/api/reports/business-detail
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/reports/business-detail?timeDimension=halfYear&dateStr=2018-h1
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"timeDimension": "halfYear",
			"dateStr": "2018-h1"
		}

请求字段解释：

* `timeDimension` 时间维度，必填
* `dateStr` 查询日期，必填

  | timeDimension |   dateStr格式    |
  | :-----------: | :--------------: |
  |      day      |    yyyy-MM-dd    |
  |     month     |     yyyy-MM      |
  |   halfYear    | yyyy-h1、yyyy-h2 |
  |     year      |       yyyy       |

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
	        "sum": {
	            "orderIncome": 6400,
	            "financeConfirmIncome": 3200,
	            "integralCost": 100,
	            "cashIncome": 5300,
	            "testingItemConfirmCost": 2100,
	            "testingItemCost": 2100
	        },
	        "list": [
	            {
	                "orderIncome": 3400,
	                "financeConfirmIncome": 2200,
	                "integralCost": 100,
	                "cashIncome": 3300,
	                "testingItemConfirmCost": 2100,
	                "testingItemCost": 1100
	                "dateStr": "2018-05",	                          
	            }
	        ]
	    }

返回字段解释：
	
* sum 总计
* orderIncome 订单金额(总计)
* financeConfirmIncome 开票金额(总计)
* integralCost 积分金额(总计)
* cashIncome 到账金额(总计)
* testingItemCost 检验机构结算金额(总计)
	 list 数据数组	
* dateStr  日期
* orderNumber 订单编号
* 其他字段,同订单表字段一致



#### 业务明细表导出Excel

- Path：/api/export/reports/business-detail
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/export/reports/business-detail?timeDimension=halfYear&dateStr=2018-h1
Header: 
	x-auth: {登录时返回的token}
Query:
	{
		"timeDimension": "halfYear",
		"dateStr": "2018-h1"
	}
```

请求字段解释：

- `timeDimension` 时间维度，必填

- `dateStr` 查询日期，必填

  | timeDimension |   dateStr格式    |
  | :-----------: | :--------------: |
  |      day      |    yyyy-MM-dd    |
  |     month     |     yyyy-MM      |
  |   halfYear    | yyyy-h1、yyyy-h2 |
  |     year      |       yyyy       |

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"
```



#### 大区对比

* Path：/reports/region-contrast
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET https://domain.com/api/reports/region-contrast?dateStr=2018-05-31&timeDimension=day
	Header: 
		x-auth: {登录时返回的token}

请求字段解释：

* `timeDimension` 时间维度，必填；可选值：day、month、halfYear、year

* `dateStr` 时间维度对应的时间，必填，格式如下

  | timeDimension |   dateStr格式    |
  | :-----------: | :--------------: |
  |      day      |    yyyy-MM-dd    |
  |     month     |     yyyy-MM      |
  |   halfYear    | yyyy-h1、yyyy-h2 |
  |     year      |       yyyy       |

返回成功示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
    {
        "sum": {
            "orderCancelAvgCost": 100,
            "orderCancelCount": 2,
            "orderIncome": 180,
            "regionId": "sum",
            "financeConfirmIncome": 0,
            "regionName": "总计",
            "orderPlaceCount": 3,
            "integralCost": 0,
            "cashIncome": 0,
            "orderPlaceAvgCost": 127,
            "testingItemConfirmCost": 2100,
            "testingItemCost": 0
        },
        "list": [
            {
                "orderCancelAvgCost": 100,
                "orderCancelCount": 2,
                "orderIncome": 180,
                "regionId": "1",
                "financeConfirmIncome": 0,
                "regionName": "华北",
                "orderPlaceCount": 3,
                "integralCost": 0,
                "cashIncome": 0,
                "orderPlaceAvgCost": 127,
                "testingItemConfirmCost": 2100,
                "testingItemCost": 0
            }
        ]
    }
```



#### 大区对比导出Excel

- Path：api/export/reports/region-contrast
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/export/reports/region-contrast?dateStr=2018-05-31&timeDimension=day
Header: 
	x-auth: {登录时返回的token}
```

请求字段解释：

- `timeDimension` 时间维度，必填；可选值：day、month、halfYear、year

- `dateStr` 时间维度对应的时间，必填，格式如下

  | timeDimension |   dateStr格式    |
  | :-----------: | :--------------: |
  |      day      |    yyyy-MM-dd    |
  |     month     |     yyyy-MM      |
  |   halfYear    | yyyy-h1、yyyy-h2 |
  |     year      |       yyyy       |

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"
```



#### 省份对比

- Path：/reports/province-contrast
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/reports/province-contrast?dateStr=2018-05-31&timeDimension=day
Header: 
	x-auth: {登录时返回的token}

```

请求字段解释：

- `timeDimension` 时间维度，必填；可选值：day、month、halfYear、year

- `dateStr` 时间维度对应的时间，必填，格式如下

  | timeDimension |   dateStr格式    |
  | :-----------: | :--------------: |
  |      day      |    yyyy-MM-dd    |
  |     month     |     yyyy-MM      |
  |   halfYear    | yyyy-h1、yyyy-h2 |
  |     year      |       yyyy       |

返回成功示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
    {
        "sum": {
            "orderCancelAvgCost": 100,
            "orderCancelCount": 2,
            "orderIncome": 180,
            "financeConfirmIncome": 0,
            "orderPlaceCount": 3,
            "integralCost": 0,
            "provinceName": "总计",
            "provinceId": "sum",
            "cashIncome": 0,
            "orderPlaceAvgCost": 127,
            "testingItemConfirmCost": 0,
            "testingItemCost": 0
        },
        "list": [
            {
                "orderCancelAvgCost": 100,
                "orderCancelCount": 2,
                "orderIncome": 180,
                "financeConfirmIncome": 0,
                "orderPlaceCount": 3,
                "integralCost": 0,
                "provinceName": "山东",
                "provinceId": "1",
                "cashIncome": 0,
                "orderPlaceAvgCost": 127,
                "testingItemConfirmCost": 0,
                "testingItemCost": 0
            }
        ]
    }
```



#### 省份对比导出Excel

- Path：api/export/reports/province-contrast
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/export/reports/province-contrast?dateStr=2018-05-31&timeDimension=day
Header: 
	x-auth: {登录时返回的token}

```

请求字段解释：

- `timeDimension` 时间维度，必填；可选值：day、month、halfYear、year

- `dateStr` 时间维度对应的时间，必填，格式如下

  | timeDimension |   dateStr格式    |
  | :-----------: | :--------------: |
  |      day      |    yyyy-MM-dd    |
  |     month     |     yyyy-MM      |
  |   halfYear    | yyyy-h1、yyyy-h2 |
  |     year      |       yyyy       |

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"
```



#### 检测项目对比

- Path：/reports/testing-item-contrast
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/reports/testing-item-contrast?dateStr=2018-05-31&timeDimension=day
Header: 
	x-auth: {登录时返回的token}
```

请求字段解释：

- `timeDimension` 时间维度，必填；可选值：day、month、halfYear、year

- `dateStr` 时间维度对应的时间，必填，格式如下

  | timeDimension |   dateStr格式    |
  | :-----------: | :--------------: |
  |      day      |    yyyy-MM-dd    |
  |     month     |     yyyy-MM      |
  |   halfYear    | yyyy-h1、yyyy-h2 |
  |     year      |       yyyy       |

- `sortBy`根据哪项进行排序，可选值：`placeCount`(下单数，默认)、`orderIncome`(订单金额)

- `sortMode`排序方式可选值：`desc`(降序，默认)、`asc`(升序)



返回成功示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
    {
        "sum": {
            "testingItem": "总计",
            "testingItemFinanceConfirmIncome": 0,
            "testingItemOrderIncome": 1500,
            "testingItemRecordCost": 0,
            "testingItemConfirmRecordCost": 0,
            "testingItemCashIncome": 0,
            "testingItemId": "sum"
        },
        "list": [
            {
                "testingItem": "血液套餐1",
                "testingItemFinanceConfirmIncome": 0,
                "testingItemOrderIncome": 1500,
                "testingItemRecordCost": 0,
                "testingItemConfirmRecordCost": 0,
                "testingItemCashIncome": 0,
                "testingItemId": "4001"
            }
        ]
    }
```



#### 检测项目对比导出Excel

- Path：api/export/reports/testing-item-contrast
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET https://domain.com/api/export/reports/testing-item-contrast?dateStr=2018-05-31&timeDimension=day
Header: 
	x-auth: {登录时返回的token}

```

请求字段解释：

- `timeDimension` 时间维度，必填；可选值：day、month、halfYear、year

- `dateStr` 时间维度对应的时间，必填，格式如下

  | timeDimension |   dateStr格式    |
  | :-----------: | :--------------: |
  |      day      |    yyyy-MM-dd    |
  |     month     |     yyyy-MM      |
  |   halfYear    | yyyy-h1、yyyy-h2 |
  |     year      |       yyyy       |

- `sortBy`根据哪项进行排序，可选值：`placeCount`(下单数，默认)、`orderIncome`(订单金额)

- `sortMode`排序方式可选值：`desc`(降序，默认)、`asc`(升序)

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"
```



#### 利润表

* Path：/api/reports/profits
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/reports/profits?timeDimension=halfYear&dateStr=2018-h1
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"timeDimension": "halfYear",
			"dateStr": "2018-h1"
		}

请求字段解释：

* `timeDimension` 时间维度，必填
* `dateStr` 查询日期，必填


返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
	    {
	        "sum": {
	            "orderCancelAvgCost": 100,
	            "regionName": null,
	            "cashIncome": 0,
	            "orderPlaceAvgCost": 127,
	            "total": 0,
	            "orderCancelCount": 2,
	            "orderIncome": 180,
	            "regionId": null,
	            "financeConfirmIncome": 0,
	            "orderPlaceCount": 3,
	            "integralCost": 0,
	            "profit": 0,
	            "testingItemCost": 0,
	            "testingItemConfirmCost": 0
	        },
	        "list": [
	            {
	                "orderCancelAvgCost": 100,
	                "regionName": "华北",
	                "cashIncome": 0,
	                "orderPlaceAvgCost": 127,
	                "total": 0,
	                "orderCancelCount": 2,
	                "orderIncome": 180,
	                "regionId": "1",
	                "financeConfirmIncome": 0,
	                "orderPlaceCount": 3,
	                "integralCost": 0,
	                "profit": 0,
	                "testingItemCost": 0,
	                "testingItemConfirmCost": 0
	            }
	        ]
	    }


返回字段解释：
	
* sum 总计
* orderIncome 订单金额(总计)
* financeConfirmIncome 开票金额(总计)
* integralCost 积分金额(总计)
* cashIncome 到账金额(总计)
* testingItemCost 检验机构结算金额(总计)
* profit 毛利(总计)
* total 物流费用(总计)
   list 数据数组	
* regionName  地区



#### 利润表导出Excel

- Path：/api/export/reports/profits
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/export/reports/profits?timeDimension=halfYear&dateStr=2018-h1
Header: 
	x-auth: {登录时返回的token}
Query:
	{
		"timeDimension": "halfYear",
		"dateStr": "2018-h1"
	}

```

请求字段解释：

- `timeDimension` 时间维度，必填
- `dateStr` 查询日期，必填

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"

```



#### 收入明细表

* Path：/api/reports/income-detail
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/reports/income-detail
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"timeDimension": "month",
			"dateStr": "2018-05"
		}

请求字段解释：

* `timeDimension` 时间维度，月
* `dateStr` 时间，必填



返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
			"sum": {
				"testingItemPrice": 15600000,
				"orderPrice": 15600000,
				"testingItemCost": 13000000
			},
			"list": [
				{
					"patientName": "钱品妍",
					"orderNumber": "GTON-20180101-3",
					"orderId": "GTON-20180101-3",
					"payments": [
						{
							"tradeNumber": "2113241997",
							"total": 8000,
							"localStaffPayroll": true,
							"payTime": 1514786400000,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"id": 8,
							"type": "pay",
							"userName": "新欧",
							"account": "1514786400000",
							"paymentType": "支付宝"
						},
						{
							"tradeNumber": "2113241998",
							"total": -2000,
							"localStaffPayroll": false,
							"payTime": 1514786400000,
							"localStaffPayrollTrans": "否",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"id": 9,
							"type": "refun",
							"userName": "",
							"account": "",
							"paymentType": "微信"
						}
					],
					"hospitalName": "北大人民医",
					"testings": [
						{
							"testingAgencyId": 1001,
							"orderId": "GTON-20180101-3",
							"testingAgency": "杰傲湃思",
							"updateTime": 1530243680000,
							"testingResult": "正常",
							"testingItemId": 2,
							"testingItemPrice": 1200000,
							"testingItem": "PIK3CA基因突变",
							"testingReportUrl": null,
							"samplingTime": 1514775600000,
							"createTime": 1530243680000,
							"sampleConfirmTime": 1514775600000,
							"testingReportUploadTime": 1514779200000,
							"id": 40,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 1000000,
							"testingReportNumber": "GTON-20180101-3-9"
						},
						{
							"testingAgencyId": 1003,
							"orderId": "GTON-20180101-3",
							"testingAgency": "所罗门",
							"updateTime": 1530243680000,
							"testingResult": "正常",
							"testingItemId": 5,
							"testingItemPrice": 2000000,
							"testingItem": "KRAS基因突变",
							"testingReportUrl": null,
							"samplingTime": 1514775600000,
							"createTime": 1530243680000,
							"sampleConfirmTime": 1514775600000,
							"testingReportUploadTime": 1514779200000,
							"id": 41,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 1800000,
							"testingReportNumber": "GTON-20180101-3-137"
						}
					],
					"doctorName": "曹杰卿",
					"invoices": [
						{
							"invoiceSellerAddress": "盐城经济技术开发区希望大道南路5号4楼14、15层 0515-6669888",
							"invoiceTime": 0,
							"invoiceTaxAmount": 168000,
							"invoiceAmount": 2800000,
							"invoicePostTaxAmount": 2632000,
							"invoiceItemUnitPrice": 2800000,
							"invoiceNumber": "43641694",
							"orderIds": null,
							"invoiceBuyerName": "吴易奚",
							"invoiceSellerBank": "中国农业银行盐城开发区科技支行 1041111",
							"updateTime": 1530265183000,
							"invoiceDate": "2018-01-01",
							"invoiceCode": "3200171322",
							"invoiceSellerTaxPayerNumber": "913209005592111",
							"invoiceTaxRate": 6,
							"invoiceBuyerBank": null,
							"invoiceSellerName": "江苏华生基因数据科技股份有限公司",
							"deleted": false,
							"invoiceBuyerTaxPayerNumber": null,
							"createTime": 1530265183000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemCount": 1,
							"invoiceBuyerAddress": null,
							"invoiceGoodsCodeVersion": "1"
						}
					],
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180101-3",
					"patientClinicalDiagnosis": "胆囊癌",
					"patientAge": 59,
					"orderPrice": 3200000,
					"localStaffName": "陈品如"
				},
				{
					"patientName": "赵婧文",
					"orderNumber": "GTON-20180102-2",
					"orderId": "GTON-20180102-2",
					"payments": [
						{
							"tradeNumber": "2113241996",
							"total": 4000,
							"localStaffPayroll": true,
							"payTime": 1514854800000,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"id": 7,
							"type": "pay",
							"userName": "刘某",
							"account": "370983892420013400",
							"paymentType": "银行转账"
						}
					],
					"hospitalName": "北医三院",
					"testings": [
						{
							"testingAgencyId": 1002,
							"orderId": "GTON-20180102-2",
							"testingAgency": "北京迈基诺",
							"updateTime": 1530243681000,
							"testingResult": "正常",
							"testingItemId": 4,
							"testingItemPrice": 500000,
							"testingItem": "EGFR基因突变（血浆）",
							"testingReportUrl": null,
							"samplingTime": 1514862000000,
							"createTime": 1530243681000,
							"sampleConfirmTime": 1514862000000,
							"testingReportUploadTime": 1514865600000,
							"id": 50,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 400000,
							"testingReportNumber": "GTON-20180102-2-892"
						}
					],
					"doctorName": "金启昭",
					"invoices": [
						{
							"invoiceSellerAddress": "盐城经济技术开发区希望大道南路5号4楼14、15层 0515-6669888",
							"invoiceTime": 0,
							"invoiceTaxAmount": 210000,
							"invoiceAmount": 3500000,
							"invoicePostTaxAmount": 3290000,
							"invoiceItemUnitPrice": 3500000,
							"invoiceNumber": "43641694",
							"orderIds": null,
							"invoiceBuyerName": "赵璇海",
							"invoiceSellerBank": "中国农业银行盐城开发区科技支行 1041111",
							"updateTime": 1530265194000,
							"invoiceDate": "2018-01-02",
							"invoiceCode": "3200171328",
							"invoiceSellerTaxPayerNumber": "913209005592111",
							"invoiceTaxRate": 6,
							"invoiceBuyerBank": null,
							"invoiceSellerName": "江苏华生基因数据科技股份有限公司",
							"deleted": false,
							"invoiceBuyerTaxPayerNumber": null,
							"createTime": 1530265194000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemCount": 1,
							"invoiceBuyerAddress": null,
							"invoiceGoodsCodeVersion": "1"
						}
					],
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180102-2",
					"patientClinicalDiagnosis": "淋巴癌",
					"patientAge": 77,
					"orderPrice": 500000,
					"localStaffName": "陈品如"
				},
				{
					"patientName": "赵山川",
					"orderNumber": "GTON-20180103-1",
					"orderId": "GTON-20180103-1",
					"payments": [
						{
							"tradeNumber": "2113241990",
							"total": 5000,
							"localStaffPayroll": true,
							"payTime": 1514952000000,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"id": 1,
							"type": "pay",
							"userName": "张某",
							"account": "211324199006140734",
							"paymentType": "支付宝"
						},
						{
							"tradeNumber": "2113241991",
							"total": -5000,
							"localStaffPayroll": false,
							"payTime": 1514952000000,
							"localStaffPayrollTrans": "否",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"id": 2,
							"type": "refun",
							"userName": "",
							"account": "",
							"paymentType": "支付宝"
						}
					],
					"hospitalName": "聊城市人民医院",
					"testings": [
						{
							"testingAgencyId": 1003,
							"orderId": "GTON-20180103-1",
							"testingAgency": "所罗门",
							"updateTime": 1530243682000,
							"testingResult": "正常",
							"testingItemId": 6,
							"testingItemPrice": 2100000,
							"testingItem": "KRAS基因突变（血浆）",
							"testingReportUrl": "http://todaysoft.com.cn",
							"samplingTime": 1514966400000,
							"createTime": 1530243682000,
							"sampleConfirmTime": 1514966400000,
							"testingReportUploadTime": 1514970000000,
							"id": 67,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 1900000,
							"testingReportNumber": "GTON-20180103-1-411"
						},
						{
							"testingAgencyId": 1002,
							"orderId": "GTON-20180103-1",
							"testingAgency": "北京迈基诺",
							"updateTime": 1530243682000,
							"testingResult": "正常",
							"testingItemId": 4,
							"testingItemPrice": 500000,
							"testingItem": "EGFR基因突变（血浆）",
							"testingReportUrl": null,
							"samplingTime": 1514966400000,
							"createTime": 1530243682000,
							"sampleConfirmTime": 1514966400000,
							"testingReportUploadTime": 1514970000000,
							"id": 68,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 400000,
							"testingReportNumber": "GTON-20180103-1-442"
						}
					],
					"doctorName": "姜骏烁",
					"invoices": [
						{
							"invoiceSellerAddress": null,
							"invoiceTime": 1516032000000,
							"invoiceTaxAmount": 10401,
							"invoiceAmount": 173363,
							"invoicePostTaxAmount": 162962,
							"invoiceItemUnitPrice": 173363,
							"invoiceNumber": "43641694",
							"orderIds": null,
							"invoiceBuyerName": "吴美兴",
							"invoiceSellerBank": null,
							"updateTime": 1531379498000,
							"invoiceDate": "2018-01-15",
							"invoiceCode": "3100171389",
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxRate": 6,
							"invoiceBuyerBank": null,
							"invoiceSellerName": null,
							"deleted": false,
							"invoiceBuyerTaxPayerNumber": null,
							"createTime": 1531379498000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemCount": 1,
							"invoiceBuyerAddress": null,
							"invoiceGoodsCodeVersion": "1"
						},
						{
							"invoiceSellerAddress": null,
							"invoiceTime": 1516028400000,
							"invoiceTaxAmount": 23084,
							"invoiceAmount": 384747,
							"invoicePostTaxAmount": 361663,
							"invoiceItemUnitPrice": 384747,
							"invoiceNumber": "43641694",
							"orderIds": null,
							"invoiceBuyerName": "赵中锴",
							"invoiceSellerBank": null,
							"updateTime": 1531379498000,
							"invoiceDate": "2018-01-15",
							"invoiceCode": "3100171391",
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxRate": 6,
							"invoiceBuyerBank": null,
							"invoiceSellerName": null,
							"deleted": false,
							"invoiceBuyerTaxPayerNumber": null,
							"createTime": 1531379498000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemCount": 1,
							"invoiceBuyerAddress": null,
							"invoiceGoodsCodeVersion": "1"
						},
						{
							"invoiceSellerAddress": null,
							"invoiceTime": 1516122000000,
							"invoiceTaxAmount": 8640,
							"invoiceAmount": 144000,
							"invoicePostTaxAmount": 135360,
							"invoiceItemUnitPrice": 144000,
							"invoiceNumber": "43641694",
							"orderIds": null,
							"invoiceBuyerName": "李启菁",
							"invoiceSellerBank": null,
							"updateTime": 1531379499000,
							"invoiceDate": "2018-01-16",
							"invoiceCode": "3100171396",
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxRate": 6,
							"invoiceBuyerBank": null,
							"invoiceSellerName": null,
							"deleted": false,
							"invoiceBuyerTaxPayerNumber": null,
							"createTime": 1531379499000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemCount": 1,
							"invoiceBuyerAddress": null,
							"invoiceGoodsCodeVersion": "1"
						}
					],
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180103-1",
					"patientClinicalDiagnosis": "肝癌",
					"patientAge": 83,
					"orderPrice": 2600000,
					"localStaffName": "秦艺菲"
				},
				{
					"patientName": "吴春芗",
					"orderNumber": "GTON-20180103-2",
					"orderId": "GTON-20180103-2",
					"payments": [
						{
							"tradeNumber": "2113241992",
							"total": 6000,
							"localStaffPayroll": true,
							"payTime": 1514962800000,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"id": 3,
							"type": "pay",
							"userName": "李某",
							"account": "370983223724468200",
							"paymentType": "微信"
						}
					],
					"hospitalName": "北医三院",
					"testings": [
						{
							"testingAgencyId": 1003,
							"orderId": "GTON-20180103-2",
							"testingAgency": "所罗门",
							"updateTime": 1530243683000,
							"testingResult": "正常",
							"testingItemId": 6,
							"testingItemPrice": 2100000,
							"testingItem": "KRAS基因突变（血浆）",
							"testingReportUrl": "http://todaysoft.com.cn",
							"samplingTime": 1514962800000,
							"createTime": 1530243683000,
							"sampleConfirmTime": 1514962800000,
							"testingReportUploadTime": 1514966400000,
							"id": 73,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 1900000,
							"testingReportNumber": "GTON-20180103-2-519"
						}
					],
					"doctorName": "金启昭",
					"invoices": [
						{
							"invoiceSellerAddress": null,
							"invoiceTime": 1516194000000,
							"invoiceTaxAmount": 33210,
							"invoiceAmount": 553500,
							"invoicePostTaxAmount": 520290,
							"invoiceItemUnitPrice": 553500,
							"invoiceNumber": "43641694",
							"orderIds": null,
							"invoiceBuyerName": "吴诗琪",
							"invoiceSellerBank": null,
							"updateTime": 1531379500000,
							"invoiceDate": "2018-01-17",
							"invoiceCode": "3100171399",
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxRate": 6,
							"invoiceBuyerBank": null,
							"invoiceSellerName": null,
							"deleted": false,
							"invoiceBuyerTaxPayerNumber": null,
							"createTime": 1531379500000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemCount": 1,
							"invoiceBuyerAddress": null,
							"invoiceGoodsCodeVersion": "1"
						},
						{
							"invoiceSellerAddress": null,
							"invoiceTime": 1516100400000,
							"invoiceTaxAmount": 219240,
							"invoiceAmount": 3654000,
							"invoicePostTaxAmount": 3434760,
							"invoiceItemUnitPrice": 3654000,
							"invoiceNumber": "43641694",
							"orderIds": null,
							"invoiceBuyerName": "钱虹君",
							"invoiceSellerBank": null,
							"updateTime": 1531379500000,
							"invoiceDate": "2018-01-16",
							"invoiceCode": "3100171397",
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxRate": 6,
							"invoiceBuyerBank": null,
							"invoiceSellerName": null,
							"deleted": false,
							"invoiceBuyerTaxPayerNumber": null,
							"createTime": 1531379500000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemCount": 1,
							"invoiceBuyerAddress": null,
							"invoiceGoodsCodeVersion": "1"
						}
					],
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180103-2",
					"patientClinicalDiagnosis": "胰腺癌",
					"patientAge": 64,
					"orderPrice": 2100000,
					"localStaffName": "孔祥栋"
				},
				{
					"patientName": "孙荣廷",
					"orderNumber": "GTON-20180103-5",
					"orderId": "GTON-20180103-5",
					"payments": [
						{
							"tradeNumber": "2113241995",
							"total": 3000,
							"localStaffPayroll": true,
							"payTime": 1514962800000,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"id": 6,
							"type": "pay",
							"userName": "苏某",
							"account": "370983214245431900",
							"paymentType": "支付宝"
						}
					],
					"hospitalName": "北京阜外医院",
					"testings": [
						{
							"testingAgencyId": 1002,
							"orderId": "GTON-20180103-5",
							"testingAgency": "北京迈基诺",
							"updateTime": 1530243684000,
							"testingResult": "正常",
							"testingItemId": 4,
							"testingItemPrice": 500000,
							"testingItem": "EGFR基因突变（血浆）",
							"testingReportUrl": null,
							"samplingTime": 1514952000000,
							"createTime": 1530243684000,
							"sampleConfirmTime": 1514952000000,
							"testingReportUploadTime": 1514955600000,
							"id": 98,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 400000,
							"testingReportNumber": "GTON-20180103-5-326"
						},
						{
							"testingAgencyId": 1001,
							"orderId": "GTON-20180103-5",
							"testingAgency": "杰傲湃思",
							"updateTime": 1530243684000,
							"testingResult": "正常",
							"testingItemId": 2,
							"testingItemPrice": 1200000,
							"testingItem": "PIK3CA基因突变",
							"testingReportUrl": null,
							"samplingTime": 1514952000000,
							"createTime": 1530243684000,
							"sampleConfirmTime": 1514952000000,
							"testingReportUploadTime": 1514955600000,
							"id": 99,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 1000000,
							"testingReportNumber": "GTON-20180103-5-772"
						},
						{
							"testingAgencyId": 1001,
							"orderId": "GTON-20180103-5",
							"testingAgency": "杰傲湃思",
							"updateTime": 1530243684000,
							"testingResult": "正常",
							"testingItemId": 1,
							"testingItemPrice": 1500000,
							"testingItem": "HER2（ERBB2）扩增",
							"testingReportUrl": null,
							"samplingTime": 1514952000000,
							"createTime": 1530243684000,
							"sampleConfirmTime": 1514952000000,
							"testingReportUploadTime": 1514955600000,
							"id": 100,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 1000000,
							"testingReportNumber": "GTON-20180103-5-439"
						}
					],
					"doctorName": "姜博",
					"invoices": [
						{
							"invoiceSellerAddress": "盐城经济技术开发区希望大道南路5号4楼14、15层 0515-6669888",
							"invoiceTime": 0,
							"invoiceTaxAmount": 198000,
							"invoiceAmount": 3300000,
							"invoicePostTaxAmount": 3102000,
							"invoiceItemUnitPrice": 3300000,
							"invoiceNumber": "43641694",
							"orderIds": null,
							"invoiceBuyerName": "孙芳",
							"invoiceSellerBank": "中国农业银行盐城开发区科技支行 1041111",
							"updateTime": 1530265204000,
							"invoiceDate": "2018-01-03",
							"invoiceCode": "3200171335",
							"invoiceSellerTaxPayerNumber": "913209005592111",
							"invoiceTaxRate": 6,
							"invoiceBuyerBank": null,
							"invoiceSellerName": "江苏华生基因数据科技股份有限公司",
							"deleted": false,
							"invoiceBuyerTaxPayerNumber": null,
							"createTime": 1530265204000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemCount": 1,
							"invoiceBuyerAddress": null,
							"invoiceGoodsCodeVersion": "1"
						}
					],
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180103-5",
					"patientClinicalDiagnosis": "鼻咽癌",
					"patientAge": 59,
					"orderPrice": 3200000,
					"localStaffName": "孔祥栋"
				},
				{
					"patientName": "吴诗琪",
					"orderNumber": "GTON-20180103-6",
					"orderId": "GTON-20180103-6",
					"payments": [
						{
							"tradeNumber": "2113241993",
							"total": 8000,
							"localStaffPayroll": true,
							"payTime": 1514966400000,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"id": 4,
							"type": "pay",
							"userName": "刘某",
							"account": "370983495979869950",
							"paymentType": "银行转账"
						},
						{
							"tradeNumber": "2113241994",
							"total": 2000,
							"localStaffPayroll": true,
							"payTime": 1514966400000,
							"localStaffPayrollTrans": "是",
							"orderPayAttorneyUrl": "http://todaysoft.com.cn",
							"id": 5,
							"type": "pay",
							"userName": "刘某",
							"account": "370983495979869950",
							"paymentType": "微信"
						}
					],
					"hospitalName": "北京阜外医院",
					"testings": [
						{
							"testingAgencyId": 1002,
							"orderId": "GTON-20180103-6",
							"testingAgency": "北京迈基诺",
							"updateTime": 1530243685000,
							"testingResult": "正常",
							"testingItemId": 4,
							"testingItemPrice": 500000,
							"testingItem": "EGFR基因突变（血浆）",
							"testingReportUrl": null,
							"samplingTime": 1514952000000,
							"createTime": 1530243685000,
							"sampleConfirmTime": 1514952000000,
							"testingReportUploadTime": 1514955600000,
							"id": 113,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 400000,
							"testingReportNumber": "GTON-20180103-6-898"
						},
						{
							"testingAgencyId": 1001,
							"orderId": "GTON-20180103-6",
							"testingAgency": "杰傲湃思",
							"updateTime": 1530243685000,
							"testingResult": "正常",
							"testingItemId": 1,
							"testingItemPrice": 1500000,
							"testingItem": "HER2（ERBB2）扩增",
							"testingReportUrl": null,
							"samplingTime": 1514952000000,
							"createTime": 1530243685000,
							"sampleConfirmTime": 1514952000000,
							"testingReportUploadTime": 1514955600000,
							"id": 114,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 1000000,
							"testingReportNumber": "GTON-20180103-6-812"
						},
						{
							"testingAgencyId": 1003,
							"orderId": "GTON-20180103-6",
							"testingAgency": "所罗门",
							"updateTime": 1530243685000,
							"testingResult": "正常",
							"testingItemId": 5,
							"testingItemPrice": 2000000,
							"testingItem": "KRAS基因突变",
							"testingReportUrl": null,
							"samplingTime": 1514952000000,
							"createTime": 1530243685000,
							"sampleConfirmTime": 1514952000000,
							"testingReportUploadTime": 1514955600000,
							"id": 115,
							"testingAgencyAddress": "北京市海淀区",
							"testingItemCost": 1800000,
							"testingReportNumber": "GTON-20180103-6-486"
						}
					],
					"doctorName": "姜博",
					"invoices": [
						{
							"invoiceSellerAddress": null,
							"invoiceTime": 1516392000000,
							"invoiceTaxAmount": 22324,
							"invoiceAmount": 372072,
							"invoicePostTaxAmount": 349748,
							"invoiceItemUnitPrice": 372072,
							"invoiceNumber": "43641694",
							"orderIds": null,
							"invoiceBuyerName": "钱品阎",
							"invoiceSellerBank": null,
							"updateTime": 1531379503000,
							"invoiceDate": "2018-01-19",
							"invoiceCode": "3100171414",
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxRate": 6,
							"invoiceBuyerBank": null,
							"invoiceSellerName": null,
							"deleted": false,
							"invoiceBuyerTaxPayerNumber": null,
							"createTime": 1531379503000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemCount": 1,
							"invoiceBuyerAddress": null,
							"invoiceGoodsCodeVersion": "1"
						},
						{
							"invoiceSellerAddress": null,
							"invoiceTime": 1516348800000,
							"invoiceTaxAmount": 74400,
							"invoiceAmount": 1240000,
							"invoicePostTaxAmount": 1165600,
							"invoiceItemUnitPrice": 1240000,
							"invoiceNumber": "43641694",
							"orderIds": null,
							"invoiceBuyerName": "孙芳",
							"invoiceSellerBank": null,
							"updateTime": 1531379503000,
							"invoiceDate": "2018-01-19",
							"invoiceCode": "3100171415",
							"invoiceSellerTaxPayerNumber": null,
							"invoiceTaxRate": 6,
							"invoiceBuyerBank": null,
							"invoiceSellerName": null,
							"deleted": false,
							"invoiceBuyerTaxPayerNumber": null,
							"createTime": 1531379503000,
							"invoiceTaxClassificationCode": "1.1",
							"invoiceItemName": "*信息技术服务*基因检测服务费",
							"invoiceItemCount": 1,
							"invoiceBuyerAddress": null,
							"invoiceGoodsCodeVersion": "1"
						}
					],
					"orderLogisticsUrl": "http://localhost:8000/query/genetic-test/order-logistics/GTON-20180103-6",
					"patientClinicalDiagnosis": "淋巴癌",
					"patientAge": 77,
					"orderPrice": 4000000,
					"localStaffName": "陈禹西"
				}
			]
		}

返回字段解释：
	
* sum 总计
* testingItemPrice 检测费用总计
* testingItemCost 检测成本总计
* orderPrice 银行转账总计

* list 数据数组
* orderPrice: 订单金额,
* doctorName 医生名称
* hospitalName 医院名称
* localStaffName 地服人员
* orderId 订单id
* orderNumber 订单编号 
* patientAge 病人年龄
* patientClinicalDiagnosis 临床诊断
* patientName 病人姓名
* orderLogisticsUrl 订单物流信息
* invoices 订单关联的发票信息
* testings 订单关联的检测套餐信息
* payments 缴款信息
* type 支付/还是退款 (pay/refund)
* total 支付或退款金额
* account 支付或退款账号
* userName 支付或退款人姓名
* localStaffPayroll 是否是地服转存(true/false) 
* localStaffPayrollTrans 是否是地服转存(是/否)
* paymentType  支付方式
* orderPayAttorneyUrl 授权委托书
* payTime 支付时间



#### 收入明细表导出Excel

- Path：/api/export/reports/income-detail
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/export/reports/income-detail
Header: 
	x-auth: {登录时返回的token}
Query:
	{
		"timeDimension": "month",
		"dateStr": "2018-05"
	}

```

请求字段解释：

- `timeDimension` 时间维度，月
- `dateStr` 时间，必填

返回成功示例   

​	excel文件流

返回失败示例：

```
Header: 
	Content-Type: application/json;charset=utf-8
Body:
	"failed"
```

### 物流信息

#### 订单id获取物流列表

* Path：/api/query/order-logistics/{id}
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/query/order-logistics/GTON-20180101-2
	Header: 
		x-auth: {登录时返回的token}

Path请求字段解释：

* `id` 订单id，必填

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
			"testingReport": [
				{
					"recordId": 2,
					"expressNumber": "1116076709214",
					"createTime": 1514800800000,
					"orderId": "GTON-20180101-1",
					"eventTime": 1514800800000,
					"messageId": "C0A80867559318B4AAC25F17FB810012",
					"updateTime": 1514800800000,
					"id": 2,
					"tag": "order",
					"event": "orderLogistics",
					"expressCompanyId": "ems",
					"logisticsType": "testingReport",
					"expressCompanyName":"邮政",
					"testingItemId": 1
				}
			],
			"sample": [
				{
					"recordId": 2,
					"expressNumber": "1116076709214",
					"createTime": 1514800800000,
					"orderId": "GTON-20180101-1",
					"eventTime": 1514800800000,
					"messageId": "C0A80867559318B4AAC25F17FB810012",
					"updateTime": 1514800800000,
					"id": 2,
					"tag": "order",
					"event": "orderLogistics",
					"expressCompanyId": "ems",
					"logisticsType": "sample",
					"expressCompanyName":"邮政",
					"testingItemId": 1
				}
			],
			"invoice": [
				{
					"recordId": 1,
					"expressNumber": "821148818417",
					"createTime": 1514800800000,
					"orderId": "GTON-20180101-1",
					"eventTime": 1514800800000,
					"messageId": "C0A80867559318B4AAC25F17FB810012",
					"updateTime": 1514800800000,
					"id": 1,
					"tag": "order",
					"event": "orderLogistics",
					"expressCompanyId": "sf",
					"logisticsType": "invoice",
					"expressCompanyName":"顺丰"
				},
				{
					"recordId": 3,
					"expressNumber": "7042503016",
					"createTime": 1,
					"orderId": "GTON-20180101-1",
					"eventTime": 1514800800000,
					"messageId": "C0A80867559318B4AAC25F17FB810012",
					"updateTime": 1,
					"id": 3,
					"tag": "order",
					"event": "orderLogistics",
					"expressCompanyId": "debangwuliu",
					"logisticsType": "invoice",
					"expressCompanyName":"德邦"
				}
			]
		}

返回字段解释：
	
* testingReport 寄送检测报告物流
* invoice 寄送发票物流
* sample 寄送样本物流
* id 物流记录id
* expressNumber 快递单号
* expressCompanyId 快递公司ID
* expressCompanyName 快递公司名称
* logisticsType 物流类型
* testingItemId 检测项目id
	 createTime 创建时间	
* updateTime 更新时间

#### 获取物流状态

* Path：/api/query/order-logistics
* Method：GET
* Success Code：200
* Failure Code：400、401、404、500

请求示例：

	GET http://domain.com/api/query/order-logistics
	Header: 
		x-auth: {登录时返回的token}
	Query:
		{
			"expressNumber": "1116076709214",
			"expressCompanyId": "ems"
		}

请求字段解释：

- `expressNumber` 快递单号，必填
- `expressCompanyId` 快递公司ID，必填

返回成功示例   

	Header: 
		Content-Type: application/json;charset=utf-8
	Body:
		{
			"result": true,
			"code": 0,
			"data": [
				{
					"state": "[北京市]投递并签收，签收人：他人收 本人签收",
					"time": "2018-03-30 12:25:21"
				},
				{
					"state": "[北京市]北京邮政速递中关村区域分公司学院路营投部安排投递，预计23:59:00前投递（投递员姓名：张先东;联系电话：18519363694）",
					"time": "2018-03-30 07:49:39"
				},
				{
					"state": "离开中国邮政速递物流股份有限公司北京市国货航航空邮件处 发往北京邮政速递中关村区域分公司学院路营投部",
					"time": "2018-03-30 05:01:19"
				},
				{
					"state": "到达 中国邮政速递物流股份有限公司北京市国货航航空邮件处 处理中心",
					"time": "2018-03-29 20:43:00"
				},
				{
					"state": "已离开北京邮政速递上地区域分公司育新花园营投部，发往中国邮政速递物流股份有限公司北京市国货航航空邮件处",
					"time": "2018-03-29 20:12:30"
				},
				{
					"state": "[北京市]北京邮政速递上地区域分公司育新花园营投部已收件（揽投员姓名：王永振,联系电话:）",
					"time": "2018-03-29 17:19:00"
				}
			],
			"trackingNo": "1116076709214",
			"express": "ems",
			"state": "3"
		}



### 绩效考核

#### 上传并导出

- Path：/api/kpi/upload-export
- Method：POST
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/kpi/upload-export
Header: 
	x-auth: {登录时返回的token}

```

Form字段解释：

- `kpiDate` 要生成的绩效的月份，如要生成`2018-06`月绩效，则传入该月中任意1个毫秒数即可，如`1527782400000`，必填，Long类型
- `file`上传的绩效模板，必须为`xlsx`格式

返回成功示例   

​	返回导出的excel文件

#### 查看导出历史

- Path：/api/kpi/history
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/kpi/history
Header: 
	x-auth: {登录时返回的token}

```

返回字段解释

+ exportFileName：导出绩效文件名
+ exportId：导出绩效id
+ exportTime：导出绩效事件
+ kpiDate：指定的要生成绩效的月份（毫秒）
+ uploadFileName：导出绩效基于的模板的文件名
+ uploadId：导出绩效基于的模板的id

返回成功示例   

```
[
	{
		"exportFileName":"2018-08月地府绩效明细.xlsx",
		"exportId":1,
		"exportTime":1534352606000,
		"kpiDate":1533052800000,
		"uploadFileName":"2018-05月地服绩效明细（含东北、西南）.xlsx",
		"uploadId":1
	},
	{
		"exportFileName":"2018-06月地府绩效明细.xlsx",
		"exportId":2,
		"exportTime":1534352671000,
		"kpiDate":1527782400000,
		"uploadFileName":"2018-05月地服绩效明细（含东北、西南）.xlsx",
		"uploadId":1
	},
	{
		"exportFileName":"2018-06月地府绩效明细.xlsx",
		"exportId":3,
		"exportTime":1534385367000,
		"kpiDate":1527782400000,
		"uploadFileName":"2018-05月地服绩效明细（含东北、西南）.xlsx",
		"uploadId":1
	}
]
```

#### 导出历史生成的绩效

- Path：/api/kpi/export-history
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/kpi/export-history
Header: 
	x-auth: {登录时返回的token}

```

请求字段解释

- type：`uolpad`：导出上传的模板；`export`：下载上次导出的绩效
- id：`uploadId`或`exportId`

返回成功示例   

​	导出的excel文件



#### 重新生成绩效

- Path：/api/kpi/export-again
- Method：GET
- Success Code：200
- Failure Code：400、401、404、500

请求示例：

```
GET http://domain.com/api/kpi/export-again
Header: 
	x-auth: {登录时返回的token}

```

请求字段解释

- id：要重新生成的绩效的`id`

返回成功示例   

​	导出的excel文件