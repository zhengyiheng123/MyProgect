# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#指定代码的压缩级别
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度
-dontpreverify
# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses
# 避免混淆泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-verbose
-ignorewarnings

 -keep class com.talkfun.**{
        *;
    }

    -keep class fi.iki.elonen.**{
      *;
    }

    -keep class tv.danmaku.ijk.media.**{
      *;
    }

    #io.socket
    -keep class io.socket.**{*;}
    -keep interface io.socket.** { *; }
    -keep class org.apache.commons.net.**{*;}


    #retrofit2
    -dontwarn retrofit2.**
    -keep class retrofit2.** { *; }
    -keep interface retrofit2.** { *; }
    -keepattributes Signature
    -keepattributes Exceptions

    #okhttp3
    -keepattributes Signature
    -keepattributes Annotation
    -keep class okhttp3.** { *; }
    -keep interface okhttp3.** { *; }
    -dontwarn okhttp3.**
    -dontwarn okio.**

    #RxJava RxAndroid
    -dontwarn sun.misc.**
    -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
       long producerIndex;
       long consumerIndex;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
        rx.internal.util.atomic.LinkedQueueNode producerNode;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
        rx.internal.util.atomic.LinkedQueueNode consumerNode;
    }