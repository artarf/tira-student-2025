# Tehtävän lisäohje

Tämän ohjeen alkupää on kertausta edeltäviltä kursseilta. Näiden asioiden kanssa on usein ongelmia tällä kurssilla, joten lue tämä jos koet että on syytä kerrata miten muuttujia vertaillaan Javassa, riippuen siitä onko kyse perustietotyypistä vai luokan oliosta.

---

Javassa muuttujien vertailua voidaan tehdä *neljällä eri tavalla*:

1. Käyttäen vertailuoperaattoreita (`<, >, <=, >=, ==, !=`) -- mutta vain silloin kun vertailtavien muuttujien tietotyypit ovat ns. perustietotyyppejä tai [primitiivisiä tietotyyppejä](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html) (`byte, short, int, double, float, long, char, boolean`).
2. Käyttäen luokan `equals` -metodia -- vertailtaessa kahden oliomuuttujan yhtäsuuruutta. Tässä oletuksena on että luokka ylikuormittaa `Object` -luokasta perityn `equals` -metodin.
3. Käyttäen luokan `compareTo` -metodia -- vertailtaessa muuttujia jotka ovat luokan instansseja (olioita), niiden ns. luonnollista järjestystä (kumpi tulee ennen toista vai ovatko ne kenties samassa järjestyksessä). Tämän käyttäminen edellyttää että luokka toteuttaa `Comparable` -rajapinnan.
4. Käyttäen `Comparator` -vertailijaa, joka tekee samaa kuin kolmas vaihtoehto, mutta omassa erillisessä vertailijaoliossaan.

Tapa 1 toimii siis vain kun vertailtavat muuttujat ovat listattuja primitiivisiä tietotyyppejä. Tavat 2-4 toimivat siis vain kun vertaillaan olioita, eli luokan instansseja. 

Se yleinen virhe mitä tehdään, on käyttää tavan 1 vertailua kun vertailtavat muuttujat ovatkin olioita -- näin ei pidä tehdä, vaan pitää käyttää tapoja 2-4.

--- 

Kurssin ohjelmointitehtävissä toteutetaan ja käytetään mm. lajittelu- ja hakualgoritmeja. Lajittelussa lajiteltavia asioita aina **vertaillaan** jotta ne voidaan laittaa oikeaan järjestykseen. Vertailua toki tehdään myös muuallakin kuin lajitteluissa; se on erittäin yleinen operaatio, myös tämän kurssin muissa tehtävissä. Hauissa taas vertaillaan olioiden yhtäsuuruutta; onko tämä taulukon indeksissä x oleva olio sama kuin haettava olio.

Olet ehkä oppinut vertailemaan muuttujien arvoja operaattoreilla `<, <=, >, >=` sekä `==` ja `!=`. Esimerkiksi C-kielessä:

```C
	int a = 10;
	int b = 20;

	if (a < b) {
		printf("Muuttuja a on pienempi kuin b");
	}
	if (a == b) {
		printf("Muuttujat a ja b ovat yhtä suuret");
	}
	if (a != b) {
		printf("Muuttujat a ja b ovat eri suuret");
	}
```

Tuo sama koodi toimii myös Javassa, mutta vain kun käytetään perustietotyyppejä eli pienellä kirjaimella alkavia tietotyyppejä.

Javan **luokkien ja olioiden** kanssa näitä **vertailuoperaattoreita ei voi eikä saa käyttää**. Ne toimivat *vain perustietotyyppien* kuten kokonaisluvut (`int`), reaaliluvut (`double`, `float`) sekä merkkitieto (`char`), kanssa. Aina kun Javan tietotyypin nimi alkaa Isolla Alkukirjaimella, kyseessä on *luokka* ja muuttuja on tällöin olio eli luokan instanssi, **eikä vertailuoperaattoreita voi silloin käyttää yhtä- ja erisuuruuden arviointiin**.

> Huomaa että kun toteutetaan ja käytetään geneerisiä algoritmeja ja tietorakenteita (kuten tällä kurssilla), niiden kanssa ei voi käyttää Javan perustietotyyppejä, vaan *aina* luokkia/olioita.

> Poikkeuksena vain ja ainoastaan se tilanne, jossa halutaan katsoa ovatko kaksi oliota täsmälleen sama olio eli *sama muuttuja*. Silloin käytetään vertailuoperaattoreita `==` ja `!=`.

Koska Javan *geneeriset* luokat ja metodit toimivat *vain luokkien*, eivät perustietotyyppien (`int, double, char`...) kanssa, se tarkoittaa sitä että geneeristen algoritmien ja luokkien toteutuksessa vertailuja ei noilla operaattoreilla tehdä. Koska toteutat algoritmit tässä tehtävässä geneerisinä, tämä on tärkeä asia.

> Asiaa hämmentää vielä se, että Javassa `Integer` -luokka on toteutettu siten, että se toimii yhteen `int` -perustietotyypin kanssa, ja Java tekee automaattisesti implisiittisiä muunnoksia näiden kahden tietotyypin, `Integer` ja `int` välillä. Siksi näiden luokkien (`Integer, Long, Double, Float, Character`) kanssa voi ollakin että nuo vertailuoperaattorit toimivat. Mutteivät omien luokkiesi kanssa. Sekavaa? Kyllä.

**Miten vertailut sitten Javassa pitää toteuttaa?**

Olioiden **yhtä- tai erisuuruutta** vertailtaessa käytetään `equals` -metodia (jonka *oletustoteutus* periytyy kaikille luokille `Object` -luokasta). 

Tämä `equals` -metodi pitää itse omille luokille toteuttaa (ylikuormittaa, *override*) *täsmälleen* samalla rajapinnalla kuin se on `Object` -luokassa esitelty. Kuhunkin luokkaan tehdään sitten luokalle *omanlaisensa* yhtäsuuruuden vertailu, kun halutaan tietää ovatko luokan oliot `equal` eli "samanarvoisia". Esimerkiksi henkilö -olioita vertailtaessa tämä voisi tarkoittaa vaikka henkilötunnuksen vertailua. Nimi ei ole kovin hyvä vaihtoehto tähän, sillä kahdella *eri* henkilöllä voi olla *sama* nimi.

Kun taas halutaan verrata **järjestystä**, vertailtavan luokan täytyy Javassa toteuttaa `Comparable` -rajapinta, ja järjestysvertailu (`<, <=, >, >=`) tehdään kutsumalla rajapinnan määrittelemää `compareTo` -metodia:

```Java
   Integer a = 10; // Integer on luokka, ei perustietotyyppi int.
   Integer b = 20;

   if (a.compareTo(b) < 0) {
      print("Muuttuja a on pienempi kuin b");
   }
   if (a.equals(b)) {
      printf("Muuttujat a ja b ovat yhtä suuret");
   }
   // Tai (mutta ei aina):
   if (a.compareTo(b) == 0) {
      printf("Muuttujat a ja b ovat yhtä suuret");
   }
   if (!a.equals(b)) { // Huomaa huutomerkki - NOT equals
      printf("Muuttujat a ja b ovat eri suuret");
   }
   // Tai (mutta ei aina):
   if (a.compareTo(b) != 0) {
      printf("Muuttujat a ja b ovat yhtä suuret");
   }
```

Aivan liian **yleinen virhe** kurssin tehtävissä on se, että (varsinkin itse tehtyjen luokkien ja/tai algoritmien kanssa) olioiden vertailu tehdään väärin käyttämällä vertailuoperaattoreita. Se menee läpi kääntäjästä, ohjelma toimii, mutta toimii **väärin**. Koska silloin ei vertailla sitä mitä pitäisi. Vertailuoperaattoreilla verrataan onko *olio* sama tai eri *olio*, esimerkiksi:

```Java
	Person one = new Person("Tiina");
	Person another = new Person("Pertti");
	Person third = one;
	if (one == another) { // false, different objects
		// ...
	}
	if (one == third) { // true, one and third refer to the same _object_, coder Tiina!
		// ...
	}
```

*Joskus* tietysti olion viittauksien vertaaminen on ihan paikallaan, mutta *vain* silloin kun nimenomaan halutaan katsoa viittaako joku muuttuja `x` samaan olioon kuin toinen muuttuja `y`. Esimerkiksi C -kielessä tällöin vertailtaisiin osoittaako pointteri samaan osoitteeseen, samaan tietoon.

Oikea tapa verrata ovatko henkilöt (semanttisesti) yhtäsuuret (eivätkä sama olio) on tämä:

```Java
	Person one = new Person("Tiina");
	Person another = new Person("Pertti");

	if (one.equals(another)) { // false, different names
		// ...
	}
```
Olettaen, että luokka toteuttaa `equals` metodin vertailemalla `Person` -olioiden nimiä. Vastaavasti tässä esimerkissä..:

```Java
	Person one = new Person("Tiina");
	Person another = new Person("Tiina");

	if (one.equals(another)) { // true, same names, same person
		// ...
	}
```
...todettaisiin että henkilöt ovat sama koska heillä on sama *nimi*. 

> Toki oikeassa henkilötietoja käsittelevässä sovelluksessa henkilön identiteetti (onko ihminen a sama kuin ihminen b) kannattaisi toteuttaa siten, että ihmisillä voi olla sama  nimi ja silti he ovat eri ihminen (`equals` palauttaisi false saman nimisillekin ihmisille), käyttäen jotain uniikkia tietoa (asiakasnumero, henkilötunnus (vain ja ainostaan jos se on todella tarpeen!), tai vaikkapa UUID).

Toinen yleinen virhe on se, että vertailumetodi `equals` joko unohdetaan toteuttaa tai se toteutetaan väärin (vertailu ei toimi kuten olisi syytä toimia), jolloin koodi ei toimi oikein. Tai `compareTo` -metodi toteutetaan väärin, jolloin tiedot esimerkiksi lajitellaan väärään järjestykseen. Ole siis näiden kanssa tarkkana!

# Miten Comparatoria käytetään?

Vertailua voidaan tehdä myös omalla vertailuolioillaan, "komparaattorilla". Tämä on kätevää silloinkin kun luokka toteuttaa `Comparable` -rajapinnan, ja sen olioita voidaan vertailla sen avulla ns. luonnolliseen järjestykseen. Mutta joskus halutaankin vertailla jotain ihan muuta kuin luonnollista järjestystä sillä `Comparable`:n tavalla.

Esimerkiksi jos `Person` -luokka on toteutettu näin:

```Java
class Person implements Comparable<Person> {
	private String name;
	private int age;

	@Override
	public int compareTo(final Person another) {
		return this.name.compareTo(another.name);
	}
}
```
Eli vertaillaan henkilöolioita luonnolliseen järjestykseen nimien kautta. Mutta joskus halutaankin vertailla henkilöiden ikää, ja lajitella henkilöt vaikkapa iän mukaan nousevaan järjestykseen (nuorin ensin). Tällöin tämä voidaan tehdä `Comparator` -rajapinta toteuttamalla:

```Java
   Person [] persons;
	...
    Comparator<Person> ageAscending = new Comparator<>() {
        @Override
        public int compare(final Person first, final Person second) {
            return first.getAge() - second.getAge();
        }
    };
	 // Sort by age ascending order
	 SimpleSort.insertionSort(persons, ageAscending);
```

Alla lisätietoa `Comparator`:n käytöstä. Sitä tarvitaan kurssin useissa tehtävissä.

Jos haluttaisiin lajitella kokonaislukutaulukko luonnolliseen (*natural*, nouseva) järjestykseen, ei tarvita mitään comparaattoria ollenkaan:

```Java
Integer [] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 4};
SimpleSort.insertionSort(array);
```

Koska `Integer` toteuttaa rajapinnan `Comparable` ja siten sillä on toteutus metodista `compareTo` jolloin lajittelualgoritmi käyttää sitä hyväkseen. **Katso** miten tämä tehdään `SimpleSort` -luokassa, lisäyslajittelun toteutuksissa joissa ei käytetä `Comparator`:ia (nämä kaksi rajapintaa ovat siis eri asia)!

Huomaa että sekä `Comparable` että `Comparator` palauttavat vertailumetodeissaan (`compareTo` ja `compare`) kokonaisluvun (`int`):

* jos palautettu arvo on < 0, ensimmäinen olioista (`compareTo`:n `this` -olio) on pienempi,
* jos palautettu arvo on nolla, oliot ovat vertailun näkökulmasta samanlaiset eli samassa järjestyksessä,
* jos palautettu arvo on > 0, ensimmäinen olioista (`compareTo`:n `this` -olio) on suurempi.

Sama voidaan tehdä myös antamalla eksplisiittisesti komparaattori, jonka `Comparator` itse luo:

```Java
Integer [] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 4};
SimpleSort.insertionSort(array, Comparator.naturalOrder());
```

Tai jos halutaan lajitella taulukko *laskevaan* järjestykseen:

```Java
Integer [] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 4};
SimpleSort.insertionSort(array, Comparator.reverseOrder());
```

Voidaan myös tehdä niin että määritellään ihan oma komparaattori-luokka, joka toteuttaa `Comparator` -rajapinnan:

```Java
class AscendingOrderComparatorForIntegers implements Comparator<Integer> {
    @Override
    public int compare(Integer first, Integer second) {
        return first - second;
    }
}
Integer [] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 4};
SimpleSort.insertionSort(array, new AscendingOrderComparatorForIntegers());
// Array is now [1, 1, 2, 3, 4, 4, 5, 5, 6, 9]
```
Nyt jos ensimmäinen numero on *pienempi* kuin toinen, vähennyslasku palauttaa negatiivisen arvon (< 0) eli ensimmäinen tulee järjestyksessä ennen toista. Jos ensimmäinen on suurempi, vähennyslasku palauttaa *positiivisen* arvon, eli ensimmäinen tulee järjestyksessä myöhemmin kuin toinen, sen jälkeen.

Tämä lajittelee siis luonnolliseen eli nousevaan järjestykseen. Varsinkin jos samaa komparaattoria tarvitaan sovelluksessa monessa paikkaa, tämä kannattaa tehdä omaan luokkaansa.

Tai sitten voidaan luoda nousevaan (*natural*) järjestykseen lajitteleva komparaattori *kutsuttaessa* lajittelualgoritmia, parametrissa:

```Java
SimpleSort.insertionSort(array, (first, second) -> first - second);
```

Komparaattori luodaan siis "lennosta" tässä: `(first, second) -> first - second`.

Tuossa annetaan tavallaan se `Comparator.compare` -metodin *runko*, mitä se tekee. Koska metodissa on kaksi parametria, pitää laittaa `(first, second)` ja sitten nuolen jälkeen se operaatio, joka palauttaa kokonaisluvun -- koska `Comparator.compare` palauttaa sellaisen. 

Vielä yksi tapa tehdä vertailuolioita on käyttää `Comparator`:ssa valmiina löytyviä muita keinoja:

```Java
   Comparator<Person> comparator = Comparator.comparingInt( Person::getAge );
```
Eli luodaan vertailija joka vertailee kokonaislukuja (`comparingInt`), ja kerrotaan sille mistä ne kokonaisluvut joita vertaillaan, saadaan -- kutsumalla `Person` -luokan metodia `getAge()` joka palauttaa `int`:n.

Voit itse päättää miten noita komparaattoreita tehtävissä luot, pääasia että homma toimii eli lajittelu menee oikeaan järjestykseen ja haut toimivat.