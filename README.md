# Chess Engine
by Mikhail Bogdanov  
Readme created May 15th 2024    

---  

# background:  
After finishing first year UBC, and taking CPSC 110, which is the introductory course to computer science where they taught us a bunch of recursive search algorythms in Dr. Racket,
I was eager to try to apply my knew knowledge to an actual project. Than one day while scrolling youtube I came across the Min Max algoryghtm, which I immediately drew connections 
with to how it could be implemented as a chess bot. I initially made a min max tic tac toe bot, to get a grasp of recursion and search algorythms, as well as to get a better understanding of the C++ programing language, which I was new to. I decided to use C++ since
I've heard how it's a fast, low level programming language which is what I assumed is needed for a project like a chess bot since it would need to do a lot of computation fast. I managed to get an in terminal version running, however I wasn't able to get multithreading 
to work the way I wanted to, before summer classes ramped up, and I didn't have much time left to work on the project. However, in the 2nd summer term I took CPSC 210 which is a software design course, where we had to write a piece of software, with a graphical UI, unit tests, and 
logging. The main issue was the project had to be done in JAVA, and my bot was in C++. I translated my bot to JAVA, and was successfully able to multithread it, to get 100% CPU usage.
# features/stats:
- on average 19 second think time
- 1100 rated according to chess.com
- Multithreaded to always use 100% CPU
- ability to save games
- ability to load games even if the app was closed
- unit tests to get 100% line and branch covering
- logging
# if I were to do it again:
- if I were to start this project from scratch I would finish it in C++. there was a significant slowdown when I transfered it from C++ to JAVA, and the multithreaded JAVA performance was barely able to beat standard, singlecore C++.
- since this bot was designed in C++ where strings are pretty much arrays, the state of the game is saved as a string. However in JAVA a string is a class, and string operations create a new string class when concatinating. This is part of the reason JAVA code is so much slower than C++ in my use case, and if I made it in JAVA from scratch I would save the state of the game as an array of bytes.

below is a demonstration of the game my bot played against a 700 rated chess.com bot, with my bot playing as black:

![Sequence 01_1](https://github.com/mishaBogdanov/chessBot/assets/78181954/c118d59a-7bcf-4c64-9c74-a84fb24a7b34)  

![Screenshot 2024-05-15 175317](https://github.com/mishaBogdanov/chessBot/assets/78181954/54f637df-d267-43d7-87e0-633175f1798c)  

![image](https://github.com/mishaBogdanov/chessBot/assets/78181954/835f8628-f46a-460f-b88d-6821912f0282)  


---
# June 2024 Update:
---

Turned this program into a multithreaded back end server using spring boot. New features include:
- playing multiple games at the same time
- hosting API fetch previous, as well as current games
- hosting API to request an evaluation of a currently played game
- created a front end to use the API s using React.
Below is a demonstration of the new features:


![Recording 2024-06-16 001718_5](https://github.com/mishaBogdanov/chessBot/assets/78181954/ae0993c2-7adb-41f1-ada9-c5960ec55c88)

## explanation:

There are 2 games being played on the "server" concurrently. The React aplication uses API to fetch the current games being played, and get access to the entire history of each game. You are then able to evaluate each position with my engine, which gives the next best move for the correct player, as well as the evaluation of the currently viewed position. As we can see, it gives a very high evaluation to the first game, since White has mate in a couple moves, and is in general in a way better position having captured most of blacks pieces. Evaluating the 2nd game, gives us a -2.1 rating which is about even, however my engine considers the position to be winning for black.

