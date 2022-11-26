

### Redis集群搭建（docker-compose方式）



#### 搭建环境及目标：

- 服务器系统：CentOS7.6
- 内核版本：3.10.0-957.21.3.el7.x86_64
- Redis版本：6.2.4
- Docker版本：18.03.1-ce
- Docker-compose版本：1.25.1
- 搭建目标：3主3从集群

#### 步骤如下：

1. 准备环境
   - 更新yum包：`yum -y update`
   - 安装docker
     - 安装所需软件包：`yum install -y yum-utils device-mapper-persistent-data lvm2`
     - 查看可用docker版本：`yum list docker-ce --showduplicates | sort -r`
     - 选择目标版本并安装：`yum -y install docker-ce-18.03.1.ce`
     - 启动docker：`systemctl start docker`
     - 查看docker版本：`docker version`
   - 安装docker-compose：
     - 请求目标网址进行下载：`curl -L https://github.com/docker/compose/releases/download/1.25.1/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose`
     - 添加可执行权限：`chmod +x /usr/local/bin/docker-compose`
     - 查看docker-compose版本：`docker-compose version`

2. 创建主持久化目录

```linux
mkdir -p /opt/docker/redis
cd /opt/docker/redis
```

3. 编写基础redis.conf文件

```linux
vim redis.conf
```

```conf
port 7001
cluster-enabled yes
cluster-config-file nodes-7001.conf
cluster-node-timeout 5000
appendonly yes
protected-mode no
requirepass gientech
masterauth gientech
cluster-announce-ip 47.116.12.196
cluster-announce-port 7001
cluster-announce-bus-port 17001
```

4. 创建子节点相关目录，并将基础文件重定向拷贝至指定节点目录内

```linux
for i in $(seq 7001 7006); do mkdir -p /opt/docker/redis/node_${i}/{conf,data};done

for i in $(seq 7001 7006); do sed "s/7001/${i}/g" /opt/docker/redis/redis.conf > /home/web/redis/node_${i}/conf/redis.conf;done
```

5. 配置docker-compose.yml

```yml
version: "3"

# 定义服务，多个redisserver
services:
  # 服务名称
  redis-7001:
    # 创建容器时所需的镜像
    image: redis:6.2.4 
    # 容器名称
    container_name: redis-7001
    # 容器总是重新启动
    restart: always
    # 数据卷，目录挂载
    volumes:
      - /opt/docker/redis/node_7001/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /opt/docker/redis/node_7001/data:/data
    ports:
      - 7001:7001
      - 17001:17001
    command:
      redis-server /usr/local/etc/redis/redis.conf

  redis-7002:
    image: redis:6.2.4
    container_name: redis-7002
    restart: always
    volumes:
      - /opt/docker/redis/node_7002/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /opt/docker/redis/node_7002/data:/data
    ports:
      - 7002:7002
      - 17002:17002
    command:
      redis-server /usr/local/etc/redis/redis.conf

  redis-7003:
    image: redis:6.2.4
    container_name: redis-7003
    restart: always
    volumes:
      - /opt/docker/redis/node_7003/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /opt/docker/redis/node_7003/data:/data
    ports:
      - 7003:7003
      - 17003:17003
    command:
      redis-server /usr/local/etc/redis/redis.conf

  redis-7004:
    image: redis:6.2.4 
    container_name: redis-7004
    restart: always
    volumes:
      - /opt/docker/redis/node_7004/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /opt/docker/redis/node_7004/data:/data
    ports:
      - 7004:7004
      - 17004:17004
    command:
      redis-server /usr/local/etc/redis/redis.conf

  redis-7005:
    image: redis:6.2.4
    container_name: redis-7005
    restart: always
    volumes:
      - /opt/docker/redis/node_7005/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /opt/docker/redis/node_7005/data:/data
    ports:
      - 7005:7005
      - 17005:17005
    command:
      redis-server /usr/local/etc/redis/redis.conf

  redis-7006:
    image: redis:6.2.4
    container_name: redis-7006
    restart: always
    volumes:
      - /opt/docker/redis/node_7006/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /opt/docker/redis/node_7006/data:/data
    ports:
      - 7006:7006
      - 17006:17006
    command:
      redis-server /usr/local/etc/redis/redis.conf
```

6. 启动docker-compose，自动拉取镜像并配置

``` linux
docker-compose up -d
```

7. 开启防火墙相关端口

```linux
# 查看防火墙状态
systemctl status firewalld
# 启动防火墙
systemctl start firewalld
# 添加放行端口
firewall-cmd --zone=public --add-port=17001/tcp --permanent
firewall-cmd --zone=public --add-port=17002/tcp --permanent
firewall-cmd --zone=public --add-port=17003/tcp --permanent
firewall-cmd --zone=public --add-port=17004/tcp --permanent
firewall-cmd --zone=public --add-port=17005/tcp --permanent
firewall-cmd --zone=public --add-port=17006/tcp --permanent

firewall-cmd --zone=public --add-port=7001/tcp --permanent
firewall-cmd --zone=public --add-port=7002/tcp --permanent
firewall-cmd --zone=public --add-port=7003/tcp --permanent
firewall-cmd --zone=public --add-port=7004/tcp --permanent
firewall-cmd --zone=public --add-port=7005/tcp --permanent
firewall-cmd --zone=public --add-port=7006/tcp --permanent
# 刷新防火墙策略
firewall-cmd --reload
# 查看放行端口列表
firewall-cmd --list-port
# 最后放开云端服务器的防火墙相应端口区间（7001/7006,17001/17006）
```



7. 集群构建

````linux
# 指定命令进入容器
docker exec -it redis-7001 bash
# 执行集群构建命令
echo -e 'yes' | redis-cli -a gientech --cluster create 47.116.12.196:7001 47.116.12.196:7002 47.116.12.196:7003 47.116.12.196:7004 47.116.12.196:7005 47.116.12.196:7006 --cluster-replicas 1
# 查看集群状态
redis-cli -a gientech -h 47.116.12.196 -p 7001 cluster info
# 查看集群节点
redis-cli -a gientech -h 47.116.12.196 -p 7001 cluster nodes
````

