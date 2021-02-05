- 新建用户

  ```shell
  adduser nic
  #接下来输入密码啥的，注意是adduser，不是useradd，这两个有区别，参考(https://p3terx.com/archives/add-normal-users-with-adduser-and-useradd.html)
  ```

- 给用户授权

  ```shell
  visudo
  # 如下图所示
  ```

  ![image-20210205092245800](C:\Users\nic\AppData\Roaming\Typora\typora-user-images\image-20210205092245800.png)

  

  添加完之后按Ctrl+x ，然后按回车，然后再按Ctrl+x

- 禁止root登录

  - 修改sshd配置文件

  ```shell
  vi /etc/ssh/sshd_config
  
  #PermitRootLogin yes  改成no
  ```

  - 重启sshd服务

  ```
  sudo /etc/init.d/ssh restart
  ```

  