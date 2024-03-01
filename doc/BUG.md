# BUG登记
## 1.springboot启用6666端口,chrome显示无法访问此网站，报错ERR_UNSAFE_PORT
原因
```
Google Chrome 存在默认非安全端口列表，Firefox也有类似的端口限制。
6666-6669端口是IRC协议使用的缺省端口，存在安全风险，容易被木马等程序利用，应该是出于安全考虑，谷歌，火狐浏览器给屏蔽了吧
```

解决方案

```
【一】
更换其他端口如8899等

【二】
如果一定要使用上述端口的解决办法
选中Google Chrome 快捷方式，右键属性，在”目标”对应文本框添加：
–explicitly-allowed-ports=87,6666,556,6667
```

Google Chrome 默认非安全端口列表，搭建网站要建议尽量避免以下端口：

 | 端口 | 协议 |
 | ---- | ------ |
 | | 1 | tcpmux |
 | 7 | echo |
 | 9 | discard |
 | 11 | systat |
 | 13 | daytime |
 | 15 | netstat |
 | 17 | qotd |
 | 19 | chargen |
 | 20 | ftp data |
 | 21 | ftp access |
 | 22 | ssh |
 | 23 | telnet |
 | 25 | smtp |
 | 37 | time |
 | 42 | name |
 | 43 | nicname |
 | 53 | domain |
 | 77 | priv-rjs |
 | 79 | finger |
 | 87 | ttylink |
 | 95 | supdup |
 | 101 | hostriame |
 | 102 | iso-tsap |
 | 103 | gppitnp |
 | 104 | acr-nema |
 | 109 | pop2 |
 | 110 | pop3 |
 | 111 | sunrpc |
 | 113 | auth |
 | 115 | sftp |
 | 117 | uucp-path |
 | 119 | nntp |
 | 123 | NTP |
 | 135 | loc-srv /epmap |
 | 139 | netbios |
 | 143 | imap2 |
 | 179 | BGP |
 | 389 | ldap |
 | 465 | smtp+ssl |
 | 512 | print / exec |
 | 513 | login |
 | 514 | shell |
 | 515 | printer |
 | 526 | tempo |
 | 530 | courier |
 | 531 | chat |
 | 532 | netnews |
 | 540 | uucp |
 | 556 | remotefs |
 | 563 | nntp+ssl |
 | 587 | stmp? |
 | 601 | ?? |
 | 636 | ldap+ssl |
 | 993 | ldap+ssl |
 | 995 | pop3+ssl |
 | 2049 | nfs |
 | 3659 | apple-sasl / PasswordServer |
 | 4045 | lockd |
 | 6000 | X11 |
 | 6665 | Alternate IRC [Apple addition] |
 | 6666 | Alternate IRC [Apple addition] |
 | 6667 | Standard IRC [Apple addition] |
 | 6668 | Alternate IRC [Apple addition] |
 | 6669 | Alternate IRC [Apple addition] |

参考：

https://blog.csdn.net/hbiao68/article/details/105435257/



2.

