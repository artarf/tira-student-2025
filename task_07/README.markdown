# Linkitetty lista

## Aluksi

**HUOM** 99.99%:lle meistä, linkitetty lista -tietorakenteen toteuttaminen *edellyttää* kynän ja paperin käyttöä jotta osaisimme suunnitella ja toteuttaessa pähkäillä, miten linkitetyn listan eri algoritmit toimivat. Nämä kannattaa siis toteuttaa tämä siten että *jatkuvasti* olet pähkäillyt kynän ja paperin kanssa miten algoritmi toimii ja vasta sitten etenet ohjelmoimaan. Ja toteuttaessa jatkaa pähkäilyä. Ilman paperin ja kynän käyttöä on melko todennäköistä että toteutuksestasi tulee aivan liian monimutkainen ja buginen. Tätä voi olla vaikea uskoa, mutta näin se vaan on.

![Opettaja hahmottelee reverse() algoritmia kynällä ja paperilla](doodling-reverse-algorithm.png)

Jos et ole katsonut aiheen luentomateriaaleja ja -tallennetta, tee se ehdottomasti ensin. Tehtävän tekemisen vaikeudet johtuvat aivan liian usein siitä, että linkitettyä listaa ei ensin oteta haltuun periaatetasolla, vaan rynnätään koodaamaan. Sitten asiat menevätkin aika nopeasti metsään.

Jos et ole jo opetellut **käyttämään debuggeria** ongelmien diagnosointiin ja ratkomiseen, nyt on myös sille hyvä aika. Jos toteutukseen tulee bugeja, usein sen näkee erittäin hyvin debuggaamalla oman algoritmin toimintaa.

## Yleistä tämän tietorakenteen toteuttamisesta

Tässä ohjelmointitehtävässä toteutat *kahteen suuntaan* linkitetyn lista -tietorakenteen (*doubly-linked list*) ja sovellat sitä toteuttamalla primitiivisen **matopelin**. 

Linkitetyn listan toteutus tehdään `shared` -komponenttiin, annetun rajapinnan pohjalta ja testataan toimivaksi. Tietorakenteen toteutus vaatii noin 150 riviä koodia, riippuen koodaustyylistäsi. 

Tehtävän soveltavassa osuudessa täydennät annettua koodia, tämä vaatii noin 10 koodirivin lisäämistä. Kun `shared` -komponenttiin toteutettu tietorakenne toimii, sen testit menevät läpi, muista käyttää Maven > Install -komentoa `shared` projektissa, jolloin toteutus on saatavilla myös tässä komponentissa, soveltavaan tehtävään.

Linkitetyssä listassa sisäisenä tietorakenteena **ei käytetä** taulukkoa, eli mitään reallokointeja ei tässä tarvitse tehdä, eikä linkitetyllä listalla ole mitään kapasiteetin käsitettäkään. Varmista kurssin luentomateriaaleista ja kirjallisuudesta, että ymmärrät miten linkitetty lista toimii.

Toteutuksessa **vaaditaan** algoritmeille seuraavat aikakompleksisuusluokat:

* `addFirst(E)`: O(1)
* `addLast(E)`: O(1)
* `add(int, E)`: O(n)
* `getFirst()`: O(1)
* `getLast`: O(1)
* `get(int)`: O(n)
* `indexOf(E)`: O(n)
* `removeFirst()`: O(1)
* `removeLast()`: O(1)
* `remove(E)`: O(n)
* `remove(int)`: O(n)
* `size()`: O(1)
* `clear()`: O(1)
* `reverse()`: O(n) -- muistikompleksisuuden on oltava O(1), eli operaatio ei käytä tilapäisiä taulukoita tms. kääntämiseen.
* `reversedTo(LinkedListInterface<E>)`: O(n)
* `toString()`: O(n) - tämä ylikuormitetaan Object-luokasta perittynä, kuten pinotehtävässäkin.

Mikäli koodi ei vastaa kyseistä aikakompleksisuusluokkaa, toteutus on korjattava vastaamaan näitä, ennen kuin tehtävän deadline tulee vastaan. Sen jälkeen suoritus on hylätty jos aikakompleksisuusluokkavaatimukset eivät täyty.


## Kommentti linkitetyn listan metodien läpikäyntien aikatehokkuudesta

**Huomaa** että joistakin linkitetyn listan metodeista *voitaisiin* kutsua toisia, ja näin *voitaisiinÄ sekä yksinkertaistaa toteutusta että hyödyntää jo tehtyä koodia eli uudelleenkäyttää metodeja toisista metodeista. Esimerkiksi elementin poistaminen metodissa `remove(E)` voidaan toteuttaa siten, että:

1. ensin haetaan elementin indeksi toteuttamallasi metodilla `indexOf(E)` ja sitten 
2. poistetaan se toteuttamallasi toisella metodilla `remove(int)`.

Näin vältytään toteuttamasta samaa linkitetyn listan läpikäyntiä silmukassa myös `remove(E)` -metodissa koska se on jo tehty metodissa `remove(int)`. Koska `indexOf(E)` on O(n) ja `remove(int)` on myös O(n), tällöin myös näitä kutsuvan `remove(E)` metodin aikakompleksisuusluokka on se sama O(n). 

**Tällä tavalla toteutettuna `remove(E)` käy kuitenkin listan läpi samaan kohtaan aina kahdesti!** Eli:

1. ensin listaa hypitään alusta lähtien, solmusta toiseen etsien elementtiä ja palauttaen sen indeksi, ja
2. sen jälkeen listaa edetään alusta lähtien, tietty määrä eteenpäin, kyseiseen indeksiin ja poistetaan elementti siitä indeksistä.

Jos lista on todella pitkä ja/tai tätä operaatiota toistetaan samalle lyhyellekin listalle usein, voidaan nähdä aikatehokkuusmittauksistakin että `remove(E)` on aina hitaampi kuin `remove(int)`.

Vaikka aikakompleksisuusluokka onkin aina O(n), tuolla tavalla `remove(E)` toteutettuna se on **kaksi kertaa hitaampi** kuin että `removeI(E)` sisältäisi oman silmukkansa joka käy listan läpi **vain kerran**.

Tämä on yksi esimerkki siitä, että aikakompleksisuusluokka on teoreettinen käsite algoritmin monimutkaisuudesta nimenomaan aineiston koon (n) suhteen. Mutta kuten tästä nähdään, algoritmi voidaan toteuttaa siten että sen O on O(n), mutta se voi silti olla saman aineiston käsittelyssä kaksi kertaa hitaampi kuin toinen O(n) -algoritmi. Se on itse asiassa O(2n), mutta vakiothan tiputetaan aina pois --- vaikka ne kuvaavatkin todellisuutta, algoritmi O(2n) on kaksi kertaa hitaampi kuin O(n) -algoritmi.

Koska kurssin teema on aikatehokkuus, **en suosittele** yllä kuvatunlaista koodin uudelleenkäyttöä, vaan **vahvasti suosittelen** toteuttamaan linkitetyn listan algoritmit siten, että jokaisessa O(n) -metodissa tehdään oma silmukkansa, jossa aineistoa käydään läpi vain kerran, ei kahdesti (saatikka vielä useampaakin kertaa).

## Linkitetyn listan toteutuksesta

**Toteuta** rajapintaluokan mukainen kahteen suuntaan linkitetty lista `shared` -komponenttiin. **Lue** huolellisesti annetun rajapinnan `LinkedListInterface<E>` dokumentaatio kommenteista.

**Muista** myös toteuttaa `ListFactory` -luokkaan metodeihin toteutuksesi instantiointi. Testit ja soveltava tehtävä saavat toteutuksesi linkitetystä listasta tämän tehdasluokan avulla, joten jos sen metodit ovat toteuttamatta, testit eivät mene läpi eikä soveltava tehtävä toimi.

Linkitetty lista -luokassa kannattaa tehdä oma solmu (`Node<T>`) apuluokka, ns. inner -luokkana. Solmu sisältää:

1. Tietoelementin (ns. satelliittidataa), geneerinen tyyppi `T element`.
2. linkin edelliseen solmuun (`Node<T> previous`), joka on null jos edellistä solmua ei ole.
3. linkin seuraavaan solmuun (`Node<T> next`), joka on null jos seuraavaa solmua ei ole.

Linkitetty lista -luokka sisältää kolme jäsenmuuttujaa:

1. Linkitetyn listan solmujen lukumäärän (linkitetyn listan koko)
2. Linkitetyn listan ensimmäisen solmun (`first`), joka on tyhjälle listalle null.
3. Linkitetyn listan viimeisen solmun (`last`), joka on tyhjälle listalle null.

Mieti toteutuksessa erityisesti seuraavia asioita ja tilanteita joissa usein tulee ongelmia:

* Varmista että linkitetyn listan solmujen `next` ja `previous` -linkit edelliseen ja seuraavaan solmuun ovat aina oikeelliset.
* Listan ensimmäisen solmun `previous` on aina null.
* Listan viimeisen solmun `next` on aina null.
* Kun listalla on vain yksi solmu, sen `previous` ja `next` ovat null, ja sekä `first` että `last` osoittavat tähän samaan solmuun.
* Kun lisäät ja poistat elementtejä, muista aina myös päivittää listan koko -jäsenmuuttujan arvo.

Poistaminen, tilanteita joissa usein tulee tehtyä bugeja:

* Jos poistamisalgoritmi poistaa solmun joka on listan ensimmäinen, tietorakenteen `first` -solmu pitää laittaa osoittamaan ensimmäisestä *seuraavaan* elementtiin, josta tulee siis uusi ensimmäinen solmu.
* Jos poistamisalgoritmi poistaa elementin joka on listan viimeinen, toiseksi viimeisen elementin `next` pitää asettaa null:ksi ja tietorakenteen `last` -solmu pitää asettaa viittaamaan tähän toiseksi viimeiseen elementtiin. Toiseksi viimeisestä tulee siis listan uusi viimeinen solmu.
* Kun listalla on kaksi solmua joista toinen poistetaan, pitää huolehtia siitä että koodi toimii siten että `first` ja `last` osoittavat tähän samaan viimeiseen elementtiin listalla, ja sen `previous` ja `next` ovat myös varmasti null.
* Jos poistamisalgoritmi poistaa solmun joka on listan ainoa solmu, `first` ja `last` pitää asettaa null:ksi.
* Kun poistetaan solmu listan keskeltä, poistettavaa edeltävän solmun `next` pitää laittaa osoittamaan poistettavan `next` -solmuun, ja poistettavan jälkeen tulevan solmun `previous` pitää asettaa osoittamaan poistettavaa edeltävään solmuun (poistettavan `previous`).

Summa summarum: kutakin linkitetyn listan algoritmia toteuttaessasi mieti aina että toimiihan tämä aina silloinkin kun

1. lista on tyhjä,
2. listalla on kaksi elementtiä,
3. lisätään tai poistetaan listan alkuun,
4. lisätään tai poistetaan listan loppuun.
5. lisätään tai poistetaan listan keskeltä, kun molemmilla puolilla on solmu.

Toteuta myös `toString()` samalla tavalla kuin pinotehtävässä. Linkitetyn listan toteutuksen tulee palauttaa listan elementit järjestyksessä. Jos listalle on sijoitettu elementit näin...:

```Java
	listToTest.addFirst("3");
	listToTest.addFirst("2");
	listToTest.addFirst("1");
	System.out.println(listToTest.toString());
```
...koodi tulostaa: `[1, 2, 3]`.

Jos kohtaat ongelmia, **pyydä apua**. Tietorakenne on suhteellisen vaikea, joten on ymmärrettävää että sen kanssa voi mennä aivot solmuun. Siinä tapauksessa jos et löydä ongelman syytä testien ja debuggaamisen avulla:

* Katso luennot ja demoluento(tallenne) aiheesta.
* Hae apua ohjaussessioista.
* Käytä UKK Tikettisysteemiä, katso onko siellä UKK:ta ja kysy apua.
* Myös sähköposti toimii.

Kysyvä ei tieltä eksy.


## Testaus ja parannukset aikatehokkuuteen

Kun suoritat `shared` komponentin testejä, testi `ReverseListTests.reversedListTest()` vertailee Javan `ArrayList` ja `LinkedList` -luokkien aikasuoritustehokkuutta oman linkitetyn listasi aikatehokkuuteen.

Kun suoritat kyseisen testin, konsoliin tulostuu aikatehokkuusmittauksia. **Vertaile** oman tietorakenteesi tehokkuutta Javan molempien tietorakenteiden aikatehokkuuksiin. Suorita testi **monta kertaa**, sillä aineisto on suhteellisen pieni ja satunnaiset tekijät aiheuttavat vaihtelua suoritusaikoihin. Pyri näkemään useamman suorituskerran jälkeen säännönmukaisia eroja toteutusten välillä.

**Pohdi** mistä mikäkin merkittävämpi ero aikatehokkuuksista voisi johtua?

Voit nähdä esimerkiksi seuraavanlaisia mittauksia kun vertaillaan indeksillä hakemisen aikoja (metodi `get(int index)`):

```console
...
Getting grape Brajda Mala using get(int) method -- at index 89500/107400...
Java ArrayList: getting grape took	      3875 ns.
Java LinkedList: getting grape took	       750 ns.
Your linked list: getting grape took	    213375 ns.
...
```

Tästä esimerkistä voidaan nähdä, että Javan sekä taulukko että linkitetty lista ovat **merkittävästi** nopeampia kuin opettajien oma linkitetty lista kun haetaan elementtejä `get(int index)` -metodilla. Opettajan linkitetty lista on n. 285 kertaa hitaampi kuin Javan.

Huomaa, että tässä haetaan linkitetyn listan loppupäässä olevaa elementtiä, tuossahan lukee "...at index 89500/107400". Jos omissa mittauksissasi omasta toteutuksestasi näet samankaltaisia eroja, **mieti** mistä ne johtuvat...

Taulukko (Javan `ArrayList`) on varmastikin nopea ainakin siksi, että indeksillä voidaan viitata suoraan tiettyyn muistiosoitteeseen: `array[index]` ja lukea arvo sieltä. Jos RAM -muistissa oleva taulukko on kovin iso, se ei mahdu kokonaan tietokoneen välimuistiin (cache). Tai jos taulukko on jouduttu pyyhkimään pois välimuistista muuta dataa varten, ja se on vain RAM:ssa.

Silloin voi olla että jos taulukon loppupää jossa haluttu data on, ei ole välimuistissa, se joudutaan hakemaan RAM:sta välimuistiin, ja siksi testeissä voi näkyä että taulukko olisi hitaampi kuin Javan linkitetty lista (`LinkedList`) *tässä tietyssä operaatiossa* tässä tietyssä suorituskontekstissa.

Linkitettyjen listojen indekseihin ei voi samalla tavalla "suoraan" osoittaa kuin taulukoiden indekseihin. Sen sijaan linkitettyjen listojen kanssa pitää hyppiä ensimmäisestä solmusta solmujen `next` -viittauksilla eteenpäin kunnes ollaan hypitty indeksin osoittama määrä linkkejä eteenpäin. Tämä hyppely vie aikaa, ja siksi yleensä linkitettyä listaa ei suurten tietomäärien käsittelyssä käytetä. Poikkeuksena tilanteet, joissa listaa luetaan ja kirjoitetaan *vain* alku- ja/tai loppupäistä, ei keskeltä (kuten `get(int index)` tekee). Koska alku- ja loppupäiden luku ja kirjoitus on *aina* O(1) operaatio.

Mutta **miksi** sitten Javan linkitetty lista on paljon nopeampi `get(int)` -metodin kanssa, kuin tässä oma linkitetyn listan totetus? **Senkin pitää hyppiä niitä solmujen `next` -viittauksia eteenpäin** ja laskea ollaanko nyt haetussa indeksissä. Ihan samalla tavalla on tehty tämä opettajienkin malliratkaisu. Miksi se Javan linkitetty lista on muka sitten noin nopea? Vieläpä voi olla nopeampikin kuin Javan `ArrayList`?!

Olet ehkä toteuttanut oman `get(int index)` metodisi siten että algoritmi aloittaa aina alusta ja etenee listaa `next` -viittauksilla solmuja kohti listan loppupäätä. Kuten yllä oleva opettajan toteutuskin tekee.

**Muuta** `get(int index)` -metodin toteutusta siten, että jos haettu indeksi onkin linkitetyn listan **puolenvälin jälkeen**, lähdetäänkin linkitetyn listan **lopusta alkuunpäin**, edeten listaa lopusta alkuun päin, laskien milloin ollaan haetun indeksin kohdalla. Toteutuksenhan tuli olla kahteen suuntaan linkitetty lista, joten voit ihan hyvin edetä joko alusta loppuun tai lopusta alkuun.

Tämä muutos nopeuttaa algoritmia pitkien listojen kanssa *merkittävästi*. Sen sijaan että hypitään tällä testiaineistolla alusta 89 500 kertaa eteenpäin, kunnes ollaan halutussa indeksissä, hypitäänkin 107 400 - 89 500 = 17 900 askelta lopusta alkuun. Tämä on 20% alkuperäisen operaation askeleista. Merkittävä vähennys suoritettaviin operaatioihin algoritmissa!

Kun korjataan opettajan linkitetyn listan `get(int index)` -metodin toteutuksen malliratkaisu toimimaan tällä periaatteella:

```console
...
Getting grape Brajda Mala using get(int) method -- at index 89500/107400...
Java ArrayList: getting grape took            3917 ns.
Java LinkedList: getting grape took            708 ns.
Your linked list: getting grape took           834 ns.
...
```

Nyt nähdään että suhteellinen ero Javan linkitetyn listan ja opettajan linkitetyn listan välillä on huomattavasti pienempi. Opettajan toteutus on tällä suorituskerralla vain hieman hitaampi Javan linkitettyä listaa, tuo pieni ero nanosekunneissa voi olla satunnaistakin vaihtelua. Eli Javan linkitetty lista toimi nopeasti juuri siksi että se teki kuten yllä kerrottiin. Jos haettava indeksi on puolivälin jälkeen, lähdetään lopusta, muuten alusta.

Kannattaa siis ainakin indeksipohjaisissa algoritmeissa lähteä ehdottomasti liikkeelle siitä päästä linkitettyä listaa, joka on lähempänä haluttua indeksiä.

### Aikakompleksisuusluokkapohdintaa

Tämä on jälleen kerran hyvä esimerkki siitä että aikakompleksisuusluokkien teorian ja käytännön välillä *voi* olla merkittävä mitattava ero. 

Alkuperäinen `get(int index)` joka lähtee aina linkitetyn listan alusta, on O(n) -operaatio. Samoin se operaatio joka lähtee alusta tai lopusta, optimoiden hakunopeutta, on sekin O(n). Toinen on kuitenkin ensimmäistä merkittävästi nopeampi operaatio - käytännössä.

Kun vielä otetaan huomioon luennoillakin mainittu fakta, että aikakompleksisuusluokka-analyysi olettaa teoreettisen tietokoneen, ja todellisissa koneissa esimerkiksi RAM ja välimuistien arkkitehtuuri myös vaikuttaa aikatehokkuuteen, voidaan näillä faktoilla arvioida sitäkin miksi linkitetyn listan `get(int)` -- joka on O(n) -- voi tietyissä tilanteissa olla nopeampi kuin Javan taulukkopohjaisen `ArrayList`:n `get(int)` joka on O(1) -operaatio.

Aikakompleksisuusluokat eivät siltikään ole turha asia ymmärtää. Pitää vain osata katsoa myös niiden taakse, ja miettiä voiko algoritmin toimintanopeutta parantaa myös aikakompleksisuusluokan "sisällä", huomioiden se mikä on järkevää tai turhaa, ja tehdä fiksuja ratkaisuja. Samoin on syytä ymmärtää miten todelliset tietokoneet vaikuttavat aikatehokkuuteen, varsinkin RAM/cache. On useita tapoja parantaa tiedon prosessointinopeutta hyödyntäen tietoa siitä, miten välimuistit toimivat, esimerkiksi.

## Testien nopeutus jatkoa ajatellen

Kun olet saanut yllä olevat asiat testattua toimiviksi, **voit vähentää testidatan määrää** testissä `ReverseListTests` , sen metodissa `fillWithTestData()`. Siellä on `for` -silmukka joka generoi testidataa:

```Java
      for (int count = 0; count < 100; count++) {
```
**Muuta** tuo `100` vaikka arvoksi `10`, jolloin testi nopeutuu selkeästi. Näin sinun ei tarvitse jatkossa odottaa niin kauaa kun testit päättyvät ja `shared` -komponentin kirjasto luodaan.

## Soveltava tehtävä -- Matopeli

Kun olet saanut `shared` -komponentin testit menemään läpi, muista tehdä Maven > Lifecycle > install (tai komentoriviltä `mvn clean install`). Tämän jälkeen linkitetyn listan toteutuksesi on käytettävissä myös tässä soveltavassa tehtävässä.

Tässä soveltavassa tehtävässä viimeistelet **matopelin**, jossa pelin mato eli käärme (*snake*) on linkitetty lista -tietorakenne. Kutsutaan matoa tästä eteenpäin käärmeeksi.

Pelin idea on varmaan tuttu, mutta tässä tiivistelmä siitä miten peli tässä tehtävässä toimii:

1. Kun olet toteuttanut puuttuvan koodin alla olevien ohjeiden mukaisesti, ja käynnistät ohjelman (`WormGameApp.main`), tulee näkyviin ikkuna.
2. Pelissä näkyy käärme (vihreä pikseli), ja omena (ruokaa, punainen pikseli).
3. Paina välilyöntinäppäintä kerran, ja peli käynnistyy.
4. Pelin käynnistyttyä käärme alkaa liikkumaan ylöspäin.
5. Ohjaa käärmettä nuolinäppäimillä (vasen, oikea, ylös, alas). Voit myös käyttää näppäimiä a, d, w, s.
6. Jos käärmeen pää kohtaa omenan, se syö sen ja käärmeen pituus kasvaa yhdellä pikselillä.
7. Välilyöntinäppäimen painallus pistää pelin tauolle.

Muuta ei tapahdu, eli sovellus ei laske pisteitä, käärme voi hävitä ikkunan ulkopuolelle, käärmeen törmääminen itsensä kanssa ei tee mitään, jne.

**Koodista puuttuu** palasia joiden takia peli ei toimi. **Täydennä** puuttuva koodi, joka liitty linkitetty lista -tietorakenteen toimintaan pelissä.

Ohjeet tähän löydät luokan `WormGamePanel` koodista. Muuta koodia mitä sovelluksessa ja tuossa luokassa on, liittyen grafiikkaohjelmointiin tai timereiden toimintaan, ei tarvitse ymmärtää tai muuttaa millään tavalla.

Luokassa on valmiina linkitetty lista -jäsenmuuttuja:

```Java
   private LinkedListInterface<Point> snake;
```

Johon täydentämäsi koodi liittyy. Muutokset, jotka teet on tiivistettynä alla. Samat asiat neuvotaan koodin kommenteissa.

### initializeSnake()

Metodissa **luot** `ListFactory`:n avulla linkitetyn listan jossa Javan AWT:n `Point` -olioita -- koordinaattipisteitä joissa käärme sijaitsee. Käärme mallinnetaan siis listana pisteitä joissa se luikertelee. Samalla **lisäät** käärmeelle pään linkitetyn listan kärkeen. Tässä vaiheessa käärme on siis vain yksi pikseli näytöllä.

### paintComponent()

Metodi piirtää madon pisteet (`Point`) näytölle. **Hae** linkitetyltä listalta `for` -silmukassa pisteet yksi kerrallaan piirrettäväksi, käyttäen linkitetyn listan metodia `get(int index)`. Piirroskoodi on jo valmiina, sitä ei tarvitse osata tehdä.

### tick()

Metodia kutsutaan timerista (ajoitin; valmiiksi tehtyä koodia) aina joka 240. millisekunti. Metodissa:

  * **haet** madon *nykyisen* pään (listan *ensimmäinen* elementti) pisteen. Sitä tarvitaan alempana koodissa. 
  * Valmis koodi luo uuden käärmeen pään sijainnin koordinaattipisteen valmiiksi. **Lisää** uusi käärmeen pään sijainti linkitetyn listan *kärkeen*. Käärmeen pituus kasvoi siis yhdellä.

Jos *nykyisen* pään sijainti on sama kuin omenan sijainti, käärme saa syötävää. Koodi lisää sitten uuden omenan peliin satunnaiseen koordinaattipisteeseen. Jos taas *nykyisen* pään ja omenan sijannit olivat erit, käärme *ei saanut* syötävää ja sen koko ei pidä kasvaa -- tällöin poista käärmeen (linkitetyn listan) *viimeinen* elementti. Koska käärme sai uuden pään (se liikkui eteenpäin) ja sen pituus kasvoi, ruokaa ilman jääneen käärmeen häntä täytyy poistaa jotta sen pituus pysyisi samana.


## Tarkistukset

Analysoi erityisesti sitä, onko linkitetyn listan toteutuksesi oikeasti sellainen että se vastaa tehtävän alussa esiteltyjä aikakompleksisuusvaatimuksia. Jos ei vastaa, korjaa toteutusta.

> Mikä tahansa metodi jossa on *silmukka*, ei *voi* olla O(1) -- onko sinulla silmukoita metodeissa joissa vaatimus oli O(1)? Tai kutsutko tällaisesta metodista jotain *toista* metodia jonka O on muuta kuin O(1)? Jos näin on, aikakompleksisuusvaatimus ei täyty.


## Lopuksi

Kun olet valmis, varmista että kaikki uusi ja muutettu koodi on paikallisessa git -repositoryssäsi ja myös etärepositoryssäsi (komennot `git commit`, tarvittaessa uusille tiedostoille `git add` sekä `git push`).

## Tietoja

* Kurssimateriaalia Tietorakenteet ja algoritmit -kurssille | Data structures and algorithms 2021-2025.
* Tietojenkäsittelytieteet, Tieto- ja sähkötekniikan tiedekunta, Oulun yliopisto.
* (c) Antti Juustila 2021-2025, INTERACT Research Group.