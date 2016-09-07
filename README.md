# Material-Raised-Button
The library that implements material raised button widget according with Material guidelines.  
View based on [RobotoTextView library](https://github.com/johnkil/Android-RobotoTextView) and CardView.  
Elevation works on 15+ API because it based on CardView's elevation parameter:)

**Jelly Bean**  
![](https://github.com/VyacheslavShmakin/Android-Material-RaisedButton/blob/master/sample/demo_jelly_bean.gif)

**Nougat**  
![](https://github.com/VyacheslavShmakin/Android-Material-RaisedButton/blob/master/sample/demo_nougat.gif)

Download
--------

Gradle:

```groovy
compile 'com.github.VyacheslavShmakin:material-raised-button:1.0.1'
```

Maven:

```xml
<dependency>
    <groupId>com.github.VyacheslavShmakin</groupId>
    <artifactId>material-raised-button</artifactId>
    <version>1.0.1</version>
    <type>aar</type>
</dependency>
```


Usage
-----
#### In Code
It's just simple view so you can use all standard methods.
You just have an additional methods.
``` java
RaisedButton rbtn = ...;
CardView currentCardView = rbtn.getRootView();
RobotoButton currentRobotoButton = rbtn.getButtonView();
```

#### In XML
Standard xml parameters that can be processed by RaisedButton:
``` xml
android.R.attr.layout_width
android.R.attr.layout_height
android.R.attr.background
android.R.attr.ellipsize
android.R.attr.minLines
android.R.attr.maxLines
android.R.attr.minWidth
android.R.attr.maxWidth
android.R.attr.minHeight
android.R.attr.maxHeight
android.R.attr.textColor
android.R.attr.textSize
android.R.attr.text
android.R.attr.enabled
```
RaisedButton's xml parameters:
``` xml
R.attr.rb_cornerRadius
R.attr.rb_elevation
R.attr.rb_maxElevation
R.attr.rb_innerPaddings
R.attr.rb_innerPaddingLeft
R.attr.rb_innerPaddingRight
R.attr.rb_innerPaddingTop
R.attr.rb_innerPaddingBottom
```

**NOTE**  
**1.** InnerPaddings, InnerPaddingLeft, InnerPaddingTop, InnerPaddingRight, InnerPaddingBottom - defines padding inside button (between text and its borders on each side);  
InnerPaddings parameter automatically excludes separate paddings (Left, Top, Right, Bottom);  
**2.** CornerRadius parameter will work only on **21+ API**.  
To provide corner radius on **pre-Lollipop APIs** you should use specific drawable with rounded corners;
So to provide corner radius on all APIs you should use **rb_cornerRadius** parameter in xml  (**for 21+ API**) and create specific drawable with rounded corners (**for Pre-Lollipop APIs**)


