# Taustaa 

**Koodin oikeellisuuden** varmistamisessa voidaan käyttää niinsanottuja *invariantteja*. Oudosta termistä huolimatta, asia on aika yksinkertainen.

Invariantti tarkoittaa ohjelmakoodissa tilannetta, **jonka tulee aina pitää paikkaansa**. Esimerkiksi päivämääräoliossa jos on jäsenmuuttujat `day`, `month` ja `year`, on olemassa sääntö että noiden kolmen arvon yhdessä tulee esittää joku oikea ja validi päivämäärä tietyssä kalenterijärjestelmässä (esim. meidän gregoriaaninen kalenterimme).

Tällöin esimerkiksi päivämäärä 29.2.2025 (day, month, year) *ei ole* validi päivämäärä (vuosi ei ole karkausvuosi), ja tuo invariantti, eli ehto oikeellisuudesta ei pidä paikkaansa. Samoin päivämäärä 33.1.1976 ei pidä paikkaansa, sillä tammikuussa ei ole 33 päivää.

Kyse on siis siitä, että on olemassa tietyt säännöt tai speksit, suunnittelu (*design*) jota koodi toteuttaa. Jos koodi ei toteuta suunnittelua, se on virheellistä, ei-oikeellista. Jos tarkoitus *oli* toteuttaa se suunnittelu, mutta tuli tehtyä virheitä, silloinkin koodi on bugista, ei-oikeellista. Toki sekin on mahdollista että koodi on toteutettu täsmälleen kuten suunnitelma sanoo, mutta *bugi onkin suunnitelmassa*. Silloin suunnitelma pitää korjata ja korjata koodikin suunnitelman mukaisekssi.

Invarianttien paikkaansapitävyyttä voidaan tarkistaa koodissa esimerkiksi asserteilla ("väittämä" jonka tulee pitää paikkaansa), tai heittämällä poikkeuksia (*exception*) jos joku tietty ehto ei pidä paikkaansa.

Lue tämä materiaali pohjaksi harjoitukselle. Tässä käsitellään näitä asioita Javan näkökulmasta, muissa ohjelmontikielissä on samantyyppisiä asioita käytettävissä.

## Lähteitä

Lisätietoa Javan asserteista voit [lukea täältä](https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html), ja [poikkeusten käsittelystä täältä](https://docs.oracle.com/javase/tutorial/essential/exceptions/index.html).

Muita lähteitä:
* https://en.wikipedia.org/wiki/Design_by_contract
* https://en.wikipedia.org/wiki/Invariant_(mathematics)#Invariants_in_computer_science
* https://en.wikipedia.org/wiki/Assertion_(software_development)
* https://en.wikipedia.org/wiki/Postcondition
* https://en.wikipedia.org/wiki/Precondition

## Miksi invariantteja pitäisi sitten koodissa tarkistaa?

Koodin toteutuksessa pitäisi toteuttaa annetut speksit. Jokainen funktio tai metodi lupaa tehdä jonkin asian (*design-by-contract*), jonka oikeellisuus voidaan tarkistaa invarianteilla.

Invariantteja on monenlaisia, esimerkiksi:

1. **Esiehdot** (*precondition*): ennen kuin koodinpätkää voi suorittaa, tietyt ehdot on oltava voimassa.
2. **Jättöehdot** (*postconditions*): kun koodinpätkä on suoritettu, sen piti saada aikaan tietty *oikeellinen* tulos.
3. **Silmukkainvariantit** (*loop invariant*): kun toistuvaa silmukkaa suoritetaan, *jokaisella* silmukan pyörähdyskerralla tiettyjen ehtojen pitää pitää paikkaansa, sekä
4. **Luokkainvariantit** (*class invariant*): luokan jäsenmuuttujien arvojen pitää olla loogisia, usein myös toistensa suhteen.

Näissä kaikissa tilanteissa voidaan tarkistaa, pitävätkö ne koodin suunnitellut suorittamisen ehdot paikkaansa vai eivät. Jos näitä asioita ei tarkista, koodiin voi jäädä bugeja eli se on ei-oikeellista. Kun bugista koodia suoritetaan, eli koodin suoritusehdot eivät pidä paikkaansa, ohjelma sekoilee, sotkee tietoja, toimii väärin, ja saattaa aiheuttaa merkittäviäkin taloudellisia tai inhimillisiä vahinkoja tai jopa johtaa ihmishenkien menetykseen.

Invarianttien käyttö auttaa seuraavasti:

* Algoritmia **ei edes yritetä suorittaa** tilanteessa, joissa se *ei voi* toimia oikeellisesti. 
  * Jos algoritmi on monimutkainen, pitkä ja hidas, virhe voi ilmestyä vasta jonkin aikaa sen suorituksen oltua menossa, joka on kaikki **tuhlattua suoritusaikaa**. 
  * Algoritmi voi myös mahdollisesti, ennenkuin bugi ilmenee, **sotkea** peruuttamattomasti sovelluksen dataa, joka on nyt sitten (ainakin osin) sekaisin. Tämä on nyt siis vältetty.
* **Koodia kutsuva koodi saa tietää heti, mikä on pielessä**; Ei tarvitse arvailla, miksi tuli mitäkin outoja poikkeuksia koodin suorituksesta, kun algoritmi kertoo heti missä se vika on: "parametrit eivät kelpaa, tarkista mitä välität parametreina, lue speksit".
* Vakavien virheiden tapahtuessa, **koko sovellus kaadetaan välittömästi** asserteilla. Koodari ei voi edes yrittää jättää heitettyjä poikkeuksia huomioimatta. Sovelluksen kaatumisen huomaa, **varmasti**. Näin tehdään tarkoituksellisesti yleensä vain kehityksen aikana, ei tuotantokäytössä.

## Mikä on assert?

Koodissa oleva assert (väite, vakuutus, vahvistus) tarkistaa onko joku tietty ehto tosi vai ei. Jos ehto ei ole tosi, ohjelman suoritus **pysäytetään välittömästi**. Esimerkiksi koodissa oleva assert väittää että taulukoiden `input` ja `output` kokojen tulee olla sama...:

```Java
   assert(input.length == output.length);
```

...ja jos väite ei pidä paikkaansa, pysäytetään ohjelman suoritus, ja konsoliin tulostuu esimerkiksi:

```console
Exception in thread "main" java.lang.AssertionError
        at oy.tol.tira.task_01.ArrayReversals.postConditions(ArrayReversals.java:70)
```

Assert voidaan myös kirjoittaa ilman sulkuja, ja sen perässä (kaksoispisteen jälkeen) voi olla myös joku arvo, esim. ylläoleva voidaan kirjoittaa myös:

```Java
   assert input.length == output.length : input.length;
```

Vaikutus on muuten sama, mutta konsoliin tulostuu myös taulukon `input` senhetkinen pituus, jos väite ei pitänyt paikkaansa. Joskus assertin yhteydessä näkyvästä muuttujan arvosta voi olla siis hyötyä virheen analyysissä.

> Huomaa että Javan assert:it, joita tässä käytetään, toimivat *vain* jos ne erityisesti otetaan käyttöön virtuaalikoneen käynnistyksessä, tietyllä parametrilla. 

> Joissakin muissa kielissä (C/C++, Swift,...) assertit ovat käytössä ns. debug -käännöksessä, ja kun ohjelma sitten käännetään ns. release -käännökseksi, assertit käytännössä poistetaan käännetystä koodista. Javassa ei ole mitään erityistä debug tai release -käännöstä, kuten "oikeissa" käännettävissä ohjelmointikielissä kuten C/C++ ja vaikkapa Swift.

**Normaalisti assertit eivät siis ole osa toimivaa käännettyä Java -sovellusta**. Siksi assertien käyttö on nimenomaan osa **kehityksen aikaista** ohjelmiston koodin oikeellisuuden tarkistusta ja bugien metsästystä. 

Kun kehitysvaihe lähestyy loppuaan, virheenkäsittely ja virheiden löytyminen pitäisi tehdä yksikkötestien ja poikkeusten käsittelyn avulla. Toki muutkin laadunvarmistuksen keinot, kuten oikeellisuuden todistaminen "käsin" koodia tulkitsemalla, induktio (kts. luentomateriaali) sekä koodin katselmointisessiot ovat kaikki hyviä käytäntöjä ohjelmistojen laadun varmistamiseksi, joita kannattaa käyttää.


## Mikä on poikkeus?

Assert on eri asia kuin poikkeus (*exception*). Poikkeukset ovat käytössä aina, sekä ohjelmiston kehityksen aikana, että tuotantokäytössä, eikä niitä erikseen tarvitse Javassakaan "laittaa päälle".

> Poikkeuksien heittäminen ei todellakaan aina tarkoita sitä että koodi olisi bugista. Ohjelmisto toimii aina osana ympäristöään, ja jos tilanne ympäristössä estää koodin suorittamisen (esim. verkkoyhteys katkesi tai tiettyä tiedostoa ei olekaan olemassa), se on *poikkeustilanne*, jonka takia koodi voi heittää poikkeuksen. Heitetty poikkeus tulisi aina kuitenkin käsitellä; jotain meni pieleen, mitä tälle asialle pitäisi tehdä?

Poikkeuksen eli poikkeuksellisen tilanteen sattuessa heitetään (*throw*) poikkeus, ohjelman *suoritus jatkuu*, siten että meneillään olevan operaation suoritus kyllä keskeytetään eikä seuraavia koodirivejä suoriteta. Ohjelman suoritus siis jatkuu toisin kuin assertien kanssa -- metodikutsupinossa usein ylempänä olevasta `try/catch` -lohkosta, jossa kyseinen heitetty poikkeus otetaan kiinni (*catch*) ja virhe käsitellään jotenkin.

> Toki silloin, jos ohjelmassa ei edes main:ssa ole kyseisen heitetyn poikkeuksen kiinni ottamista varten `catch` -lohkoa, silloin ohjelman suoritus pysähtyy kyllä. Jos main ei käsittele poikkeusta ja jatka jotenkin suoritusta poikkeuksista huolimatta, vaan main:sta palataan myös -- silloin ohjelman suoritus loppuu.

Esimerkiksi jos yllä oleva koodi taulukoiden pituuden varmistamiseen olisi toteutettu poikkeuksilla näin...:

```Java
   if (input.length != output.length) {
		throw new RuntimeException("Input and output arrays must have equal lengths");
	}
	// Täällä lisää koodia...
```
...ohjelma jatkaa suoritustaan, mutta ei tuosta koodilohkosta eteenpäin seuraaviin suoritettaviin lauseisiin, vaan suoritus jatkuu **ylempänä** [metodikutsupinossa](https://en.wikipedia.org/wiki/Call_stack) olevassa try/catch -rakenteessa, jossa napataan kiinni `RuntimeException` -poikkeus (tai `Exception`, joka on `RuntimeException`:n yläluokka). Siellä poikkeus käsitellään (tulisi käsitellä) ohjelman logiikan kannalta järkevästi, mitä se milloinkin voisi ollakaan.

Järkeviä poikkeustenkäsittelytapoja ovat *esimerkiksi*:

* Peruutetaan operaatio (vähän niinkuin tietokantojen kanssa, transaktio peruutetaan, *rollback*), ja ilmoitetaan virheestä käyttäjälle joka voi yrittää käynnistää toimintoa uudelleen, samasta lähtötilanteesta kuin ensimmäiselläkin kerralla.
* Jos ongelma oli syöttötiedoissa, ilmoitetaan ongelmista käyttäjälle, joka voi korjata syötteet oikeellisiksi ja suorittaa operaatio uudelleen.
* Yritetään operaatiota saman tien tai pienen viiveen jälkeen uudelleen, jos ongelma oli vaikkapa verkkoyhteydessä. Jos operaatio ei vieläkään onnistu, pyydetään käyttäjää tarkistamaan että verkkoyhteys toimii.
* Jos tiedostoa, joka piti tallentaa, ei ollutkaan olemassa, luodaan se ja sen jälkeen jatketaan tallentamista. Toki tämän voisi toteuttaa ilman poikkeuksiakin niin että koodi ensin itse tarkistaa onko tiedostoa olemassa, ja jos ei ole, luo sen. Tai käyttää sellaista ohjelmointirajapintaa joka hoitaa tämän automaattisesti.
* Jne., jotain sovelluksen toiminnan kannalta järkevää. 

> Ohjelmiston suunnittelussa ja toteutuksessa yksi **tärkeä** asia joka pitää huomioida on se, miten ja missä kohtaa koodia sovellus käsittelee järkevästi mitäkin virheitä joita sen suorituksessa voi tapahtua. Eli minne ne try/catch -rakenteet kannattaa sijoittaa ohjelman arkkitehtuurissa. Tämä ei ole tämän kurssin ydinasiaa, tällä kurssilla keskitytään yksittäisten algoritmien ja tietorakenteiden oikeellisuuden varmistamiseen. Joka tietysti on osa tuota ohjelmiston oikeellisuuden varmistamisen kokonaisuutta.

Assertien tarkoituksena on siis auttaa bugien löytämistä ja korjaamista kehityksen aikana. Poikkeusten käsittelyn tarkoituksena sen sijaan on varmistaa, kun ohjelmistoa käytetään tuotantoympäristössä, ilmenee poikkeuksellisia tilanteita, ohjelmisto pystyy käsittelemään nämä poikkeukselliset tilanteet ilman että ohjelma kaatuu, sotkee käyttäjän tietoja, käyttäytyy väärin tai muuten vaan sekoaa.

## Milloin poikkeuksia ei saa käyttää?

**HUOMAA** että poikkeuksia **ei saa** käyttää toteuttamaan ohjelman *toimintalogiikkaa*. Jos (ja kun) poikkeus tapahtuu, sen käsittely on aina **raskas** prosessi, joka vie aikaa ja muita koneen resursseja. Ohjelman suoritus siis hidastuu, mahdollisesti merkittävästi. Siksi toimintalogiikan ohjaamiseen pitää käyttää poikkeusten sijaan niitä normaaleja perusrakenteita: peräkkäisyys sekä ehto-, toisto- ja valintarakenteet.

Esimerkkinä seuraava algoritmi, joka on siis HUONO ja tällaista EI saa tehdä:

```Java
	// DO NOT DO THIS KIND OF ****!
    private static int calculateHashWithExceptions(final String string) {
        final int PRIME_NUMBER = 31;
        int hashC = 31;
        int index = 0;
        while (true) {
            try {
                hashC = hashC * PRIME_NUMBER + string.charAt(index);
                index++;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return hashC;
    }
```

Tässä käydään läpi merkkijonoa `string` indeksillä hakien merkki kerrallaan kaikki merkkijonon merkit. Kun `index`:n arvo kasvaa suuremmaksi (>=) kuin merkkijonossa on merkkejä, `string.charAt` heittää poikkeuksen `IndexOutOfBoundsException`. Tässä poikkeusta on siis käytetty *toimintalogiikan ohjaamiseen*. Valitettavasti tällaisia ratkaisuja kurssin tehtävissäkin joskus näkyy.

**NÄIN EI SIIS SAA TEHDÄ!**

Sen sijaan, tulee käyttää niitä normaaleja opittuja ratkaisuja, tässä oikea toteutus:

```Java
    private static int calculateHashNormally(final String string) {
        final int PRIME_NUMBER = 31;
        int hashC = 31;
        for (int index = 0; index < string.length(); index++) {
			hashC = hashC * PRIME_NUMBER + string.charAt(index);
        }                
        return hashC;
    }
```
Eli käytetään ihan normaalia `for` -silmukkaa jossa varmistetaan ettei mennä ohi merkkijonon loppupään eikä kyseistä poikkeusta siis koskaan edes heitetä. Koodi on tässä muutenkin laadukkaampaa ja lyhyempää sekä helpompilukuista.

Kuten yllä mainittiin, poikkeusten käsittely on raskas operaatio. Kun testiohjelma laskee molemmilla algoritmeilla tiivisteen (*hash*) merkkijonosta miljoonalle merkkijonolle, ja mitataan molempien algoritmien suoritusaika, testiohjelma tulostaa (suoritettu Apple Silicon M1 -prosessorilla):

```
Hash calc using exceptions took 778 ms
Hash calc in a for loop without exceptions took 20 ms
Exception way is 38,90 times slower than the usual way
```

Eli poikkeuksia käyttämällä hidastetaan algoritmin toimintaa melkein nelikymmenkertaisesti. Tuo hitaus näkyisi jo ihmisellekin; yleensä rajana pidetään noin 200-250 ms. Jos operaatio kestää pidempään, käyttäjä huomaa sen ohjelman tökkimisenä. Älä tee siis näin.


## Milloin asserteja on järkevää käyttää?

Ensinnäkin, kertauksena yllä olevasta, assertteja on järkevää käyttää nimenomaan bugien löytämiseen *kehityksen* aikana.

Assertteja ja poikkeuksia käytetään aika tyypillisesti näissä tilanteissa:

* Metodien toteutuksen alussa varmistamaan, että metodin suorittamisen esiehdot pitävät paikkaansa.
  * Tosin myös poikkeuksia käytetään, syistä jotka mainitaan seuraavassa luvussa alla.
* Metodien toteutuksen lopussa, varmistamaan että metodin suorittamisen jälkeen jättöehdot pitävät paikkaansa, eli että metodi teki sen mitä sen piti tehdä.
* Silmukkainvarianttien testaamisessa, eli varmistamaan että silmukan suorittamisen aikana asiat ovat *jokaisella* silmukan kierroksella, niinkuin niiden pitäisi.
* Luokkainvarianttien varmistamiseen, eli että luokan jäsenmuuttujien tila on oikeellinen metodien suorittamisen jälkeenkin.


## Milloin asserteja ei kannata käyttää?

Assertteja **ei suositella** käytettäväksi *julkisten* (`public`) metodien parametrien (eli argumenttien) esiehtojen tarkistamiseen. 

Oletuksena kannattaisi pitää periaatetta, että julkisen metodin *kutsuja on vastuussa** siitä että kutsussa parametrien arvot ovat oikeelliset. Toki kutsuvan koodin kirjoittajalle pitää kertoa parametreihin liittyvät säännöt, speksit. 

Julkisessa algoritmissa, parametrit on kuitenkin hyvä tarkistaa, muttei asserteilla jotka kaatavat ohjelman, jos parametrit eivät ole oikeelliset. Toki privaattien (`private`) luokan (apu)metodien alussa assertien käyttö on ihan ok, koska niitä kutsutaan vain siitä omasta luokasta eli omasta koodista käsin.

*Julkisten* metodien parametrien oikeellisuuden tarkistuksessa kannattaa siis käyttää poikkeuksia. Eli jos parametrit eivät ole kunnossa, silloin heitetään poikkeus, esimerkiksi `InvalidArgumentException`. Näitä toteuttaessa voi katsoa löytyykö Javan valmiista poikkeusluokista sopiva poikkeusluokka, vai pitäisikö tehdä omia, sovelluskohtaisia poikkeusluokkia joita käyttää.

Poikkeuksia käytetään tässä siksi, että toteutettaessa julkisia metodeja, joita voi siis kutsua "kuka vaan mistä vaan", kutsuva koodi voidaan tehdä ajallisesti paljon myöhemmin kuin julkisen metodin koodi. Siksi nämä tarkistukset ja muut virheelliset tavat kutsua metodia ilmaistaan kutsujalle poikkeuksin. Kutsuvaa koodia kun ei välttämättä suoriteta, kuten yllä kerrottiin, siten että Javan assert:it on erityisesti otettu käyttöön, virtuaalikoneen tietyllä parametrilla. Siksi julkisten metodien virheiden tarkistuksessa ei voida olettaa että assertit ovat toiminnassa -- käytetään poikkeuksia.

> Jos teet koodia vain itsellesi tai pienelle ryhmälle koodareita, assertteja voi hyvin käyttää julkisissakin metodeissa. Koska vain te käytätte kyseistä koodia, assertit ovat todella nopea ja varma tapa varmistaa oikeellisuutta kehityksen aikana. Korjatte sitten vaan ne parametrin välityksessä tehdyt virheet, ja koodi ei enää kaadu suoritettaessa.

Koska assertteja on tarkoitus käyttää vain kehityksen aikana, niitä ei kannata käyttää tilanteissa, jossa ohjelman suorittaminen **todellisessa käytössä** vaatii oikeellisen toiminnan suorittamista. Koska yleensä assertit eivät ole käytössä tuotantoversiossa, ja koodissa olisi assert, virheen tapahtuessa mitään ei siis tapahdu, ja ohjelma siis jatkaa suoritustaan tilassa, joka voi olla täysin sekaisin (*undefined behaviour*), eikä mikään takaa sitä että jatkossakaan ohjelma pystyisi suorittamaan tehtäviään oikeellisesti. Esimerkiksi, jos tiedostosta lukeminen epäonnistuu ja se tarkistetaan assertilla:

```Java
	byte [] bytes = fileReader.read(file);
	assert(bytes != null && bytes.length > 0);
	handle(bytes);
```
Nyt jos lukeminen epäonnistuu (bytes on null tai sen pituus on nolla), assert ei tuotantokäytössä tee mitään ja koodi jatkaa käsittelemään luettuja tavuja, vaikkei se voi mitenkään onnistua. Mitä tapahtuu seuraavaksi, riippuu `handle` -metodin toteutuksesta. Jos `bytes` on vaikkapa `null`, eikä `handle` tarkista parametriaan, yritys käsitellä taulukkoa joka on `null` aiheuttaa sitten `NullPointerException`:n.

Siksi tällaisissa tilanteissa kannattaa käyttää poikkeuksia:

```Java
	byte [] bytes = fileReader.read(file);
	if (bytes == null || bytes.length == 0) {
		throw new MyApplicationSpecificException("Reading the data file failed");
	}
	handle(bytes);
```

Nyt toiminta keskeytyy myös tuotantokäytössä, `handle` -metodia ei virheen tapahtuessa edes kutsuta, ja virhe käsitellään jossain muualla olevassa try/catch -lohkossa (josta tätä metodia kutsuttiin, suoraan tai mutkien kautta) jossa sitten virhe `MyApplicationSpecificException` käsitellään.

Jotkut ovat sitä mieltä, ettei asserteja kannattaisi käyttää ollenkaan, vaan aina poikkeuksia. Asiasta voi olla siis montaa mieltä. Tässä harjoituksessa käytetään molempia, jotta ne varmasti tulisivat tutuksi, ja voisit sitten itse valita mitä keinoa milloinkin kannattaa käyttää, olipa ohjelmointikieli jota käytät, mikä vaan. 

Lopuksi toteaisin, että asserteja kannattaa kyllä käyttää varmistamaan bugiton softa. On aika lailla turhaa ja turhauttavaakin, nähdä poikkeuksen heitettävän koodista siksi, että se on *bugista*. Kutsuja ei tee sillä tiedolla, että toteutus on buginen oikeastaan mitään (muuta kuin toteaa ettei sitä koodia kannata käyttää vaan teenpä itse homman uusiksi tai etsin toisen valmiin toteutuksen). Ainoa oikea ratkaisu tässä tilanteessa on korjata se bugi koodista pois, ja varmistaa sen oikeellisuus -- vaikkapa niitä assertteja käyttäen, jos ne auttavat ymmärtämään sitä, miksi koodi ei toimi oikeellisesti.

> Loppujen lopuksi assert:n käyttö heittää Javassa poikkeuksen `java.lang.AssertionError`, jonka *voi* napata kiinni try/catch -rakenteella, joten voi kysyä onko asserteilla ja poikkeuksilla Javan tapauksessa eroa paljoakaan. Muuta kuin että assertit toimivat vain jos ne laitetaan päälle. Useimmissa muissa kielissä assert oikeasti kaataa sovelluksen välittömästi eikä niitä voi napata kiinni. Suurin virhe ja moraalittomuus minkä voi tällä tiedolla tehdä, on se että laittaa koodin try/catchin joka nappaa `AssertionError`:n ja ei tee sillä mitään, tehokkaasti hiljentäen sen tiedon että softa on buginen.
