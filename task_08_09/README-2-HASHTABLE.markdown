# Hajautustaulu

Toteutat tässä tehtävässä 9 hajautustaulu -tietorakenteen (*hash table*) sekä oman hajautusfunktion, ja näiden toimivaksi testaamisen jälkeen sovellat oppimaasi ja tekemääsi laskemaan uniikkien sanojen esiintymislukumääriä mm. Project Gutenbergistä haettuihin kirjoihin. 

Hajautustaulun toteutus tehdään `shared` -komponenttiin, annetun rajapinnan pohjalta ja testataan toimivaksi. Tietorakenteen toteutus vaatii noin 150 riviä koodia, riippuen koodaustyylistäsi. 

> Olet jo tehnyt tehtävässä 8 BST:n ja soveltanut sitä tässä samaisessa Books and Words -sovelluksessa, joten tässä tehtävässä tehtävä koodimäärä soveltavaan tehtävään liittyen on vähäisempi. 

Tehtävän soveltavassa osuudessa täydennät annettua koodia, tämä vaatii noin 20 koodirivin lisäämistä. Kun `shared` -komponenttiin toteutettu tietorakenne toimii, sen testit menevät läpi, muista käyttää Maven > Install -komentoa `shared` projektissa, jolloin hajautustaulusi toteutus on saatavilla myös tässä komponentissa, soveltavaan tehtävään.

Hajautustaulussa sisäisenä tietorakenteena **käytetään** taulukkoa, eli taulukon reallokoinnista tulee huolehtia oikeellisesti. Varmista kurssin luentomateriaaleista ja kirjallisuudesta, että ymmärrät miten hajautustaulu toimii, ja miten taulukon reallokointi eroaa esim. pinon ja jonon taulukoiden reallokoinnista.

Toteutuksessa **vaaditaan** hajautustaulun algoritmeille seuraavat aikakompleksisuusluokat:

* `add(K, V)`: O(1), paitsi jos joudutaan reallokoimaan, jolloin O(n).
* `get(K)`:  O(1)
* `size()`: O(1)
* `isEmpty()`: O(1)
* `clear()`: O(1)
* `toArray()`: O(n)
* `compress()`: O(n)
* `getStats()`: O(1) - hajautustauluun liittyvän tilastotiedon hakemista (törmäysten lukumäärä, luotaamisaskeleiden maksimilukumäärä aineistoa lisätessä, taulukon täyttöaste, jne.).

Metodin `compress()` (tiivistäminen) tarkoituksena on vähentää tietorakenteen muistinkulutusta. Jos tietorakenne toteutetaan triviaalisti taulukon kokoa suurentaen aina vaikkapa kaksinkertaiseksi, se voi sisältää jossain vaiheessa turhan paljon tyhjää tilaa. Tai jos toteuttaisimme hajautustaulusta poistamisen algoritmin, taulu voi tyhjentyä sen koon (kapasiteetin) kasvamisen jälkeen hyvinkin vähän elementtejä sisältäväksi. Tällöin suurin osa taulukosta on tyhjää, joka tuhlaa muistia. Tiivistäminen pienentää taulukon kokoa, kuitenkin siten että sen täyttäminen on edelleen nopeaa.

## Lähteitä

* Kurssin luentokalvot.
* Liveluento (tallenne) ja sen vinkit ja esimerkit.
* Kurssikirja: Introduction to Algorithms, 4th ed (Comer et al):
  * Hajautustaulut 275...
  * Hajautusfunktiot: ss 282...
  * Open addressing (avoin osoittaminen) ss. 293...
  * Linear probing (lineaarinen luotaaminen) ss 297...
* Muu kirjallisuus ja verkkolähteet. Etsi kirjallisuudesta ja nettilähteistä erilaisia hajautusfunktioita ja kokeile mikä niistä on tehokkain tässä tilanteessa.
* `oy.interact.tira.util.Dictionary` -rajapinta (interface) ja sen dokumentaatio.
* `oy.interact.tira.factories.HashTableFactory`, tehdasluokka joka instantioi toteutuksesi hajautustaulusta, kunhan se on valmis.
* `oy.interact.tira.student.CodeWordsCounter`, työkaluluokka, joka laskee käytettyjen sanojen määrää kooditiedostoista.
* [Javan Object.hashCode()](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#hashCode()), dokumentaatio Javan `hashCode()` -metodista. Lue myös ko. metodin alta löytyvän `equals(Object)` -metodin dokumentaation kohta "API Note".
* [Javan Comparable -rajapinta](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)


## Miksi hajautustaulu? 

Hajautustaulu sekä binäärinen hakupuu pyrkivät molemmat välttämään tavallisten taulukoiden haittapuolet. Näin pyritään tietorakenteisiin joiden käyttäminen (elementtien lisääminen, haku, poistaminen jne) on nopeampaa kuin tavallisten taulukoiden kanssa, varsinkin jos halutaan varmistaa että sama tieto ei ole tietorakenteessa kahdesti.

Tavallisten taulukoiden käyttäminen tällaisessa tilanteessa vaatisi lajittelemattoman taulukon lineaarista hakua -- etsitään aina ennen lisäämistä taulukkoon, onko elementti jo taulukossa, ja jos on, päivitetään sen arvo. Jos taas ei ole, lisätään elementti taulukon loppuun. Tämä tekee lisäämisestä jokaisen elementin kohdalla O(n) -operaation. Jos n elementtiä lisätään taulukkoon yhdellä kertaa, kokonaisuudesta tulee O(n^2), joka on jo todella huono.

Voisimme tietysti ajatella että no lajitellaan taulukko, ja sitten voidaan hakea puolitushaulla onko elementti siellä, jolloin haku on sitten O(log2 n) -- todella nopea. Mutta jos elementti lisätään taulukkoon uutena sen loppuun, silloin taulukko pitäisi taas lajitella, koska puolitushakua ei voi käyttää lajittelemattoman taulukon kanssa. Tai jos elementti lisätään suoraan omaan järjestyksen mukaiseen paikkaansa, sen jälkeen tulevat on siirrettävä sitten yksi pykälä eteenpäin. Tämäkin vie aikaa.

BST ratkaiseen tämän siten, että aikakompleksisuus tietorakenteeseen lisäämisellä on O(log2 n), joka on todella nopea.

Hajautustaulu eroaa BST:stä siinä, että se *käyttää* taulukoita, mutta eri tavalla kuin "tavallinen" taulukko. Luennoilla on käyty läpi hajautustaulun toimintaperiaate, joten luennot katsottuasi ymmärrät mitä tässä ollaan tekemässä.

Kuten BST, myös hajautustaulu käyttää avain-arvo -pareja. Avain on se tieto jonka perusteella parin paikka taulukossa määräytyy. Arvo on sitten se tieto joka avaimen avulla voidaan lisätä hajautustauluun nopeasti, ja myös hakea tietoa sieltä nopeasti.

## Toteutuksen ohjeet

> Huomaa että tässä(kään) tehtävässä **ei saa käyttää** Javan tietosäiliöluokkia (`List`,`Collection` tai `Map` -rajapintojen toteutukset, esim. `ArrayList`tai mitään muutakaan) tai algoritmeja luokissa `Arrays` ja `Collections`. Kaikki tietorakenteet ja algoritmit *toteutetaan itse* käyttäen Javan tavallisia taulukoita ja/tai omia luokkia sekä Javan perustietotyyppejä int, Integer, String, ja niin edelleen.

**Tutustu** `shared` -komponentin packagessa `oy.interact.tira.student`, olevaan hajautustaulun toteutuksen luokan runkoon nimeltään `HashTableDictionary`:

```Java
public class HashTableDictionary<K, V> implements Dictionary<K, V> {
```

**Lue** huolellisesti `Dictionary` -rajapinnan **dokumentaatio** ja **toteuta metodit** luokan `HashTableDictionary` toteutukseen, ohjeiden mukaisesti, luennoista ja muista yllä mainituista lähteistä hajautustaulusta saamasi tietämyksen perusteella.

Kuten yltä koodista näet, luokan on toteutettava `Dictionary` -rajapinta, ja se sisältää avain-arvo -pareja (K eli key ja V eli value). Huomaa, että toisin kuin BST, hajautustaulun avaimen **ei tarvitse toteuttaa** `Comparable` -rajapintaa. Hajautustaulun elementeillä ei siis ole mitään järjestyksen käsitettä.

**Lisää** hajautustauluun jäsenmuuttujaksi **avain-arvo** -parien taulukko. **Käytä** tähän valmiiksi annettua `Pair` -luokkaa. Löydät sen `shared` -kirjaston packagesta `util`. Allokoi taulukko hajautustaulun muodostimessa.

Lisääminen taulukkoon toteutetaan tällä periaatteella:

1. Lisätessä hajautustauluun uutta avain-arvo -paria, **lasketaan** avaimelle **tiiviste** (*hash*) kutsuen avaimen (`K`) metodia `hashCode()`.
2. Tiiviste **sovitetaan** hajautustaulun sisäisen taulukon kokoon sopivaksi **indeksiksi**. Jos tiivisteen arvo on vaikkapa 323151, ja taulukon koko 32, tiivisteestä on saatava arvo indeksille, joka on välillä 0...31.
3. Katsotaan onko kyseisessä indeksissä null vai ei:
  3.1 jos indeksissä on null, avain-arvopari voidaan **sijoittaa** kyseiseen indeksiin uutena elementtinä.
  3.2 jos indeksissä on avain-arvo -pari jonka avain on *sama* kuin lisättävä avain (`equals()` palauttaa `true`), **päivitetään** kyseisessä indeksissä oleva avain-arvo -pari.
  3.3 jos indeksissä on avain-arvo -pari jonka avain on *eri* kuin lisättävä avain (`equals()` palauttaa `false`), tapahtui **törmäys** (*collision*). Tällöin etsitään uutta indeksiä esimerkiksi lineaarisella luotaamisella tai neliöllisellä luotaamisella (*quadratic probing*), ja jatketaan askeleesta 2.

> Tämä prosessi on selitetty tarkemmin luennoilla ja demoissa.

Oletuksena törmäysten käsittely tehdään **luotaamisella** (*probing*), joko lineaarinen tai neliöllinen luotaus käy. Jos välttämättä haluaa, **omaa** linkitettyä listaa aiemmasta ohjelmointitehtävästä saa myös käyttää törmäysten käsittelyyn. Mitään muuta linkitetyn listan kaltaista toteutusta **ei** saa käyttää törmäysten käsittelyyn. 

> Tämä siksi että aiempina vuosina tällaiset "custom" -toteutukset ovat olleet aivan liian yleinen **plagioinnin** keino; toteutus on otettu jostain muualta, ja sama koodi on ollut kopioituna useammassakin palautuksessa. Siksi tätä ei nyt lähtökohtaisesti sallita.
> 
> Linkitettyjen listojen käyttö törmäysten käsittelyssä tekee toteutuksesta myös monimutkaisemman kuin luotaamisen käyttäminen. Muutamissa tapauksissa se on johtanut myös työn hylkäämiseen; linkitetyillä listoilla on vältetty taulukon reallokointi, listojen pituudet kasvavat suurilla aineistoilla tuhansiin, joka tekee toteutuksesta järkyttävän hitaan.

Lisäksi, ennen lisäämistä (tai heti sen jälkeen) on hyvä tarkistaa, onko hajautustaulun täyttöaste (*fill factor*) liian suuri. Jos taulukossa on liian vähän vapaata tilaa, täyttöaste on suuri, törmäysten määrä (askel 3.3) kasvaa merkittävästi, ja vapaan paikan etsiminen taulukosta hidastuu myös merkittävästi.

> Täyttöaste on siis hajautustaulun elementtien lukumäärän suhde taulukon kokoon. Jos taulukon koko on esimerkiksi 128 ja hajautustaulussa on tietyllä hetkellä 42 elementtiä, täyttöaste on tällä hetkellä 42 / 128 = 0,328125 = noin 33%.

**Huomaa** täyttöastetta laskiessa miten kokonaisluvut käyttäytyvät tietokoneessa. Kun taulukon koko on `int` -tietotyyppiä, elementtien lukumäärä on myös `int`, niin *kokonaislukujen* jakolasku 42 / 128 tuottaa lopputulokseksi *myös kokonaisluvun* ja jakolaskun lopputulos on **nolla**. Koska elementtien lukumäärä on aina pienempi kuin taulukon koko, täyttöaste tuolla tavalla laskettuna on **aina** nolla -- paitsi toki silloin kun taulukko on täysin täynnä eli `size == array.length`. 

Siksi täyttöastetta laskiessa **täytyy** tehdä tyyppimuunnos (*type cast*), tässä esimerkki:

```Java
public class Divide {

   public static void main(String [] args) {
      int size = 42;
      String [] array = new String[128];
      
      double fillFactorWrong = size / array.length;
      System.out.format("Wrongly calculated fill factor: %.2f%n", fillFactorWrong);

      // Type cast is needed!:
      double fillFactorRight = size / (double)array.length;
      System.out.format("Correctly calculated fill factor: %.2f%n", fillFactorRight);
   }
}
```
Kun käännät ja suoritat kyseisen koodin, se tulostaa (desimaalierotin on pilkku koska koneen locale on fi_FI):

```console
$ javac Divide.java
$ java Divide
Wrongly calculated fill factor: 0,00
Correctly calculated fill factor: 0,33
```

Yleensä sopiva täyttöäaste hajautustaululle on 60-65%. Jos taulukossa olevia elementtejä on enemmän suhteessa taulukon kokoon, reallokointi kannattaa tehdä tässä vaiheessa. **Kokeile** toteutuksessasi erilaisten täyttöasteiden vaikutusta tietorakenteen aikatehokkuuteen.

Kun taulukko reallokoidaan, on **erinomaisen tärkeää** laskea kaikille taulukossa oleville elementeille uusi sijainti uudessa eri kokoisessa taulukossa, edellä mainittua periaatetta käyttäen. Koska indeksi elementille määräytyy myös taulukon koon perusteella, kakkosaskeleessa sovitettu indeksi ei enää ole validi kun taulukon koko muuttuu.

Hajautustaulun `get()` -metodi hakee elementtiä taulukosta täsmälleen samalla tavalla kuin lisätessä, laskien tiivistettä ja sovittaen sen taulukon kokoon. Jos operaatio ei ole *täsmälleen* sama, `get()`:n laskema indeksi on eri kuin `add()`:n laskema, eikä lisättyä oliota löydykään, vaikka se olisikin taulukossa -- jossain toisessa indeksissä.

> Jos reallokoidessa kopioit elementit vanhasta taulukosta uuteen samoihin indekseihin kuin missä ne olivat vanhassa taulukossa, `get()` ei löydä elementtejä kuin tuurilla. On myös virhe kopioida elementit vanhasta taulukosta uuteen alkaen indeksistä 0 eteenpäin. **Ainoa oikea** toimiva tapa on laskea indeksit uudestaan, em. tavalla, uuden taulukon kokoa käyttäen.

Sama pätee tietysti myös `compress()` -algoritmiin, jossa taulukon kokoa mahdollisesti *pienennetään*. Silloinkin elementit pitää sijoittaa uuteen pienempään taulukkoon uudestaan, indeksit laskien.

Metodi `hashCode()` on esitelty Javan luokassa `Object`. Kuten tiedät, *kaikki* Javan luokat ovat suoraan tai epäsuorasti `Object` -luokan aliluokkia, joten jokainen aliluokka voi itse **uudelleenmääritellä** (*override*) miten tiiviste lasketaan juuri sen tietyn luokan jäsenmuuttujista.


## Hajautustaulun testaaminen ilman omaa hajautusfunktiota

Kun olet saanut hajautustaulusi toteutettua, ja instantioit sen `HashTableFactory` -luokassa, voit testata sen toiminnallisuutta testillä `HashTableGenericTests`. Älä suorita muita testejä tässä vaiheessa.

Jos testi epäonnistuu, korjaa bugit. Varmista että olet ymmärtänyt tietorakenteen toiminnan oikein luennoista ja muista lähteistä, ja että olet lukenut ja noudattanut ohjeet tehtävää tehdessäsi.

Jos tarvitset apua, kysy opettajilta. Saamme opettamisesta palkkaa, joten autammme mielellämme.


## Hajautusfunktion toteutus

Tämän tehtävän osalta toinen tärkeä kurssin oppimistavoite on toteuttaa **oma** tehokas hajautusfunktio. Testi `HashTableGenericTests` käytti avaimena Javan `String` -luokkaa, jossa on jo tietysti valmiina sille sopiva oma hajautusfunktion toteutus jo valmiina.

Tässä osuudessa tehtävää **toteutat oman hajautusfunktion**, **hyödyntämättä* Javan tai mitään muutakaan valmista toteutusta hajautusfunktiosta.

> Voit ja ehdottomasti kannattaakin etsiä *tietoa* erilaisista hajautusfunktioista kirjallisuudesta ja luentomateriaalistakin, ja kokeilla mikä niistä olisi tehokkain *oman* hajautusfunktiosi toteuttamiseen. Kirjoitat kuitenkin tämän hajautusfunktiosi lähdekoodin **ihan itse**, et kopioi sitä mistään, etkä anna sitä LLM:n toteutettavaksi.

Hajautustaulu käyttää siis avainolion `hashCode()` -metodia tiivisteen laskemiseen.

Jokainen hajautustaulun avain (`K` eli key) on Javan olio, perii luokan `Object` jossa on määritelty metodi `hashCode()`. Aliluokat uudelleenmäärittelevät (`@Override`) tämän metodin siten että ne laskevat itse oliolle *hajautusavaimen* **tämän** *hajautusfunktion* avulla.

Näin jokainen avainluokka voi *ihan itse* määritellä mistä *tämän* luokan jäsenmuuttujista tiiviste lasketaan ja miten. Ei ole mitään yleistä yhtä tapaa laskea tiivistettä, vaan se **toteutetaan aina jokaiselle luokalle erikseen**. 

Kun teet oman luokan, on **aina** syytä miettiä, laitetaanko luokan olioita sellaisiin tietosäilöihin jotka hyödyntävät tiivisteitä. Javassa näitä ovat mm. `Hashtable` ja `HashSet`. Ja tällä kurssilla oma hajautustaulusi toteutus.

Metodien `hashCode()`n ja `equals()` toteutukset käyttävät samoja jäsenmuuttujia yhtäsuuruuden ja tiivisteen määrittelyssä. Eli `equals()` tarkistaa tiettyjä jäsenmuuttujia vertaillen ovatko oliot sama olio, ja vastaavasti `hashCode()` laskee tiivisteen **täsmälleen** samoista jäsenmuuttujista kuin mitä `equals()` käyttää.

Hajautusfunktio palauttaa kokonaisluvun (`int`) joka voi olla arvoltaan positiivinen tai negatiivinen numero. Koska *hajautustaulussa* taulukon indeksin pitää olla aina `0 <= index < array.length`, hajautusfunktion palauttaman *kokonaisluvun arvo on sovitettava* taulukon indeksiksi, riippuen hajautustaulun taulukon tämänhetkisestä pituudesta. Miten tämä tehdään, on näytetty kurssin luennoilla.

Tiivistefunktio `hashCode()` **ei** kuitenkaan tätä tiivisteen "supistamista" tuohon taulukon kokoon tee -- se tehdään vasta *hajautustaulun* toteutuksessa. Jokaisen luokan hajautusfunktio pyrkii *hajauttamaan* arvot **mahdollisimman laajalle** alueelle kokonaislukujen arvoaluetta, eli palauttavat (Javassa) arvoja väliltä `Integer.MIN_VALUE-Integer.MAX_VALUE` eli `-2147483648...2147483647`.

**Toteuta** `Person` -luokalle **hajautusfunktio** ylikuormittaen se. Toteuta metodi **käyttämättä** (kutsumatta) mitään valmista Javan `hashCode()` metodia. Laske tiiviste merkki merkiltä, seuraten koodissa olevia kommentteja ja ohjeita, sekä luentojen oppia.

Luokassa `Person` on otettava huomioon se, että useilla ihmisillä voi olla sama nimi. Eli kahdelle eri yksilölle `Person.compareTo()` voi palauttaa nollan (nimet ovat samat), mutta `Person.equals()` voi silti palauttaa `false`. Kyse on kahdesta eri yksilöstä, joilla on sama nimi.

`Person.equals()` vertailee siis henkilöiden *identiteettiä*, ja tiiviste tulee laskea tästä samasta henkilön *identiteetistä*. Kahdella eri ihmisellä, joiden tiiviste on eri, voi olla sama nimi. Ja toisin päin, kahdella saman nimisellä ihmisellä on eri tiiviste, koska he ovat eri yksilöt, joilla on eri identiteetti.

> Lue Person -luokan koodin luokan dokumentaatio tiedoston alusta. Se kertoo nämä perusssäännöt, viitaten Javan dokumentaatioon.

Muista että `Person` -luokan `hashCode()` -metodia voidaan käyttää muidenkin tietorakenteiden tai -algoritmien kuin hajautustaulun kanssa. Siksi `hashCode()`:n toteutus **ei saa** rajoittaa palautettavan kokonaisluvuna arvoa millään tavoin; sen pitää voida olla erittäin suuri tai pieni positiivinen *tai* negatiivinen kokonaisluku. Tämä luku rajataan taulukon kokoon **vasta** hajautustaulu -tietorakenteessa.

**Testaa** tiivistefunktion toteutusta `shared` -kirjastossa testillä `task_08_09/PersonHashTests`. Testi testaa seuraavia asioita:

1. palauttaako tiivistefunktio (`Person.hashCode()`) aina nollan tai muun yhden ja saman arvon -- se on yleensä merkki siitä että **ohjeita ei ole luettu eikä tiivistefunktiota ole toteutettu ollenkaan**. Tai sitten tiivistefunktio on toteutettu **väärään paikkaan** (yleensä hajautustaulu-luokkaan) ja/tai muuten väärällä tavalla. 
2. Onko tiivistefunktio toteutettu kutsumalla `Person.id`:n `hashCode()` -metodia, eli ei ole toteutettu *omaa* hajautusfunktiota vaan kutsutaan tuota `id` -jäsenmuuttujan `hashCode()` -metodia `String` -luokassa -- tässä **ei saanut** käyttää Javan valmiita hajautusfunktioita vaan **toteuttaa sellainen itse**.
3. Toinen asia mitä testi tuottaa on se tieto, että tuottaako tiivistefunktio mahdollisimman **uniikkeja tiivisteitä** eri `Person` -olioille. 
4. Ja lopuksi, testi tarkistaa myös sen, että tiivisteiden arvojen joukossa on varmasti **myös negatiivisia arvoja**.

Tarkoitushan on nimenomaan *hajauttaa* kokonaislukujen arvoja mahdollisimman laajalle arvoalueelle, siten että eri henkilöille tulisi eri tiiviste. Ideaalitilanteessa joka ikisellä henkilöllä, joka ikinä luodaan, olisi uniikki tiivisteen arvo. Tiivistefunktiot eivät kuitenkaan tähän yleensä pysty, koska ne nimensä mukaisesti "tiivistävät" olion identiteetin yhteen 32-bittiseen kokonaislukuun, jolloin väistämättä sattuu törmäyksiä (*collision*) eli kahdelle eri oliolle tuleekin *sama* tiiviste. Tämän olisi kuitenkin syytä olla suhteellisen harvinaista.

Esimerkiksi opettajan eräällä tiivistefunktion toteutuksella (niitä on kokeiltuna *useita*, tee sinä samaa) `PersonHashTests` -testi tulostaa:

```console
...
==> Starting to analyse Person.hashCode with 1 000 000 persons 2025/08/13 15:05:57
  Testing Person.hashCode took 159 ms
  For 1 000 000 persons, there were 116 duplicate hashes
```

Tästä nähdään että kun aineiston koko on miljoona henkilöä, vain 116 henkilölle tuli sama tiiviste. Eli törmäyksiä tuli vain 0,0116% tapauksista.

Testi menee läpi vaikka törmäyksiä tulisi paljon (liikaa). Mutta **sinun tulee itse varmistaa** katsomalla testitulostuksia, että näin ei tapahdu. Jos noita duplikaatteja tiivisteitä tulee prosentteja, tai peräti kymmeniä prosentteja, hajautusfunktiosi on selkeästi *huono*, se tekee myös hajautustaulusta hitaan ja **funktiota pitäisi ehdottomasti parantaa**.

Huono hajautusfunktio johtaa hajautustauluun lisätessä siihen, että törmäyksiä (askel 3.3 yllä) tulee paljon, niiden hoitaminen koodissa kestää, jolloin sekä hajautustauluun olioiden lisääminen että niiden etsiminen hidastuu. Siksi on olennaista toteuttaa hyvä hajautusfunktio.

Huomaa myös että kun hajautustaulu vielä rajoittaa tiivisteen arvon alueelle `0..<array.length`, eli leikkaa tiivisteen arvosta pois negatiiviset arvot ja arvot jotka ovat suurempia kuin taulukon koko, törmäyksiä syntyy vielä enemmän kuin mitä tämä testi ilmaisee. Eli jos jo tässä testissä tulostuu paljon törmäyksiä kun arvoa ei vielä edes rajoiteta taulukon kokoon, niitä syntyy varmasti vielä paljon enemmän hajautustaulussa kun näin tehdään!


## Hajautustaulun testaaminen oman hajautusfunktion kanssa

Testaa vielä suorituskykyä tilanteessa jossa hajautustauluun lisätään asteittain nouseva määrä elementtejä, testillä `PerformanceWithPersonHashTableTests`. Testi tulostaa konsoliin hajautustauluun lisättävien elementtien lukumäärän sekä lisäämisen ja hakemisen keston millisekunneissa. 

Elementteinä tässä testissä on hajautustaulun avaimena (`K`, key) oleva `Person` -olio, ja arvona henkilön "koodarinimi", wu-tang -nimi. Esimerkiksi:

* avaimena henkilö jonka nimi on "Juustila, Antti" ja arvona koodarinimi "Ðisconnected Multiplexer" tai:
* avaimena henkilö jonka nimi on "Lappalainen, Jouni" ja arvona koodarinimi Ïnvariant Semaphore".

Lisäksi testi tulostaa:

* hajautustaulun täyttöasteen. Alla esimerkin ensimmäisen, 10 000 kokoisen aineiston kanssa täyttöaste on 0.61, eli 61%,
* montako kertaa tapahtui törmäys (*collision*) heti ensimmäiseen indeksiin lisätessä, 
* montako kertaa törmäyksiä tapahtui kaiken kaikkiaan (myös luodatessa), ja 
* montako kertaa luodatessa piti pisimmillään etsiä vapaata paikkaa taulukossa.

Opettajan testikoneella tulostuu esim. seuraavaa, malliratkaisun toteutuksella:

```console
--------------------------------
Testing hash table performance with Person as key
Take output to spreadsheet app for time efficiency analysis
--------------------------------
n,Time to add (ms),Time to get (ms),Fill factor,Initial collision count,Total collision count,Max probing steps
10000,12,6,0.61,3054,6154,18
20000,10,4,0.61,6099,12165,17
30000,13,6,0.46,6773,11007,11
40000,15,9,0.61,12299,24780,22
50000,21,4,0.38,9537,14409,14
...
990000,430,197,0.47,232003,387810,15
1000000,458,178,0.48,237025,398532,19
```

Huomaa että **sinun tulee itse toteuttaa** näiden laskureiden ja muiden tietojen hallinta omaan hajautustauluusi, siten että kyseisiä tietoja ylläpidetään oikein. Lue ohjeet koodin kommenteista ja varmista tarvittaessa opettajalta että asiat menevät oikein.

**Vie** tämä csv -data taulukkolaskinsovellukseen ja **analysoi** graafien kanssa aineiston koon kasvun vaikutusta lisäys- ja hakualgoritmien aikatehokkuuteen. **Pohdi** myös täyttöastetta, törmäysten lukumääriä ja luotausketjujen pituuksia. Mitä nämä kertovat toteutuksesi **tehokkuudesta**?

Jos toteutuksesi on **hidas**, mieti olisiko jossain parantamisen varaa. Onko hajautusfunktio `Person.hashCode()` varmasti hyvä tälle aineistolle, ottaen huomioon mistä tiiviste lasketaan? Onko törmäysten käsittely hyvin tehty? Toimisiko quadratic probing paremmin kuin linear probing ja millä vakioilla? Tapahtuuko reallokointi sopivassa paikassa ja kasvatetaanko taulukon kokoa sopivasti?

> Turhan usein eteen tuleva **erittäin huono** ratkaisu hitauteen on nostaa hajautustaulun sisäisen taulukon oletuskoko todella suureksi. Oletuskoon tulisi olla 16-32 tai jotain vastaavaa, pieni numero. Jos nostat koon vaikkapa sataan tuhanteen, vältät kyllä reallokoinnin ja aikatehokkuus parantuu. Mutta tällöin huononnat merkittävästä toteutuksen *muistitehokkuutta*. Hajautustauluja käytetään myös suhteellisen pienten aineistojen kanssa, vaikkapa kymmeniä, satoja tai vain joitakin tuhansia. Jos taulukon kooksi kuitenkin aina luodaan sata tuhatta, suurin osa on siitä tyhjää ja se vie muistia muulta sovellukselta ja tietokoneelta.
>
> Lisäksi koko taulukko ei välttämättä mahdu välimuistiin ja se hidastaa hajautustaulun käyttöä, eli vaikuttaa myös aikatehokkuuteen. Sekään ei ole todellakaan hyvä ratkaisu, että taulukon koko vaikkapa kolmin- tai nelinkertaistetaan aina reallokoidessa. Tämä johtaa helposti liian suuriin taulukoihin joista suurin osa on aina tyhjää, ja siihen, että käytettävissä oleva muisti loppuu kesken, vaikka konservatiivisemmalla taulukon koon kasvattamisella kaikki toimisi ihan hyvin ja muisti riittäisi. Siksi tällaiset huonot ratkaisut ovat kurssilla **hylkäämisen peruste**.

**Tallenna** analyysisi aikatehokkuudesta n:n kasvaessa, ja **vertaile** sitä tehtävän 8 BST:n aikatehokkuuteen samankaltaisessa tehtävässä.

**Kumpi** tietorakenne on **aikatehokkaampi** milläkin aineiston koolla? Onko lisäys- ja hakualgoritmien (add, get) välillä eroa näissä tietorakenteissa?

Mikä on lisäys- ja hakuoperaatioiden aikakompleksisuusluokka, kun arvioit sekä toteutuksiasi että aikamittauksiasi? Mitkä asiat teoreettisen aikakompleksisuusluokan arvion **lisäksi** vaikuttavat näihin *todellisiin* mitattuihin aikoihin?

> Opettajan testikoneella miljoonan aineiston käsittely kesti alle sekunnin. Jos koneellasi jo pienemmätkin aineistot hyydyttävät koneen, voit lopettaa testin kesken ja käsitellä sen datan, jonka sait kerättyä. Varaudu kuitenkin useiden minuuttien odottamiseen.

Jos testit menevät läpi, tietorakenteen toteutuksen pitäisi olla kunnossa, ja voit edetä soveltavaan tehtävään.


## Soveltava tehtävä

Soveltava tehtävä on jo esitelty edeltävässä tehtävässä 8. Tässä siis ohjeistetaan vain ne asiat mitä tässä tehtävässä lisätään jo toimivaan sovellukseen.

**Muista** taas ensin asentaa `shared` -kirjasto, eli VS Codessa Maven > Lifecycle > install tai komentoriviltä `mvn clean install` kirjaston omassa hakemistossa. **Varmista** että testit menivät kaikki läpi, sillä muuten kirjastoa ei muodosteta. Vasta tämän jälkeen uudet asiat ovat käytettissä tässä komponentissa.

## Sovelluksen testaus hajautustaulutoteutuksella
 
**Toteutit** jo `BookBase` -luokkaan tarvittavat lisäykset tehtävässä 8, ja lisäksi BST:tä hyödyntävän toteutuksen unikkien sanojen laskentaoperaatiosta.

Seuraavaksi **toteutat** `BookBase` -luokasta konkreettisen aliluokan `HashTableBookImplementation`, joka toteuttaa `BookBase` -luokasta puuttuvat abstraktit metodit. Toteutuksen **on käytettävä** `shared` -komponenttiin tekemääsi hajautustaulua uniikkien sanojen ja esiintymislukumäärien käsittelyyn.
 
**Lue tarkkaan** Book -rajapinnan sekä `BookBase` -luokan metodien kuvaukset lähdekoodissa olevista kommenteista, ja noudata myös näissä olevia ohjeita toteutuksessasi.

`CorrectnessTestsHashTable` -testi käyttää näitä kirja- ja ignore-tiedostoja. Näiden tiedostojen sanamäärät ja sanat on laskettu etukäteen, ja testit vertailevatkin oman toteutuksesi löytämiä uniikkeja sanoja/sanamääriä näihin tunnettuihin sanoihin/sanamääriin ja sijainteihin top100 -listalla. 

Jos testien mielestä toteutuksesi antamat tulokset eroavat (liikaa tai vähänkään, riippuen testistä), testi ilmoittaa että se on epäonnistunut. Jos vastaava BST-toteutus toimi oikeellisesti, silloin tässä hajautustaulua hyödyntävässä toteutuksessa on jotain vikaa. Etsi silloin ongelmia luokista `HashTableBookImplementation` sekä `HashTableDictionary` (`shared` -kirjastossa).

Mieti missä vika on, ja korjaa se. Pyydä tarvittaessa opettajilta apua.

## Suorituskyky- ja vertailutesti

`PerformanceTests` -testit suorittavat toteutuksesi testejä kaikkien kirjatiedostojen kanssa ja mittaavat suoritusaikaa kirjan koon suhteen. Lisäksi testi varmistaa että sanojen kokonaislukumäärä ja unikkien sanojen lukumäärä ovat samat sekä BST että hajautustaulu -toteutuksissa.

Nämä mitatut suoritusajat muine tietoineen tallennetaan tiedostoon `compare.csv`. Tässä tiedostossa löytyy pilkuilla eroteltuina erilaisia mittaustietoja, esim:

```
Book,Bytes,Words (n),Unique words (m),Time BST (ms),Time Hashtable (ms),log2(n),log2(n) + m log2(m)
word.txt,7,1,1,10,2,0,0
tiny.txt,238,38,29,6,7,5,121
sapmirussian.txt,5004,455,347,17,16,8,2784
...
```

Kaikki taulukkolaskentasovellukset osaavat avata tai tuoda tällaisen tiedoston jonka jälkeen voit taulukkolaskinsovelluksessa (Excel, Numbers, Sheet, jne.) analysoida tuloksia.

Aikamittaukset tässä mittaavat aina yhden tiedoston käsittelyä, eli lisääminen ja top-listan muodostaminen (lajittelu ja tulostus mukaan lukien).

> Kaksi viimeistä saraketta sisältää aineiston koosta n lasketut tiedot, log2 n ja log2(n) + m log2(m). Näitä ei suoraan voi verrata mihinkään, mutta niiden *muutostrendi* aineiston koon kasvussa voi olla mielenkiintoista seurata BST:n aikamittausten kanssa. BST:n lisäämisen pitäisi olla O(log2 n) ja tulosten käsittelyn pitäisi olla teoriassa log2(n) + m log2(m), jossa log2(m) on puuhun lisääminen ja m log2 m on aineiston lajittelu nopealla lajittelualgoritmilla. 

Kun suoritat näitä suorituskykytestejä, tee se mieluummin komentoriviltä ja sammuta kaikki mahdolliset muut sovellukset ennen testien suorittamista:

```console
mvn -Dtest=PerformanceTests test
```

Näin minkään IDEn (VS Code, Eclipse), pelien, selaimen tms. jne. puuhastelut siinä samalla eivät hidasta testiajoja suorittamalla omaa koodiaan, käyttämällä tietokoneen muistia (RAM) sekä välimuisteja (*cache*) -- ne kaikki vievät suoritusaikaa testeiltä hidastaen niitä.

**Analysoi** taulukkolaskinsovelluksella sitä, miten aineiston koon kasvaessa suoritusaika kasvaa. Onko kasvu lineaarista, logaritmista, linearitmista, neliöllistä, kuutiollista, exponentiaalista vai peräti faktoriaalista? Analysoi myös käyttämiäsi algoritmeja ja niiden aikakompleksisuutta ja vertaa analyysisi tuloksia taulukkolaskinohjelman piirtämiin graafeihin. Mitä ne kertovat toisistaan?

Perustuen molempien tietorakenteiden aikatehokkuustesteihin `shared` -komponentissa, sekä tässä suorittamiisi testeihin, mitä voit sanoa toteutuksiesi tehokkuudesta?

Kumpaa käyttäisit oletusarvoisesti? Vaikuttaako aineiston koko valintaasi? Vastaavatko omat mittaustulokset opettajan malliratkaisun tuloksia, joita on demonstroitu kurssin liveluennoilla?

Jos merkittäviä eroja on, voisiko olla että tietorakenteesi kaipaa aikatehokkuuden parantamista?

## Tarkistukset

Analysoi erityisesti sitä, onko hajautustaulun toteutuksesi oikeasti sellainen että se vastaa tehtävän alussa esiteltyjä aikakompleksisuusvaatimuksia. Jos ei vastaa, korjaa toteutusta.

> Mikä tahansa metodi jossa on *silmukka*, joka käy läpi koko aineiston ei *voi* olla O(1) -- onko sinulla silmukoita metodeissa joissa vaatimus oli O(1)? Tai kutsutko tällaisesta metodista jotain *toista* metodia jonka O on muuta kuin O(1)? Jos näin on, aikakompleksisuusvaatimus ei täyty.
>
> Jos sinulla on metodissa silmukka, mutta se kuinka monta kertaa se pyörähtää, ei riipu aineiston koosta, ei ole silloin O(n) vaan jotain muuta.

**Varmista** ettet ole käyttänyt toteutuksessa kurssilla kiellettyjä ratkaisuja. Tähän on työkalu jolla voit tarkistaa yleisimmät virheet tässä asiassa. Lue sen ohjeet `shared` -komponentin readme:stä.

## Lopuksi

Kun olet valmis, varmista että kaikki uusi ja muutettu koodi on paikallisessa git -repositoryssäsi ja myös etärepositoryssäsi (komennot `git commit`, tarvittaessa uusille tiedostoille `git add` sekä `git push`).

## Tietoja

* Kurssimateriaalia Tietorakenteet ja algoritmit -kurssille | Data structures and algorithms 2021-2025.
* Tietojenkäsittelytieteet, Tieto- ja sähkötekniikan tiedekunta, Oulun yliopisto.
* (c) Antti Juustila 2021-2025, INTERACT Research Group.