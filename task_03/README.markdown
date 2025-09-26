# Osittaminen

**HUOMIOI kurssin deadlinet**. Tämä tehtävä kuuluu kurssin ensimmäiseen deadlineen, ja palautuksesta myöhästymisellä on seurauksia. Lisätietoja kurssin aikataulun ohjeista löydät muualta kurssin materiaaleista.

Tehtävässä käsitellään myös **aikakompleksisuusluokkia**. Varmista että olet katsonut tai katsot samalla luennot ja luentomateriaalin, jossa käsitellään aikakompleksisuuksia ja niiden analysointia.
  
---

Aluksi **algoritmin suunnittelutehtävä**, johon ei kannata käyttää aikaa enempää kuin noin tunti.

**Suunnittele** algoritmi (paperilla, vapaamuotoisella kuvaustavalla, koodilla) siten että:

* algoritmi poistaa **mahdollisimman vähillä** operaatioilla alla olevasta esimerkkitaulukosta kaikki konsonantit, siten että vokaalit sijoittuvat kaikki taulukon alkupäähän ja konsonanttien tilalle sijoitetaan `null`,
* operaatioiksi lasketaan vain sijoitusoperaatiot (`=`), joita tarvitaan elementtien siirtelyyn taulukossa ja/tai apumuuttujan kanssa,
* muita taulukoita tai muita aputietorakenteita **ei saa käyttää**, vaan algoritmi tulee ratkaista vain tätä yhtä taulukkoa ja korkeintaan *yhtä* apumuuttujaa, jonka tietotyyppi on merkki (esim. Javan `char` tai `Character`), apuna käyttäen (ns. "in-place" -algoritmi), sekä
* voit käyttää vain peräkkäisyyttä, ehto- ja toistorakenteita (`if`, `for`) etkä mitään muita valmiita algoritmeja.

Esimerkkitaulukon sisältö lähtötilanteessa (kirjaimet ylärivillä ja taulukon indeksit alarivillä)...:

  a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p  q  r  s  t  u  v  w  x  y  z  å  ä  ö
  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28

... ja kun vokaalit on siirretty taulukon alkuun:

  a  e  i  o  u  y  å  ä  ö  j  k  l  m  n  d  p  q  r  s  t  b  v  w  x  f  z  g  h  c
  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
  
...ja koko algoritmin suorituksen jälkeen kun konsonantit on poistettu, taulukon tulee näyttää tältä...:

  a  e  i  o  u  y  å  ä  ö  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _
  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
  
...jossa loppupään `null`:t on osoitettu alaviivoilla. Huomaa että algoritmi on puolivakaa (*stable*), eli vokaalien järjestys lopputuloksessa on sama kuin lähtötilanteessa. Loppupään elementtien vakaudella ei ole väliä, sillä ne joka tapauksessa poistetaan joukosta.

**Kuinka monella sijoitusoperaatiolla** sait algoritmisi toimimaan? 

> Yksinkertainen naiivi ratkaisu vaatii yhteensä n. 990 sijoitusoperaatiota. Vähän parempi ratkaisu vaatii n. 630 sijoitusoperaatiota. Jos saat aikaiseksi algoritmin joka vaatii vain noin 45 sijoitusoperaatiota, se on tämän tehtävän kannalta *optimaalinen ratkaisu*.

Jos sijoitusoperaatioiden määrä algoritmissasi oli suuri, tässä tehtävässä opit tekemään algoritmin, joka tekee vaaditun työn optimaalisemmin.

## Oppimistavoite

Harjoituksen oppimistavoitteena on tietojoukon (tässä tapauksessa taulukko) **osittaminen eli partitiointi** predikaatin avulla.

Partitiointia käytetään tietojoukon jakamiseen kahteen osajoukkoon. Osittamista voidaan käyttää eri tarkoituksiin, esimerkiksi eri lajittelualgoritmeissa, mutta se on myös itsessään usein kätevä algoritmi olla olemassa.

Harjoitus sisältää vaiheittaisen selityksen harjoituksen oppimasta. Itse **ohjelmoinnin osuus** on tässä suhteellisen pieni. Osittamisalgoritmin, jonka toteutat, pituus on noin **20 riviä koodia**, esiehtotarkistuksineen. Lisäksi käytät tekemääsi osittamisalgoritmia *filtteröintiin* eli suodattamaan tietojoukosta vain halutut elementit omaan uuteen taulukkoonsa. Tämän algoritmin toteutus onnistuu **kolmella rivillä koodia**.

> Huomaa, ettei sinun tarvitse ymmärtää miten tämän harjoituksen Java Swingillä toteutettu käyttöliittymä toimii. Keskity vaan harjoituksen aiheeseen. Paikka johon sinun tulee toteuttaa koodia on osoitettu alla ohjeissa. 
> 
> Huomaa myös että tehtävän koodi sisältää kolme vaihtoehtoista, huonoa poistamisalgoritmia sekä varsinaisen toteutettavan poistamisalgoritmin, joita et toteuta. Tutustut vain näiden toimintaan.

## Lähteet

* Luentomateriaali ja kirjallisuus
* Javan [`Predicate` -rajapinta](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/util/function/Predicate.html)
* [Predikaatti yleisemmin](https://en.wikipedia.org/wiki/Predicate_(logic))
* [Embrace Algorithms](https://web.archive.org/web/20180815051704/https://developer.apple.com/videos/play/wwdc2018/223/) (video aiheesta Apple WWDC2018; Internet Archive)

## Demo

Tehtävässä on askeleittain etenevä demo joka motivoi tekemään aikatehokkaan algoritmin. Demo käydään läpi myös luennolla / videolla.

### Yhden olion poistaminen

Käynnistä sovellus `ShapesApp` -luokan main metodista käsin. Sen jälkeen:

1. Suorita valikosta komento **Create 18 shapes**. Huomaa miten taulukon luonti animoidaan. **Odota** animaation loppumista (kun taulukon indeksi 17 on täytetty).
2. Suorita valikosta komento **Remove 1 from middle**. Taulukosta poistetaan keskeltä yksi elementti. Odota taas animaation loppumista.

Huomaa että puolivälin toiselta puolelta elementit siirtyvät yhden pykälän vasemmalle täyttäen poistetun elementin jättämän "aukon".

Tässä taulukon tärkeä ja hyödyllinen omaisuus on se, että vaikka elementtejä poistetaan keskeltäkin, taulukko pidetään "ehyenä" -- välillä `0..<array.size` on *vain* olioita, *ei null -viittauksia*. Näin taulukon käsittely on yksinkertaista ja nopeaa: koodissa voidaan aina olettaa että indeksien `0..<array.size` välillä on *aina* valideja `Shape` -olioita. Lisäksi, taulukon pienentäminen siten että sen koko sovitetaan niin että sinne mahtuu vain oliot, on helppoa. Tämä säästää sovelluksen muistin käytössä.

Tämä poistetun jälkeen tulevien elementtien siirtäminen tapahtuu `Shapes.remove()` -metodeissa. Tämä valmiina annettu koodi huolehtii siitä että kun indeksien `0..<count` väleistä poistetaan `Shape` -olioita, sen jälkeen tulevat siirtyvät kaikki pykälän vasemmalle, täyttäen `null` -välit taulukossa.

Jatketaan eteenpäin.

### Usean olion poistaminen - algoritmi 1

Tässä ja seuraavissa vaiheissa taulukosta poistetaan aina useita olioita; kaikki valitut (harmaat) piirroselementit.

Ohjelmassa (`ShapeApp.actionPerformed()`) poista -valikkokomentoja käsitellään tähän tyyliin:

```Java
shapes.select(shape -> shape.getLineColor() != Color.GRAY);
shapes.removeNotSelected1();
```
Eli: ensin valitaan kaikki muut kuin harmaat Shape -oliot ja sen jälkeen muut kuin valitut poistetaan. `Shapes` -luokassa on useita removeNotSelectedX -metodeja joihin tutustutaan yksi kerrallaan.

Mutta ennen kuin jatkat, **mieti miten itse toteuttaisit** algoritmin joka poistaa taulukosta kaikki valitut piirroselementit. Sama suunnittelutehtävä, joka on tehtävän alussa. Ottaen huomioon että poistamismetodi `Shapes.remove` aina poistaa elementin taulukosta saman tien ja siirtää sen jälkeen tulevia yhden pykälän taulukon alkuun päin. Kenties -for -silmukalla...? Käytäisiin vaikka läpi elementit taulukossa, alusta loppuun ja poistetaan kaikki valitut shapet yksi kerrallaan taulukosta kutsumalla `Shapes.remove(int)` metodia? Vai jotain muuta?

Seuraavaksi:

1. Suorita valikosta komento **Create 18 shapes** ja odota että taulukko on luotu.
2. Suorita valikosta komento **Remove gray shapes BUGGY algo 1**. 

Huomaat että ohjelmassa tulee **virheilmoitus** (`NullPointerException`) ja konsoliin tulostuu call stack.

Tämä johtuu *huonosti toteutetusta* algoritmista 1 `Shapes` -luokassa, joka poistaa ei-valitut elementit näin:

```Java 
    public void removeNotSelected1() throws ArrayIndexOutOfBoundsException, InterruptedException {
        // 1st bad way, crashes since index out of bounds.
        int endIndex = count();
        for (int index = 0; index < endIndex; index++) {
            if (!shapeArray[index].isSelected()) {
                // Remove moves elements after the removed down by one index, filling
                // the empty gap that otherwise would have a null in it.
                removeAndShiftDown(index);
            }
        }
    }
```
Eli:

1. otetaan tilapäiseen muuttujaan elementtien lukumäärä,
2. käydään indeksit läpi alusta loppuun eli elementtien määrän verran,
3.   jos shape-elementtiä ei ole valittu, poistetaan se.

Ongelma tässä on askel 1. Kun `remove(int)` poistaa elementin ja siirtää muita alkuun päin, ja näitä poistetaan useita, loppupäässä olevien elementtien tilalle on tullut null. Silmukka etenee kuitenkin "vanhaan" taulukon loppuindeksiin asti (`endIndex`) jossa ei enää ole muuta kuin null -elementtejä. Tästä johtuu `NullPointerException`, kun kutsutaan `shapeArray[index].isSelected()`.

Eli tämä ei kelpaa. Olisitko tehnyt tämän virheen itse kun pohdit algoritmin toteutusta yllä olevan ohjeen mukaisesti? Tuskimpa, todennäköisesti olisit silmukan toistoehdossa kutsunut `count()` -metodia. Onneksi et siis olisi tehnyt tätä bugia.

Tämä on siis täysin kelvoton algoritmi tähän tarkoitukseen, ja se voidaankin tästä eteenpäin unohtaa. Seuraavaksi yritetään jotain parempaa.

### Edellisen ongelman ratkaisu - algoritmi 2

Edellinen toteutus ei siis ottanut huomioon sitä että elementtien määräkin muuttuu kun niitä poistetaan silmukassa, ja taulukossa olevat elementit siirtyvät alkuun päin poistamista tehdessä.

Siksi toteutusvaihtoehto 2 alla. Valitse taas valikosta komento **Create 18 shapes** ja odota että taulukko on täytetty. 

Sen jälkeen, suorita valikosta komento **Remove gray shapes SLOW algo 2**. Nyt ei pitäisi tulla ongelmia, tällä algoritmilla (katso vain `else` -haaran `for` -silmukkaa, `if`-lohkon koodi liittyy demon animaation toteutukseen ja *sitä ei tarvitse ymmärtää*).

```Java
public void removeNotSelected2() throws ArrayIndexOutOfBoundsException, InterruptedException {
    // 2nd bad (slow) way
    if (listener != null) {
//...
    } else {
        for (int index = 0; index < count; index++) {
            // This is not buggy but slow, since it does the removal
            // and shifting elements down for _each_ element removed.
            if (!shapeArray[index].isSelected()) {
                removeAndShiftDown(index);
                index -= 1; // since for loop increases index by one
            }
        }
    }
}
```

Tässä vältetään *kolme ongelmaa* jotka olivat ensimmäisessä toteutuksessa:

1. nyt ei oteta elementtien lukumäärää paikalliseen muuttujaan joka ei päivity elementtejä poistettaessa, vaan käytetään jäsenmuuttujaa `count` -- `Shapes.remove()` päivittää sitä aina poistaessaa elementin. Ei mennä laskuissa sekaisin siitä, montako elementtiä taulukossa on.
2. if -lauseessa tarkistetaan vielä varalta, ettei elementti vaan ole null, ennenkuin mennään katsomaan onko se valittu poistettavaksi -- ei tule null pointer exceptionia.
3. jos elementti poistetaan, `index` -laskurin arvoa vähennetään yhdellä. Muuten hypättäisiin sen elementin yli, joka siirrettiin taulukossa alkuun päin, nyt poistetun elementin tilalle! Voihan olla että sekin olisi poistettava elementti, joten sekin pitää tutkia.

Näyttäisi siltä että kaikki toimii erinomaisen hyvin? Ei ihan kuitenkaan.

Valitse valikosta komento **Create 10 000 shapes** ja sen jälkeen komento **Remove gray shapes SLOW algo 2**.

Poistaminen tapahtuu aika sukkelaan (koneesi nopeudesta riippuen). Mutta valitsepa komento **Create 100 000 shapes** ja sen jälkeen komento **Remove gray shapes SLOW algo 2**.

Nyt poistaminen kestää *kauan*. Näet VS Coden Terminalista jotain tätä luokkaa: `removeSelected2 took 4414 ms`. Eli opettajan koneella noin 4.5 sekuntia.

Tämä on *aivan liian hidasta* interaktiivisen graafisen ohjelman (esim. peli) tarpeisiin. Käytettävyysasioista puhuttaessa usein mainitaan n. 200 ms olevan se raja, jonka jälkeen operaation kestässä kauemmin, käyttäjä alkaa huomaamaan että sovellus tökkii. Eli algoritmi on oikeellinen mutta aikakompleksisuusluokaltaan todella huono.

**Mikä on kyseisen elementtien poistamisen aikakompleksisuusluokka?** Mieti koko algoritmia *kokonaisuutena*, eli silmukka *sekä* silmukassa tehdyt operaatiot ja kutsutut metodit.

Kun teet saman 18 elementillä, näet käyttöliittymässä montako siirto-operaatiota (sijoituslauseita) tämä vaati. **Laita tämä muistiin**.

Tämä hitausongelma ratkaistaan (ainakin jossain määrin) seuraavaksi.

### Nopeutetaan poistamista - algoritmi 3

Edellisen ratkaisun ongelma oli osin siinä, että silmukka etenee `Shape` -taulukon *alusta loppuun*. Välillä kun poistetaan valittuja olioita, *kaikki* myöhemmin tulevat oliot siirretään pykälä taulukon alkuun päin. *Myös ne elementit jotka tullaan poistamaan myöhemmin*. Eihän niitä kannattaisi siirrellä kun ne poistetaan kuitenkin myöhemmin, vai mitä olet mieltä?!

Tässä tehdään siis paljon turhaa työtä. Kannattaisiko sen sijaan edetä taulukon *lopusta alkuun päin*? Näin siirretään lopusta alkuun vain niitä elementtejä jotka on päätetty pitää taulukossa, eikä siirrellä poistettavia elementtejä ollenkaan.

Näin toimii `Shapes` -luokan algoritmin kolmas toteutus (taas huomioi vain `else` -haaran silmukka):

```Java
public void removeNotSelected3() throws ArrayIndexOutOfBoundsException, InterruptedException {
    if (listener != null) {
        // ...
    } else {
        // 3rd works, but...
        for (int index = count - 1; index >= 0; index--) {
            // This avoids moving elements after removed to be shifted down,
            // since we remove from the end towards the beginning, so we 
            // are not moving the same elements so many times.
            if (!shapeArray[index].isSelected()) {
                removeAndShiftDown(index);
            }
        }
    }
}
```

Eli lähdetäänkin liikkeelle taulukon *lopusta* ja edetään alkuun päin, poistaen elementtejä ja aina poistaessa, siirtäen vain taulukossa poistetun jälkeen olevia -- ja taulukossa pidettäviä elementtejä -- alkuun päin. Koska aina poistetun jälkeen tulevat *on jo aiemmin käsitelty* ja todettu että ne pidetään taulukossa. Ei siis siirrellä turhaan elementtejä joita ei pidetä taulukossa. Kaikki tämä turha työ joka tehtiin algoritmissa 2 jää tekemättä. 

Vaikka olet oppinut tyypillisesti perusohjelmointikursseilla käymään läpi taulukoita ja muita tietosäiliöitä alusta loppuun, *se ei aina ole se paras tai edes oikea vaihtoehto*.

Luo taas 18 elementin aineisto, ja sen jälkeen suorita tämä poistamiskomento valikosta nimeltä **Remove gray shapes BIT FASTER algo 3**.

Näet taas käyttöliittymässä montako siirto-operaatiota (sijoituslauseita) tämä vaati tällä algoritmilla. **Laita tämä muistiin**.

Kokeile miten nämä kaksi toteutusta selviävät 100 000 elementin taulukosta. Huomaa että ohjelma tulostaa terminaaliin molempien toteutusten **suoritusajan**. Omalla koneellani lopusta alkuun -toteutus on n 50% alusta loppuun -toteutuksen suoritusajasta:

```console
removeSelected2 took 4174 ms
removeSelected3 took 2001 ms
```

Eli merkittävä suoritusnopeuden parannus, **yli puolet ajasta pois** pelkästään sillä että käydäänkin taulukkoa läpi eri suunnasta!

**Mikä on algoritmin 3 aikakompleksisuusluokka?** Huomioi taas *koko algoritmi*, myös silmukassa kutsutut metodit.

Silti, tuo reilun kahden sekunnin suoritusaika on vieläkin *liian paljon*. Jos esimerkiksi pelisovellus odotuttaisi käyttäjäänsä kaksi sekuntia kun poistetaan suuria määriä polygoneja eli pelihahmojen graafisia elementtejä, tämä olisi täysin sietämätön tilanne. Siksi etenemme kohti parempaa toteutusta...

## Algoritmi 4 -- osittaminen ja poistaminen lopusta

Edeltävien ratkaisujen kautta on nähty että aikaa menee paljon siihen, että halutaan pitää shape -oliot taulukossa peräkkäin ja siirrellään jäljempänä olevia shape -olioita alaspäin poistettujen tilalle.

Tästä on omat hyötynsä joten siitä ei kannata luopua. Esim. jos shapeja luodaan vaikka tuo 100 000 ja poistetaan niistä puolet, voitaisiin samalla myös *tiivistää* poistamisen jälkeen taulukon kokoa pienemmäksi. Näin saadaan vapautettua muistia muuhun käyttöön. Eli jatkamme tällä periaatteella.

Algoritmin 3 toteutus on siis vielä hidas. Kun taulukkoa on edetty lopusta alkuun, ja poistetaan vaikka jäljellä olevista 50 000 elementistä keskimmäinen, tästä loppuun päin olevat n 25 000 elementtiä pitää silti siirtää yksi kerrallaan alkuun päin, jokaisella poistokerralla aina uudestaan ja uudestaan jokaisen poiston yhteydessä.

Mikä olisi järkevämpi tapa?

Vastaus (tai yksi sellainen) on **partitiointi** eli **osittaminen**.

**Tutustu** sovelluksen pienellä **18 elementin** aineistolla siihen, miten osittaminen toimii. Luo 18 elementtiä, ja valitse valikosta komento **Remove gray shapes by PARTITIONING algo 4**. Animointi näyttää miten partitiointi toimii, ja tämä selitetään myös alla tarkemmin.

Näet taas käyttöliittymässä montako siirto-operaatiota (sijoituslauseita) tämä vaati. **Laita tämä muistiin**.

**Vertaile** näiden eri algoritmien vaatimia sijoituslauseiden lukumääriä. **Mieti** miten tämä skaalautuisi, kun elementtien lukumäärä kasvaa taulukossa kymmeniin tai satoihin tuhansiin.

> Isojen aineistojen käsittely ei demosovelluksessa vielä onnistu, sillä et ole toteuttanut siihen liittyvää koodia tässä vaiheessa.

### Osittamisen periaate

Osittamisen periaate tässä tapauksessa on seuraava. Alla esimerkkitaulukossa kuvitellaan että x:llä merkityt valitut elementit jäävät taulukkoon:

Taulukko ennen partitiointia, elementit d, h, l o, p ja q merkitty pidettäväksi taulukkoon:

```
koko : 24, pituus 24
arvo :  a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p  q  r  s  t  u  v  w  z
index:  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23
pidä :           x           x           x        x  x  x
```

Ja taulukko partitioinnin jälkeen:

```
koko : 24, pituus 24
arvo :  d  h  l  o  p  q  a  b  c  e  f  g  i  j  k  m  n  r  s  t  u  v  w  z
index:  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23
pidä :  x  x  x  x  x  x
```

Taulukon sisältö siis ositetaan eli jaetaan kahteen *osaan*: toiselle puolelle taulukkoa (loppuun) siirretään poistettavat elementit, toiselle puolelle (alkuun) säilytettävät elementit. Tässä tapauksessa indeksi 6 osoittaa jakokohdan - sitä ennen säilytettävät oliot, siinä indeksissä ja sen jälkeen poistettavat oliot.

Partitioinnin nopeusetu tulee tästä. Nyt on helppo poistaa kaikki elementit indeksistä 6 alkaen. Ja koska loppupäässä ei ole muita kuin poistettavia elementtejä, näitä *poistettavia ei tarvitse enää siirrellä* taulukossa alkuun päin, vaan ne merkitään kaikki null:ksi (alaviivat alla) yksinkertaisessa for-silmukassa:

```
koko :  6, pituus 24
arvo :  d  h  l  o  p  q  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _
index:  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23
pidä :  x  x  x  x  x  x
```

Bonuksena, jos haluttaisiin vapauttaa sovelluksen käyttämää muistia muuhun tarkoitukseen, voitaisiin poistamisen jälkeen taulukon kokoa pienentää, niin että sinne mahtuvat vain ne valitut, pidettävät elementit:

```
koko :  6, pituus 6
arvo :  d  h  l  o  p  q
index:  0  1  2  3  4  5
pidä :  x  x  x  x  x  x

```

Näin voidaan käyttää vapautunut muisti (indeksistä 6 eteepäin muisti on vapautettu) muuhun tarkoitukseen.

Partitioinnin logiikka on siis yksinkertainen. Algoritmikaan ei ole kovin monimutkainen. Alla karkea kuvaus siitä **miten toteutat partitioinnin** tämän harjoituksen ohjelmointitehtävänä.


### Ohjeet algoritmin 4 toteutukselle

> **HUOM:** Kun toteutetaan puolivakaa (*stable*) osittamisalgoritmi, taulukon alkuun ositetut elementit **säilyttävät järjestyksensä** toistensa suhteen. Älä siis toteuta sellaista ositusalgoritmia joka "sotkee" elementtien järjestyksen. Partitioinnin on siis oltava puolivakaa (*half stable*) algoritmi. Erityisesti piirrosohjelmassa tämä on tärkeää, sillä kun piirroselementit ovat osittain toistensa päällä, ja piirretään siinä järjestyksessä josssa ne taulukossa ovat, järjestyksen sekoittaminen saa piirroksen näyttämään ihan erilaiselta kuin oli tarkoitus. Taulukossa alumpana olevat elementit piirretään muiden taakse, ja viimeisenä olevat muiden päälle.

Toteutat partitioinnin ja poistamisen **geneerisinä algoritmeina**. Miksi geneerisenä? Siksi että jos tarvitsemme algoritmeja myös myöhemmin, silloin sitä ei tarvitse enää uudestaan toteuttaa. Kätevää!

Miten partitiointia sitten hyödynnetään Shapes -sovelluksessa, on jo valmiina koodissa `else` -haarassa, mutta kommentoituna pois:

```Java
public void removeNotSelected4() {
    // 4th fast way
    if (listener != null) {
// ...
    } else {
        // int index = Partition.byRule(shapeArray, count, shape -> shape.isSelected());
        // removeFrom(index);
        // shapeArray = Partition.filter(shapeArray, count, shape -> shape.isSelected());
    }
}
```
Tehtäväsi on toteuttaa kolme metodia: 

1. Ensimmäisen `Partition.byRule` -algoritmin toteutat **`shared` -komponenttiin** `Partition` -luokkaan ja testaamalla tuossa komponentissa algoritmin oikeellisuuden. 
2. Kun osittaminen toimii, toteutat toisen algoritmin `Shapes.removeFrom`, joka poistaa taulukosta poistettavat elementit.
3. **Jätä** vielä kolmas kommentoitu rivi kommentteihin!

Sen jälkeen testaat miten tämä toimii ottamalla `else` -haaran koodista *kaksi ensimmäistä riviä* pois kommenteista. Tässä ei vielä saavuteta muistinsäästöetua, sillä taulukon koko ei vielä pienentynyt, vaan sovellus pitää muistissaan edelleen isoa taulukkoa jossa loppupää on `null`:eja. Poistaminen kuitenkin nopeutuu huimasti.

Kun ensin olet saanut yllä olevan toimimaan, alla olevien ohjeiden mukaisesti ja olet kokeillut miten se toimii, *sen jälkeen*:

3. Toteutat algoritmin `Partition.filter`, joka hyödyntää `Partition.byRule` -algoritmia sekä `ArrayUtils.copyOf` -algoritmeja. Näiden avulla saavutetaan vielä muistinkäytön väheneminenkin sovelluksessa.


### 1. Osittamisen toteutuksen askeleet

1. **Avaa** `shared` -komponentti omaan VS Code ikkunaan (jos ei jo ole), ja **toteuta** metodi `public static <T> int byRule(T [] array, int count, Predicate<T> rule)` luokkaan `Partition` ohjeita noudattaen. 

Osita taulukko siten että siirrät predikaatin ehdon mukaiset elementit taulukon alkuun. Katso esimerkkiä alta, miten predikaatilla testataan onko joku elementti predikaatin mukainen.

> [`Predicate`](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/util/function/Predicate.html) on Javassa geneerinen rajapintaluokka.
>
> Predikaatti yleisemmin on [funktio](https://en.wikipedia.org/wiki/Predicate_(logic)) joka palauttaa boolean -arvon (`true` tai `false`; tosi tai epätosi). Koodinpätkässä yllä predikaatti kertoo siis sen, onko joku shape -olio valittu vai ei. Kaikki modernit ohjelmointikielet sisältävät vastaavan predikaatin käsitteen eri tavoin toteutettuna.

Lue metodin `Partition.byRule` dokumentaatio koodin kommenteista. Metodi suorittaa osittamisen ja palauttaa indeksin jossa on ensimmäinen elementti, joka *ei täytä* predikaatin ehtoa. Jos kaikki elementit taulukossa ennen `count` -indeksiä täyttävät predikaatin ehdon, metodi palauttaa elementtien lukumäärän (samainen `count`).

Javan `Predicate`:n käyttämisestä kutsuvan koodin puolella löydät esimerkin `Shapes.select` -metodissa sekä `ShapeApp.actionPerformed` -metodissa.

`Predicate` -luokan tärkein metodi on `test`. Sillä nimensä mukaisesti *testataan* onko joku olio predikaatin mukainen olio eli palauttaako `test` metodi sen olion suhteen arvon `true` vai `false`. Esimerkiksi `Shapes.select` -metodissa...:

```Java
    public void select(Predicate<Shape> toCompare) {
        int index = 0;
        for (Shape shape : shapeArray) {
...
            if (toCompare.test(shape)) {
                shapeArray[index].setSelected(true);
            }
```
...testataan, että jos shape -olio täyttää predikaatin `toCompare` ehdon, se asetetaan valituksi.

Tätä metodia kutsutaan `ShapeApp.actionPerformed()` metodista käsin:

```Java
  shapes.select(shape -> shape.getLineColor() != Color.GRAY);
``` 
Näin valitaan kaikki piirroselementit, joissa toteutuu predikaatin mukainen ehto, että piirroselementin viivan väri on jotain muuta kuin harmaa. Kun tätä metodia on kutsuttu, valituksi tulevat kaikki ei-harmaat elementit. Kun kutsutaan tuota partitiointialgoritmia, sille taas sanotaan että järjestä kaikki *valitut* taulukon alkuun, ja muut loppuun.

**Huomaa** että `Partition.byRule` tekee vain osittamisen, eikä poista mitään elementtejä taulukosta. Se tehdään erillisenä askeleena omassa algoritmissaan `removeFrom(int)`.

**Hyödynnä** algoritmin toteutuksessa valmiina saamaasi `shared` -komponentin algoritmia `ArrayUtils.swap`.

### 2. Osittamisen oikeellisuuden testaus

**Testaa** toteuttamaasi `Partition.byRule` -algoritmia `shared` -komponentissa valmiina olevilla testeillä luokassa `PartitionByRuleTests`. Kun testit menevät läpi, algoritmi toimii oikeellisesti.

**Kun testit osoittavat algoritmisi toimivan** oikeellisesti, **luo** shared -komponentin lähdekoodista **.jar -kirjasto**, siten kuin `shared` -komponentin readmessä neuvotaan. Näin tämä uusi algoritmi on käytettävissä tämän tehtävän `task_3` -komponentillekin.

### 3. Poistaminen Shapes -luokassa

2. Toteuta `Shapes` -luokan metodi `removeFrom(int indexToSelected)` -- poista kaikki elementit taulukosta ko. indeksistä eteenpäin, sijoittamalla indekseihin `null`. **Muista** myös lopuksi päivittää taulukossa olevien elementtien lukumäärä `Shapes` -luokan `count` -jäsenmuuttujaan, jos elementtejä poistettiin.

Metodissa `removeNotSelected4()` muista ottaa kommentit pois algoritmien kutsun kohdalta kun olet toteuttanut ne:

```Java
public void removeNotSelected4() {
    // 4th fast way
    int firstNotSelectedIndex = Partition.byRule(shapeArray, count, shape -> shape.isSelected());
    removeFrom(firstNotSelectedIndex);
    // shapeArray = Partition.filter(shapeArray, count, shape -> shape.isSelected());
}
```

**Kokeile toteutustasi** myös sovelluksen **graafisessa käyttöliittymässä**. Luo 100 000 shapea valikosta ja käytä algoritmiasi poistamaan kaikki harmaat shapet. Vertaa havainnoimaasi suorituskykyä algoritmiin 2 ja 3 -- ohjelma tulostaa konsoliin näiden kaikkien suoritusajat isoilla aineistoilla, joten katso sieltä. Huomaatko eron myös käyttäjän näkökulmasta?

Nyt olet saanut poistamisen toteutettua aikatehokkaasti osittamisen avulla.

### 4. Aikatehokkuuden varmistaminen

**HUOM**: jos algoritmisi suhteellinen nopeus verrattuna algoritmeihin 2 ja 3 ei vastaa konsolitulostuksissa alla olevia opettajan ratkaisun aikamittauksia, oikeellisuudesta huolimatta algoritmissasi on vikaa:

```
removeSelected2 took 4418 ms
removeSelected3 took 2112 ms
removeSelected4 took 11 ms
```
Kuten näet, osittaminen algoritmilla 4 kesti tällä kertaa 11 ms, kun algoritmi 3 käytti tähän 2112 ms ja hitain algoritmi 2 käytti tähän 4418 ms. Jotain samassa suhteessa pitäisi näkyä sinunkin toteutuksellasi.

> Jos tässä on isoja ongelmia, katso myös alta filtteröinnin aikatehokkuuden testaus. Koska filtteröinnin aikatehokkuus on käytännössä riippuvainen vain osittamisen aikatehokkuudesta, voit käyttää sitä testiä varmistamaan aikatehokkuus. Tosin vasta kun olet toteuttanut myös sen kolmen rivin `Partition.filter` -algoritmin.

Vielä kuitenkaan ei saavuteta muistikompleksisuuden suhteen haluttuja etuja. Taulukko on edelleen suuri (katso mitä tulostuu konsoliin kun suoritat algoritmin 4 `ShapesApp`:ssa), ja sisältä suuren määrän tyhjää (`null` -elementtejä). Koska elementtejä oli suuri määrä, haluamme vapauttaa muistia muuhun tarpeeseen. Jos taulukossa oli vaikkapa 100 000 elementtiä ja puolet niistä poistettiin, taulukon kooksi pitäisi saada nyt 50 000.

### 5. Filtteröinnin toteutus

Osittamista voidaan käyttää myös toteutettaessa *filtteröintiä*. Esimerkiksi Swift -ohjelmointikielessä taulukko -luokka ja muut tietokokoelmaluokat toteuttavat algoritmin `filter` joka on toteutettu osittamisella. `filter` ei muokkaa alkuperäistä taulukkoa, vaan luo (pienemmän) taulukon johon sisältyy filtterin mukaiset elementit kopioituna alkuperäisestä taulukosta:

```Swift 
let array : [Int?] = [ nil, 3, 1, nil, nil, 4, 1, 5, nil, 9, 2, 6, nil, 5, 4, nil ]

let piNumbers = array.filter( { $0 != nil } )

for number in piNumbers {
	print(number!, terminator: " ")
}
```
Yllä oleva tulostaa: "3 1 4 1 5 9 2 6 5 4". Alkuperäinen taulukko jää sellaisekseen.

Tavoitteena seuraavaksi on se, että metodi poistamisalgoritmille 4, joka on nyt tällainen...:

```Java
public void removeNotSelected4() {
    // 4th fast way
    int firstNotSelectedIndex = Partition.byRule(shapeArray, count, shape -> shape.isSelected());
    removeFrom(firstNotSelectedIndex);
    // shapeArray = Partition.filter(shapeArray, count, shape -> shape.isSelected());
}
```

...niin tämän askeleen toteuttamisen jälkeen poistaminen näyttää tältä:

```Java
public void removeNotSelected4() {
    /// ....
    // 4th fast way
    // int firstNotSelectedIndex = Partition.byRule(shapeArray, count, shape -> shape.isSelected());
    // removeFrom(firstNotSelectedIndex);
    shapeArray = Partition.filter(shapeArray, count, shape -> shape.isSelected());
    count = shapeArray.length;
}
```
Eli annamme `Partition.filter` metodille taulukon ja tiedon montako elementtiä siinä on (indeksien `0..<count` välillä), ja predikaatin joka kertoo millä tavalla osittaminen tehdään. Algoritmi hyödyntää toteuttamaasi osittamisalgoritmia, ja palauttaa uuden taulukon joka sisältää vain predikaatin mukaiset elementit eikä yhtään nullia. Näin **taulukko on kooltaan pienempi**, ja muistia vapautuu muuhun käyttöön.

**Toteuta** `shared` -kompontenttiin filtteröintialgoritmi, `Partition.filter` seuraavasti:

1. luo uusi kopio alkuperäisestä taulukosta (koska `filter` ei saa muuttaa alkuperäistä taulukkoa),
2. suorita osittaminen kopioidulle taulukolle, ja
3. luo uusi pienempi taulukko, johon kopioidaan vain ositetun taulukon alkupään elementit, ja palauta tämä taulukko kutsujalle.

Kun hyödynnät tähän toteuttamaasi osittamisalgoritmia sekä valmiiksi saamiasi `shared` -komponentin `ArrayUtils.copyOf` -metodeja, toteutus vaatii **vain kolme riviä koodia**.

**Testaa** `filter` -algoritmin oikeellisuus `shared` -komponentin testeillä testiluokassa `PartitionFilteringTests`. **Muista** luoda shared jar -tiedosto ennenkuin jatkat filtteröinnin kokeilua tässä Shapes -sovelluksessa.

Ennenkuin kokeilet miten tämä toimii 10 000 ja 100 000 aineistoilla, **pitää muistaa vielä yksi asia**. Aikaisemmin `removeFrom` päivitti elementtien lukumäärän `Shapes` -luokan taukossa. Nyt se pitää tehdä itse `removeNotSelected4` metodissa. Huomaa yllä olevaan `removeNotSelected4()` -metodin koodinpätkään lisätty `count` -arvon päivitys.

Jos kaikki toimii oikeellisesti, **suorita** vielä `shared` komponentin testi `FilterEfficiencyTests`. Testi mittaa filtteröinnin (ja sitä kautta myös partitioinnin) aikatehokkuutta verrattuna algoritmi 3:n kaltaiseen for-silmukkaan. Mikäli filtteröinti/partitiointi eli ole selkeästi for-silmukkaa nopeampi, testi epäonnistuu. Tällöin partitiointialgoritmissasi on todennäköisesti jotain vikaa; se toimii liian hitaasti vaikka menisi aiemmista oikeellisuustesteistä läpi.

Tässä tämän aikatehokkuustestin tulostuksen loppuosa opettajan toteutuksella mitattuna:

```
 FINISHED: Compare the speeds of filter/partition and for loop implementation
 - Filter/partition should be way faster than the for loop
 - Filter/partition speed average:     6 ms
 - for loop speed average    :  2243 ms
 - Difference, partitioning is 374 times faster than loop speed
 ```
josta nähdään että for -silmukka oli tässä testissä noin 374 kertaa hitaampi kuin filtteröinti/partitiointi.

## Yhteenveto

Kun olet saanut osittamis- ja filtteröintialgoritmit toimimaan ja käyttöön `ShapesApp`:ssa, on saavutettu kaksi tärkeää tavoitetta:

1. Poistamisen **aikatehokkuus on hyvä** osittamisalgoritmin hyödyntämisen vuoksi, sekä
2. Sovelluksen **muistitehokkuus parani merkittävästi** filtteröinnin myötä; myös taulukon kokoa pienennetään aina poistamisen yhteydessä.

> **Pohdi**, mikä on toteuttamiesi ositusalgoritmien **aikakompleksisuusluokka?** Miten `filter` -algoritmin **muistikompleksisuuden kanssa kävi; mikä se on?**

## Viimestelytyöt

Kun olet testannut että toteutuksesi toimii, eli `shapes` -komponentin testit menevät kaikki läpi, ja `ShapesApp` toimii odotetusti, vie koodisi etärepoosi ennen tehtävien deadlinea.

**Varmista** ettet ole käyttänyt toteutuksessa kurssilla kiellettyjä ratkaisuja. Tähän on työkalu jolla voit tarkistaa yleisimmät virheet tässä asiassa. Lue sen ohjeet `shared` -komponentin readme:stä.

## Ongelmia, kysymyksiä?

Osallistu kurssin luennoille, ohjaussessioihin ja verkkofoorumeille, kysy apua, lisäselvityksiä ja ohjeita opettajilta tarvittaessa.

Jos sinulla on ongelmia työkalujen kanssa, varmista että sinulla on oikea JDK asennettuna ja käyttöjärjestelmäsi ympäristömuuttujat (environment variables, PATH ja JAVA_HOME) osoittavat oikeaan JDK:n hakemistoon. Varmista että Maven on myös asennettu oikein ja git toimii.

## Tietoja

* (c) Antti Juustila 2021-2025.
* Kurssimateriaalia Tietorakenteet ja algoritmit -kurssille | Data structures and algorithms 2021-2025.
* Tietojenkäsittelytieteet, Tieto- ja sähkötekniikan tiedekunta, Oulun yliopisto.
