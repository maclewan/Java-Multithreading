## Table of contents
* [Technologies](#technologies)
* [General info](#general-info)
* [Application description](#application-description)
	

## Technologies
<img src="https://empirica.pl/wp-content/uploads/2016/01/java8.png" alt="drawing" height=50px/>
<img src="https://ucarecdn.com/9d0c1b04-20b7-4deb-b211-dda3513c4918/" alt="drawing" height=50px/>
<img src="https://www.techcentral.ie/wp-content/uploads/2019/07/Java_jdk_logo_web-372x210.jpg" alt="drawing" height=50px/>
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/IntelliJ_IDEA_Logo.svg/1024px-IntelliJ_IDEA_Logo.svg.png" alt="drawing" height=50px/> 



## General info
Projekt studencki na zajęcia z Kursu Programowania.
Tworzony przez [Maciej Lewandowicz](https://github.com/sasuke5055).


## Application description
Celem zadania było opanowanie pracy wielowątkowej w Javie. Zadanie polegało na napisaniu symulacji 'Wilk i zające', 
w której zwierzęta poruszają się po planszy o jedno pole w każdym ruchu. Zające mają za zadanie uciekać przed wilkiem. 
Każde ze zwierząt porusza się z zadanym opóźnieniem z zakresu <-k,k>. 

Każdy zając reperezentuje tu jeden wątek. Kiedy zostaje złapany przez wilka, wątek jest usypiany i niszczony. 
Symulacja kończy się, gdy wilk unicestwi wszystkie zające.

Użytkownik może podać w parametrach wielkośc planszy (x,y), ilośc zajęcy i opóźnienie k.

![](images/g1.png)

![](images/g2.png)

Po zakończeniu symulacji prezentowane jest podsumowanie z czasem trwania całej symulacji. 

![](images/g3.png)
