# Java Homework at univ.

Make word-typing game using UI(AWT,Swing), Thread, File IO.

* * *

## Used Api
* Java api version : 11

### File IO
> * *[Files](https://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html)* in [FileManager](https://github.com/Kasania/OOPHomework/blob/master/src/com/kasania/core/FileManager.java)
> * *[BufferedWriter](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/BufferedWriter.html)* in [FileManager](https://github.com/Kasania/OOPHomework/blob/master/src/com/kasania/core/FileManager.java)

### Concurrent Programming
> * *[Executors](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/Executors.html)*
> in [FileManager](https://github.com/Kasania/OOPHomework/blob/master/src/com/kasania/core/FileManager.java) and [WordTypeGame](https://github.com/Kasania/OOPHomework/blob/master/src/com/kasania/core/game/logic/WordTypeGame.java)
> * *[ExecutorService](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/ExecutorService.html)*
> in [FileManager](https://github.com/Kasania/OOPHomework/blob/master/src/com/kasania/core/FileManager.java)
> * *[FutureTask](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/FutureTask.html)*
> in [FileManager](https://github.com/Kasania/OOPHomework/blob/master/src/com/kasania/core/FileManager.java)
> * *[ScheduledExecutorService](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/ScheduledExecutorService.html)* 
> in [WordTypeGame](https://github.com/Kasania/OOPHomework/blob/master/src/com/kasania/core/game/logic/WordTypeGame.java)
> * *[ScheduledFuture](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/ScheduledFuture.html)*
> in [WordTypeGame](https://github.com/Kasania/OOPHomework/blob/master/src/com/kasania/core/game/logic/WordTypeGame.java)

### Graphics Components
> * *[Swing Library](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/package-summary.html)*
> in [Files in Graphics package](https://github.com/Kasania/OOPHomework/tree/master/src/com/kasania/graphics)
> * *[AWT Canvas](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/package-summary.html)*
> in [GameCanvas](https://github.com/Kasania/OOPHomework/blob/master/src/com/kasania/graphics/scenes/ingame/GameCanvas.java)
> * *[BufferStrategy](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/image/BufferStrategy.html)*
> in [GameCanvas](https://github.com/Kasania/OOPHomework/blob/master/src/com/kasania/graphics/scenes/ingame/GameCanvas.java)
> * *[Font](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/Font.html)*
> in [Files in Graphics package](https://github.com/Kasania/OOPHomework/tree/master/src/com/kasania/graphics)

### [Functional Interfaces](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/function/package-summary.html)
> Used throughout the project. Especially used in UI events.
> * Lambda Expressions 
> * Runnable 
> * Consumer
> * BiConsumer

## In progress
> * Make Settings and UI
> * Separate Logic from "Word Type Game class"


## TODO
> * MultiPlayer Game Logic and UI