springboot demo


keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650


//生成服务器端私钥
openssl genrsa -out server.key 2048   
//生成服务端证书请求文件 注意生成过程中需要你输入一些服务端信息
openssl req -new -key server.key -out server.csr   
//使用CA证书生成服务端证书  关于sha256，默认使用的是sha1，在新版本的chrome中会被认为是不安全的，因为使用了过时的加密算法。
openssl x509 -req -sha256 -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -days 3650 -out server.crt 
//打包服务端的资料为pkcs12格式(非必要，只是换一种格式存储上一步生成的证书) 生成过程中，需要创建访问密码，请记录下来。
openssl pkcs12 -export -in server.crt -inkey server.key -out server.pkcs12  




//生成服务端的keystore（.jks文件, 非必要，Java程序通常使用该格式的证书）生成过程中，需要创建访问密码，请记录下来。 把ca证书放到keystore中（非必要） 
keytool -importkeystore -srckeystore server.pkcs12 -destkeystore server.jks -srcstoretype pkcs12 
keytool -importcert -keystore server.jks -file ca.crt



javax.net.ssl.SSLHandshakeException: Received fatal alert: handshake_failure 解决方案
这个是jdk导致的，jdk里面有一个jce的包，安全性机制导致的访问https会报错，官网上有替代的jar包，换掉就好了
目录 %JAVA_HOME%\jre\lib\security里的local_policy.jar,US_export_policy.jar
JDK7 http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
JDK8 http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html


keytool -import -alias example -keystore  D:\Program Files\Java\jdk1.8.0_121\jre\lib\security\cacerts -file example.cer
密码：changeit