### 后端controller说明

前后端以json进行通信。

本文件说明适用于购票用例向后端发出请求的全过程，示例流程如下：
```
1. 首页获取所有电影信息
     http://localhost:8080/getAllMovies
     可参考文件: movies.json
2. 点击某一个电影(比如id = 1)，获取某个电影的信息
     http://localhost:8080/getMovie/1
     可参考文件: movie.json
3. 点击购买，进入所有包含电影的影院信息(目前信息不足以筛选)
     http://localhost:8080/getCinemas/1
     可参考文件: cinemas.json
4. 点击某一个影院(比如影院id=1)，获取该影院所有该电影的场次
     http://localhost:8080/getSessions/1/1
     可参考文件: sessions.json
5. 选择可选的座位后，向后端发送请求
     http://localhost:8080/buyTickets
     json: {
           "userId": 1,
           "sessionId": 1,
           "seats": [
              2,
              4,
              2,
              5
           ]
           }
    后端返回订单号15
6. 前端根据订单号，显示当前订单
     http://localhost:8080/getOrder/15
   可参考文件: order.json
用例结束
```

* 一点说明
  关于座位号：
  &emsp;前端数据假设为1-base，后端数据则为0-base存储
  &emsp;前端传json按1-base传
  &emsp;但后端传来的数据是0-base的，注意转换
  安全性有待补充，我尽力
&nbsp;

* 获取所有电影列表
```
url: http://localhost:8080/getAllMovies
所需参数：空
所需json：空
返回电影列表 movies.json
```

* 获取某一个电影
```
url: http://localhost:8080/getMovie/{id}
所需参数：id -> int //电影id
所需json：空
返回某一个电影 movie.json
```

* 获取放映某电影的所有影院
```
url: http://localhost:8080/getCinemas/{movieId}
所需参数：movieId -> int //电影的id
所需json：空
返回影院列表 cinemas.json
```

* 获取某个影院放映某电影的所有场次
```
url: http://localhost:8080/getSessions/{movieId}/{cinemaId}
所需参数：movieId -> int //电影的id
         cinemaId -> int //电影院的id
所需json：空
返回场次列表 sessions.所需json
返回json说明：部分字段可能为空，不过在此页面用不到
             二维数组seats表示座位状态，其中0表示可选，-1表示已被其他人选择。
```

* 买票
```
url: http://localhost:8080/buyTickets
所需参数：空
所需json: {
              "userId": int, //用户的id
              "sessionId": int, //场次的id
              "seats": int[] //座位的位置，其中奇数表示行，偶数表示列
          }
返回orderId: 若为-1，则说明购票失败；否则返回订单号
```

* 根据订单号获取订单
```
url: http://localhost:8080/getOrder/{id}
所需参数：id -> int //订单id
所需json: 空
返回某个订单 order.json
```

* 根据用户查找订单
```
url: http://localhost:8080/getOrders/{uid}
所需参数： uid -> int //用户的id
所需json: 空
返回订单列表 orders.json
```
