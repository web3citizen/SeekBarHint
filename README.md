# SeekBarHint

## Description
![image](https://github.com/thinkSky1206/SeekBarHint/blob/master/images/Screenshot_01.png)
## Getting Started
**1** In your `build.gradle`:

```gradle
repositories { 
            jcenter()
            maven { url "https://jitpack.io" }
}


dependencies {
            compile 'com.github.thinkSky1206:SeekBarHint:0.1.0'
}
```

**2** In layout XML

```xml
<com.liuwp.seekbarhint.SeekBarHint
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```
##TODO
1. 添加自定义属性
2. 添加不同形状popup text
3. 修复在ScrollView的bug 
4. 其他

##Thanks
[progresshint](https://github.com/techery/progresshint)
