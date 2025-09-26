# Oikeellisuus

## TL;DR

Tässä ohjelmointitehtävässä varmistat algoritmin oikeellisuuden (*correctness*) sekä löydät algoritmin bugit, tekemällä pieneen reverse -algoritmiin esi- ja jättöehtojen tarkistukset, sekä silmukkainvariantteja. Lisäksi teet yhteen pieneen luokkaan esiehtojen tarkistuksia ja luokkainvariantin. Samalla korjaat löytyneet bugit, ohjeita seuraten.

Päätavoite tosin **ei nyt ole** vain löytää niitä bugeja vaan oppia koodin **oikeellisuuden** varmistamista **invarianteilla**. Älä siis tavoittele sitä että saat bugit korjattua ilman assertien ja poikkeusten käyttöä, esi-, jättö- ja silmukkainvarianttien käyttöä, sillä **niiden käytön oppiminen** on tämän harjoituksen päätavoite, ei bugien löytäminen ihan muuten vaan.

**Huomaa** myös että tätä mitä opit ja teet tässä harjoituksessa, tulee soveltaa **jokaisessa** muussakin kurssin ohjelmointitehtävässä. Jokainen algoritmi jonka toteutat, tulee olla oikeellinen, ja tehtävien testit testaavat myös esimerkiksi että algoritmisi testaa esiehdot (*precondition*) ja toimii oikeellisesti. Jos toteutettu algoritmi ei testaa esiehtoja tai ole oikeellinen, se ei ole hyväksyttävä eikä se läpäise testejä. Siksi kannattaa käydä harjoitus läpi huolella ja soveltaa näitä opittuja asioita myöhemmissä tehtävissä.

> Saat kyllä korjata löydetyt bugit, mutta vasta **sen jälkeen** kun olet löytänyt bugien syyt asserttien, esi- ja jättöehtojen sekä silmukkainvarianttien avulla. 

Tehtävässä tuotat noin 20 riviä koodia. Jos tuotat n. 5 riviä koodia päivässä, tehtävän koodi on tehty neljässä päivässä ja huomioiden tehtävän aineiston, koko tehtävä pitäisi olla valmis viikossa.

## Toteutuksen demo

**Katso** tehtävän alkuosan **toteutusdemo** [YouTubesta](https://youtu.be/1wygIb4B7y8). Siinä tehdään karkeasti ottaen se mitä tässä tehtävän ohjeissa neuvotaan tekemään. Voit tehdä tehtävän lukien ohjeet läpi ja sitten koodaten itse, videota pausettaen (code along -tyyliin) samat asiat. Tehtävän loppuosan (luokkainvariantit) demovideo julkaistaan katsottavaksi myöhemmin. Yritä ensin tehdä tämäkin osio itse, ja varmista sitten toiselta demovideolta sen julkaisun jälkeen että asiat menivät omassa toteutuksessasi oikein.

## Johdanto

Jos tiedät jo mitä invariantit, assertit ja poikkeukset ovat, tämä alun lukuosuus on helppo ja nopea käydä läpi. Jos taas et tiedä, lue lukuosuus ajatuksella läpi, sillä näitä asioita sovelletaan tässä tehtävässä, ja myös koko kurssin ajan. Koska kurssin toinen päätavoite on koodin oikeellisuuden varmistaminen, **asia on tärkeä**, ei vain tässä harjoituksessa vaan myös jatkossa.

**Lukuosuus** on omassa [README-INVARIANTS.markown](README-INVARIANTS.markdown) -dokumentissaan.

> Tehtävän ohjeet ovat perusteelliset ja pitkät, koska kurssilla on tarkoitus tukea itsenäistä opiskelua. Älä siis säikähdä ohjeiden pituutta. Jos kerrotut asiat ovat sinulle jo tuttuja tai ymmärrät ohjeet saman tien, erinomaista. Muussa tapauksessa lue teksti sekä seuraa ohjeita. Opeteltavan asian perusteiden ymmärtäminen on ensimmäinen ja tärkein askel oppimiseen ja sitä kautta asian soveltamiseen käytännössä.

**Tarkoituksena** harjoituksella on lisäksi:

1. kouluttaa asennetta, jossa algoritmeja toteutettaessa mietitään jatkuvasti sen oikeellista toteutusta, sekä
2. antaa työkaluja algoritmin oikeellisuuden toteuttamiseen sekä virheiden jäljittämiseen, kun koodissa on **bugeja** jonka juurisyytä ei välttämättä heti huomaa.
3. edesauttaa ylipäätään laadukkaan koodin ja sovellusten toteutusta. Jos koodi ei ole oikeellista, **millään muulla ei oikeastaan ole väliä**, ja koodi on kelpoista lähinnä roskakoriin.
4. **auttaa kurssin myöhemmissä harjoituksissa** siinä, että saat nopeammin kiinni omassa koodissasi olevat virheet ja korjattua ne nopeammin kun käytät tässä opittuja tekniikoita.


## Miten assertit otetaan Javassa käyttöön?

Assertit toimivat kun Java -ohjelmalla koodia ajettaessa annetaan parametri `-ea` (*enable assertions*), esimerkiksi `java -ea my.java.mainClass`. Tämä voidaan tehdä monin eri keinoin. Tässä harjoituksessa se tehdään Visual Studio Coden launch configuration:ia käyttäen. 

> Näin assertit ovat varmasti käytössä vain kehityksen aikana, sillä oikeastihan käyttäjä ei koskaan käynnistä Java-ohjelmaa mistään softakehitystyökalusta käsin. Käyttäjälle annetaan sellaiset sovelluksen asennuspaketit, käynnistysohjeet ja -skriptit, joissa parametria `-ea` ei anneta.

Jos tämän tehtävän hakemistossa ei ole jo valmiina alihakemistoa `.vscode`, tee sellainen. Huomaa tuo piste, se pitää olla hakemiston nimessä. 

Luo hakemiston `.vscode` sisälle tiedosto `launch.json`, ja kopioi alta sen sisältö (älä kopioi koodimuotoilua varten olevia rivejä jotka alkavat kolmella tick -merkillä "```"):

```JSON
{
	"configurations": [
		{
			"type": "java",
			"name": "Launch Array Reversal Test Client",
			"request": "launch",
			"mainClass": "oy.tol.tira.task_01.ArrayReversalClient",
			"vmArgs": "-ea"
		},
		{
			"type": "java",
			"name": "Launch Class Invariant Test Client",
			"request": "launch",
			"mainClass": "oy.tol.tira.task_01.CustomDateClient",
			"vmArgs": "-ea"
		}
	]	
}
```

Nyt kun koodi *suoritetaan nimenomaan VSCodesta käsin*, assertit ovat käytössä, koska tuossa annetaan virtuaalikoneelle argumenttina (`vmArgs`) arvo `-ea` (enable assertions).


## Algoritmin oikeellisuuden tutkiminen ja varmistaminen

**HUOM:** Tässä harjoituksessa tavoitteena **ei ole** heti alussa yrittää nähdä ja korjata koodissa olevia bugeja, vaan käyttää sekä asserteja että poikkeuksia, joilla koodissa olevat bugit tulevat selkeämmin ilmi. **Älä siis korjaa virhettä** vaikka heti näkisitkin missä se on, vaan kokeile assertien ja poikkeusten kanssa miten virheet löydetään ja tuodaan näkyviin.

Luokassa `ArrayReversalClient` on metodi `reversed()`, jolla voidaan kääntää taulukon elementtien järjestys päinvastaiseksi. `reversed()`, luo ja palauttaa uuden taulukon, jossa syötteenä annetun taulukon elementit ovat käänteisessä järjestyksessä ja syötetaulukon elementit pysyvät paikoillaan.

Algoritmi toimii kuitenkin virheellisesti. Joskus bugit johtavat näkyvään ennakoimattomaan virheeseen (poikkeus), joskus taas virhe näkyy vain tutkimalla algoritmin aikaansaamia taulukoita ja katsomalla, onko todellakin elementtien järjestys nyt päinvastainen. Eikä se aina ole.

Bugi eli virhe tarkoittaa sitä, että koodi toimii vastoin sen speksejä (*specification*) eli määrittelyjä, jotka algoritmille tai tietorakenteelle on ohjelmiston suunnittelussa annettu tai määritelty. Tässä tapauksessa koodi on toteutettu selkeästi noudattamatta speksejä.

Tässä **speksit** algoritmille, joita orjallisesti *olisi pitänyt* toteutuksessa noudattaa:

Esiehdot (*preconditions*), ehdot joiden täytyy olla voimassa ennenkuin algoritmia edes aletaan suorittamaan:

1. Parametritaulukko ei saa olla null, muuten heitetään poikkeus `IllegalArgumentException`.
2. Parametritaulukon koko täytyy olla vähintään kaksi, muuten heitetään poikkeus `IllegalArgumentException`. Tämä siksi, että järjestyksen kääntämisellä on väliä vain jos arvoja on vähintään kaksi. Muuten operaatio ei tee mitään ja se on tarpeeton.
3. Parametritaulukossa ei saa olla `null` -arvoja, muuten heitetään poikkeus `IllegalArgumentException`.

Jättöehdot (*postCondition*), ehdot joiden täytyy olla voimassa algoritmin suorittamisen jälkeen:

4. Käännetyn, metodista palautetun uuden taulukon koko (pituus) pitää olla sama kuin parametritaulukon.
5. Käännetyn, metodista palautetun uuden taulukon arvojen tulee olla samat kuin parametritaulukossa, mutta käänteisessä eli päinvastaisessa järjestyksessä.

> Huomaa että kyseiset ehdot on mainittu myös kooditiedostossa, metodien kommenteissa. Tämä on hyvä tapa noudattaa, speksit ovat mahdollisimman lähellä koodia joka ne toteuttaa! Tämä hyödyttää sekä algoritmin toteuttamista että sen hyödyntämistä muualta käsin.
> 
> Tehtävän algoritmi on tarkoituksella näin yksinkertainen, jotta voisit keskittyä oikeellisuuden arviointiin ja varmistamiseen assertien ja poikkeusten avulla.

**Annetulla toteutuksella esi- tai jättöehdot eivät toteudu**. Näet sen suorittamalla ohjelman `ArrayReversalClient`, jolloin konsoliin tulostuu esimerkiksi:

```console
1: Testing null array reversed()
FAIL: Expected that the method throws IllegalArgumentException since array size is zero,
        but threw instead: java.lang.NullPointerException: Cannot read the array length because "input" is null
2: Testing empty array reversed()
Reversed:
[]
FAIL: Expected that the method throws since array size is zero
```

Testiohjelma odottaa testissä, että `reversed()` hoitaa oikein tilanteen, jossa argumentti ei ole oikeellinen (vaan `null`), heittämällä poikkeuksen `IllegalArgumentException`. Heitetty poikkeus `NullPointerException` viittaa kyllä oikeaan suuntaan, mutta kun speksit sanovat että argumenttivirheestä pitää ilmoittaa oikealla poikkeuksella, niin *speksejähän pitää silloin noudattaa*. Nyt ei ole noudatettu. 

Sen sijaan algoritmi **yrittää käyttää** parametritaulukkoa joka on `null`. käyttämällä sen ominaisuuksia, ja kun se epäonnistuu, Java heittää tuon `NullPointerException` -poikkeuksen. Metodi ei siis tarkista mitenkään esiehtojaan, yrittää käyttää parametria ja epäonnistuu surkeasti.

Seuraavassa testissä taas odotetaan että koska taulukko on tyhjä (pituus on nolla), pitäisi heittää samainen `IllegalArgumentException`. Sen sijaan algoritmi -- tarkistamatta syötteitä -- alkaa käsittelemään taulukkoa ja vieläpä ei-oikeellisesti, jolloin heitetään `ArrayIndexOutOfBoundsException`. Tämäkään ei vastaa speksejä.

> Jos sinua mietityttää että eikö se nyt ole ihan sama mikä poikkeus sieltä tulee, kunhan tulee, niin lue tuo [annettu taustamateriaali](README-INVARIANTS.markdown), ajatuksen kanssa, vielä uudelleen.


### Tehtävän askeleet

Tässä käydään läpi askel askeleelta, miten koodiin lisätään tarkistuksia (esi- ja jättöehdot, silmukkainvariantit, sekä luokkainvariantit) joilla saat heti selville sen että koodi on ei-oikeellista. Näissä käytetään sekä assertteja että poikkeuksia.


### Esiehdot reversed() -metodissa

**Muuttamatta** algoritmin (virheellistä) **logiikkaa mitenkään**, lisää koodiin seuraavat **tarkistukset** joilla voidaan arvioida algoritmien toteutuksien oikeellisuutta ja löytää bugit.

**Toteuta** luokkaan `ArrayReversals` **privaatti** metodi `static void preCondition(Integer [] array)` jossa tarkistat että algoritmien suorittamisen **esiehdot pitävät paikkaansa**, eli parametritaulukkoon liittyvät, yllä mainitut säännöt, pätevät.

**Lisää kutsu** `preConditions` -metodiin heti ensimmäiseksi tehtäväksi asiaksi `reversed()` -algortmissa. Koska reverse -algoritmi on julkinen, assertien sijaan *heitä `preConditions` -metodissa speksien mukainen poikkeus*, **jos** esiehdot eivät pidä paikkaansa. 

Kun olet toteuttanut preConditions -metodin, tämä pitäisi näkyä ohjelman suorituksessa ja sen tulostuksissa näin:

```
Testing ArrayReversals.reversed()
1: Testing null array reversed()
Expected ArrayReversals.reversed to throw and it did, all is OK
2: Testing empty array reversed()
Expected ArrayReversals.reversed to throw and it did, all is OK
```

Nyt algoritmi ei siis edes yritä käsitellä virheellisiä (speksien vastaisia) parametreja, vaan ilmoittaa virheestä heti kutsujalle, jonka tulee ratkaista ongelma -- välittämällä parametrit speksien mukaisesti.

**Huomaa** myös, että `preConditions()` -metodin toteutuksessa **ei kannata** tehdä turhankin yleistä **aloittelijan ratkaisua** ja heittää poikkeusta näin...:

```Java
   throw new IllegalArgumentException(); // tai  IllegalArgumentException(null)
```

...vaan esimerkiksi **näin**:

```Java
   throw new IllegalArgumentException("Array to reverse must not contain nulls");
```

Anna **aina** poikkeusolion mukana tieto siitä, **mikä** meni pieleen ja **miksi**. Tämä on todella arvokasta tietoa sille koodarille, joka yrittää käyttää algoritmiasi ja homma menee metsään. Tämä nopeuttaa huomattavasti virheiden korjausta myös kutsuvan koodin kirjoittajan päässä. Tietysti olettaen että se toinen koodari osaa lukea ja välittää lukea virheilmoituksia. Sekin turhan yleinen moka, ettei näin tehdä.

### Jättöehdot reversed() -metodissa

Esiehtojen tarkistuksen jälkeen `reversed()` ei enää yritäkään käsitellä ei-oikeellisia parametreja. Se ei siltikään toimi oikeellisesti, kuten näet tulostuksesta:

```
3: Testing array with even (6) number of elements reversed()
Original:
[1, 2, 3, 4, 5, 6]
Reversed:
[1, 2, 3, 4, 5, null]
Expected:
[6, 5, 4, 3, 2, 1]

4: Testing array with odd (5) number of elements reversed()
Original:
[1, 2, 3, 4, 5]
Reversed:
[1, 2, 3, 4, null]
Expected:
[5, 4, 3, 2, 1]
```

Algoritmi ei kaadu, muttei se kyllä toimi oikeellisestikaan. Vaatii koodarilta tarkkaavaisuutta hoksata ettei kaikki ole kuten pitää. 

Seuraavaksi teet jättöehtojen tarkistuksen, jotta nähtäisiin toimiiko koodi oikeellisesti vai missä menee vikaan.

 **Toteuta** luokkaan `ArrayReversals` **privaatti** metodi `static void postCondition(final Integer [] input, final Integer [] output)`, jossa tarkistat, että myös algoritmien **jättöehdot** pitävät paikkaansa, nyt **assertien** avulla. 

Jättöehdon tarkistukseen annetaan alkuperäinen parametrina tullut, koskematon taulukko sekä taulukko, jossa järjestys on käännetty. Tässä tulee tarkistaa että speksien mukainen tavoite on saavutettu: taulukot ovat saman kokoiset *ja* käännetyn taulukon järjestys on päinvastainen alkuperäiseen verrattuna.

> Kun tarkistat että järjestykset taulukoissa ovat päivastaisia, joudut vertaamaan olioita keskenään. Vertaa molemmissa taulukoissa olevien `Integer` -olioiden yhtäsuuruutta niiden `equals` -metodilla.
> 
> Tarvitset tässä yhden silmukan ja kaksi indeksiä. Toinen indeksi käy input -taulukkoa alusta loppuun, toinen output-taulukkoa lopusta alkuun. Joka silmukan kierroksella, tarkista assertilla että input-taulukon input -indeksissä oleva elementti on saman suuruinen kuin output -taulukon indeksin elementti.

Käytä `postCondition()`:n toteutuksessa **asserteja**, koska **jos jättöehdot eivät pidä paikkaansa, algoritmi on buginen** ja ainoa tapa ratkaista ongelma kehityksen aikana on **korjata bugit**. Teemme ongelman **todella** näkyväksi kaatamalla sovelluksen assertteja käyttäen.

Kun olet toteuttanut `postCondition()` -metodin, lisää metodin `ArrayReversals.reversed()` loppuun, viimeiseksi riviksi, kutsu `postCondition()` -metodiin.

Asserttien lisääminen ei tietenkään korjaa bugeja, mutta nyt ainakin tiedämme, tulostuksia katsomatta, että algoritmi ei toimi oikein, ja miksi ei toimi.

> Tämä on tavallaan mustalaatikkotestausta (*black box testing*, tuttu termi testauskurssilta), tiedämme jos jättöehtojen tarkistus kaataa sovelluksen, että *jossain* tämän algoritmin koodissa on bugi, muttemme vielä tiedä *missä* kohtaa.
> 
> Huomaa sekin, että tämä tarkistuskoodikin on koodia. Eli jos teet bugeja näissä *tarkistuskoodeissa*, silloin joko tarkistuskoodi ei huomaa toteutuksen virheitä, tai tarkistuskoodi ilmoittaa virheistä tilanteessa jossa sellaista ei ole tapahtunut. Ole siis huolellinen näiden metodien toteutuksessa ja kysy opettajilta apua jos et saa tätä toteutettua!

Kun **kokeilet** jättöehtojen tarkistuskoodin vaikutuksia `reversed()` -metodin käyttämiseen testiohjelmasta käsin, huomaat miten sovelluksen toiminta muuttuu. Sovellus kaatuu välittömästä kun `postCondition` -metodissa oleva assert ei pidäkään paikkaansa:

```
Exception in thread "main" java.lang.AssertionError: null
        at oy.tol.tira.task_01.ArrayReversals.postCondition(ArrayReversals.java:71)
        at oy.tol.tira.task_01.ArrayReversals.reversed(ArrayReversals.java:39)
```

Näet myös sen rivin jolla assert epäonnistui, ja voit sitten miettiä miksi juuri tämä väite koodin tilasta ei pitänyt paikkaansa. Jotta saisimme lisätietoa virheestä, käytämme sen löytämiseen silmukkainvariantteja. Jatka siis tehtävän suorittamista eteenpäin.


### Silmukkainvariantit bugien metsästyksessä

Osa bugeista johtuu siitä, että silmukat, joissa järjestyksen kääntäminen tehdään, ovat bugisia. Näiden bugien metsästykseen on hyvä käyttää *silmukkainvariantteja*.

Silmukkainvariantti tarkoittaa sitä, että *jokaisella* yksittäisellä silmukan suorittamisaskeleella on olemassa väite, jonka täytyy pitää paikkaansa. Assertteja voidaan käyttää tarkistamaan, että tämä ehto pitää aina paikkaansa.

Seuraavaksi toteutatkin näitä silmukkainvariantteja algoritmiin `ArrayReversals.reversed()`, jotta päästäisiin selvyyteen siitä, *miksi* silmukat ovat bugisia ja *miten*. 

> Vertauksena testaukseen, nyt teemme ikäänkuin valkealaatikkotestausta (*white box testing*), eli kurkistamme metodin sisälle, valaisemme sen laatikon ja katsomme mitä siellä on pielessä.

**Älä** tässäkään tehtävässä lähde ratkomaan algoritmin bugisuutta vielä itse, vaan harjoituksen kohteena on **silmukkainvariantin käyttö** bugin löytämisessä. **Lue** ohjeet alta ensin, kokeile silmukkainvariantin avulla bugin löytämistä ja vasta sitten kun bugi on näin paljastunut, korjaa se.

Kuten näimme yllä olevasta tulostuksesta, järjestys on käännetyssä (reversed) taulukossa täsmälleen sama kuin input -taulukossa, ja lisäksi viimeinen elementti käännetyssä taulukossa on `null`. Sama tapahtuu myös taulukon kanssa jossa on pariton määrä elementtejä.

*Suunniteltuna* ratkaisuna algoritmille on tässä ollut se, että kopioidaan input -taulukon elementit *alusta loppuun päin*, output-taulukkoon *lopusta alkuun päin*, jolloin silmukan päätyttyä input-taulukon elementit ovat output-taulukossa käänteisessä järjestyksessä.

Tässä algoritmin suunniteltu toimintalogiikka pseudokoodina:

```
1. luo uusi, samankokoinen taulukko output, kuin input -taulukkokin on.
2. määrittele indeksimuuttuja addIndex joka osoittaa output-taulukon loppuun (arvo on taulukon pituus - 1).
3. määrittele indeksimuuttuja index, joka lähtee input -taulukon alusta (arvo on nolla)
4. jokaiselle elementille input-taulukon indeksissä index = 0..<input.length
  4.1. sijoita input-taulukon elementti addIndex:n osoittamaan indeksiin output -taulukkoon
  4.2. vähennä addIndex:n arvoa yhdellä
4. palauta output -taulukko kutsujalle.
```

Jos katsotaan noita indeksien arvoja, mitä niiden *pitäisi* olla algoritmin edetessä, kun input -taulukossa on testiaineiston kuusi (6) elementtiä, numerot 1...6, indekseissä 0...5, silmukan eri kierroksilla näin:

input: [1, 2, 3, 4, 5, 6]

| Kierros | index  | addIndex | output -taulukko   |
|--------:|-------:|---------:|--------------------|
|       1 |      0 |        5 | [_, _, _, _, _, 1] |
|       2 |      1 |        4 | [_, _, _, _, 2, 1] |
|       3 |      2 |        3 | [_, _, _, 3, 2, 1] |
|       4 |      3 |        2 | [_, _, 4, 3, 2, 1] |
|       5 |      4 |        1 | [_, 5, 4, 3, 2, 1] |
|       6 |      5 |        0 | [6, 5, 4, 3, 2, 1] |


Kun tuota etenemistä katsotaan, hoksataan että, *jos* algoritmi toimisi oikein indeksejä käsitellessään, jokaisella kierroksella index + addIndex on arvoltaan viisi (5), eli *taulukon pituus miinus yksi*. 

*Jos* siis bugi on indeksien arvojen muuttamisessa, se paljastuu assertilla joka tarkistaa onko indeksien summa sama kuin taulukon pituus miinus yksi. Tämä on yksi silmukkainvariantti tämän algoritmin tapauksessa. Tuon ehdon pitää pitää *aina* paikkaansa, perustuen algoritmin suunnitteluun.

**Lisää** kyseinen assert silmukan sisälle **silmukkainvariantiksi** assertia käyttäen. Ylempänä luvussa "Mikä on assert?" mainittiin että voit saada assertin näyttämään myös arvon jos assertin ehto ei pidä paikkaansa. Tässä kohtaa voit käyttää vaikka tuota indeksien summaa.

Mitä tapahtuu? Löydätkö tämän silmukkainvariantin avulla silmukassa olevan bugin?

Jos et, kysy apua opettajilta, ja saa sitä kaveriltakin kysyä vinkkiä. Kaverilta ei saa *kopioida* ratkaisuja, se on plagiointia ja siitä saa kurssilta saman tien hylätyn merkinnän. Kirjoita jokainen koodirivi tässä ja muissakin harjoituksessa ihan itse, "omin sanoin".

Kun olet huomannut ja sen *jälkeen* **korjannut** addIndex:n arvon puuttuvan muuttamisen silmukassa, kokeile uudestaan, toimiiko algoritmi nyt? 

**Ei pitäisi toimia**, jos korjasit vain sen ongelman jonka ym. silmukkainvariantti paljasti. Tulostus on edelleen väärin (voit kommentoida `reversed()` -algoritmin lopussa olevan kutsun `postCondition` -metodiin tilapäisesti pois että näkisit saman kuin alla):

```console
3: Testing array with even (6) number of elements reversed()
Original:
[1, 2, 3, 4, 5, 6]
Reversed:
[1, 2, 3, 4, 5, null]
Expected:
[6, 5, 4, 3, 2, 1]

4: Testing array with odd (5) number of elements reversed()
Original:
[1, 2, 3, 4, 5]
Reversed:
[1, 2, 3, 4, null]
Expected:
[5, 4, 3, 2, 1]
```

Vaikka paljastimmekin bugin koodista, koodissa on siis vielä ongelmia. Itse asiassa mikään lopputuloksessa ei muuttunut.

Kun kerta algoritmin piti mennä niin, että silmukassa, kun input -taulukon index:ssä oleva elementti laitetaan output -taulukon addIndex:iin, niin **tarkistetaan** nyt varalta sekin että *ovatko ne elementit näissä kahdessa taulukoissa, näissä kahdessa indekseissä, aina arvoltaan samat*. Näinhän pitäisi aina olla, jos luet vielä tarkkaan ym. pseukokoodin tarkkaan läpi.

**Tee** siis **uusi assert**, jossa tarkistat tämänkin silmukkainvariantin: onko for-silmukan lohkon lopussa aina voimassa väite, että parametrina tulleen taulukon `array` indeksissä `index` oleva elementti on sama kuin `output[addIndex]` -indeksissä oleva elementti. Näinhän pitäisi olla jos algoritmin toteutus vastaisi suunniteltua pseudokoodia.

Huomaat että väite ei pidä paikkaansa, ja voit myös katsoa tulostuksesta, mitä `output[addIndex]`:ssä oikein on (käyttäen assertin muotoa joka näyttää halutun tiedon kaksoispisteen jälkeen, tässä opettajan toteutus tästä assert:sta):

```
Exception in thread "main" java.lang.AssertionError: null
        at oy.tol.tira.task_01.ArrayReversals.reversed(ArrayReversals.java:34)
        at oy.tol.tira.task_01.ArrayReversalClient.testReversedEvenNumberOfElements(ArrayReversalClient.java:57)
        at oy.tol.tira.task_01.ArrayReversalClient.testReversed(ArrayReversalClient.java:17)
```

Ainoa syy miksi assert ei pitäisi paikkaansa, vaan indeksissä on `null`, on se, että algoritmi ei laita silmukassa elementtiä output -taulukon *oikeaan* indeksiin `addIndex`. Etsi se rivi `reversed()` -metodin silmukan koodista, jossa tämä tehdään. 

**Huomaa virhe ja korjaa se**. Tarvittaessa lue uudestaan algoritmin suunniteltu pseudokoodi ja vertaa sitä toteutukseen.

Virheen korjaamisen jälkeen pitäisi tulostua seuraavaa (jättöehtojen tarkistuskutsu pois kommentoituna että näemme tulostuksen):

```console
B2: Testing array with even (6) number of elements reversed()
Original:
[1, 2, 3, 4, 5, 6]
Reversed:
[null, 5, 4, 3, 2, 1]
Expected:
[6, 5, 4, 3, 2, 1]
```

Nyt näemme, että taulukossa järjestys alkaa olemaan käännetty, mutta käännetyn taulukon alussa on silti vielä `null`. Ongelman havaitseminen jää kuitenkin vielä ihmisen huomattavaksi, ja olisi aina parempi että bugisen koodin bugisuus huomataan automaattisesti. Speksithän sanoivat että:

> "5. Käännetyn taulukon arvojen tulee olla samat kuin parametritaulukossa, mutta käänteisessä järjestyksessä."

Tämä ei selvästikään kokonaisuutena vielä toteudu. Tätä vartenhan tehtiin jo `reversed()` metodin yhteydessä se jättöehtojen tarkistus metodissa `postCondition()`. Ja se tarkistus kyllä tämän bugin huomaakin.

Jos kommentoit aiemmin pois jättöehtojen tarkistuksen nähdäksesi tulostuksia, **lisää** se takaisin `reversed()` algoritmin loppuun.

Tuloksena konsolissa pitäisi näkyä jälleen jotain tämän kaltaista, kuten opettajien malliratkaisukin tekee:

```console
B2: Testing array with even (6) number of elements reversed()
Original:
[1, 2, 3, 4, 5, 6]
Exception in thread "main" java.lang.AssertionError: null
        at oy.tol.tira.task_01.ArrayReversals.postCondition(ArrayReversals.java:100)
        at oy.tol.tira.task_01.ArrayReversals.reversed(ArrayReversals.java:68)
```

Eli kutsuttaessa `reversed()` -metodia, lopuksi `postCondition()` -metodissa oleva `assert` huomasi että elementit input ja output -taulukoissa eivät vastanneet toisiaan, ja output -taulukossa on null.

Voit tehdä vielä yhden assertin jolla tämä bugi tulee selkeästi esiin. Selvästikin nimittäin on nyt niin, että sijoittamisia `output` -taulukkoon tehdään *yhden kerran liian vähän*, koska sinne jää yksi null ja se jää nimenomaan taulukon alkuun. Tätä output -taulukkoahan täytettiin *lopusta alkuunpäin*. 

Kun tiedät miten for-silmukka toimii, tässä voidaan katsoa *for-silmukan jälkeen*, onko väite totta että `index` -muuttujan arvo kertoo että *koko* `input` -taulukko on käyty läpi, sijoittaen elementit `output` -taulukkoon.

`input` -taulukon läpikäynti on nimittäin tehty oikein silloin, kun **muuttujan `index` arvo on *for -silmukan jälkeen* sama kuin `input` -taulukon koko**. Jos et ole tästä ihan varma, niin **mieti vielä kerran miten for-silmukka toimii**, for-silmukoitahan on tehty Ohjelmointi 1 -kurssilta alkaen, joten tässä sen toimintaperiaatetta ei enää kerrata.

Lisää tämä assert `reversed()` metodin for-lohkon *jälkeen*, *ennen* `postCondition()` -metodin kutsua:

```Java
	for (/*empty*/; index < array.length - 1; index++) {
		// ...
	}
	assert(index == array.length) : index;
	postCondition(array, output);
```

Eli -- jos kerta koko taulukon elementtejä läpi käydessä, index:n arvoa kasvatetaan (nollasta alkaen) joka kierroksella, niin sitä kasvatetaan viimeisen kerran, kun kuuden elementin taulukossa käsiteltiin viimeistä indeksiä viisi (5), index:n arvo kasvaa kuudeksi (6), ja silmukan ehto `index < array.length` ei enää pidä paikkaansa jolloi... eiku... ööööö .... hetkinen... siis mitäh!?

Silmukan toistoehtohan on koodissa `index < array.length - 1`!! Jolloin kun taulukon pituus on 6, ja indeksi on kasvanut arvoon 4, ja se siitä kasvaa 5:ksi, ja se on **jo silloin** yhtäkuin `array.length - 1` (6-1 == 5) eikä silmukkaa enää jatketa!!! Vaikka **viimeinen indeksi input -taulukkoa on vielä käsittelemättä**! Silmukka lopettaa jo indeksin 4 jälkeen?!

Tuo on siis viimeinen bugi tässä algoritmissa, jonka saimme asserteilla kiinni. 

**Korjaa** nyt tämäkin off-by-one bugi tästä algoritmista. Nyt sen pitäisi toimia oikein.


## Luokkainvariantit

Viimeisenä oikeellisuuteen liittyvänä tehtävänä tutustutaan vielä luokkainvariantteihin (*class invariants*). Luokkainvarianteilla pyritään varmistamaan se että luokan olion *tila* (*state*) on oikeellinen.

Olion *tila* tarkoittaa käytännössä sen jäsenmuuttujien *nykyisiä* arvoja tietyllä ajan hetkellä.

Mistä tiedetään sitten onko olion tila "oikeellinen"? Se riippuu oliosta. Esimerkiksi, jos luodaan henkilö -olio, ja kyseisen henkilöluokan jäsenmuuttujat olisivat tällä tietyllä henkilö -oliolla:

* henkilötunnus: "220303+996H",
* syntymäaika vuonna 2003,
* sukupuoli: mies

Niin kyseisen olion tila  *ei olisi oikeellinen* (nykyisten suomalaisten henkilötunnuksen säännöillä), sillä kyseinen henkilötunnus kuuluisi henkilölle joka on syntynyt 22.3.1803 (erotinmerkki `+` on 1800 -luvulla syntyneillä) ja on sukupuoleltaan nainen (numero `996` on parillinen).

> Tuo esimerkki on testikäyttöön generoidusta henkilötunnuksesta, sellaista ei ole oikeasti syntyneellä ihmisellä.

Eli olion tilan oikeellisuus riippuu aina siitä, millaiseksi se luokka on suunniteltu ja mitä speksattuja sääntöjä näihin olion jäsenmuuttujien tilaan liittyy. Oikeellisuus riippuu siis tässäkin tapauksessa aina spekseistä, eli siitä millaiseksi ohjelmiston toiminta on suunniteltu.

> Analogia kurssien suoritukseen: kurssien tehtävänannot ja deadlinet ovat speksejä. Tehtävien suorittaminen speksejä noudattaen johtaa hyvään arvosanaan. Speksien noudattamatta jättäminen taas johtaa arvosanan alentamiseen tai kurssisuorituksen hylkäämiseen.

### Luokkainvariantti -tehtävä

Tässä tehtävässä saat taas valmista koodia joka kuitenkaan ei aina toimi oikeellisesti. Tavoitteena on jälleen soveltaa invariantteja tarkistamaan ettei luokan tilaa voi asettaa väärin.

Tutustu tehtävän luokkaan `CustomDate` jonka avulla voidaan käsitellä päivämääriä. Se sisältää jo *valmiiksi* joitakin esiehtojen tarkistuksia, ja *tyhjän*  metodin `invariant()` luokkainvarianttien tarkistamista varten. 

Huomaa myös että `invariant()` -metodia kutsutaan valmiiksi **kaikissa niissä luokan metodeissa, joissa luokan tilaa voi muuttaa**, metodin lopuksi. Tämä on hyvin tyypillistä luokkainvarianteille. Aina kun tilaa ollaan muutettu, varmistetaan että olion tila on validi, oikeellinen. Jos ei ole, joko heitetään poikkeus tai (kehityksen aikana) kaadetaan ohjelma assertilla.

Luokan toimintaa testataan luokan `CustomDateClient` metodeissa, joita kutsutaan luokan `main` -metodista käsin. Huomaa taas että `.vscode/launch.json` -tiedostossa assertit on otettu käyttöön testatessa tämän tehtävän oikeellisuutta.

Tavoitteena tehtävässä on parantaa `CustomDate`:n toteutusta lisäämällä koodiin invariantteja jotka estävän virheellisten päivämäärien aikaansaamisen luokan avulla. Samalla invarianttien avulla voidaan löytää ja korjata bugeja toteutuksessa.

### Suorita koodi

Kun suoritat `CustomDateClient` -luokan `main` -metodin, toteutus tulostaa seuraavaa:

```
===> testValidDatesConstruction()
GOOD JOB: Didn't complain about valid dates

===> testInvalidDatesConstruction()
NOT GOOD: Didn't catch 7 test cases

===> testPreConditionTest()
Date is now: 31.12.2025
Trying to set the month of that date to 2...
Date is now: 31.2.2025
NOT GOOD: Date is still wrong now!

===> testsuccessfulAdvance()
Date is now 20.2.2025, advancing it eight (8) days...
Date is now 28.2.2025
GOOD JOB: we passed the assert so date 28.2.2025 was as expected

===> testSuccessfulAdvance2()
Date is now 20.2.2025, advancing it nine (9) days...
Date is now 1.3.2025
GOOD JOB: we passed the assert so date 1.3.2025 was as expected

===> testFailingAdvance()
Date is now 20.12.2025, advancing it two weeks (14 days)...
Date is now -2147483648.-2147483638.2025
NOT GOOD: advancing a date failed: -2147483648.-2147483638.2025

===> testChangingLeapYearToNonLeapYear()
Date is now 29.2.2000, setting year to non-leap year...
Date is now: 29.2.2025
```

Kaikki rivit joissa on teksti "NOT GOOD" ilmaisevat ei-oikeellista toiminnallisuutta:

* `testInvalidDatesConstruction()` -metodissa luodaan päivämääräolioita jotka eivät ole oikeellisia päivämääriä. Voit katsoa mitä nämä päivämäärät ovat, annetun testikoodin jäsenmuuttujasta `DateValues [] invalidValues`.
* `testPreConditionTest()` -metodissa luodaan päivämääräolio arvolla 31.12.2025, ja yritetään vaihtaa kuukaudeksi 2 (helmikuu). Tämä onnistuu, ja saadaan *ei-oikeellinen* päivämäärä 31.2.2025.
* `testFailingAdvance()` -metodissa luodaan päivämäärä 20.12.2025, ja siirretään sitä 14 päivää eteenpäin, ja tuloksena on päivämäärä -2147483648.-2147483638.2025 joka on tietysti ihan väärin.
* `testChangingLeapYearToNonLeapYear()` -metodissa asetetaan päivämääräksi karkauspäivä 29.2.2000, ja vaihdetaan vuodeksi 2025. Saadaan tulokseksi ei-oikeellinen päivämäärä 29.2.2025, koska vuosi 2025 ei ole karkausvuosi jolloin helmikuussa on 28 päivää. Ei toimi oikein. 

Huomaa viimeisessä kohdassa, että tässä client ei tarkista saamansa päivämäärän oikeellisuutta, toisin kuin joissakin muissa tilanteissa, eikä tulosta "GOOD JOB" tai "NOT GOOD". Esimerkkinä tilanteesta, että jos client ei suhtaudu epäluuloisesti ja oleta bugisutta ja tarkistele saamiansa tietoja, se tekee virheen... Koska tuo saatu päivämäärähän ei ole oikeellinen. Tähän tilanteeseen ei pitäisi päätyä. Joko client voi luottaa lopputulokseen, tai sitten luokka kertoo että tapahtui virhe. Tässä ei tapahdu kumpaakaan. Parantamista `CustomDate`:n koodissa siis riittää.

Invarianttien ja parin bugin korjaamisen jälkeen **kaikissa** tulostuksissa testiohjelmasta pitäisi saada ulos ainoastaan GOOD JOB -tekstejä, eikä yhtään NOT GOOD -tulostuksia, ja kaikki päivämäärät ovat oikeellisia. Joko bugi on korjattu, tai invariantit kertovat käyttäjälle että ei tällaista voi tehdä, teit virheen.

**Tutki** mitä näiden testimetodien testeissä yritettiin tehdä ja miksi se epäonnistui, mitä ei-oikeellista tässä oikein tapahtui mitä ei olisi saanut tapahtua.

> Muista että oikeellisuus on aina oikeellisuutta vasten joitakin **speksejä eli määrittelyjä**. Jotka kertovat mikä on se oikeellinen, odotettu ja haluttu toiminnallisuus. Jos et tiedä etkä ymmärrä speksejä ja mitä koodilta halutaan, et voi *millään keinoilla* tuottaa oikeellista koodia -- muuten kuin tuurilla. Tämä on siis aina validi väite kaikessa koodaamisessa. Speksit on vain ymmärrettävä, ja jos ei ymmärrä, sitten kysytään, keskustellaan ja selvitetään mitkä ne oikeellisen toiminnan speksit ovat. Eli jos et ymmärrä kurssin tehtävien speksejä, **kysy opettajalta**.

### Muodostimet ja luokkainvariantti

Ensimmäinen asia on hoitaa kuntoon `CustomDate`:n muodostimet (*constructor*). Testi `testInvalidDatesConstruction()` luo päivämääräolioita väärillä päivämäärillä. Nämä tulee saada kiinni luokkainvariantilla. Muodostimissa on jo valmiiksi kutsu `invariant()` -metodiin, esim:

```Java
	public CustomDate(final int d, final int m, final int y) throws IllegalStateException {
		this.day = d;
		this.month = m;
		this.year = y;
		invariant();
	}
```
**Toteuta** `invariant()` -metodi siten että se tarkistaa päivämäärän oikeellisuuden `day`, `month` ja `year` -jäsenmuuttujia sekä luokassa olevia apumetodeja käyttäen.

**Kokeile** mitä testiohjelma tulostaa nyt. Jos olet toteuttanut `invariant()`:n tarkoituksenmukaisesti, tulostuu aluksi seuraavaa:

```
===> testValidDatesConstruction()
GOOD JOB: Didn't complain about valid dates

===> testInvalidDatesConstruction()
GOOD JOB: Catched class java.lang.IllegalStateException for date 32.1.1900
GOOD JOB: Catched class java.lang.IllegalStateException for date 31.11.2025
GOOD JOB: Catched class java.lang.IllegalStateException for date 3.13.2025
GOOD JOB: Catched class java.lang.IllegalStateException for date -1.1.2025
GOOD JOB: Catched class java.lang.IllegalStateException for date 1.-1.2025
GOOD JOB: Catched class java.lang.IllegalStateException for date 29.2.1800
GOOD JOB: Catched class java.lang.IllegalStateException for date 29.2.1900
...
```

Näet että *nyt* luokkainvarintin avulla voidaan *estää* väärien päivämäärien luominen. Viimeiset kaksi päivämäärää ovat esimerkiksi väärin, vuodet 1800 ja vuodet 1900 *eivät* ole karkausvuosia. Kuten tiedät, jos Javan luokan muodostimessa tapahtuu poikkeus, oliota ei edes luoda, eli testissä luotava muuttuja `var testDate = new CustomDate(value.d, value.m, value.y);` eli `testDate` olisi `null` kun poikkeus heitetään.

Jos et saa vastaavanlaisia tuloksia, **tarkista** että toteutuksesi `invariant()` -metodissa on varmasti oikein.

**Tarkista** mitä muita virheitä korjaantui, eli tulostuksissa on nyt joissakin muissakin kohtaa "GOOD JOB". Koska muuallakin olion tilaa muuttavissa metodeissa on valmiina kutsu `invariant()` -metodiin.

Testi `testPreConditionTest()` tuottaa pienen pettymyksen:

```
===> testPreConditionTest()
Date is now: 31.12.2025
Trying to set the month of that date to 2...
GOOD JOB: Class invariant catches the error
Date is now: 31.2.2025
NOT GOOD: Date is still wrong now!
```

Tässä luokkainvariantti saa kiinni virheen, kun yritetään laittaa päivämääärän 31.12.2025 kuukausi helmikuuksi, `invariant()` heittää kyllä poikkeuksen, mutta päivämäärä on silti tässä nyt väärin. Poikkeuksen heittäminen on hyvä asia, mutta se että päivämäärä silti saatiin asetettua vääräksi, ei ole hyvä asia. Olisi parempi jos päivämäärää *ei voida* laittaa vääräksi ollenkaan. 

Virhettä ei olisi ehkä edes huomattu, jos kyseisen *testin koodi* ei tarkistaisi lopuksi onko päivämäärä oikeellinen:

```Java
		if (date.getDay() > CustomDate.daysInMonth(date.getMonth(), date.getYear())) {
			System.out.println("NOT GOOD: Date is still wrong now!");
		}
```

**Korjaa** metodin `CustomDate.setMonth()` -toteutusta niin että virhe saadaan kiinni (heitetään poikkeus) *ja* päivämäärää ei muuteta, jos annettu parametri saisi aikaan väärän päivämäärän. Vinkki: tee tähänkin esiehdon (*precondition*) tarkistus.

Korjauksen jälkeen pitäisi tulostua:

```
===> testPreConditionTest()
Date is now: 31.12.2025
Trying to set the month of that date to 2...
GOOD JOB: precondition catches the error
Date is now: 31.12.2025
```

Eli saatiin virhe kiinni ja päivämäärää *ei muuteta* alkuperäisestä, koska siitä olisi tullut ei-oikeellinen.

Tämän korjauksen jälkeen tulostuksissa on vielä kaksi virhettä, katsotaan näistä jälkimmäinen ensin:

```
===> testChangingLeapYearToNonLeapYear()
Date is now 29.2.2000, setting year to non-leap year...
GOOD JOB: Class invariant catches the error
Date is now: 29.2.2025
```

Näissä tulostuu "GOOD JOB" taas esimerkiksi siitä että jos `CustomDate` -luokka itse hoitaa asian puolittain oikein, ja client eli luokan käyttäjä luottaa siihen, asiat eivät siltikään mene oikein. Samaan tapaan kuin `setMonth` -metodi; sen lopussa invariant ilmoitti että asia ei ole kunnossa mutta sitä ennen kuitenkin muutti kuukauden tarkistamatta asiaa. Onneksi korjasit tämän bugin `setMonth` -metodissa.

**Korjaa** tämäkin virhe, jossa asetetaan vuosi kutsumalla `setYear` -metodia ilman asiaankuuluvia esiehtojen tarkistuksia. Samaan tapaan kuin kuukaudenkin asettamisessa, annetussa koodissa *ei tarkisteta* voidaanko vuosi vaihtaa toiseksi siten että päivämäärä on vaihtamisen jälkeenkin oikeellinen. **Lisää** tähäkin vastaava tarkistus, siten ettei päivämäärää muuteta *ja* heitetään asiaan liittyvä poikkeus.

Korjauksen jäkeen pitäisi tulostua:

```
===> testChangingLeapYearToNonLeapYear()
Date is now 29.2.2000, setting year to non-leap year...
GOOD JOB: precondition catches the error
Date is now: 29.2.2000
```

Eli virheestä ilmoitetaan *ja* päivämäärää ei muuteta virheelliseksi.

Nyt olisi vielä korjattava ongelmat `CustomDate.advance` -metodissa, joka näkyy tästä tulostuksesta:

```
===> testFailingAdvance()
Date is now 20.12.2025, advancing it two weeks (14 days)...
GOOD JOB: Class invariant catches the error
Date is now: -2147483648.-2147483638.2025
```

Tässäkin metodin lopussa oleva `invariant()` -kutsu varmistaa että tiedämme, ettei olion tila ei ole enää oikeellinen, siksi tuossa näkyy "GOOD JOB". Kyseessä on kuitenkin bugi koodissa, ei siinä miten koodia kutsutaan, eli esiehdon tarkistus tässä ei auta, ja saatu päivämääräkin on virheellinen. Metodi tarkistaa jo esiehdossaan että parametri on oikeellinen:

```Java
	public void advance(final int days) {
		if (days <= 0) {
			throw new IllegalArgumentException("Days to advance must be > 0");
		}
...
```
Joten siitä nyt ei ole kyse. Huomaat myös että kaksi aiempaa testiä tälle `CustomDate.advance()` -toiminnallisuudelle *toimii oikeellisesti*:

```
===> testsuccessfulAdvance()
Date is now 20.2.2025, advancing it eight (8) days...
Date is now 28.2.2025
GOOD JOB: we passed the assert so date 28.2.2025 was as expected

===> testSuccessfulAdvance2()
Date is now 20.2.2025, advancing it nine (9) days...
Date is now 1.3.2025
GOOD JOB: we passed the assert so date 1.3.2025 was as expected
```

Voit tarkistaa tämän kalenterista laskemalla itse.

Voimme olla kuitenkin tyytyväisiä siihen, että luokkainvariantti ilmoittaa virheestä. Tässä voisi taas hyödyntää silmukkainvariantteja, mutta katsotaan nyt ongelma läpi tulkitsemalla koodin käytöstä ja epämuodollisella induktion soveltamisella.

Huomaatko **mitä eroa** on tuossa epäonnistuvassa tapauksessa onnistuviin tapauksiin? Selittäisikö se sitä, miksi tuo yksi tapaus ei toimi oikein?

Jos et, erohan on se, että epäonnistuvassa testitapauksessa *vuosi vaihtuu* eli aloituspäivämäärä on 20.12.2025, ja päivämäärää siirretään siitä kaksi viikkoa (14 päivää) eteenpäin, jolloin pitäisi saada päivämäärä 3.1.2026. Saadaan kuitenkin aikamoinen sotku -2147483648.-2147483638.2025.

Miten ihmeessä päivä ja kuukausi voivat tulla negatiivisiksi arvoiksi, ja vieläpä näin isoiksi negatiivisiksi arvoiksi? Kun katsot `advance()` -metodin toteutusta, päivämäärän päivää ja kuukautta muutetaan seuraavasti:

```Java
			do {
				final int remainingDays = CustomDate.daysInMonth(month, year) - day;
				if (day + toAdd > CustomDate.daysInMonth(month, year)) {
					toAdd -= remainingDays + 1;
					day = 1;
					month += 1;
				} else {
					day += toAdd;
					toAdd = 0;
				}
			} while (toAdd > 0);
		} else {
			day += days;
		}
```
Eli aina lasketaan montako päivää vielä pitää päivämäärää siirtää, selvitetään montako päivää kuussa on, että pitääkö mennä seuraavan kuun puolelle, lisätään tällöin kuukauden arvoa jne.

Muuttujan `day` arvoa muutetaan kuitenkin aina lisäämällä (`day += toAdd` ja `day += days`), ja samoin kuukauden arvoa (`month += 1`). Miten siis voi olla että arvot ovat tuossa virheellisessä päivämäärässä negatiivisia?

Nyt kun käytät ihan maalaisjärkeä ja tietoa edeltäviltä kursseilta, huomaat että on kaksi mahdollisuutta miksi näin voisi käydä:

1. Kokonaisluvun ylivuoto (*integer overflow*): silmukka pyörii niin kauan, että `day += days` tai `day += toAdd` tapahtuu niin monta kertaa että mennään arvoon `Integer.MAX_VALUE` ja seuraavan lisäyksen jälkeen arvoksi tuleekin todella suuri negatiivinen kokonaisluku.
2. Muuttujan `remainingDays` arvoksi tulee *negatiivinen* arvo ja sitä kautta päivämäärää *pienennetään*, ei kasvateta, vaikka `advance` -metodissa pitäisi.

Katsotaan tuota metodia `CustomDate.daysInMonth(month, year)` josta `remainingDays` saa arvonsa:

```Java
	public static int daysInMonth(final int month, final int year) {
		switch (month) {
		case 1, 3, 5, 7, 8, 10, 12:
			return 31;
		case 4, 6, 9, 11:
			return 30;
		case 2:
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		default:
			return -1;
		}
	}
```

Tässä katsotaan mistä kuukaudesta on kyse ja palautetaan kyseisen kuukauden päivien lukumäärä. Vuosikin pitää tietää, sillä helmikuussa on joko 28 tai 29 päivää, riippuen siitä onko karkausvuosi vai ei (`isLeapYear` kertoo sen).

Jos kuukausi ei ole validi, metodi palauttaa -1.

Nyt kun palaat vielä takaisin `advance()` -metodin toteutukseen, ja huomaat että kuukauden arvoa tarvittaessa aina lisätään yhdellä, hoksaat että *entäs jos vuosi vaihtuu eli pitäisi mennä kuukaudesta 12 kuukauteen 1...*.

Tällöin käy niin että `daysInMonth` -metodissa mennään default -caseen ja palautetaan -1 -- jolloin päivien käsittely ei enää toimikaan oikein!

Tässä on siis kaksi virhettä:

1. `advance` -metodin pitäisi toimia siten, että jos kuukauden arvon lisäämisen jälkeen se onkin 13, kuukauden arvoksi laitetaan 1 *ja* vuoden arvoon lisätään yksi.
2. `daysInMonth` -metodista puuttuu parempi virheen käsittely. Jos joku kysyy montako päivää on kuukaudessa joka *ei ole* välillä 1...12 (*inclusive*), ei pitäisi palauttaa -1, vaan pitäisi heittää `IllegalArgumentException("Parameter month has an illegal value");`.

**Tee kyseiset korjaukset** metodeihin. 

`daysInMonth` -metodissa on nyt hyvä tilaisuus oppia uusi ja hyödyllinen paikka laittaa tuo invariantti -- laita se `default:` -caseen. Heitä siellä tuo poikkeus. Yleisesti, jos olet ihan varma että switch/case -rakenteessa on aina vain ja ainoastaan tietyt validit arvot joita tarkistella case -kohdissa, silloin default case voi heittää poikkeuksen (tai tehdä `assert(false)`) -- viesti on siis se, että "tämä tapaus ei kerta kaikkiaan voi tapahtua, sillä jos se tapahtuu, kyseessä on virhe". Toimimme nyt näin. Jos joku kyselee montako päivää on kuussa jonka arvo on jotain muuta kuin 1...12, heitetään poikkeus.

### Luokkainvarianttien yhteenveto 

Näiden korjausten jälkeen, tulostuksissa pitäisi näkyä vain "GOOD JOB" **ja** kaikki tulostetut päivämäärät ovat joko oikeellisia tai niiden ilmoitetaan olevan ei-oikeellisia. Ei ole mahdollista luoda ei-oikeellisia päivämääriä ja luokan käyttäjälle kerrotaan poikkeuksilla jos luokkaa yritetään käyttää väärin, eli speksien vastaisesti.

## Koko tehtävän yhteenveto 

Huomaat kun käyt yllä olevan tehtävän läpi, mikä on assertien ja poikkeusten heittämisen arvo koodissa. Moni muuten silmien ohi tulostuksissa vilistävä bugi jäisi kenties ohjelmaan, "kun en hoksannut katsoa tulostuksia". 

Yleensäkin, oikeissa ohjelmissahan ei mitään konsoliin edes tulosteta (tai edes pitäisi tulostaa), eikä kukaan ole katsomassa niitä konsolitulostuksia, joten virheiden huomaaminen tulostuksien perusteella ei ole edes koskaan hyvä tai toimiva idea. **Siksi - käytä invariantteja**!

> Toki edelleen oikeissa softissa on hyvä tuottaa *lokitietoa* virhetilanteista, mutta siihen ei kannata käyttää konsolitulostuksia vaan näitä varten erikseen tehtyjä loggauskirjastoja, jotka tallentavat lokitietoa myös tiedostoihin. Paljon parempi vaihtoehto virheiden jäljitykseen ja analyysiin.

Tässä invariantteja käytiin läpi ohjatusti, varmistaen oikeellisuus ja osoittaen bugit joita valmiissa koodissa oli.

Oikeassa tilanteessahan on niin, että teet uutta koodia (tai muokkaat vanhaa), ja kukaan ei ole välttämättä sanomassa että a) tässä on bugeja tai b) laita koodiin tällaisia assertteja ja esi- sekä jättöehtotarkistuksia tähän kohtaan.

Siispä olisi hyvä opetella ja ottaa tavaksi **miettiä algoritmeja kirjoittaessa**:

1. Mitä ehtoja ja sääntöjä **parametreihin** liittyy ja kirjoittaa **heti**, ennen metodin varsinaisen toteuksen aloittamista, **esiehtotarkistukset**, jotta saisit heti kiinni bugit jotka liittyvät ei-oikeellisiin parametreihin.
2. Mikä pitäisi olla algoritmin prosessoinnin **oikeellinen lopputulos**, ja kirjoittaa **heti**, ennen metodin varsinaisen toteutuksen aloittamista, **jättöehtotarkistukset**, jotta saisit heti kiinni bugit oman algoritmisi toteutuksesta.
3. Harkita onko tarpeen (heti tai vasta bugien tullessa?) toteuttaa invariantteja, erityisesti silmukkainvariantteja algoritmiin (joko asserteilla tai poikkeuksilla), jotta voisit varmistua siitä että algoritmisi *askeleet* toimivat oikeellisesti, oli se sitten omaa koodia tai omien tai toisten toteuttamien metodien kutsumista algoritmin sisältä.

Silloin kun algoritmilla on kovia suorituskykyvaatimuksia (aikatehokkuus), kannattaa joko käyttää asserteja (jolloin ne eivät hidasta tuotantokäytössä softan toimintaa) tai kommentoida pois invariantit koodista **sen jälkeen** kun oikeellisuus on testattu perusteellisesti. Jos ja kun uusia bugeja ilmenee, invariantit voi ottaa pois kommenteista testauksen helpottamiseksi ja bugien löytämiseksi.