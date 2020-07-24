# Mobile Lesson 13
This repo contains all materials covered in Mobile Lesson 13:  

**Lesson Overview:**  
In this lesson, we are going to discussspeech to text and text to speech applications.  

**Use Case Description:**  
Text to speech and speech Recognizer  

**Programming elements:**  
Speech to Text and Text to Speech  

**Source Code:**  
https://umkc.box.com/s/qtuzr2vdw5bic4t0ess48trt7yvu4bm4  

Create a Medical Assistant Application in the following steps:  
1. Use the layout given in the second use case speech to text.
2. As soon as the app opens,it should say hello
3. Then the user clicks on the mic button to say hello
4. Once the user said hello, the app should speak, "what is your name?"
5. Then the user clicks on the mic button to say, "My name is" + &lt;your name&gt;
6. Extract the name of the user and then save it as editor level.

```java
private SharedPreferences preferences;
private SharedPreferences.Editor editor;
preferences = getSharedPreferences(PREFS,0);

editor = preferences.edit();
editor.putString(NAME,<extracted name>).apply();
NAME = "name";
```

7. Also, show the name on the screen 
8. When the user asks the following questions, appropriate answers should be given:

**Implement any three of these:**  
| Question                                 | Answer                                                |
| ---------------------------------------- | ----------------------------------------------------- |
| I am not feeling good. What should I do? | I can understand. Please tell your symptoms in short. |
| Thank you, my Medical Assistant          | Thank you too + &lt;user name&gt; + Take care               | 
| What time is it?                         | The time is + &lt;current time&gt;                          | 
| What medicines should I take?            | I think you have a fever. Please take this medicine.  |

**Hint: (Java code to get time)**  
```java
SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");//dd/MM/yyyy
Date now = new Date();
String[] strDate = sdfDate.format(now).split(":");
if(strDate[1].contains("00"))
    strDate[1] = "o'clock";
system.out.println("The time is " + sdfDate.format(now));
```