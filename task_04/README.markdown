# Nopea lajittelu - quicksort

Tehtävässä toteutat rekursiivisen pikalajittelualgoritmin (*quicksort*) ja vertailet soveltavassa tehtävässä sen aikatehokkuutta lisäyslajittelualgoritmiin (annettu valmiina) ja filtteröintiin (jonka toteutit jo aiemmin).

Pikalajittelualgoritmin toteutus voidaan tehdä eri tavoin. Perusoppikirjatoteutus vaatii noin 15 riviä koodia. Hoaren partitioinnilla tehty toteutus vaatii n. 20 riviä koodia.

Lisäksi teet pienen apualgoritmin, joka etsii hakuehdon mukaisen pienimmän/suurimman elementin taulukosta. Tämän toteutus vaatii n. 10 riviä koodia.

Tehtävän soveltavassa osuudessa teet tämän `task_04` -komponentin ohjelmaan kolme algoritmia joissa on kaikissa samankaltainen rakenne, kunkin toteutus vaatii 15-20 riviä koodia, siten että voit hyödyntää ensimmäisen koodia toisessa, yhtä riviä muuttaen (kolmas on vähän erilainen). Tehtävässä ei siis ole isosti ohjelmoitavaa, yhteensä noin 45-50 riviä koodia.

## Lähteet

* Luentomateriaali ja kirjallisuus.
* Tehtävän 2 lisäohje olioiden vertailusta Javassa (`Comparable, Comparator`).
* Javan [`Comparator` -rajapinta](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/util/Comparator.html).
* Javan [`BiPredicate` -rajapinta](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/util/function/BiPredicate.html).

Otetaan ensin kuppi kahvia (tai teetä)...

## Kahvilassa kuultua osa 1

Pera: "Hei Tiina, miten se on, kuinka paljon tilaa pitäisi varata käyttöliittymän suunnittelussa meidän asiakastietonäytöllä asiakkaiden sukunimelle? Onkohan ne yleensä kuinka lyhyitä tai pitkiä?"

Tiina: "Jaa empä kyllä tiedä. Mutta senhän voi kyllä tarkistaa helposti. Ladataan vaan  muistiin kaikki asiakkaat ja katsotaan kuinka pitkiä nimet on ja kuinka yleisiä ne eri pituiset nimet ovat, eli montako kertaa ne eri pituiset nimet esiintyy meidän asiakkaiden joukossa."

Pera: "Niin, samalla saadaan vähän tilastotietoakin nimistä. Miten tuo kannattaisi toteuttaa?"

Tiina: "Miten vaikka jos tehtäisiin siten että lajitellaan taulukko asiakkaita nimen pituuden mukaan järjestykseen. Alkuun tulee ne lyhyimmät sukunimet ja loppuun sitten pisimmät."

Pera: "Mutta se antaa vain ne asiakkaat tuossa järjestyksessä, lopussa viimeisenä on se nimistä pisin, se on kyllä helppo sieltä katsoa. Mutta vielä pitäisi laskea kyllä sitten sitä yleisyyttäkin, että kuinka monta asiakasta on joilla sukunimen pituus on vaikkapa 20 merkkiä. Miten se tuosta taulukosta saataisiin?"

Tiina: "No aika helposti. Kun taulukko on lajiteltu, lähdetään alusta ja katsotaan mikä on sen indeksissä nolla olevan sukunimen pituus ja laitetaan se muistiin. Lisäksi tarvitaan laskuri jonka arvo on 1, eli meillä on yksi asiakas jonka sukunimi on tämän pituinen. Sitten edetään yksinkertaisesti silmukalla taulukossa eteenpäin ja lisätään laskurin arvoa yhdellä aina kun se seuraavan asiakkaan sukunimen pituus on sama kuin edellisen. Kun seuraavan asiakkaan sukunimen pituus onkin suurempi, tulostetaan ensin se edellisen sukunimen pituus ja laskurin arvo, resetoidaan laskuri ja nykyisen käsiteltävän nimen pituus ja jatketaan sen seuraavaksi pidemmän sukunimen esiintymismäärän laskemista".

Pera: "Ahaa no niin tietysti. Minäpä toteutan tuon, meillähän on jo valmiina se lisäyslajittelualgoritmi toteutettuna. Testataan sitä aluksi vain 100 000 asiakkaan testiaineistolla niin saadaan vähän näkemystä siihenkin kauanko algoritmilla menee tässä aikaa..."

## 1. askel: hyödynnä lisäyslajittelualgoritmia

Tässä toteutat yllä kuvatun, nimien yleisyyttä kartoittavan algoritmin, käyttäen valmiina annettua lisäyslajittelualgoritmia `shared` -komponentin luokasta `SimpleSort`.

Koska lajittelua ei tehdä `Person` -luokan oletusjärjestyksellä (sen metodi `compareTo`, joka lajittelee nimien aakkosjärjestyksen mukaan järjestykseen), on lajittelussa käytettävä `Comparator` -rajapintaa, joka lajittelee nimen *pituuden* mukaan järjestykseen.

`SimpleSort`:sta on olemassa toteutuksia joissa parametrina annetaan vertailijaolio (`Comparator` -rajapinnan toteutus), jota käytetään järjestämisessä. Näin lajittelualgoritmi voi lajitella aineiston mihin tahansa järjestykseen halutaankin.

> Jos `Comparator` -rajapinta, jota tässä tarvitaan, on vielä hakusessa, **kertaa ensin tehtävän 2 ohje** [SUPPORT_COMPARATORS.markdown](../task_02/SUPPORT-COMPARISONS.markdown).
>
> Lisäksi, löydät esimerkkejä `Comparator`:n käytöstä metodista `SortApp.comparatorExamples()`.

**Toteuta** kahvilassa kuulemasi laskenta-algoritmi tämän tehtävän luokkaan `SortApp`, jossa on valmiina metodi `countByInsertionSorting` juuri tätä tarkoitusta varten. Metodissa on myös kommenteissa ohjeita algoritmin kirjoittamiseen.

Koodissa on valmiina myös `main` -metodi, joka kutsuu metodeja, jotka tekevät varsinaisen työn. Näissäkin metodeissa on valmiina testiaineiston luonti ja aikamittauskoodi.

**Tehtäväksesi** jää kirjoittaa koodi joka tekee kahvilassa kuullun keskustelun mukaisen toteutuksen. 

> Huomaa että sovelluksen aineistolla lisäyslajittelu on jo aika hidas. Kuten alta näet, opettajan koneella lajittelu kesti yli 20 sekuntia. Odota siis tulosta kärsivällisesti. Lisäyslajittelualgoritmi on annettu valmiina, joten sen pitäisi olla oikeellinen.

Tulostuksena algoritmista pitäisi saada jotain tällaista:

```
Starting to count by using SimpleSort.insertionSort...
Shortest last name was Vu, Dyllon Arfin, age 127, 2 characters
Longest last name was Standertskjöld-Nordenstam, Gianluca Valery, age 107, 25 characters
Name length:     2 count: 116
Name length:     3 count: 633
Name length:     4 count: 4311
Name length:     5 count: 11701
Name length:     6 count: 14433
Name length:     7 count: 17822
Name length:     8 count: 18312
Name length:     9 count: 13440
Name length:    10 count: 11108
Name length:    11 count: 5481
Name length:    12 count: 1576
Name length:    13 count: 593
Name length:    14 count: 298
Name length:    15 count: 103
Name length:    16 count: 53
Name length:    17 count: 7
Name length:    19 count: 5
Name length:    22 count: 6
Name length:    25 count: 2
Handled 100 000 persons out of 100 000
InsertionSort: N: 100000 - duration of counting all name lengths: 20784 ms
```

**Varmista** että algoritmisi laskee *kaikkien* eri pituisten sukunimien esiintymismäärien summan, ja että se *vastaa aineiston kokoa* (taulukon pituus)! Tämä näkyy toiseksi viimeisellä rivillä tulostusta. Jos nämä eivät ole samat, algoritmissasi on bugi ja osa aineistosta jää käsittelemättä!

> Huomaa että koska niitä, joilla on lyhyin ja pisin nimi, on monta ja nämä nimet ovat erit, tulostetut nimet ovat vain joku satunnainen asiakas kaikista niistä asiakkaista joilla on lyhyin tai pisin nimi. Tällä ei ole lopputuloksen kannalta merkitystä; tärkeää on saada nuo lyhimpien ja pisimpien nimien *pituudet*, sekä tuo eri pituisten nimien esiintymismäärä aineistossa.

Jos kaikki toimii, jatka eteenpäin, kahvilan kautta.

## Kahvilassa kuultua osa 2

Pera: "Toteutin nyt tuon algoritmin, ja sain kyllä tuloksia sieltä aikaan. Mutta -- suoritusaika on tällä kohtuullisen pienellä aineistolla *yli 10 sekuntia* omalla koneellani. Ja meillä on asiakkaita miljoonia. Mitäs nyt?!"

Tiina: "Selkeästi näyttää siltä että lisäyslajittelun aikakompleksisuusluokka on huono ajatellen meidän asiakkaiden määriä. Pitäisi keksiä jotain muuta..."

Pera: "Miten olisi pikalajittelu? Se ainakin kuulostaa nopealta."

Tiina: "Hyvä idea! Kerran kyllä jaksaa odottaa tuon 10 sekuntia mutta jos käytetään samaa algoritmia laskemaan muidenkin tietoelementtien esiintymismääriä eri kriteerein, joita on kymmeniä tai satoja, parempi että tämä olisi nopeampi algoritmi. Toteutatko sinä tämän pikalajittelun ja katsotaan saataisiinko tuloksia sillä nopeammin?"

Pera: "Hold my beer..."

## 2. askel: hyödynnä pikalajittelualgoritmia

**Toteuta** `shared` -kirjaston luokkaan `FastSort` *rekursiivinen pikalajittelualgoritmi*. 

**Testaa** sen oikeellisuus kirjastossa valmiiina olevilla testeillä. Toteuta algoritmista versio joka lajittelee elementit luonnolliseen järjestykseen sekä toinen, joka lajittelee taulukon parametrina saamallansa komparaattorilla mihin vaan järjestykseen.

> Voit toteuttaa ns. oppikirjaversion pikalajittelun osittamisvaiheen toteutuksessa, tai sitten käyttää Hoaren partitiointialgoritmia. Parasta olisi jos toteuttaisit molemmat, ja vertailisit niiden aikatehokkuutta.
> 
> Kun olet saanut testattua algoritmin oikeellisuuden, muista luoda `shared` -kirjastosta se .jar -tiedosto kuten tähänkin asti. Ohjeet tähän löytyivät sieltä `shared` -komponentista.

Kun olet saanut aikaiseksi toimivan pikalajittelualgoritmin, **kopioi** askeleen 1 koodista lajittelu- ja laskentaosuus tämän projektin metodiin `countByFastSorting` valmiina annetun koodin väliin, ja **muuta** sitä niin että lisäyslajittelun sijaan kutsutaan toteuttamaasi pikalajittelualgoritmia.

Kun suoritat taas `main` -metodin, **tarkkaile** seuraavia asioita molempien toteutusten tulostuksesta:

1. Onhan tulokset uudessa algoritmissa varmasti **oikeelliset**, eli samat kuin lisäyslajittelualgoritmissakin? 
  * Molemmat algoritmit käyttävät samaa tietoaineistoa, joten tulosten tulee olla **identtiset**.
2. Mikä on pikalajittelualgoritmilla saatu **nopeusetu** verrattuna lisäyslajittelualgoritmiin?

**Mikä on lisäyslajittelualgoritmin aikakompleksisuusluokka? Mikä on toteuttamasi pikalajittelualgoritmin aikakompleksisuusluokka?**

3. Mitä tapahtuu kun **kommentoit** `main` -metodista taulukon sisällön sekoittamisen (`shuffle`) **ennen pikalajittelualgoritmin kutsumista**, jolloin pikalajittelu saa valmiiksi lajitellun taulukon lajiteltavakseen? 
  * Hidastuuko pikalajittelun toteutus? Vähän vai paljon, vai ei ollenkaan? 
  * Tietyillä tavoilla toteutettuna, pikalajittelun aikatehokkuus romahtaa, kun se lajittelee *jo järjestyksessä olevaa* aineistoa. Kävikö sinun toteutuksellasi niin?

> Jos pikalajittelusi aikatehokkuus romahtaa jo lajitellun aineiston käsittelyssä, ongelma on siinä miten se osittaa aineistoa. Pikalajittelussa aikatehokkuuden kannalta parasta olisi jos aina valittaessa pivot -arvoa, ja puolitettaessa sen perusteella lajiteltavaa osuutta kahteen osataulukkoon, osataulukot olisivat suurin piirtein saman kokoiset.
>
> Jotkut tavat toteuttaa pikalajittelu ja osittaa taulukkoa johtavat siihen, että toinen puolikas on koko taulukko ja toinen on kooltaan nolla. Tämä johtaa erittäin epäedulliseen tilanteeseen, ja tämä näkyy erityisesti silloin kun lajiteltava taulukko on jo valmiiksi lajitellussa järjestyksessä. Ratkaisuna on joko pivot -arvon valinta jollain toisella tapaa, tai käyttää vaikkapa Hoaren partitiointialgoritmia, joka ei kärsi valmiiksi järjestyksessä olevasta aineistosta aikatehokkuuden kannalta. Se on muutenkin nopeampi kuin ns. perusoppikirjaratkaisu. TJEU.


## Kahvilassa kuultua osa 3

Tiina: "Nythän tämä nopeutui selvästi, hienoa Pera!"

Pera: "No niin teki. Tosin jäin vielä miettimään, että kun tiedämme quicksortin aikakompleksisuusluokan, ja mietitään tätä mitä algoritmin pitäisi tehdä, eli laskea nimien pituuksien esiintymisiä, niin mietin että tarvitaanko tässä lajittelua itse asiassa ollenkaan...?"

Tiina: "Niin miten sen sitten voisi tehdä?"

Pera: "No ajattelin että jos vaan tekisi silleen että ensin etsitään ilman lajittelua taulukosta joku asiakas, jolla on lyhin nimi. Ihan sama mikä niistä lyhimmistä ja pisimmistä, sillä ei ole väliä. Käydään siis vaan läpi *lajittelematonta* taulukkoa alusta asti, otetaan ekan asiakkaan nimen pituus ja oletetaan että se on lyhin. Sitten luupataan taulukko läpi, ja jos löytyy vielä lyhyempi sukunimi, otetaan se toistaiseksi lyhimmäksi. Sitten kun päästään taulukon loppuun, meillä on joku niistä asiakkaista, jolla on lyhin nimi. Ihan sama kuka. Nämähän ovat aikatehokkuusluokaltaan lineaarisia algoritmeja. Sama tehdään uudestaan pisimmälle nimelle."

Tiina: "Okei... lineaarinen aikakompleksisuusluokka on kyllä nopeampi kuin pikalajittelun aikakompleksisuusluokka. Mutta tuo antaa vasta lyhimmän ja pisimmän nimen pituuden, entä ne tulokset mitä edellä saatiin eli eri pituisten sukunimien esiintymislukumäärät?"

Pera: "No meillähän on siihenkin jo valmis algoritmi, jonka aikakompleksisuusluokka on sekin parempi kuin pikalajittelun. Tehdään vaan siten, että jos lyhin nimi oli vaikka 2 merkkiä, ja pisin 25, niin *filtteröidään* koko aineistosta ne henkilöt joilla on täsmälleen tietyn pituinen sukunimi, ja toistetaan sitä."

Tiina: "Höh, siis miten?!"

Pera: "No tässä hahmoteltuna pseudokoodissa: 

```
int lyhimmän_nimen_pituus = etsi joku henkilö jolla sukunimi on lyhin, ja ota sen nimen pituus
int pisimmän_nimen_pituus = etsi joku henkilö jolla sukunimi on pisin, ja ota sen nimen pituus

for (nimen_pituus välillä lyhimmän_nimen pituus...pisimmän_nimen_pituus) {
    taulukko_henkilöitä = filtteröi asiakkaat joilla sukunimen pituus == nimen_pituus
    tulosta nimen_pituus, taulukon taulukko_henkilöitä pituus
}
```

Tiina: "Ahaa! Lyhimmän ja pisimmän nimen etsimiset ovat molemmat lineaarisia algoritmeja, samoin filtteröinti! Vaikka filtteröintiä toistetaan, se toistojen lukumäärä on aineiston kokoon nähden kuitenkin aika pieni. Tässä on vain kolme lineaarista algoritmia, joten hyvällä tuurilla ja ainakin teoriassa tämä on nopea. Aikakompleksisuusluokkien periaatteiden mukaanhan vakiot, tässä tuo kolme (3) tiputetaan pois arvioitaessa algoritmin aikakompleksisuusluokkaa, joten tämä on lineaarinen algoritmi, kun taas pikalajittelu on huonompi aikakompleksisuusluokaltaan.

Pera: "Niin-pä!! Pitää vaan kokeilla oikeasti miten tämä toimii, sillä teoria ja käytäntö eivät aina ole ihan sama asia. Haluaisitko sinä tällä kertaa toteuttaa tämän algoritmin?"

Tiina: "No kuule samat sanat eli hold my beer...!"

## 3. askel: hyödynnä filtteröintiä

Tämä askel toteutetaan `SortApp`:n metodiin `countByFiltering`, jonka runko on jo valmiina koodissa.

Myös itse filtteröinti on toteutettu aiemmassa tehtävässä. Tässä jää tehtäväksi:

1. Tehdä geneerinen, yleiskäyttöinen *apualgoritmi* joka hakee annetusta lajittelemattomasta aineistosta jonkun vertailun mukaisen minimi- tai maksimiarvon, ns "reunusarvon", ilman lajittelua.
2. Tehdä kahvilassa kuullun pseudokoodin mukainen algoritmi, joka laskee nimien pituuden esiintymislukumäärät lajittelemattomasta taulukosta *filtteröintiä* hyödyntäen.
3. Vertailla tämän algoritmin aikatehokkuutta edellisiin.

Käydään nämä läpi järjestyksessä.

**Ensiksi**, toteuta `shared` -kirjaston luokkaan `ArrayUtils` apumetodi `T findEdgeValue(T [] fromArray, BiPredicate<T, T> usingPredicate)`. Tämän avulla voidaan etsiä "reunimmainen" eli pienin tai suurin arvo taulukosta annetulla *bipredikaatilla*.

Esimerkiksi jos halutaan suurin ja pienin kokonaisluku taulukosta, se tehdään tällä algoritmilla näin:

```Java
Integer [] values = {1, 2, -6, 1, -7, 9, 0, 3, 10, 11, 5, 6};
Integer smallest = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) < 0);
System.out.format("Smallest integer is %d%n", smallest);
Integer largest = ArrayUtils.findEdgeValue(values, (value1, value2) -> value1.compareTo(value2) > 0);
System.out.format("Largest integer is %d%n", largest);
// Prints:
// Smallest integer is -7
// Largest integer is 11
```
Suomeksi, `ArrayUtils.findEdgeValue` etsii yllä näkyvällä ensimmäisellä kutsukerralla *pienintä* arvoa `values` -taulukosta vertaillen kahta arvoa `value1` ja `value2` toisiinsa, siten että `value1` on pienempi kuin `value2`. Toisella kerralla vertailu on toisin päin, eli etsitään suurinta arvoa.

Huomaa että tässä `Predicate` jota käytettiin partitiointialgoritmissa, ei riitä, koska se tutkii aina vain *yhtä* arvoa taulukossa. `findEdgeValue`:n pitää vertailla *kahta* arvoa löytääkseen niistä pienemmän. Muuten `BiPredicate`:n käyttö on samanlaista kuin `Predicate`:n. 

Tämä kahden arvon vertailu onnistuu `BiPredicate`:lla. Algoritmin `findEdgeValue` pseudokoodi on tässä:

```
1. Tarkista esiehdot (*preconditions*; lue metodin dokumentaatio kommenteista!), heitä poikkeus jos ne eivät täyty.
2. Ota ensimmäinen elementti taulukosta oletuksena reunusarvoksi muuttujaan `found`.
3. `for` -silmukassa, käy läpi taulukkoa indeksivälillä 1..<pituus ja testaa bipredikaatilla, onko taulukon indeksissä oleva elementti suhteessa `found` -muuttujaan, predikaatin ehdon mukainen.
  3.1. Jos on, ota taulukon indeksissä oleva elementti talteen `found`-muuttujaan.
4. palauta found muuttujassa oleva elementti.
```

**Testaa* algoritmin oikeellisuus `shared` -komponentissa olevalla testillä `task_04/FindEdgeValueTests`. Kun algoritmi toimii, jatka eteenpäin.

**Seuraavaksi**, toteuta `SortApp.countByFiltering` -algoritmi, siten että se noudattaa kahvilassa kuulemaasi pseudokoodia. Lue myös annetun koodin mukana olevat kommentit.

Kun olet saanut tämänkin algoritmin toimimaan, siten että tulokset ovat samat kuin lisäys- ja pikalajittelun avulla saamasi tulokset, voit olla tyytyväinen ja jatkaa eteenpäin vertailemaan nopeiden ratkaisujen vertailua. 

### 4. askel: nopeiden ratkaisujen vertailu

Vertailussa käytetään siis 100 000 elementin aineistoa.  Näet ohjelman tulostuksista, miten näiden algoritmien ratkaisut suhteutuvat toisiinsa tällä aineistolla. 

**Mikä algoritmi** on mittaustesi perusteella **nopein** tässä tehtävässä? Mikä on kunkin eri algoritmin **aikakompleksisuusluokka** koodia ja aikamittauksiasi analysoiden?

Seuraavaksi vertaillaan vähän tarkemmin näitä nopeita ratkaisuja; kumpi näistä algoritmeista on nopeampi ja onko **aineiston koolla** asian kanssa mitään tekemistä?

Löydät ohjelmasta seuraavat koodirivit:

```Java
	private static boolean printDetails = true;
...
    final int MIN_VALUE = 100_000;
    final int MAX_VALUE = 100_000;
    final int STEP_INCREASE = 1_000;
...
```

**Muuta** nämä rivit tällaisiksi:

```Java
	private static boolean printDetails = false;
...
    final int MIN_VALUE = 1_000;
    final int MAX_VALUE = 50_000;
    final int STEP_INCREASE = 1_000;
...
```
Näin pääset testaamaan insertionsortin, quicksortin ja filtteröinnin aikatehokkuutta eri kokoisilla aineistoilla, ja turhat tulostukset jäävät pois hidastamasta testaamista. Tulostukset eli I/O (konsoliin, tiedostoon, verkkoon...) ovat aina kertaluokkaa hitaampaa suorittaa kuin muu koodi joka käsittelee dataa pino- ja kekomuistissa (*stack* ja *heap*).

Aikamittaukset tallentuvat csv -tiedostoon `measurements.csv`. **Vie** mittaukset taulukkolaskinsovellukseen ja **muodosta graafi** joka näyttää kolmen eri toteutusten aikamittaukset n:n suhteen.

Huomaat varmasti että mitä algoritmia jatkossa ei enää kannata käyttää aineiston koon kasvaessa. Mitä voit sanoa **pikalajittelun ja filtteröinnin aikatehokkuudesta suhteessa toisiinsa**?

**Seuraavaksi** jätetään pois testistä hidas insertion sort ja kasvatetaan testiaineiston kokoa. Tee koodiin seuraavat muutokset:


```Java
    final int MAX_VALUE = 350_000;
...
      // ArrayUtils.shuffle(persons);
      // if (printDetails) System.out.println("\nStarting to count by using SimpleSort.insertionSort...");
      // countByInsertionSorting(persons);
...        
```

**Suorita** ohjelma, ja analysoi taas `measurements.csv`:n mittauksia. Mitä voit sanoa nyt pikalajittelupohjaisen ja filtteröintipohjaisen ratkaisun aikatehokkuuksista, kun aineistojen koko kasvaa?

Jos näet eroja, **pohdi** mistä erot voisivat johtua?

## Yhteenveto

**Pohdi** toteuttamiesi algoritmien (`shared` -komponentti ja tämä tehtävä) **aikakompleksisuusluokkia**. Milloin kannattaa käyttää mitäkin algoritmia, käytännön tilanteissa, huomioiden algoritmien aikakompleksisuusluokat?

Mitä ajattelet siitä, miten aikakompleksisuusluokat teoriassa ja käytännössä suhteutuvat toisiinsa? Mitä tämä kertoo tilannekohtaisilla, todellisilla aineistoilla tehtyjen aikatehokkuusmittausten hyödyllisyydestä?


## Ongelmia, kysymyksiä?

Osallistu kurssin luennoille, ohjaussessioihin ja verkkofoorumeille, kysy apua, lisäselvityksiä ja ohjeita opettajilta tarvittaessa.

## Tietoja

* (c) Antti Juustila 2021-2025.
* Kurssimateriaalia Tietorakenteet ja algoritmit -kurssille | Data structures and algorithms 2021-2025.
* Tietojenkäsittelytieteet, Tieto- ja sähkötekniikan tiedekunta, Oulun yliopisto.
