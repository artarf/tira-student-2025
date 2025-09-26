# Shared -kirjasto

Tässä ohjeessa kerrotaan, miten `shared` -kirjastoa käytetään kurssin ohjelmointehtävissä ja miten käytät tarkistustyökalua varmistamaan että olet tehnyt oikeita asioita oikealla tavalla. 

Lue ohje huolellisesti, sillä tätä komponenttia tarvitaan ihan jokaisessa kurssin ohjelmointitehtävässä.

## Yleistä

Se miten tätä komponenttia kurssilla käytetään, on demottu kurssin videotallenteilla. Katso se, jos et ymmärrä ohjeita. Kysy apua tarvittaessa opettajilta.

Tämä osaprojekti (komponentti, moduli) sisältää yleiskäyttöisiä algoritmeja ja tietorakenteita sekä apuluokkia, joita tarvitaan kurssin ohjelmointitehtävien tekemisessä.

Kun etenet **kukin tehtävän ohjeita noudattaen**, lisäät useissa tehtävissä tähän komponenttiin joko **uusia algoritmeja tai tietorakenteita**.

Tämä komponentti sisältää myös **testejä**, joilla testaat tekemäsi muutoksen **oikeellisuuden**. Jos testit eivät mene läpi, et voi käyttää kyseistä algoritmia tai tietorakennetta varsinaisessa tehtävässäkään.

## Testeistä

* Jos **et ole aloittanut** toteuttamaan jonkun ohjelmointitehtävän algorimia tai tietorakennetta, testit on rakennettu niin että testiä ei suoriteta vaan se **hypätään yli** (*skip*). Näin vielä tekemättömät tehtävät eivät estä komponentin kääntämistä.
* Jos olet aloittanut toteuttamaan tehtävän algoritmin tai tietorakenteen, mutta se **ei toimi** oikeellisesti, **testi epäonnistuu**. Tällöin korjaa koodi niin että se toimii ja testit menevät läpi. Et voi jatkaa tehtävien tekemistä eteenpäin, ennenkuin virheet algoritmissa tai tietorakenteessa on korjattu.
* Jos algoritmi on toteutettu ja se toimii oikein, testit menevät läpi.

Jos yksikin testi epäonnistuu, `shared` -komponenttia ei voi kääntää, eivätkä muut tehtävät, jotka tätä komponenttia käyttävät, voi toimia. Pidä siis huolta että kaikki koodi mitä teet kussakin tehtävissä, menevät läpi tämän komponentin testeistä. Vasta sen jälkeen siirry tehtäväkohtaiseen soveltavaan tehtävään.

Alla kuvassa esimerkki jossa osa testeistä menee läpi (puolitushaku- ja lisäyslajittelutestit), ja niiden vieressä näkyy vihreä OK -symboli. Osa testeistä liittyen osittamistehtävään (task_3; `PartitionByRulesTests`) epäonnistuu, joten algoritmissa on vielä korjattavaa. Sen sijaan `PartitionFilteringTests` -testit on skipattu (harmaa nuoli kuvaa tehtävän yli hyppäämistä), sillä kyseistä algoritmia ei vielä ole aloitettu tekemään.

![Esimerkki testien tilanteesta](test-status-example.png)

Kun olet tehnyt kaikki kurssin pakolliset ohjelmointitehtävät, jokaisen testin kohdalla pitäisi näkyä vain vihreää OK:ta.

## Mitä teet AINA kun muokkaat tämän komponentin lähdekoodeja

**Aina** kun muokkaat tätä komponenttia, eli...

* **lisäät** uuden algoritmin tai tietorakenteen, tai
* **korjaat** bugin toteuttamastasi tietorakenteesta tai algoritmista, tai
* **muokkaat** jo toteuttamaasi tietorakennetta tai algoritmia mistä tahansa syystä...

...sinun tulee:

1. **testata** tuotoksesi **oikeellisuus**, ja sen jälkeen kun testit menevät läpi, 
2. **luoda** tästä `shared` -kirjaston koodista java-kirjasto eli `shared-1.0.jar` -tiedosto. Tämä menee shared -projektin `target` -hakemistoon. 

Tämä on tehtävä siksi, koska muut tehtävät hyödyntävät tässä jaetussa kirjastossa olevia omia algoritmejasi ja tietorakenteitasi. Muut komponentit käyttävät käännettyä koodia tuosta .jar -tiedostosta, joten sen on käännyttävä.

Kun olet saanut jonkun uuden algoritmin tai tietorakenteen toimimaan `shared` -komponentin testien perusteella oikeellisesti, luo tästä kirjastokomponentista .jar tiedosto **shared** -hakemistossa joko komentiriviltä tai VS Codesta käsin alla olevien ohjeiden mukaisesti.

### Komentoriviltä testaaminen ja kirjaston luominen

Siirry ensin komentorivillä tähän `shared` -hakemistoon (helpoin ja nopein tapa on avata projektin ollessa auki VS Codessa, VS Coden valikosta Terminal -näkymä) ja anna komento:

```
mvn clean install
```

Jonka jälkeen (jos testit menevät läpi!) komponentti on valmis käytettäväksi; `shared` -komponentin `target` -hakemistoon ilmestyy tiedosto `shared-1.0.jar`.

Komento ajaa ensin kaikki testit. Jos testit eivät mene läpi, .jar -tiedostoakaan ei luoda. Tällöin, korjaa bugit kunnes testit menevät läpi. Tässä esimerkki tilanteesta jossa testit menevät läpi; mitä tulostuu konsoliin:

```
...
[INFO] Installing /Users/juustila/workspace/tira-2025/shared/target/shared-1.0.jar to /Users/juustila/.m2/repository/oy/interact/tira/shared/1.0/shared-1.0.jar
[INFO] Installing /Users/juustila/workspace/tira-2025/shared/pom.xml to /Users/juustila/.m2/repository/oy/interact/tira/shared/1.0/shared-1.0.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.594 s
[INFO] Finished at: 2025-06-12T10:38:07+03:00
[INFO] ------------------------------------------------------------------------
```

> Voit myös suorittaa pelkästään testit komennolla `mvn test`, tämä ei vielä luo kyseistä kirjastoa mutta suorittaa testit.
> 
> Vastaavasti, **aina** tästä lähtien kun teet tähän komponenttiin eri ohjelmointitehtävissä muutoksia, korjaat bugeja tai lisäät algoritmeja tai tietorakenteita, **testaa** ne oikeellisesti toimiviksi testeillä, ja sitten luo kirjasto yllä mainitulla komennolla. **Vasta sen jälkeen** voit hyödyntää uutta toiminnallisuutta tehtävän omassa erillisessä projektissaan.

### Kirjaston testaus ja luominen VS Codessa 

Toinen tapa generoida tuo tarvittava `shared-1.0.jar` on tehdä sama VS Codesta käsin. Avaa shared -projekti VS Codessa **omaan ikkunaansa**, sitten avaa vasemmalla näkyvä Maven -"kansio" ja sieltä suorita komento `install`:

![Kuva siitä miten luoda jar](how-to-generate-jar.png)

Paina `install` -komennon vieressä olevaa "play" -nappia, projektin testit suoritetaan ja jos testit menevät läpi, .jar -tiedosto on target -hakemistossa ja muut tehtäväprojektit (komponentit) saavat sieltä .jar -tiedostosta koodin käyttöönsä.

Yllä kuvassa näet myös tuon `shared-1.0.jar` -tiedoston, joten testit ja käännös ovat tässä onnistuneet.

Tässäkin tapauksessa konsoliin tulostuu miten homma meni, samalla tavalla kuin ym. konsolissa tehdyllä tavalla.

Jos tulostuu virheitä (`ERROR`), siirry VS Coden testinäkymään ja aja testit. Katso mikä testi epäonnistuu. Tutki testin tulostuksia ja selvitä miksi testi epäonnistuu. Korjaa löytämäsi bugi ja kokeile uudestaan.


## Tarkistustyökalu

Kurssilla on useissakin paikoissa (luennot, tehtävien ohjeet, muu materiaali) kerrottu mitä ratkaisuja ohjelmointitehtävissä saa tai ei saa käyttää.

Koska nämä ohjeet usein unohtuvat tai niitä ei ehkä aina ymmärretä, kurssilla jaetaan **työkalu** jolla voit tarkistaa oletko käyttänyt näitä **kiellettyjä** ratkaisuja. 

Koska opiskelijoita on kurssilla satoja, ja koodia on paljon, opettajat eivät ehdi käsityönä auttaa varmistamaan kaikkia siitä että asiat ovat kunnossa, ennen deadlinea ja/tai arvostelun yhteydessä. Toivottavasti tämä työkalu auttaa kaikkia varmistamaan että asiat on tehty kuten pitää, jotta asiat voisi korjata ennenkuin ne vaikuttavat kurssin arvosteluun. Vahingossakin tulee näitä virheitä tehtyä, ja parempi että ne korjataan kurssin aikana kuin että vasta kurssin päätyttyä saat tietää työsi olevan hylätty.

**Varmista** siis tällä työkalulla, ettet ole käyttänyt toteutuksissa kurssilla kiellettyjä ratkaisuja ja olet tehnyt asioita kuten pitää.

> **Huomaa** että jos et vielä ole toteuttanut jotain tiettyä algoritmia tai tietorakennetta, tai sen toteutus on kesken, työkalu saattaa valittaa sääntörikkomuksesta. Jos taas olet mielestäsi tehnyt asiat kuten pitää, mutta työkalu silti valittaa sääntörikkomuksista, kysy opettajilta jos et ymmärrä mistä valitus johtuu.

**Suorita** työkalu repositorysi juurihakemistossa näin:

```console
java -jar checkrulestool-1.0-SNAPSHOT-jar-with-dependencies.jar 
```

> Työkalu lukee säännöt tiedostosta `CodeRules.txt`. Opettajat voivat kurssin aikana toimittaa uuden sääntötiedoston, jos huomataan että se tarvitsee päivitystä. Tai jos työkalussa huomataan puutteita tai virheitä, saat siitä uuden version. Muuten, **älä** muokkaa tai poista tätä tiedostoa!

Työkalu tarkistaa perusasiat; koodisi ei saa importata kiellettyjä Javan kirjastoluokkia ja metodien toteutusten pitää sisältää/ei saa sisältä tiettyjä vaadittuja tai kiellettyjä asioita. 

Jos työkalu ilmoittaa ongelmista, etkä ymmärrä mistä on kyse, kysy lisäohjeita opettajilta. Muussa tapauksessa, korjaa toteutustasi poistaen kielletyt importit ja noudata toteutuksen ohjeita.

Samaa (opettajien) sääntötiedostoa käytetään myös palautettujen tehtävien arvioinnissa ja kurssin arvostelussa. Jos ohjelmointitehtävissäsi on käytetty kiellettyjä ratkaisuja, työ hylätään.
