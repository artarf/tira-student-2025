# Pino eli Stack -tietorakenne

Tehtävän tavoitteena on toteuttaa pinotietorakenne (*stack*), siten että se toimii oikeellisesti, testit läpäisten. Pinon toteutuksessa käytetään sisäisenä tietorakenteena taulukkoa, jonka kokoa voidaan tarvittaessa kasvattaa dynaamisesti.

Toteutuksessa **vaaditaan** pinon algoritmeille seuraavat aikakompleksisuusluokat:

  * `capacity()`: O(1).
  * `push()`: O(1) paitsi kun joudutaan reallokoimaan: O(n).
  * `pop()`: O(1).
  * `peek()`: O(1).
  * `size():` O(1).
  * `isEmpty():` O(1).
  * `clear()`: O(1).
  * `toString()`: O(n).

Mikäli koodi ei vastaa kyseistä aikakompleksisuusluokkaa, toteutus on korjattava vastaamaan näitä, ennen kuin tehtävän deadline tulee vastaan. Sen jälkeen suoritus on hylätty.

> Tässä tehtävässä kirjoitat pinon toteutukseen noin < 50 riviä koodia, riippuen koodaustyylistä. Lisäksi **soveltavassa** tehtävässä täydennät valmista koodia, lisäten siihen n. 15 riviä koodia joka hyödyntää toteuttamaasi pinotietorakennetta.

## Lähteitä

* Kurssin luentokalvot.
* Liveluento (tallenne) ja sen vinkit ja esimerkit.
* Kurssikirja: Introduction to Algorithms, 4th ed (Comer et al) ss 254-255.
* Pinon rajapintaluokka (löytyy `shared` -komponentista) `oy.interact.tira.util.StackInterface` ja sen dokumentaatio.

## Olennaisia asioita pinon toteutuksesta

Metodin `clear()` toteutuksessa pinon kapasiteetiksi **on tultava** oletuskapasiteetti! Huomaa myös että aikakompleksisuusvaatimus tässä *todellakin* on O(1), eikä O(n).

Pinon ei tule käyttää enempää muistia kuin mitä sen maksimikapasiteetti milloinkin on. Esimerkiksi kahden (tai useamman) taulukon pitäminen *pysyvästi* muistissa (pinon jäsenmuuttujana) on *virhe*.

Pinoa toteuttaessasi, **varmista** että laitat luokan jäsenmuuttujiksi vain sellaisia tietoelementtejä joita tarvitaan koko pino-olion elinajan. Jos tarvitset jotain muuttujaa *ja sen arvoa* vain yhdessä metodissa, muuttuja kuuluu sen metodin paikalliseksi tilapäiseksi muuttujaksi. Turhat jäsenmuuttujat kuluttavat turhaa muistia. Ne voivat myös aiheuttaa bugeja jos muuttujan tilaa ei ylläpidetä oikein jokaisessa metodissa. 

> Jäsenmuuttujaksi laitetaan yllensä *vain* sellaista tietoa joka on olennainen osa olion tilaa, joka säilyy eri metodien kutsujen välillä ja koko olion elinajan. *Joskus harvoin* voidaan jäsenmuuttujaksi laittaa myös sellaista tietoa jonka laskenta on raskasta, joten tieto lasketaan kerran ja tallennetaan jäsenmuuttujaan, josta sen saa sitten valmiina, optimoiden suoritusta. Tässä tehtävässä `size()`:n toteutus on tällainen. On järkevää ylläpitää elementtien lukumäärää jäsenmuuttujassa, sen sijaan että `size()`:n toteutus kävisi joka kerta laskemassa montako ei-null -elementtiä taulukossa on. Näin metodista saadaan O(1), sen sijaan että se olisi O(n). Tätä metodia kutsutaan todennäköisesti usein, jolloin aikakompleksisuusluokka kannattaa pitää pienenä.

**Huomaa** että luokan `toString()` -metodi tässä ja myöhemmissäkin harjoituksissa toteutetaan käyttäen Javan `StringBuilder` -luokkaa, **ei** `String` -oliota muokkaillen. Suuria määriä elementtejä käsitellessä `String`:n avulla merkkijonon muodostaminen on **satoja tai tuhansia kertoja hitaampaa** kuin `StringBuilder`in käyttäminen. Jos `toString()` käyttää `String`iä merkkijonon rakentamiseen, siitä saa **miinuspisteen**.

> `String` -olion jatkuva muokkaaminen on hidasta, sillä `String` on niinsanottu [immutable](https://en.wikipedia.org/wiki/Immutable_object) olio. Vaikka merkkijonoon voi ohjelmallisesti lisätä merkkejä ja muokkailla niitä (`String`:n operaatiot `+`, `+=`, ja metodit `.append()`, jne), jokainen kerta syntyy *uusi* merkkijono-olio johon muutokset tallennetaan. Tämä tarkoittaa jatkuvaa muistin allokointia ja merkkien kopiointia paikasta toiseen. Siksi merkkijonojen rakentelua ja muuttamista pitäisi tehdä vain jos se on hyvin yksinkertaista ja tilanteissa joissa tätä ei toisteta satoja tai tuhansia kertoja. Koska pinotietorakenteessa voi olla satoja tai tuhansia (tai enemmänkin) olioita, niistä merkkijonon muodostaminen `String`:llä on [hidasta](https://stackoverflow.com/a/1253519/10557366). **Käytä** siis merkkijonojen "kasaamiseen" aina `StringBuilder`:ia joka on huomattavasti nopeampi.

**Huomaa** myös, että kun tietoelementti poistetaan pinosta kutsumalla `pop()` -metodia, taulukossa oleva **viittaus olioon on myös poistettava**. Tämä tapahtuu sijoittamalla kyseiseen taulukon indeksiin `null`. Jos tätä ei tehdä, taulukkoon jää viittaus kyseiseen olioon, eikä Javan roskien keruu voi sitten vapauttaa oliota muistista, kun `pop()`:n palauttamaa viittaustakaan ei enää käytetä. Tällöin tapahtuu muistivuoto (memory leak) joka on **ohjelmointivirhe**.


## Askel 1 - Ohjeet

Jos `shared` -komponentti ei ole valmiina auki VS Coden omassa ikkunassaan, **avaa** se sinne. Toteutat pinon sinne. Kuten aiemmissakin harjoituksissa, kun kaikki testit menevät läpi, muista tehdä .jar -kirjasto komponentin ohjeita seuraten.

**Toteuta** rajapinta `oy.interact.tira.util.StackInterface` omaan luokkaasi `oy.interact.tira.student.StackImplementation`, sinne `shared` -komponenttiin. Älä muuta millään tavalla annettua rajapintaluokkaa. Huomaa nuo packaget mistä rajapinta löytyy ja missä oma toteutus pitää olla.

Esimerkiksi, kun rajapintaluokassa on metodi...:

```Java
   public E peek() throws IllegalStateException;
```
...toteutusluokassasi on oltava vastaava *täsmälleen* samanlainen metodi:

```Java
   @Override
   public E peek() throws IllegalStateException {
		// Toteutus tänne.
	}
```

> Kun käytät jo aiemmin selitettyä `@Override` -annotaatiota, kääntäjä antaa virheen jos toteutus ei vastaa rajapinnan esittelyä. Jos et käytä annotaatiota, virhe jää helposti huomaamatta.

**Lue huolellisesti** koodista rajapintaluokan dokumentaatio, joka kertoo miten rajapinnan tulee käyttäytyä. Toteuta pino näiden ohjeiden mukaisesti.

> Huomaa että kurssilla kiertää vanhoja ratkaisuja ja C++ -demosta peräisin olevia ratkaisuja joita Javassa ei tarvita. Yksi näistä on se, että pinossa on jäsenmuuttujana kokonaislukumuuttuja `capacity` joka kertoo pinon kapasiteetin. Kun pinon sisäisen taulukon koko muuttuu, silloin tämän `capacity` -muuttujankin arvoa pitää päivittää. Jos tässä tekee virheitä, pinon tila menee sekaisin. Javassa jonon kapasiteettina voi ihan hyvin käyttää taulukon kokoa (`length`), joka tekee koodista yksinkertaisempaa, lyhyempää ja vähemmän virhealtista. Älä siis matki muiden virheitä tai C++ -demoa -- siinä kielessä taulukoilla ei ole moista Javan ominaisuutta kuin pituus.

Huomaa, että luokan metodit toimivat yhdessä ja yhteistyössä, käsitellen pinon sisäistä taulukkoa (tästä alla lisää) jota siellä käytetään elementtien muistissa pitämiseen. Pinoa ei siis kannata yrittää testata ennenkuin olet saanut toteutettua sen kaikki metodit.

Huomaa että toteutuksessa käytetään geneerisiä luokkia template -parametrilla `E` joka esitellään rajapintaluokassa `StackInterface`:

```Java
public class StackImplementation<E> implements StackInterface<E> {
```

Pino on siis geneerinen tietorakenneluokka, jonne voidaan laittaa minkä vaan luokan olioita. Vaatimusta esim. `Comparable` -rajapinnan toteuttamiseen `E`:lle ei tässä tarvita, koska pinon ei tarvitse millään tavalla vertailla pinossa olevia olioita.

Tässä tehtävässä toteuta pinon sisäinen tietorakenne tavallisilla Javan taulukoilla, joka sisältää pinoon laitettavat oliot:

```Java
private Object [] itemArray;
```

Koska *kaikki* Javan luokat perivät viime kädessä luokan `Object`, voimme luoda taulukon joka sisältää näitä `Object` -luokan olioita (oikeastaan mitä vaan sen aliluokkia siis).

Tarvitset pinon toteutuksessa seuraavia jäsenmuuttujia:

* Taulukko, jossa elementit ovat.
* Kokonaislukumuuttuja joka kertoo indeksin johon viimeisin elementti on lisätty (tai lisätään). Tämä kertoo myös pinossa olevien elementtien lukumäärän (jota tarvitaan `size()`:n toteutuksessa).

Luokan `StackImplemention` muodostimessa (constructor), toteuta se, että pino varaa muistia taulukkoon näitä pinon elementtejä varten *oletuskapasiteetin* verran. Esimerkiksi näin:

```Java
   itemArray = new Object[DEFAULT_STACK_SIZE];
```

Muodostin luo tilaa vain rajatulle määrälle olioita. Sinun täytyy huolehtia siitä, että jos tämä tila loppuu kesken kun pinon `push()` -metodia kutsutaan, siellä tarpeen vaatiessa *reallokoidaan lisää tilaa* uusien olioiden lisäämiseksi uudelleen allokoituun isompaan taulukkoon! Tämä reallokointi kannattaa tehdä luokan omaan pikku privaattiin metodiin jota kutsutaan `push` -metodista tarvittaessa. Muista kopioida tiedot vanhasta taulukosta uuteen ennenkuin korvaat vanhan jäsenmuuttujataulukon uudella.

Tyypillinen toteutus luo tilaa oletusarvoisesti esimerkiksi 16 elementille. Jotkut tykkäävät käyttää alkuarvona 32:ta. **Älä** käytä liian **suurta** oletuskokoa, sillä se tuhlaa muistia tilanteissa joissa pinoon laitetaan vain muutamia olioita. Vastaavasti reallokoinnissa, kannattaa yleensä vaikka tuplata muistitila. Jos alkuarvo on ollut 32, tuplaten siitä tulee 64, sitten 128, sitten 256 eli kasvu tapahtuu kahden potensseina.

Jos kasvatat tilaa kitsaasti, pino joutuu sinne olioita lisätessä jatkuvasti reallokoimaan. Älä esimerkiksi kasvata taulukon kokoa vain yhdellä. Tällöin jos taulukkoon lisätään tuhansia olioita, ensimmäisen reallokoinnin jälkeen *joka ikisen* uuden elementin lisääminen pinoon johtaa reallokointiin. Tämä hidastaa toteutusta tarpeettomasti.

### Lyhyt keskustelu reallokoinnista

**HUOM**: Jos **oikeasti** tulee jonkun taulukkoa hyödyntävän tietoaineiston tai -rakenteen kanssa todellinen riski siitä, että taulukon kokoa tuplatessa lähestytään Javan kokonaisluvun positiivisen arvoalueen *puoltaväliä*, reallokoinnin laskeminen pitäisi tehdä *järkevämmin* kuin vaan aina tuplata se taulukon koko. 

`Integer.MAX_VALUE` on Javassa (koska int on 32 -bittinen etumerkillinen kokonaisluku) 2 147 483 647. Puolet tästä on 1 073 741 823. Eli jos taulukon koko ylittää edes vähän tuon jälkimmäisen arvon, ja taulukon koko taas kaksinkertaistetaan, mitä luulet että tapahtuu? 

Voit itse kokeilla mitä tämä tulostaa: `System.out.format("%d%n", (1_073_741_823 + 16) * 2);` Eli oletetaan että taulukkoa on kasvatettu vähän (16) yli tuon puolivälin ja taas pitäisi reallokoida ja se tehdään yksinkertaisesti kaksinkertaistamalla nykyinen koko 1_073_741_823 + 16 eli 1_073_741_839. Tästä tulee arvoksi -2 147 483 618 eli todella suuri **negatiivinen** arvo, koska tapahtuu **kokonaisluvun ylivuoto** (*integer overflow*, tuttu juttu Laitteet ja tietoverkot -kurssilta, ainakin). Jos yrität allokoida taulukkoa negatiivisella koolla, sehän ei onnistu eli homma kaatuu siihen. Siksi olisi järkevää laskea reallokointikoko huomioiden nämä elämän tosiasiat liittyen `int` tietotyyppiin ja taulukon maksimikokoon Javassa. 

Esimerkiksi C++:ssa taulukon koko ja indeksit voidaan esittää vaikkapa `long` -tietotyypillä, joten siellä on epätodennäköistä että taulukon koon kanssa tulisi niin helposti ongelmaa; ennemmin loppuu koneesta muisti. Swiftissä `Int` on 64-bittinen (paitsi joillakin alustoilla kuten watchOS, jossa se se on 32-bittinen), joten siellä mahdollisia ongelmia tulee vasta paljon, paljon isommilla tietoaineistoilla kun Javassa.

### Jatketaan pinon toteutusta

Voit määritellä oletuskoon vakiona pino -luokan jäsenmuuttujana:

```Java
   private static final int DEFAULT_STACK_SIZE = 16;
```

Voit käyttää tätä vakiota nyt sitten joka paikassa jossa luodaan oletuskokoinen pinon taulukko, esimerkiksi:

```Java
   itemArray = new Object[DEFAULT_STACK_SIZE];
```
Näin on helppoa muuttaa tuo oletuskoko, kun joka paikassa koodissa käytetään samaa vakiota, vain muuttamalla tuon vakion arvo tuossa yhdessä paikassa missä se esitellään. Tämä on hyvää koodauskäytäntöä. 

> Koodissa pitäisi aina välttää ns. "maagisia" numeroita, eli jos siellä täällä on numeroarvoja `10`, tai `42`, tai mitään muutakaan, ne kannattaa korvata *vakioilla* (`final`, `const`) jotka selkeästi kertovat mikä on tuon numeron *tarkoitus* koodissa. Näin koodista tulee a) luettavampaa ja b) vakioiden joita käytetään useassa paikassa, arvon muuttaminen on helppoa kun se tehdään vain *yhteen* paikkaan.

**Toteuta** ylikuormittaen (`@Override`) pinoosi myös `Object` luokasta perittävä metodi `public String toString()`:

```Java
@Override
public String toString() {
   ...
```
Siten että palautettu merkkijono sisältää pinon sisällön alkaen "pohjalta", *täsmälleen* seuraavassa formaatissa, pilkut ja välilyönnit mukaanlukien:

```text
[110, 119, 121]
```

Esimerkkipinon sisältö on luotu kutsumalla `stack.push(110)`, `stack.push(119)` ja `stack.push(121)`. Metodin `pop()` kutsu palauttaisi tässä tilanteessa arvon 121.

Tyhjä pino palauttaa merkkijonon "[]". Huomaa että pino voi sisältää *mitä tahansa* Java -luokkien olioita, muutakin kuin Integer -olioita, ja näiden elementtien oletetaan tässä toteuttavan `toString()` -metodin. Jos merkkijono ei ole *täsmälleen* määrittelyn mukainen, testit epäonnistuvat.

Kun olet valmis testaamaan toteutustasi, **Luo** toteuttamasi pino-olio `oy.interact.tira.factories.StackFactory` -luokan eri metodeissa ja palauta toteutus kutsujalle. Testit ja muu annettu koodi saavat pinon toteutuksesi käsiinsä vain tämän tehdasluokan kautta, joten jos tämä unohtuu tehdä, testit epäonnistuvat. 

Jos sinulla ei ole tarvittavaa *kahta* muodostinta (*constructor*) pino-luokassasi, tee ne:

1. Parametriton versio luo pinon taulukon *oletuskapasiteetin* kokoiseksi.
2. Parametrillinen versio (int) luo taulukon sen kokoiseksi kun parametri kertoo.

> Keksitkö mitään hyviä syitä sille, miksi tuo toinen parametrillinen versio on kätevä olla olemassa?

Muistaa antaa kaikille jäsenmuuttujille järkevät arvot muodostimissa. Tarkista myös *esiehdot*, että parametreina annetut muuttujat ovat järkeviä, ja heitä tarvittaessa poikkeus `IllegalArgumentException`, kuten olet aiemmissa tehtävissä oppinut. Rajapintaluokan metodien esittelyt antavat vihjeitä siitä mitä poikkeuksia kukin metodi voisi heittää.

**Testaa** toteutustasi ensin **vain** testiluokan `StackTests` kautta. Testi löytyy hakemistosta `task_05`.

Jos kaikki toimii, voit edetä tehtävän seuraavaan, soveltavaan askeleeseen, jossa käytät toteuttamaasi pinotietorakennetta.

**Varmista** ettet ole käyttänyt toteutuksessa kurssilla kiellettyjä ratkaisuja. Tähän on työkalu jolla voit tarkistaa yleisimmät virheet tässä asiassa. Lue sen ohjeet `shared` -komponentin readme:stä.


## Askel 2 - Ohjeet

Tässä **soveltavassa tehtävässä** viimeistelet annettua koodia hyödyntäen toteuttamaasi pinotietorakennetta. 

Skenaariona tehtävässä on tilanne, jossa organisaatiossa on ollut käytössä [XML -muotoon](https://en.wikipedia.org/wiki/XML) asiakastietoja tallentava ohjelmisto, jota varten on pitänyt sitten tehdä oma editori jolla muokata asiakastietoja. Koska kukaan ei todellakaan ala käsin editoimaan suuria XML-tiedostoja. 

Suunnitelmana on nyt muuttaa pienen työkaluohjelman avulla kertalaakista kaikki XML -tiedostot [Markdown](https://en.wikipedia.org/wiki/Markdown) -tiedostoiksi, jolloin käyttäjät voivat suoraan muokata asiakastietoja millä tahansa tekstieditorilla ja niistä saa silti siistin näköisen luettavan dokumentin.

Tällainen tiedostojen rakennetta parsiva, rakenteen validoiva ja muodosta toiseen dataa konvertoiva koodi on aika yleistä todellisessa maailmassa.

Osa tästä toiminnallisuudesta on jo valmiiksi toteutettu `shared` -kirjastossa olevaan luokkaan `CustomXMLParser`: XML -muotoisen tiedon parsiminen tapahtuu siellä. Tehtäväsi on varmistaa omalla osuudellasi XML -dokumentin oikeellisuus (pinoa hyödyntäen!), ettei XML-tiedostossa ole virheitä, ja että oikeellisesta XML -tiedostosta tulostetaan konsoliin Markdown -muotoinen teksti. Tiedostoon tallentamista tässä ei tarvitse toteuttaa.

> Esimerkki tulostuksesta, jonka `ParsingApp` tekee ennen kuin olet toteuttanut tarkistuksen, on nähtävissä tiedostossa [SAMPLE-MARKDOWN.markdown](SAMPLE-MARKDOWN.markdown). Näet sieltä myös mitä pitäisi tulostua sen jälkeen kun **olet toteuttanut** XML:n rakenteen tarkistuksen pinoa hyödyntäen.
> 
> Esimerkkejä XML-syötteistä näet tämän projektin `ParsingApp`:n lähdekoodista ja tehtävän testeistä `shared` -komponentissa (joista osa on virheellistä XML:ää). Virheellisistä tilanteista esimerkkejä myös alempana.

Teet toteutuksen loppuun, valmiina annettuun luokkaan `oy.interact.tira.student.PersonXMLToMarkdownHandler`. Metodit joissa lukee `// Empty` ei tarvitse tehdä mitään. Kohdat joihin koodia tarvitaan, on merkitty kommenteilla `// STUDENT TODO`.

Toteutusta käytetään tällä periaatteella:

```Java
		CustomXMLParser parser = new CustomXMLParser();          // 1
		PersonXMLHandler checker = new PersonXMLToMarkdownHandler(false);  // 2
      parser.parse(testXML, checker);                          // 3
		System.out.println(checker.getProcessedOutput());        // 4
```

1. Luodaan `CustomXMLParser` -olio joka huolehtii XML-merkkijonojen parsimisesta, tietämättä mitä varsinaista dataa XML -tiedostossa on. Tämä toteutus on siis annettu valmiina, eikä sitä tarvitse muuttaa.
2. Luodaan `PersonXMLToMarkdownHandler` -olio, jolle `CustomXMLParser` -olio kertoo aina kun sen löytää XML-datasta elementtejä ja niiden sisältöä. Tämä luokka tarkistaa että sovelluskohtainen XML-sisältö on validia ja muuntaa sen Markdown -muotoon. Viimeistelet tämän toteutuksen.
  * Parametrin arvo `false` kertoo ettei handler tulosta jokaista askelta (tai virhettä) erikseen konsoliin. 
  * Tämän muuttaminen `true`:ksi on hyödyllistä jos haluaa nähdä askeleittain mitä tapahtuu, ja nähdä myös virheet, heti kun koodi huomaa että XML on viallista.
  * Osa testeistä antaa parmetriksi true, osa false.
3. Sanotaan parserille että parsi tämä XML -merkkijono ja kerro `CustomXMLParser` -oliolle (parametri `checker`) aina kun XML:stä löytyy elementtejä.
4. Kun handler ei tulosta yksityiskohtia, muunnettu Markdown -sisältö saadaan handleristä ulos kutsumalla metodia `getProcessedOutput()` ja voidaan tulostaa se, tai vaikka tallentaa tiedostoon.


`PersonXMLToMarkdownHandler` -olion rooli on siis hoitaa se että se pitää kirjaa siitä mitä XML sisältää, ja että:

1. XML-sisältö oikeellisesti kuvaa henkilötietoja siten niinkuin organisaatiomme on niistä kiinnostuneita, ja että
2. XML:stä löytyneet tiedot muunnetaan merkkijonoksi joka on Markdown -muodossa ja lopuksi
3. jos dokumentti oli oikeellinen, tulostetaan muodostettu Markdown -merkkijono konsoliin, muussa tapauksessa, heti kun virheellinen XML tulee vastaan, tulostuu `ERROR in source XML!` tai muuta vastaavaa ja olio heittää `SAXException` -poikkeuksen.

Eli jos esimerkiksi XML:ssä on seuraava virheellinen osio:

```XML
...
   <description>Customers from Oulu city area</subject>
...
```

Jossa `<description>` -elementtiä ei päätetäkään oikeellisesti `</description>`:lla vaan `</subject>`:lla, toteutus tulostaa (olettaen että `print` on true):

```
# Persons Document

Generated at Fri Aug 01 12:16:21 EEST 2025

## Document Information

 * Subject: Oulu Customers
 * Description: Customers from Oulu city area
ERROR: Mismatching element: <description> ends with </subject>
```

Ja keskeyttää XML:n käsittely heittämällä poikkeus `SAXException`. Keskeyttämisen huomaa siitä, että tuon virheellisen rivin käsittelyn jälkeen muuta ei enää tulostu, vaikka XML:ssä olisi vielä muutakin sisältöä.


**Tutustu** annettuun `PersonXMLToMarkdownHandler` -luokan toteutukseen. Koodi sisältää ohjeet kommenteissa toteutukselle:

```Java
// STUDENT TODO: define a member ...
...
```

Seuraa kommenttien ohjeita. Toteutat koodia seuraavasti:

Tee luokan **jäsenmuuttujaksi pinotietorakenteen rajapinnan toteuttava**, `StackInterface` ja luo `String` -olioita sisältävä pino käyttäen hyväksi `StackFactory`:ä. Pinon avulla pidetään kirjaa siitä onko XML -tiedoston elementit oikeellisia vai onko tiedosto jollain tapaa epämuodostunut. Tästä lisää alempana.

**Metodi `startDocument()`**, jota kutsutaan aina `CustomXMLParser`:sta käsin, kun alkaa uuden XML -dokumentin tarkistus, joten **kutsu** jäsenmuuttujana olevan pino-olion `clear()` -metodia tässä kohtaa. Muu tarvittava koodi on annettu valmiina. Tässä varmistetaan että aloitetaan uuden XML-dokumentin tarkistus aina ns. tyhjästä; pino on varmasti oikeassa tyhjässä alkutilassaan. Toki jos pinosi toteutuksesi on buginen, eivätkä aiemmat testit ole sitä huomanneet, ongelmia voi tulla. Muussa tapauksessa kaiken pitäisi toimia oikein.

**Metodia `startElement`** kutsutaan `CustomXMLParser`:sta kun parsija toteaa että XML:ssä alkoi uusi elementti. Esimerkiksi uuden henkilön tiedot `<person>` -elementissä tulevat seuraavaksi. Tai tullaan henkilön sukunimeen elementin `<lastname>`alkaessa. Tässäkin on annettuna valmista koodia. Sinun tehtäväsi tässä on toteuttaa pinon avulla XML:n validisuuden tarkistus. **Lisää** elementin nimi pinoon. 

> Huomaa että parametrina tulevassa elementin nimessä ei ole väkäsiä, eli `<person>` elementin tullessa vastaan, tässä parametrin arvona on merkkijono "person". Sama pätee `endElement` -metodiin (alempana).

Myöhemmin tarkistetaan päättyykö elementti oikeellisesti, eli esimerkiksi `<person>` -elementti pitää sulkea oikealla tavalla oikeassa paikassa `</person>` -elementillä (kun kutsutaan `endElement` -metodia).

**Metodissa `endElement`** ollaan kun parseri kohtaa XML:ssä jonkun elementin päättävän rakenteen, esimerkiksi kun parseri kohtaa `</person>` -rakenteen. Tässä tilanteessa *pinossa täytyy olla jotain*, koska päättävää rakennetta on vastattava alkava rakenne, esim `<person>`. Ja jos toteutat `startElement` -metodin ohjeiden mukaan, siellähän se pinossa on. Jos pino on kuitenkin tyhjä tai pinon päällimmäinen alkava elementti ei vastaa päättävää elementtiä (ne eivät ole sama merkkijono), **XML:n rakenne on viallinen ja pitää heittää poikkeus**. 

> Tässäkin metodille parametrina tuleva elementin nimi ei sisällä XML:n väkäsiä tms, vaan merkkijonon "person". Tiedämme kyllä että tässä on kyseessä `</person>` -elementistä, koska parseri kutsui nimenomaan `endElement` -metodia.

**Metodi `endDocument()`** -- tätä `CustomXMLParser`:sta kutsuttaessa ollaan käsitelty koko XML-dokumentti, joten on syytä tarkistaa puuttuuko XML -tiedostosta lopetuselementtejä, eli esimerkiksi `<document>`:ia vastaavaa elementtiä `</document> ei ole. *Jos pinossa on jotain*, tällöin XML on siis virheellinen, ja **pitää heittää poikkeus**.


Metodien toteutuksiesi pitää siis virheellisen XML:n kohdatessaan heittää poikkeus `SAXException`. Tämä keskeyttää XML-sisällön parsimisen, eikä sen läpikäyntiä enää turhaa jatketa. Tämä johtuu siitä, että `CustomXMLParser.parse()` kutsuu toteutuksesi metodeja. `parse()` ei sisällä try/catchiä -- eikä sen pidäkään niin tehdä. Näin ollen `parse()`:n suoritus keskeytyy heti kun koodisi heittää poikkeuksen, ja suorituksen kontrolli palaa `CustomXMLParser.parse()`:n kutsujalle.

Esimerkiksi `ParsingApp` tekee silloin näin:

```Java
// ...
			CustomXMLParser parser = new CustomXMLParser();
			PersonXMLToMarkdownHandler checker = new PersonXMLToMarkdownHandler(false);
			parser.parse(testXML, checker);
			System.out.println(checker.getProcessedOutput());
// ...
		} catch (SAXException e) {
			System.err.format("ERROR: %s%n", e.getMessage());
		}
	}
```

Jolloin ohjelman suoritus keskeytyy virheilmoitukseen, eikä prosessoitua outputia checkeriltä käytetä. Kokeile muuttaa `false` -parametri `true`:ksi kun haluat nähdä tarkempia tulostuksia parsimisesta.

### Testaaminen

Koska toteutettava koodi on `shared` -kirjastossa, samoin sen testit, voit testata toteutuksen oikeellisuuden siellä. Testit ovat luokassa `task_05/XMLParserTests`.

Muista että voit myös debugata testejä ja siten omaan toteutustasi `shared` -komponentissa. Testikoodi on sinänsä ihan samanlaista koodia kuin omasi, ja breakpointit toimivat myös testikoodista käsin. Voit tehdä breakpointteja omaan koodiisi, jota testikoodi kutsuu, ja ajat testejä sitten debuggerin kautta. Suoritus pysähtyy tällöin breakpointiin ja voit tutkia askel askeleelta missä vika on. Tämä on kätevää silloin kun et ymmärrä miksi koodisi ei toimi oikein.

Jos et osaa tätä, kysy apua opettajilta.

## Yhteenveto

**Pohdi** toteuttamiesi pinon algoritmien **aikakompleksisuusluokkia**. Vastaavatko ne alussa esitettyjä vaatimuksia?

## Ongelmia, kysymyksiä?

Osallistu kurssin luennoille, ohjaussessioihin ja verkkofoorumeille, kysy apua, lisäselvityksiä ja ohjeita opettajilta tarvittaessa.

Jos sinulla on ongelmia työkalujen kanssa, varmista että sinulla on oikea JDK asennettuna ja käyttöjärjestelmäsi ympäristömuuttujat (environment variables, PATH ja JAVA_HOME) osoittavat oikeaan JDK:n hakemistoon. Varmista että Maven on myös asennettu oikein ja git toimii.

## Tietoja

* (c) Antti Juustila 2021-2025.
* Kurssimateriaalia Tietorakenteet ja algoritmit -kurssille | Data structures and algorithms 2021-2025.
* Tietojenkäsittelytieteet, Tieto- ja sähkötekniikan tiedekunta, Oulun yliopisto.
