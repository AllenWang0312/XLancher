#!/bin/sh
jks_path = "C:\Users\Gzw\Documents\Tencent Files\1007991483\FileRecv\wpc_posonal.jks"
jks_nick_name = "android_4.0.3_sys_key"

#转换系统签名命令
# jks_path : 签名文件
# 123456 : 签名文件密码
# platform.pk8、platform.x509.pem : 系统签名文件
# demo : 签名文件别名

./keytool-importkeypair -k $jks_path -p qunsi003 -pk8 platform.pk8 -cert platform.x509.pem -alias $jks_nick_name