# Binäärinen hakupuu

Toteutat tässä tehtävässä 8 `shared` -kirjastoon geneerisen binäärisen hakupuun (BST; *Binary Search Tree*) ja toimivaksi testaamisen jälkeen sovellat sitä laskemaan uniikkien sanojen esiintymislukumääriä mm. Project Gutenbergistä haettuihin kirjoihin. 

Kun olet saanut toteutettua BST:n, tehtävänä 9 -- jonka ohjeet ovat omassa [README-2-HASHTABLE.markdown](README-2-HASHTABLE.markdown) tiedostossaan -- on toteuttaa hajautustaulu (*hash table*) sekä hajautusfunktio (*hash function*) ja soveltaa näitä samaan tehtävään. Lopuksi vertailet kumpi tietorakenne on tehokkaampi uniikkien sanojen laskennassa *sinun* toteutuksillasi.

> Huom: tehtävässä 8 BST ei tarvitse toteuttaa puun solmun poistamista, puusta hakemista indeksin avulla, avaimen indeksinumeron hakua, eikä puusta hakemista avain-arvo -parin *arvon* perustella. Riittää että puu on myös aina avainten mukaan luonnollisessa järjestyksessä, eli käänteistä järjestystä ei tarvitse toteuttaa. Nämä olivat vaatimuksena vielä 2024 toteutuksessa.
> 
> Huom: Samalla tavalla kuin linkitetyt listat, binääristen hakupuiden ymmärtäminen ja toteutuksen yksityiskohtien pähkäily edellyttää useimmilta kynän ja paperin käyttöä.

Tietorakenteen toteutus vaatii noin 150 riviä koodia, riippuen koodaustyylistäsi ja paljonko teet turhaa koodia. 

Tehtävän soveltavassa osuudessa täydennät annettua koodia, tämä vaatii noin 100 koodirivin lisäämistä. Kun `shared` -komponenttiin toteutettu tietorakenne toimii, sen testit menevät läpi, muista käyttää Maven > Install -komentoa `shared` projektissa, jolloin toteutus on saatavilla myös tässä komponentissa, soveltavaan tehtävään.

Binäärisessä hakupuussa sisäisenä tietorakenteena **ei käytetä** taulukkoa, eli mitään reallokointeja ei tässä tarvitse tehdä, eikä tietorakenteella ole mitään kapasiteetin käsitettä. Varmista kurssin luentomateriaaleista ja kirjallisuudesta, että ymmärrät miten binäärinen hakupuu toimii.

Toteutuksessa **vaaditaan** BST:n algoritmeille seuraavat aikakompleksisuusluokat (log on kaksikantainen logaritmi):

* `add(K, V)`: O(log n)
* `get(K)`:  O(log n)
* `size()`: O(1)
* `isEmpty()`: O(1)
* `clear()`: O(1)
* `toArray()`: O(n)
* `getStats()`: O(n) - puuhun liittyvän tilastotiedon hakemista (syvimmän ja matalimman haaran korkeus).

## Lähteitä

* Kurssin luentokalvot ja videot.
* Liveluento (tallenne) ja sen vinkit ja esimerkit.
* Kurssikirja: Introduction to Algorithms, 4th ed (Comer et al), ss 312-330.
* `oy.interact.tira.util.Tree` -rajapinta (interface) ja sen dokumentaatio.
* `oy.interact.tira.factories.BSTFactory`, tehdasluokka joka instantioi toteutuksesi BST:stä, kunhan se on valmis.

## Miksi puutietorakenteet? 

Taulukot ja niitä käsittelevät silmukat vaikuttavat aikatehokuuteen. Jos vaikka etsimme taulukosta lineaarisesti tietoelementtiä, operaatio on O(n). Tai jos haluamme lisätä järjestettyyn taulukkoon uuden elementin ja se sijoittuu vaikka taulukon keskelle, haku voidaan tehdä kyllä O(log n) ajassa (puolitushaulla), mutta kun uusi elementti lisätään taulukon keskelle, kaikki sen jälkeen tulevat elementit pitää siirtää askel taulukossa eteenpäin. Tai sitten taulukko pitää lajitella. Tapauksesta riippuen tämä voi olla O(0) tai sitten pahimmillaan O(n) tai O(n log n). Mahdollisesti pitää vielä tehdä taulukon uudelleenallokointia, jos tila loppuu kesken. Kaikki tämä vie aikaa ja mahdollisesti tarvitaan myös ylimääräistä muistia.

**Binäärinen hakupuu** pyrkii välttämään tavallisten taulukoiden haittapuolet. Näin pyritään tietorakenteisiin joiden käyttäminen (elementtien lisääminen, haku, poistaminen jne) on nopeampaa kuin tavallisten taulukoiden kanssa. Esimerkiksi BST, joka ei käytä taulukoita, mitään reallokointia ei tarvitse tehdä, ja vain käytettävissä oleva muisti on rajana sille kuinka monta oliota BST:hen voi lisätä.

> Koska Javassa taulukoiden indeksointi tehdään `int` -tietotyypillä joka on Javassa etumerkillinen 32-bittinen kokonaisluku (*signed 32 bit integer*), suurin indeksin arvo on siis 2^31-1 = 2 147 483 647. Useinmiten tämä riittää vallan mainiosti. Mutta jos taas ajatellaan *suuria* tietomääriä, tämä tarkoittaa sitä että Javan taulukoissa *ei voi enempää olioita pitää*. Määrä on aika suuri ja useinmiten JVM:stä loppuu muisti ennenkuin tuo määrä olioita tulee lisättyä taulukkon. Mutta jos ajatellaan jotain suurkonetta jossa ajetaan todella suuria data-analyysejä tai vastaavaa, silloin tietomäärät voivat olla isompiakin.
>
> Jos taulukot ei riitä, voidaan käyttää esimerkiksi binäärisiä hakupuita joita taulukoiden rajoitukset eivät koske. Toinen vaihtoehto on käyttää *useampia* taulukoita ja itse hoitaa indeksoinnit ja taulukoiden käsittelyalgoritmit siten että nämä useammat taulukot näyttävät ohjelmoijalle yhdeltä isolta taulukolta, joka indeksoidaan vaikkapa `long` -tietotyypin indekseillä. Tai sitten käytetään taulukoita mutta vaihdetaan toteutuskieleksi sellainen joka ei rajaa taulukon kokoa 32-bittisen kokonaisluvun maksimiarvolla (C++, Swift,...).

## Mitä ovat avain-arvo -parit?

Useissa ohjelmointikielissä löytyy tietorakenteita avain-arvoparien (*key-value pair*) ylläpitämiseen. Avain-arvopareilla voidaan tallentaa ja etsiä nopeasti tietoelementtejä, ja välttää siten taulukoiden käytössä tarvittavat silmukat. Usein nämä tietorakenteet on toteutettu jonkinlaisella puutietorakenteella tai hajautustaululla, tai näiden yhdistelmillä.

Esimerkkinä näistä tietorakenteista on vaikkapa [Swift -ohjelmointikielen Dictionary](https://developer.apple.com/documentation/swift/dictionary). Monissa ohjelmointikielissä vastaavaa tietorakennetta kutsutaan nimellä "map" koska se "mäppää" jonkun avaintiedon (key) johonkin arvoon (value). Esimerkkeinä [Javan Map -rajapinta ja sen toteutukset](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html) ja C++:n [std::map](https://en.cppreference.com/w/cpp/container/map) ja [std::unordered_map](https://en.cppreference.com/w/cpp/container/unordered_map).

Tässä harjoituksessa siirrytään hyödyntämään binääristä hakupuuta tietorakenteena, taulukon sijaan. Binäärinen hakupuu on tällainen avain-arvopareja sisältävä tietosäiliö joka rakentuu puun idealle.

Toteutat aluksi BST:n ja sen aputietorakenteen (puun solmu, *tree node*). Testaat sen toimintaa yksikkötesteillä ja analysoit toteutusta ja sen aikatehokkuutta, raportoiden tulokset. 

Soveltavassa tehtävässä hyödynnät sitten toteuttamaasi ja oikeelliseksi testaamaasi binääristä hakupuuta.

## Toteutuksen ohjeet

> Huomaa että puolitushaku eli *binary search* on **ihan eri asia** kuin binäärinen hakupuu. Älä siis vahingossakaan ala tässä toteuttamaan tai käyttämään puolitushakua. Puolitushakua käytetään taulukoiden kanssa, kun BST:n toteutuksessa ei käytetä taulukoita.

> Huomaa myös että tässä(kään) tehtävässä **ei saa käyttää** Javan tietosäiliöluokkia (`List`,`Collection` tai `Map` -rajapintojen toteutukset, esim. `ArrayList`tai mitään muutakaan) tai algoritmeja luokissa `Arrays` ja `Collections`. Kaikki tietorakenteet ja algoritmit *toteutetaan itse* käyttäen Javan tavallisia taulukoita ja/tai omia luokkia sekä Javan perustietotyyppejä int, Integer, String, ja niin edelleen.

**Tutustu valmiiseen toteutuksen runkoon** `shared` -komponentin packagessa `oy.interact.tira.student`, nimeltään `BinarySearchTree`:

```Java
public class BinarySearchTree<K extends Comparable<K>, V> implements Tree<K, V> {
```

**Lue** huolellisesti `Tree` -rajapinnan **dokumentaatio** ja **toteuta metodit** luokkaan `BinarySearchTree` ohjeiden mukaisesti, luennoista ja muista yllä mainituista lähteista BST:stä saamasi tietämyksen perusteella.

Kuten yltä koodista näet, luokan on toteutettava `Tree` -rajapinta, ja se sisältää avain-arvo -pareja (K eli key ja V eli value). Näistä avaimeen (`K`) on lisäksi liitetty vaatimus että se toteuttaa `Comparable` -rajapinnan. Kun puun elementtien avaimia vertaillaan, siiihen käytetään siis `Comparable` -rajapinnan metodia `compareTo`:

1. Lisätessä puuhun uutta avain-arvo -paria, lähdetään liikkeelle puun juuresta ja otetaan se nyt käsiteltäväks solmuksi.
2. Vertaillaan onko puussa oleva, nyt käsiteltävän solmun avain (`K` key) sama kuin lisättävä avain (`compareTo` palauttaa nollan). Jos on, arvo (`V` value) *päivitetään*, eikä uutta avain-arvo -paria lisätä puuhun. Vanha arvo palautetaan kutsujalle.
3. Jos uuden avaimen arvo on *pienempi* kuin nyt käsiteltävän puun solmun avain, uusi avain-arvo -pari lisätään nykyisen solmun *vasempaan* haaraan.
  * jos vasenta haaraa ei ole, luodaan se. Jos on, otetaan vasen haara nykyiseksi solmuksi ja tehdään sama kuin kohdassa 2.
4. Jos uuden avaimen arvo on *suurempi* kuin nyt käsiteltävän puun solmun avain, uusi avain-arvo -pari lisätään nykyisen solmun *oikeaan* haaraan.
  * jos oikeaa haaraa ei ole, luodaan se. Jos on, otetaan oikea haara nykyiseksi solmuksi ja tehdään sama kuin kohdassa 2.

Tämä kaikki tarkoittaa luonnollisesti sitä, että puun sisältö on aina *avaimen mukaan luonnollisessa järjestyksessä*.

Tämä BST:n toteutuksen tulee olla geneerinen ja toimia minkä tahansa tietotyyppien kanssa, kunhan avain (K) on `Comparable`. 

Puun solmujen avainarvojen tulee olla *unikkeja*, tarkoittaa sitä että puussa ei ole kahta solmua jonka avaimen arvo on sama. Siksi tuo askeleen 2 arvon päivitys.

> Esimerkiksi C++:n `map`, `multimap` ja `set` vaativat että avain on aina uniikki. Sen sijaan [std::multiset](https://en.cppreference.com/w/cpp/container/multiset) sallii useita avaimia samalla arvolla. Javan standardikirjaston `Map` -rajapinta ja sen toteutukset eivät salli samoja avainten arvoja. Tämä voidaan kiertää siten että value ei olekaan yksittäinen olio vaan vaikkapa taulukko tai lista arvoja. Vaihtoehtoisesti voi käyttää vaikka [Apache commons -kirjaston MultiMap](https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/MultiMap.html) -luokkaa. Ole siis tietoinen siitä että tällaisista tietorakenteista on usein erilaisia toteutuksia, erilaisiin tarpeisiin.

Tämän jälkeen voit toteuttaa kaikki muutkin rajapinnasta perityt metodit, rajapintojen koodin kommenttien ja kurssin teorian perusteella. Huomaa että BST:tä on aika vaikeaa testata ennenkuin kaikki metodit on toteutettu.

**Toteuta** metodi `toArray()` siten että puun elementit tulevat taulukkoon **"in-order" -järjestyksessä**.

**Toteuta** myös puutietorakenteeseen puun haarojen minimi- ja maksimisyvyyksien laskenta. Tämä auttaa analysoimaan tietorakenteen aikatehokkuutta. Tiedät mikä on binäärisen hakupuun lisäämisen ja hakemisen teoreettinen aikakompleksisuusluokka, kun puu on tasapainoinen. Mutta usein tilanne ei ole se että puu olisi tasapainoinen. Minimi- ja maksimisyvyydet auttavat arvioimaan mikä tilanne on tietyn aineiston puuhun lisäämisen jälkeen.

Kun olet saanut BST:n toteutettua, ennen sen testaamista ja käyttöä toteuta vielä tehdasmetodit luokassa `oy.interact.tira.factories.BSTFactory`.

Kun olet valmis, voit testata toteutuksesi oikeellisuutta kuten alempana luvussa BST:n testaaminen on kuvattu.


## BST:n testaaminen

Testaa ensin BST -toteutustasi `shared` -komponentissa testillä `BSTGenericTests`. Se testaa BST:n toimintaa kutsumalla sen useimpia metodeja ja varmistamalla toteutuksen oikeellisuuden. Suorita sen jälkeen toinen yksinkertainen testi `BSTFindTests` joka testaa vielä tietorakennettasi lisää.

Testaa vielä suorituskykyä tilanteessa jossa BST:hen lisätään asteittain nouseva määrä elementtejä, testillä `PerformanceWithPersonIdBSTTests`. Testi tulostaa konsoliin puuhun lisättävien elementtien lukumäärän sekä lisäämisen ja hakemisen keston. Lisäksi tulostetaan puun haarojen minimi- ja maksimisyvyydet, tiedot jotka lasket itse puun toteutuksessasi:

```console
--------------------------------
Testing BST performance with Person.id as key
Take output to spreadsheet app for time efficiency analysis
--------------------------------
n,Time to add (ms), Time to get (ms),Min depth,Max depth
10000,12,7,4,28
20000,7,11,5,31
30000,13,14,5,34
40000,14,19,5,37
...
```

**Vie** tämä csv -data taulukkolaskinsovellukseen ja **analysoi** graafien kanssa aineiston koon kasvun vaikutusta lisäys- ja hakualgoritmien aikatehokkuuteen. Mitä puun haarojen minimi- ja maksimisyvyydet kertovat mielestäsi puun tasapainoisuudesta?

**Tallenna** analyysisi BST:N aikatehokkuudesta n:n kasvaessa, sillä **vertailet** sitä tehtävän 9 hajautustaulun aikatehokkuuteen samankaltaisessa tehtävässä.

> Opettajan testikoneella viimeisen, miljoonan aineiston käsittely kesti vajaa kaksi sekuntia. Jos koneellasi jo pienemmätkin aineistot hyydyttävät koneen, voit lopettaa testin kesken ja käsitellä sen datan, jonka sait kerättyä. Varaudu kuitenkin useiden minuuttien odottamiseen.

Jos testit menevät läpi, tietorakenteen toteutuksen pitäisi olla kunnossa, ja voit edetä soveltavaan tehtävään.


## Soveltava tehtävä 

Kielen ammattilaisia (ainakin) kiinnostaa se, mitkä sanat tai kirjaimet ovat yleisimmin käytettyjä erilaisissa teksteissä, kuten kirjat, nettisivustot tai sosiaalinen media.

Esimerkiksi [BlueSky Dictionary](https://www.avibagla.com/blueskydictionary/) kartoittaa reaaliaikaisesti mitä sanoja englanninkielen sanastosta keskusteluissa käytetään.

[Project Gutenberg](https://gutenberg.org) pohtii, [mitä yksittäisiä Unicode -merkkejä kirjaston kirjoissa käytetään](https://github.com/gutenbergtools/ebookmaker/issues/276):

> "Here something that woud be really useful for PG development going forward: A count of the occurrence of unicode code points used in PG texts. A recent issue 271 notes that the 2em dash is often used but missing in many typefaces."

Tällä on merkitystä sille miten projektin eri Project Gutenbergin työkalut toteutetaan tukemaan erilaisia merkistöjen merkkejä ja niiden enkoodauksia. 

Voisimme myös analysoida mitä "sanastoa" tietty toteutettu softaprojekti lähdekoodissaan käyttää; mitä muuttujien nimet ovat, kuinka monta kertaa ne esiintyvät koodissa ja mitkä ovat käytetyimmät ohjelmointikielen avainsanat.

Tässä soveltavassa tehtävässä toteutat loppuun sovelluksen, jolla voidaan laskea sata yleisintä sanaa kirjoissa tai missä tahansa tekstitiedostoissa. Suurin osa sovelluksen koodista on annettu valmiina, toteutat oman osasi ja hyödynnät tässä toteuttamaasi binääristä hakupuuta sekä nopeaa lajittelualgoritmia.


### Lyhyt sovelluksen esittely

**Huomaa** että projekti sisältää testiaineistoja (kirjatiedostoja), joita sekä testit että sinä voit käyttää kun kokeilet sovelluksen toimintaa. Kirjatiedostot löydät pakattuna tiedostosta `testfiles.zip`. **Pura** kyseinen tiedosto siten että sen sisältämät tekstitiedostot tulevat **tämän tehtävän juurihakemistoon**, ei mihinkään alihakemistoon.

> **Älä missään tapauksessa** laita näitä tekstitiedostoja git:iin!

Sovellus:

1. Lukee tekstitiedostoa (Unicode, UTF-8 encoded) joka sisältää ison määrän tekstiä, kuten esimerkiksi kirja. Kutsumme tätä ohjeissa sanalla "kirja" ja koodissa sanalla "book".
2. Lukee toisen pienemmän tekstitiedoston (Unicode, UTF-8 encoded) joka sisältää sanoja joita *ei huomioida* (`ignore-words.txt`). Tätä kutsutaan ignore-tiedostoksi ja ignore-sanoiksi.
3. Lukiessaan kirjatiedostoa ja sieltä sanoja, sovellus katsoo onko sana ignoorattava, ja jos ei ole, laskee kaikkien tällaisten kirjan uniikkien sanojen esiintymismäärän kirjatiedostossa.
4. Tulostaa *laskevassa* järjestyksessä 100 yleisintä sanaa jotka esiintyivät kirjassa, mukaanlukien esiintymismäärän.

Sovelluksen täytyy toimia oikeellisesti ja olla aikatehokas. Suurimmalla testiaineistolla, `Bulk.txt`, suoritusajan pitää olla korkeintaan muutamia sekunteja (olettaen keskimääräisen tehokkaan tietokoneen).

**TÄRKEÄ HUOMIO** Toteuttaessasi tätä projektia, **et saa** käyttää mitään Javan tietosäiliöluokkia tai algoritmeja. Et siis voi käyttää mitään luokkia jotka toteuttavat suoraan tai epäsuorasti Javan `Collection` tai `Map` rajapinnan. Mitään valmiita algoritmeja Javan kirjastoista, esimerkiksi luokissa `Arrays` tai `Collections` **ei saa käyttää**. Lajittelussa **ei saa** käyttää Javan valmiita lajittelualgoritmeja tai muitakaan, vain ja ainoastaan omaa nopeaa lajittelualgoritmiasi jonka teit aiemmassa harjoituksessa.

Sen sijaan **sinun tulee käyttää** niitä soveltuvia tietorakenteita ja algoritmeja jotka olet **itse** toteuttanut tämän kurssin ohjelmointitehtävissä. Erityisesti käytät toteuttamaasi **binääristä hakupuuta** sekä **nopeaa lajittelualgoritmia**. Tässä ei kannata alkaa tekemään näitä jo tehtyjä asioita uudestaan ja erikseen vain tähän sovellukseen, vaan uudelleenkäytät aikaisemmin toteuttamiasi geneerisiä algoritmeja ja tietorakenteita.

> Sovelluksen valmiina annettu koodi sisältää JavaDoc -muodossa olevaa dokumentaatiota kommenteissa. 
> 
> Jos haluat generoida tästä luettavan HTML -dokumentaation, voit tehdä sen komentorivillä projektin juurihakemistossa komennolla `mvn javadoc:javadoc`. Tämän jälkeen dokumentaatio löytyy tiedostosta `target/site/apidocs/index.html` jonka voit avata nettiselaimellasi luettavaksi.

Alla oleva UML:n luokkamalli kuvaa sovelluksen rakenteen jossa muutat ja täydennät vain punaisella merkityn luokan koodia.

![UML class diagram](classes.png)

Täydennät ja teet koodia tässä tehtävässä seuraaviin luokkiin:

1. Viimeistelet sanojen lukemiseen ja tulosten käsittelyyn liittyvän koodin `BookBase` -luokassa, joka on vielä keskeneräistä koodia. Valmiina annettu esimerkkitoteutus **huonosta** ratkaisusta ei sekään toimi ennenkuin `BookBase` on viimeistelty loppuun alla olevien ohjeiden ja koodissa olevien kommenttien mukaisesti.
1. Toteutat luokan `BSTBookImplementation`, joka käyttää `shared` -komponenttiin toteuttamaasi `BinarySearchTree` -luokkaa uniikkien sanojen laskemiseen. Tästä **tarkemmat ohjeet alempana**.
  * Tehtävässä 9 teet vastaavan toteutuksen hajautustaulua hyödyntävästä luokasta `HashTableBookImplementation`.
3. Instantioit  `BSTBookImplementation` -olion `BookFactory` -luokassa, jotta sitä voitaisiin hyödyntää sovelluksesta käsin.

Sovellusta voi käyttää joko komentoriviltä (tulostaa tulokset konsoliin) tai GUI-sovelluksesta, joka näyttää tältä:

![BooksAndWordsGUI](BooksAndWordsGUI.png)

Sovellus paitsi listaa sanat yleisyysjärjestyksessä, ja näyttää graafisen sanapilven jossa useinmiten käytetyt sanat näkyvät isompina.

## Yksityiskohtaiset vaatimukset uniikkien sanojen laskennalle

Kun käsittelet tiedostoja ja raportoit tuloksia, huomaa seuraavat rajoitteet ja vaatimukset. Nämä säännöt pätevät sekä BST että Hashtable -toteutuksiin, ja suurin osa näistä vaatimuksista toteutuu koodissa jota toteutat `BookBase` -luokkaan. Näin toiminnallisuus on käytettävissä automaattisesti sekä BST että hajautustaulu -toteutuksille, jotka ovat tämän `BookBase` -luokan aliluokkia.

Säännöt ovat tarkat siksi, että tulokset olisivat mahdollisimaan **oikeellisia**. Jos et noudata alla olevia sääntöjä toteutuksessasi, sanat ja niiden esiintymismäärät tulevat lasketuksi **väärin**. Siksi on määriteltävä **tarkka** tapa jolla prosessi tehdään, ja sitä on noudatettava. Tämähän on ihan normaalia ohjelmistojen speksausta, joten asian ei pitäisi tulla yllätyksenä.

1. Kirjatiedostot ovat vain tekstiä sisältäviä tiedostoa **UTF-8** -enkoodattuna.
1. Ignore -sanoja sisältävä tiedosto on vain tekstiä ja myös **UTF-8** enkoodattu.
1. Ignore -tiedostossa ignoorattavat sanat ovat toisistaan **pilkuilla** eroteltuina, eikä välilyöntejä ole.
1. Kun sovellus laskee kirjatiedoston sanoja, koko kirjatiedosto on käsiteltävä ensimmäisestä viimeiseen merkkiin ja sanaan.
1. Kaikki ignoorattavat sanat ignore -tiedostosta on otettava huomioon, eli niitä sanoja kirjoista **ei lisätä** uniikkien sanojen joukkoon.
1. Merkkitietoa **lukiessa** tiedostoista, on tekstiä luettava eksplisiittisesti Unicode UTF-8 enkoodattuna, ASCII, Latin-1 tai mikään muukaan käyttöjärjestelmäkohtainen oletus *ei käy*.
1. Kun tulkitaan milloin *kirjan* sana vaihtuu tai katkeaa, tämän määrittelyssä **on käytettävä** Javan `Character.isLetter(int)` metodia. Eli kun tulee vastaan merkki joka ei tämän metodin paluuarvon mukaan ole "letter", silloin edelliset luetut merkit (jos niitä on), muodostavat sanan.
  * **Mitään** ascii -koodeja käyttävää ratkaisua **ei hyväksytä**, koska ne eivät toimi kuin vain englanninkielisen tekstin kanssa. Tekstiaineistoissa joita testit ja sovellus käsittelee, on myös suomen, saamen, venäjän ja japanin kielistä tekstiä.
1. Sanat, joiden pituus on **yksi merkki**, ignoorataan aina. (*sen jälkeen* kun sana on yllämainitulla tavalla luettu kirjatiedostosta).
1. Sanat on **normalisoitava** pieniksi kirjaimiksi ennen käsittelyä. Näin sana "Hello" ja "hello" katsotaan samaksi sanaksi.
1. Kun uniikit sanat esiintymismäärineen on löydetty, sovelluksen täytyy tulostaa top100 lista jossa *laskevassa järjestyksessä* esiintymismäärän mukaan tulostetaan sanat ja niiden esiintymismäärä.
  * Jos kirjassa ei ollut sataa (100) sanaa, tulostetun listan on oltava tietysti lyhyempi ja sisältää vain löydetyt uniikit sanat.
1. Lisäksi sovelluksen on tulostettava alempana tässä dokumentissa kuvatut laskurit.
1. `BookBase.java`:ssa oleva abstrakti luokka sisältää metodeja jotka on merkitty kommenteilla "// Methods for tests"... Näiden pitää palauttaa valideja arvoja top-100 -taulukosta, jonka tekemäsi koodi luo. Näitä käytetään automaattisissa testeissä varmistamaan että toteutus löytää oikeita sanoja oikeita määriä kirjoista. Jos nämä eroavat (joissakin testeissä liikaa) testien odottamista sanoista tai määristä, testi ilmoittaa toteutuksen epäonnistuneen (testi epäonnistuu).
1. Missään `BookBase` luokan konkreettisesssa aliluokan metodeissa ei saa olla mitään interaktiota käyttäjän kanssa, ei edes tulostuksia konsoliin. Käyttäjältä ei saa näiden metodien toteutuksessa myöskään kysyä mitään inputtia. Tämä näkyisi testeissä siten että aikamittaukset osoittavat koodin olevan erittäin hidasta. Näin ollen testit kertovat sovelluksen olevan liian hidas ja se hylätään aikatehokkuudeltaan huonona toteutuksena.

## BookBase luokan toteutus
 
**Toteuta ensin** loppuun abstrakti `BookBase` -luokka, siten että se täyttää yllä mainitut vaatimukset sekä rajoitteet huomioiden. Kohdat jotka vaativat muutoksia, on merkitty koodiin kommentein. Koodista puuttuu:

1. Sanojen käsittelyn yksityiskohdat; miten tiedoston merkeistä muodostetaan sanoja.
2. Kun uniikit sanat on saatu kerättyä, tulostieto pitää saada taulukkoon lajittelua varten.
3. Lajittelun jälkeen muodostetaan top -taulukko, jossa vain eniten esiintyneet sanat. Tämä taulukko jää muistiin `BookBase` -luokan jäsenmuuttujaan `topWords`.

Seuraavaksi **toteutat** `BookBase` -luokasta konkreettisen aliluokan `BSTBookImplementation`, joka toteuttaa `BookBase` -luokasta puuttuvat abstraktit metodit. Toteutuksen **on käytettävä** `shared` -komponenttiin tekemääsi binääristä hakupuuta uniikkien sanojen ja esiintymislukumäärien käsittelyyn.
 
**Tutustu tarkkaan** `BookBase` -luokan jäsenmuuttujiin sekä annettuihin metodeihin. Noudata myös koodissa olevia olevia TODO -ohjeita toteutuksessasi.

`BookBase` -luokan metodeja kutsutaan aina seuraavassa järjestyksessä:

1. `setSource(String, String)`, jossa annetaan parametreina analysoitavan kirjatiedoston nimi hakemistopolkuineen. Toteutuksesi ei saa millään tavalla muuttaa tätä annettua tiedoston nimeä tai hakemistopolkua vaan käyttää sitä sellaisenaan.
1. `countUniqueWords()`, tämä käynnistää varsinaisen prosessoinnin; ignore -tiedosto avataan ja luetaan ignore-sanat muistiin; kirjatiedosto avataan ja aletaan käsittelemään sieltä poimittuja sanoja, kuten on kuvattu yllä vaatimuksissa.
1. `report()`, jossa tulostetaan top-100 lista, lajiteltuna sanojen esiintymismäärän mukaan laskevaan järjestykseen, plus muu raportoitavat tieto (katso alumpaa ja alhaalta mitä tämä tarkoittaa).
1. `close()`, jossa vapautetaan kaikki sovelluksen varaamat resurssit (muisti, avoinna olevat tiedostot jos niitä on, jne.) ennenkuin sovellus myöhemmin lopetetaan.
 
 Esimerkki siitä, miten API toimii:

 ```Java
  BookBase theBook = BookFactory.createBSTBook();
  theBook.setSource(bookFile, wordsToIgnoreFile);
  theBook.countUniqueWords();  
  theBook.report();
  theBook.close();
```

## Raportointi ja sanojen laskurit

Sovelluksen täytyy tulostaa konsoliin tietoja siitä mitä kirjatiedostosta löytyi, yllä kuvatulla tavalla. Tämä tulostus tapahtuu metodin `BookBase.report()` toteutuksessa.

Tässä on tulostettava seuraavaa:

1. Tulostetaan top 100 lista sanoista, lajiteltuna *laskevaan* järjestykseen sanojen esiintymismäärän perusteella (yleisin sana siis ensin).
  * Mikäli useammalla sanalla on sama esiintymismäärä, nämä sanat on oltava `String.compareTo`:n mukaisessa *nousevassa* (aakkos)järjestyksessä.
2. Jos kirjassa oli vähemmän kuin sata (100) uniikkia sanaa, käsitellään vain ne sanat.
3. Tulostetaan top 100 listan *jälkeen*:
   * kirjan sanojen kokonaismäärä.
   * uniikkien sanojen määrä kirjassa.
   * ignore -sanojen lukumäärä.
   * kuinka monta sanaa kirjasta ignoorattiin kaiken kaikkiaan (mukaan lukien yhden merkin kirjaimet).

**Huomaa** että lopputuloksena saatu top-lista sanoista on oltava `BookBase` -luokassa jäsenmuuttujana. Tämä siksi että testit hakevat sieltä sanoja, niiden sijantia top-listalla sekä esiintymismääriä, ja tarkistavat että tulos on oikellinen.

> Huomaa myös että `String.compareTo` vertailee merkkijonoja siten, että järjestys ei ole esimerkiksi suomenkielen lajittelujärjestyksen mukainen. Käytät sitä kuitenkin järjestämiseen tässä ja seuraavassakin tehtävässä, jotta vertailu tehdään aina johdonmukaisesti samalla tavalla. Emme ota siis huomioon eri kielten (*locale*) lajittelujärjestyksiä. Tämä on asiaa jota käsitellään Ohjelmointi 4 -kurssilla, lokalisoinnin yhteydessä.


## Vihjeitä

Jos toteutus kaatuu omituisesti isoilla tietomäärillä, ja käytät lajittelussa jotain rekursiivista algoritmia (esimerkiksi quicksort), lue ohje [WHAT-STACKOVERFLOW.md](WHAT-STACKOVERFLOW.md).

## Sovelluksen testaus

HUOM: Testit käyttävät kirjatiedostoja projektin juurihakemistosta, kuten mainittu. **Pura** (unzip) tiedosto `testfiles.zip` ennen testien ajamista tähän samaiseen hakemistoon. Muuten testit eivät löydä tiedostoja. Yksinkertaisinta on tehdä tämä komentorivillä, jos `unzip` (tai vastaava) työkalu on käytettävissä:

```console
unzip testfiles.zip
```

> Windows -käyttäjät voivat purkaa tiedoston sisällön myös Tiedostonhallinta -sovelluksella, mikä sen nimi nyt onkaan missäkin Windowsin versiossa (Resurssienhallinta?). Muista että vaikka zip-tiedosto saattaa näkyä hakemistona ja voit tarkastella sen sisältöä, se ei ole vielä purettu. Se on purettu oikein vasta kun näet zip-tiedoston sisältämät tekstitiedostot tämän projektin juurihakemistossa.

`CorrectnessTestsBST` -testi käyttää näitä kirja- ja ignore-tiedostoja, ja testaa toteutustasi `BSTBookImplementation` -luokasta varmistaen sen oikeellisuuden. Näiden testiaineistojen (pienten) tiedostojen sanamäärät ja sanat on laskettu etukäteen, ja testit vertailevatkin oman toteutuksesi löytämiä uniikkeja sanoja/sanamääriä näihin tunnettuihin sanoihin/sanamääriin ja sijainteihin top100 -listalla. 

Jos testien mielestä toteutuksesi antamat tulokset eroavat (liikaa tai vähänkään, riippuen testistä), testi ilmoittaa että se on epäonnistunut. Mieti missä vika on, ja korjaa se. Pyydä tarvittaessa opettajilta apua.

Nyt voit suorittaa myös aikatehokkuustestit jotka vertailevat BST:n ja hajautustaulun aikatehokkuutta nimenomaan tässä tehtävässä ja tämän aineiston kanssa. 

`PerformanceTests` -testit suorittavat sekä BST että hajautustaulu -toteutuksesi testejä kaikkien kirjatiedostojen kanssa ja mittaavat suoritusaikaa kirjan koon suhteen.

**Kokeile** sovelluksen toimintaa myös joko komentorivi- tai GUI-käyttöliittymästä käsin. Toimiiko? Nämä käyttävät oletusarvoisesti BST-toteutustasi.


## Tarkistukset

Analysoi erityisesti sitä, onko binäärisen hakupuun toteutuksesi oikeasti sellainen että se vastaa tehtävän alussa esiteltyjä aikakompleksisuusvaatimuksia. Jos ei vastaa, korjaa toteutusta.

> Mikä tahansa metodi jossa on *silmukka*, joka käy läpi koko aineiston, ei *voi* olla O(1) -- onko sinulla silmukoita metodeissa joissa vaatimus oli O(1)? Tai kutsutko tällaisesta metodista jotain *toista* metodia jonka O on muuta kuin O(1)? Jos näin on, aikakompleksisuusvaatimus ei täyty.

**Varmista** ettet ole käyttänyt toteutuksessa kurssilla kiellettyjä ratkaisuja. Tähän on työkalu jolla voit tarkistaa yleisimmät virheet tässä asiassa. Lue sen ohjeet `shared` -komponentin readme:stä.

## Lopuksi

Kun olet valmis, varmista että kaikki uusi ja muutettu koodi on paikallisessa git -repositoryssäsi ja myös etärepositoryssäsi (komennot `git commit`, tarvittaessa uusille tiedostoille `git add` sekä `git push`).

## Tietoja

* Kurssimateriaalia Tietorakenteet ja algoritmit -kurssille | Data structures and algorithms 2021-2025.
* Tietojenkäsittelytieteet, Tieto- ja sähkötekniikan tiedekunta, Oulun yliopisto.
* (c) Antti Juustila 2021-2025, INTERACT Research Group.