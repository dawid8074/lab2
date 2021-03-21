Sterowanie:
Start -spacja
Obrot -spacja
W lewo -strzałka w lewo
W prawo -strzałka w prawo
Opuść klocek -strzałka w dół
--------------------------------------------------------------------------------------------------
Pomocne słownictwo:
-pierwsza płaszczyzna- miejsce, gdzie lodują wszystkie zamrożone już klocki
-druga płaszczyzna- miejsce, gdzie znajduje się klocek, którym aktualnie operujemy
-trzecia płaszczyzna- miejsce, gdzie znajduje się klocek po obrocie i przed sprawdzeniem kolizji
--------------------------------------------------------------------------------------------------
Kod został stworzony na bazie kodu flappy bird znalezionego na youtube
Stąd dwa pliki w tym renderer oraz inna składnia kolejnych funkcji względę lab1(obrazek)
Poczynając do początku kodu mamy klasę tetris wraz z konstruktorem, w który tworzone są klocki
Następnie funkcję obrot, w_lewo, w_prawo oraz w_dol
Funkcja obrot jest przypisana pod klawisz spacji. Zależnie od obecnego stanu rozpoczyna rozgrywkę bądź obraca figurę
Obrot bardzo długa funkcja, gdyż musi:
-znaleźć środek klocka
-znaleźć wiersz oraz kolumne, w której klocek jest najdłuższy/szerszy
-spróbować obrócić klocek na trzeciej płaszczyźnie- sprawdzić, czy przez obrót nie przekroczymy zakresu tablicy
-spróbować nałożyć obraz z trzeciej płaszczyzny na drugą- Jeżeli okaże się ze po obrocie klocek koliduje
z pierwszą płaszczyzną, to obrót jest anulowany
Nastepnymi funkcjami są w_lewo, w_prawo, w_dol, które sprawdzają, czy po wykonaniu ruchu nie nastąpi kolizja. Jeżeli nie to wykonują ruch

Następną funkcją jest ,,ActionPerformed" który wykonuje się w określone jednostce czasu. Mam tutaj zestaw instrukcji, które mówią o tym,
co ma się dziać bez ingerencji użytkownika. Mamy tutaj przede wszystkim czasowe opuszczanie klocków w dół. Jeżeli dojdziemy do końca planszy
lub dojdzie do kolizji z innym klockiem, to klocek przejdzie z płaszczyzny drugiej na pierwszą, a na drugą zostanie wygenerowany kolejny.
Odbywa się w tej funkcji również kasowanie pełnych linii oraz naliczanie wyniku.

Następnie znajduje się funkcja rysująca, która odpowiada za graficzne ukazanie gry.

Na samym końcu przypisanie pod przyciski funkcji, które wykonują.
