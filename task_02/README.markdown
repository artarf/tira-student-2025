# Puolitushaku

Tässä ohjelmointitehtävässä toteutat rekursiivisen puolitushakualgoritmin (*recursive binary search*) ja vertailet sen aikatehokkuutta lineaariseen hakualgoritmiin.

> Huom: **rekursio** on yksi kurssin oppimistavoitteista. Siksi iteratiivinen ratkaisu puolitushakuun **ei käy** ja tehtävä (ja kurssisuoritus) on hylätty, jos teet iteratiivisen toteutuksen.

Koodia tämä vaatii noin 30 riviä (kaksi n. 15 rivin toteutusta, toinen `Comparable` -luokille ja toinen joka toimii myös, vaikka taulukon luokat eivät toteuttaisi `Comparable` -rajapintaa, ja haluamme hakea myös muista kuin nousevaan järjestykseen lajitelluista taulukoista).

Toteuta algoritmit tehtävän 1 oppien mukaisesti, tarkistaen esiehdot ja huolehtien algoritmin oikeellisuudesta. Tehtävän yksikkötestit testaavat myös näitä asioita.

Puolitushaku vaatii lajitellun aineiston joten lajittelussa on *vertailtava* olioita. Myös hakualgoritmit tekevät vertailua. Siksi alussa peruspaketti vertailusta Javassa. **Tutustu** siis lähteisiin ja annettuun vertailua koskevaan materiaaliin. Näissä on usein tehty virheitä, joten materiaali tulee monille tarpeeseen.

## Lähteitä

* Kurssin **luennot** sekä **demot** rekursiivista algoritmeista.
* Javan [primitiiviset tietotyypit](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html).
* [Comparable](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html) 
* [Comparator](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html) 
* [Object.equals](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-) 
* [Comparable ja Comparator erot, video](https://www.youtube.com/watch?v=LBSSz2NOeQU)

Kurssin alussa on usein ongelmia Javan olioiden vertailujen kanssa. **Älä** vertaile **olioiden** yhtäsuuruutta tai erisuuruutta **operaattoreilla** (`==, !=, <, >, <=, >=`), vaan käytä aina joko olion `equals` ja/tai `compareTo` -metodia (jos olion luokka toteuttaa rajapinnan `Comparable`), tai vertailijarajapinnan `Comparator` avulla. Mallia näet valmiina annetuista lajittelu- ja lineaarinen haku -algoritmeista.

> **Lue** tarvittaessa lisätietoja lisäohjeesta [SUPPORT-COMPARISONS.markdown](SUPPORT-COMPARISONS.markdown), joka on tehty juuri siksi että tämän kanssa on usein ongelmia.

## Taustaa tehtävälle

Lineaarinen hakualgoritmi on *annettu valmiina*, samoin puolitushaun vaatima lisäyslajittelualgoritmi. Nämä molemmat on opetettu aiemmilla kursseilla:

* Lineaarinen haku on se ihan tavallinen for -silmukka, jossa etsitään haettavaa elementtiä taulukon alusta loppuun, ja haku keskeytetään kun haettava elementti löytyy. Tällaista olet käyttänyt jo Ohjelmointi 1 ja 2 -kursseilla (tai vastaava).
* Lisäyslajittelualgoritmi (*insertion sort*) on opetettu Ohjelmointi 2 -kurssilla, geneerisenä toteutuksena. Jos et muista tällaista kokeneesi, **kertaa** lisäyslajittelualgoritmi aiempien kurssien materiaaleista sekä annetusta lähdekoodista ja tutustu sen *aikakompleksisuusluokkaan*.

Näiden algoritmien toteutukset löydät `shared` -komponentista, joka on oma pieni kirjastonsa algoritmeja ja tietorakenteita, joita voit käyttää muista kurssin komponenteista käsin.

Toteutat myös alla olevien ohjeiden mukaisesti *puolitushakualgoritmin* kurssin `shared` -komponenttiin. Avaat sen projektin / komponentin **omaan** VS Code -ikkunaan (Open Folder > mene kyseiseen hakemistoon > Open), toteutat ja testaat algoritmin oikeellisuuden **siellä**.

Kun olet saanut puolitushakualgoritmin toimimaan `shared` -komponentin testien perusteella oikeellisesti, luo tästä kirjastokomponentista .jar tiedosto **shared** -hakemistossa.

**Ohjeet miten tämä tehdään, löytyvät `shared` -komponentin README -tiedostosta.**

`shared` -komponentissa on siis valmiina lineaarinen hakualgoritmi geneerisenä (luokka `LinearSearchArray`) sekä lisäyslajittelualgoritmi (luokka `SimpleSort`).

## Tehtävän ohjeet

Tehtävässä on kolme vaihetta. **Ensimmäisessä** vaiheessa toteutat **puolitushakualgoritmin** `shared` -komponenttiin, ja yhteen apuluokkaan (`Person`) metodit, joita tarvitaan hauissa ja lajittelussa silloin kun käsitellään taulukoita `Person` -olioita.

> Kannattaa **ehdottomasti** tutustua valmiina annettuihin algoritmeihin, sillä niistä näet samalla miten ne toteuttavat geneerisyyttä, ja miten vertailuissa käytetään sekä `Comparable` että `Comparator` -rajapintoja. Näistä on hyötyä kun toteutat puolitushakualgoritmeja, joissa tehdään ihan samoja asioita, mutta eri algoritmin kanssa.
>
> Annettu koodi sisältää myös muita **apumetodeja**, joista on **hyötyä myöhemmin**, esimerkiksi partitioinnin ja nopeiden lajittelualgoritmien toteutuksessa, esimerkiksi `ArrayUtils.swap`.
 
Toisessa vaiheessa **suoritat aikatehokkuustestejä**, ja vertailet puolitushaun aikatehokkuutta lineaariseen hakuun. Lisäksi pohdiskelet erilaisia sovelluksen käyttötilanteita ja miten niissä lineaarista ja puolitushakua kannattaisi missäkin tilanteissa soveltaa.

Kolmannessa vaiheessa kokeilet vielä lajittelua ja hakua **itse tekemälläsi** `Comparator` -oliolla.

### Vaihe 1

> Jos et ole vielä tutustunut tehtävän aiheeseen kurssin luentomateriaalin ja -videoiden sekä demojen (Moodle sekä liveluennot) kautta, nyt olisi hyvä aika. Kun olet, jatka sitten eteenpäin. Liian yleinen ongelma ohjelmontitehtävissä on se, että lähdetään tekemään jotain mitä ei ymmärretä. Sitten tulee sutta ja sekundaa.

Toteuta algoritmit `shared` -komponenttiin:

1. **Avaa** `shared` -komponentti **omaan** uuteen VS Code -ikkunaansa. 
  * Jos et osaa, tämä demotaan/on demottu liveluennoilla, joten katso sieltä. Voit kysyä myös apua opettajilta.

2. **Etsi** sieltä `BinSearch` -luokka.

3. **Etsi** luokasta metodit nimeltään `searchRecursively` ja **toteuta** niihin *rekursiivinen* geneerinen puolitushakualgoritmi.
  * Algoritmit palauttavat arvon `Integer.MAX_VALUE`, jonka poistat kun toteutat algoritmiasi. Tuo palautettu arvo kertoo testeille että algoritmia ei ole vielä toteutettu eikä ala valittamaan. 
  * Ensimmäinen versio metodista etsii olettaen taulukon elementtien olevan luonnollisessa järjestyksessä ja elementtien toteuttavan `Comparable` rajapinnan. Vertailu tehdään siis elementtien `compareTo` -metodilla. Esimerkiksi Javan perustietotyyppiluokat (`String`, `Integer`, jne.) toteuttavat tämän rajapinnan.
  * toinen versio metodista saa parametrina myös `Comparator` -vertailijan. **Käytä tätä vertailijaa** puolitushaussa etsimään haettavaa elementtiä, *sen sijaan* että vertailisit elementtejä niiden `compareTo` -metodilla.
  * **Muista** soveltaa mitä opit invarianteista edellisessä tehtävässä! Käytä **esiehtojen** tarkistuksia algoritmien toteutuksessa. Voit katsoa mallia annettujen algoritmien esiehtojen tarkistuksista.

4. Koska lineaarisessa haussa etsitään yhtäsuurta (`equals`) oliota, ja puolitushaussa sekä lajittelussa vertaillaan olioita etsittäessä oikeaa tietystä järjestyksestä, taulukkoon laitettavien olioiden luokan täytyy (lineaarista hakua varten) ylikuormittaa `equals` -metodi ja lajittelua ja puolitushakua varten toteuttaa `Comparable` -rajapinta.
  * Tehtävässä käsitellään `Person` -olioita. **Toteuta** `Person` -olion `equals` siten että henkilöolion identiteetti määräytyy henkilön tunnisteen (`id`) mukaan.
  * Tehtävässä lajitellaan ja haetaan `Person` -olioita. **Toteuta** `Person` -olion `compareTo` -metodi siten että henkilöolioiden vertailujärjestys määräytyy suku-, etu- ja keskimmäisen nimen mukaan (ja *nimenomaan tuossa järjestyksessä*).
* **Vältä** näissä toteutuksissa (aikatehokkuussyistä) muistin varaamista. **Älä** siis kutsu `Person.getFullName` -metodia, sillä se varaa muistia, joka on aina suhteellisen hidas operaatio. Vertaile suoraan henkilöolioiden jäsenmuuttujia, **kutsumatta mitään metodeja**.

Jos etsittävää elementtiä ei löydy, **palauta arvo -1**.

5. **Testaa** puolitushakua `shared` -projektissa olevilla testeillä `BinarySearchTests` sekä `BinarySearchPersonTests`.
  * `BinarySearchTests` lajittelee ja hakee taulukoista `Integer` ja `String` -olioita. Se testaa siis vain puolitushakualgoritmiasi (koska `equals` ja `compareTo` on näissä luokissa tehty valmiiksi). Testi tarkistaa myös että olet toteuttanut esiehtojen tarkistuksen (kurssin tehtävä 1).
  * `BinarySearchPersonTests` testaa myös puolitushakualgoritmiasi, mutta myös sitä miten olet onnistunut toteuttamaan `Person` -luokan `equals` ja `compareTo` -metodit. Jos näiden toteutus ei ole oikeellinen, lajittelu ja/tai haut eivät toimi oikeellisesti.
  * Nämä testit käyttävät siis myös valmiina annettuja algoritmeja, lineaarista hakua sekä lisäyslajittelua varten. Puolitushakuahan ei voi tehdä lajittelemattomaan aineistoon.

**Huomaa** (askel 4) toteuttaessasi `Person.equals` -metodia, että `equals`:n tulee tarkistaa onko parametrina tuleva objekti varmasti `Person` -luokan olio. Jos ei ole, metodin pitää palauttaa `false`, henkilö-oliota ei voi verrata vaikkapa traktori-olioon, joten ne eivät voi olla "samat" - palautetaan `false`. Tämän voit tehdä näin: `if (another instanceof Person) {`. Jos `another` on Person, voit tehdä tyyppimuunnoksen jonka avulla voit päästä käsiksi parametriin `Person` -oliona: `Person anotherPerson = (Person)another;` ja voit suoraan käsitellä sen jäsenmuuttujia kun vertailet ovatko molempien kaikki nimet varmasti samoja.

Kun toteutat `Person.compareTo` -metodia, huomaa että vertailet henkilöiden kolmea nimeä. Nimet ovat `String` -olioita,jotka toteuttavat `Comparable` -rajapinnan joten *myös niillä* on `compareTo` -metodi jota voit käyttää nimien vertailussa. `compareTo` palauttaa aina kokonaisluvun:

* *negatiivisen* arvon, jos `this`-olio jossa vertailua tehdään, on *pienempi* kuin parametrina tuleva olio.
* *nollan*, jos `this`-olio jossa vertailua tehdään, on *yhtsuuri* (vertailun kannalta) kuin parametrina tuleva olio.
* *positiivisen* arvon, jos `this`-olio jossa vertailua tehdään, on *suurempi* kuin parametrina tuleva olio.

Huomaa myös, että kun vertailet ensin sukunimiä, jos ne ovat erit (sukunimien `compareTo` palauttaa jotain muuta kuin nollan), *muita nimiä ei tarvitse enää edes verrata*. Jo tämän perusteella siis tiedetään kumpi tulee ensin. Vain jos sukunimet ovat samat, pitää verrata myös etunimiä. Ja jos *nekin* ovat samat vasta sitten pitää vertailla toista etunimeä (`middleName`).

Palauta suoraan se `String` -olioiden vertailun palauttama arvo `Person.compareTo`:sta kutsujalle. Arvo joka palautetaan **ei tarvitse olla** -1, 0 tai 1. Vaan se voi ihan hyvin olla < 0, 0 tai > 0. Näin `String`:kin tekee omassa `compareTo`:n toteutuksessaan, palautettu arvo kertoo merkkijonojen eron suuruudesta jotain.

> Javassa oletus on muuten se, että jos `equals` palauttaa `true`, silloin `compareTo` palauttaa nollan. `Comparable` -rajapinnan dokumentaatiossa sanotaan: "It is strongly recommended (though not required) that natural orderings be consistent with equals.". Hyvästä syystä tästä voi poiketa, mutta silloin se on syytä erikseen aina ohjelmiston suunnitteluun kyseisen luokan kohdalla kerrottava, ja luokan toteutukseen kommenteissa mainittava. Katso onko `Person` -luokassa tähän liittyvää dokumentaatiota.

Mikäli `BinarySearchTests` menee läpi, puolitushaku toimii. Jos `BinarySearchPersonTests` -testi ei mene läpi, vika on silloin siinä miten toteutit `Person`-olion metodit. Jos molemmat testit menevät läpi, teit oikein.

Jos olet toteuttanut vertailut oikein, yksi testeistä tulostaa pienen määrän koodareita ensin lajittelemattomana ja sitten *luonnolliseen* järjestykseen lajiteltuna, annetulla lisäyslajittelualgortimilla:

```
-- Unsorted Person array:
Jameson, James Third, age 87
Hedberg, Heikki Hessu, age 42
Jameson, James Second, age 67
Autio, Antti Anakonda, age 37
Gatorman, Gabriel Güllenberg, age 17
Jameson, James First, age 67
Jameson, Jill Jennifer, age 49

-- Sorted Person array:
Autio, Antti Anakonda, age 37
Gatorman, Gabriel Güllenberg, age 17
Hedberg, Heikki Hessu, age 42
Jameson, James First, age 67
Jameson, James Second, age 67
Jameson, James Third, age 87
Jameson, Jill Jennifer, age 49
```
Huomaa **oikeellinen** järjestys -- sukunimi, etunimi, toinen etunimi. Jos oma toteutuksesi ei lajittele samaan järjestykseen, testi epäonnistuu ja tulostusjärjestyskin on eri. Korjaa tällöin toteutuksesi `Person` -luokassa.

Jos tulee ongelmia, kysy apua opettajilta luokassa, Zoomissa, UKK -systeemissä tai sähköpostilla.

### Vaihe 2

Vaiheessa 2 suoritat tämän tehtävän `main` -metodin luokassa `SortAndSearchApp`.

> **HUOM**: Tämä vaihe, tässä komponentissa `task_2` käyttää `shared` -komponenttiin vaiheessa 1 tekemiäsi algoritmeja ja valmiina annettuja algoritmeja. Jos et saa testattua ja luotua `shared` -komponentin ohjeiden mukaisesti toimivaa koodia `shared-1.0.jar` -tiedostoon, testejä läpäisten, tässä vaiheessa *et voi tehdä yhtään mitään*. Korjaa siis kaikki bugit, suorita testit ja varmista että `shared` -komponentti on luotu ohjeiden mukaisesti `install` -komennolla. Tarvittaessa pyydä apua opettajilta.

**Avaa** siis tämä `task_2` komponentti/projekti omaan VS Code -ikkunaansa (jos se ei jo ole), jotta voisit suorittaa kyseisen main -metodin.

Jos vaiheen 1 testit menivät läpi, ohjelman pitäisi toimia ihan hyvin. Kun suoritat ohjelman, se tulostaa terminaaliin alla olevan esimerkin mukaisia aikamittauksia:

```
Starting to compare linear and binary search time efficiency
Time unit used is microseconds (us), 1 s is == 1 000 000 us
 - 1 second == 1 000 000 us
 - 1 millisecond == 1 000 us
Max dataset size (n) is 50 000
Searching 100 times from each dataset
n,Linear search,Insertion sort,Binary search
1000,1863,8870,96
2000,782,26785,56
3000,1326,13922,53
4000,493,23493,46
...
49000,9187,3367165,51
50000,8921,3553437,62


Total duration spent in task: 67 816 ms
```

Kuten näet, opettajan koneella suoritus kestää yli minuutin. Odota kärsivällisesti suorituksen loppumista, jos koneesi on hitaampi.

**Kopioi** tulostuksesta riviltä "n,Linear search..." alkaen, rivin "50000,8921,..." loppuun saakka kaikki teksti.

**Vie** kopioitu teksti taulukkolaskinsovellukseen (Excel, Applen Numbers, Google sheets, OpenOffice..). Nämä kaikki tukevat omalla (helpolla tai vähemmän helpolla) tavallaan sitä, että voit viedä tuollaista ns comma-separated-values (csv) dataa taulukon soluihin.

> Jos et osaa tehdä tätä itse, **kysy opettajilta neuvoa**. Kurssin teemana on aikatehokkuuden analyysi, ja sitä on syytä osata tarkastella sekä luennoilla esitellyillä tavoilla, että **mitata** ja analysoida aikatehokkuutta **todellisilla** tietokoneilla mitattuna. Tämä on siis olennaista käytännön osaamista. On olemassa myös erityisiä softan benchmark -työkaluja ja ohjelmakirjastoja, mutta yritetään pitää tekeminen kurssilla yksinkertaisena ja käytetään taulukkolaskimia.

Luo taulukkolaskimellasi **graafi** joka näyttää aineiston koon (n) suhteen sekä lineaarisen haun, lisäyslajittelun että puolitushaun suoritusajan kasvun n:n kasvun suhteen. 

Esimerkkikuva tästä alla:

![Screenshotti Google Sheetistä](graphs-google-sheet-sample.png)

**Pohdi** seuraavia asioita:

1. **Mitä** voit sanoa **lineaarisen haun aikatehokkuudesta** (aikakompleksisuusluokasta) kun tarkastelet sekä algoritmin toteutusta että aikamittausgraafejasi?
2. **Mitä** voit sanoa **puolitushaun aikatehokkuudesta** (aikakompleksisuusluokasta) kun tarkastelet sekä algoritmin toteutusta että aikamittausgraafejasi? Toki myös **luentojen** sisältö aikakompleksisuusluokista auttaa tässä.
3. **Mitä** voit sanoa lajittelussa **käytetyn lisäyslajittelualgoritmin aikatehokkuudesta** (aikakompleksisuusluokasta) kun tarkastelet sekä algoritmin toteutusta että aikamittausgraafejasi?

**Minkälaisissa tilanteissa** mielestäsi lineaarinen haku on silti **kokonaisuuden** kannalta tehokkaampi kuin puolitushakualgoritmi? Voitko kuvitella tilannetta jossakin tietyssä sovellustapauksessa, jossa puolitushakua ei kannata soveltaa? Vaikka lajittelualgoritmi olisikin lisäyslajittelua nopeampi, vaikkapa pikalajittelu (*quicksort*)?

> HUOM: Näitä tehtävien pohdintoja voidaan kysyä kurssin **tenteissä**, joten näihin pohdintoihin kannattaa panostaa.


## Kolmas vaihe

Tässä ohjelmassa on myös metodi `doYourSearch()`. Sen toteutus on hieman vajaa, sieltä puuttuu komparaattori joka vertailee `Person` -olioita *iän* mukaan.

1. Poista lohkokommentit (`/* ... */`) metodin alusta ja lopusta, koodin "aktivoimiseksi", ja lue koodin joukossa rivikommenteissa olevat ohjeet.
2. **Luo** komparaattori joka vertailee `Person` -olioita *iän* mukaan *luonnolliseen* eli nousevaan järjestykseen.
3. Kyseinen komparaattori välitetään parametrina paitsi lisäyslajittelualgoritmille, myös sekä lineaariselle että puolitushakualgoritmille.
4. Luo hakua varten sopiva `Person` -olio, jolle haettavan tiedon (ikä) arvo annetaan. Anna hakuolio parametrina hakualgoritmeille, taulukon lisäksi.

Kokeile löytävätkö sekä lineaarinen että puolitushakualgoritmi iän mukaan nousevaan järjestykseen lajitellusta taulukosta kyseisen ikäistä henkilöä. Koska henkilöolioiden ikä generidaan satunnaisesti, joka kerta kun niitä luodaan, aina ei sitä tietynikäistä löydy. Suorita koodia toistuvasti ja katso Terminaalista mitä sinne tulostuu. Voit myös kasvattaa luotavan aineiston kokoa jos tuntuu ettei ketään haetun ikäistä ala löytymään.

Jos taas lineaarinen haku löytää henkilön, mutta puolitushakusi ei, sitten algoritmissasi on vielä bugeja, vaikka testit menisivätkin läpi. Tai toisin päin, jos lineaarinen haku ei löydä ketään sen ikäistä, mutta puolitushakusi löytää, katso onko henkilön ikä varmasti se haettu. Muuten algoritmisi tai komparaattori jonka teit, on vielä virheellinen.

## Lopuksi

**Varmista** ettet ole käyttänyt toteutuksessa kurssilla kiellettyjä ratkaisuja. Tähän on työkalu jolla voit tarkistaa yleisimmät virheet tässä asiassa. Lue sen ohjeet `shared` -komponentin readme:stä.

Huomaa siis:

* Kun taulukko on lajiteltu tiettyyn järjestykseen, puolitushaun täytyy käyttää *samaa* järjestystä etsimiseen. Jos lajittelet taulukon komparaattorilla nimen mukaan *laskevaan* järjestykseen, ja sitten haet taulukosta olettaen luonnollisen eli nousevan järjestyksen (ilman komparaattoria tai eri komparaattorilla), haku ei voi toimia oikein. 
* Jos lajittelet henkilöoliot iän mukaan ja sitten haet puolitushaulla nimen perusteella, haku ei tietenkään tässäkään tilanteessa toimi oikeellisesti.
* Aineiston lajittelujärjestys ja puolitushaun hakukriteerien pitää siis olla samat, molemmat tehty samalla komparaattorilla (tai molemmat tehty oletusjärjestykseen eli luonnolliseen järjestykseen).
* Kun tietojoukko on niitä oman luokkasi olioita, *haettavalle* oliolle ei tarvitse antaa kuin niiden jäsenmuuttujien tiedot, joita komparaattori tai `compareTo` käyttää vertailuissa/hakemisessa. Tehtävän kolmannessa vaiheessa huomasit, että `Person` oliolle, jonka avulla hakeminen tehdään, ei tarvitse antaa nimiä, koska niitä komparaattori ei haussa niitä käytä, ainoastaan henkilön ikää.
  * Jos oikein tarkkaan mietit, niin jos haettavalle oliolle pitäisi antaa kaikki sen jäsenmuuttujien tiedot, niin miksi me hakua tarvitsisimme ollenkaan, meillähän olisi jo se *koko olio* käsissämme...
