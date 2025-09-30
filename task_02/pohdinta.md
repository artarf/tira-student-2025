# Ajo ympäristö

Koneen tiedot:

* **CPU**: Apple M2
* **RAM**: 8.0G
* **Käyttöjärjestelmä**: macOS 14.5
* **JDK**: openjdk version "24.0.2" 2025-07-15

Ajokomento: `mvn -q -DskipTests exec:java -Dexec.mainClass=oy.interact.tira.task_02.SortAndSearchAp`
Kaaviot (gnuplot): result.png ja result2.png

Huomiot:

* lineaarinen haku voi voittaa kun n on pieni
* ei haluta esilajittelua
* tarvitaan vain yksittäinen haku

## Pohdinta

* Linear search kasvaa suunnilleen lineaarisesti n:n mukana (O(n)).
* Insertion sort kasvaa n²-tyyliin (O(n²)) – jyrkkä nousu isolla n:llä.
* Binary search kasvaa logaritmisesti (O(log n)) – käytännössä lähes vaakasuora viiva.
