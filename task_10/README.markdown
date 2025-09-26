# Tehtävä 10

Verkot -tietorakenteen ohjelmointitehtävät vaatimukset kurssilla ovat:

* verkon muodostaminen (verteksien ja reunojen lisääminen) - yhteensä noin 20 riviä koodia,
* leveys- ja syvyyshaku - noin 25 ja 30 riviä koodia, sekä
* Dijkstran lyhimmän polun hakualgoritmin toteutus - noin 60-75 riviä koodia.

Tehtävää ei kannata säikähtää, sillä luennot, tehtävän ohjeet ja demot antavat hyvän pohjan tehtävän toteutukselle. Kysy apua opettajilta jos et ymmärrä jotain tai jäät jumiin ohjelmointitehtävän kanssa.


## Lähteitä

* Kurssin luentokalvot.
* Demovideot (linkki Moodlessa / luentomateriaaleissa).
* Kurssikirja: Introduction to Algorithms, 4th ed (Comer et al), ss 1164-1169.
* [Lyhyt opastus ja demo tietojoukkoihin](https://codeberg.org/anttijuu/SetDemo).
* [C++ -demon lähdekoodi](https://github.com/anttijuu/graphs)
* Javan [Set-rajapinta](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html).
* Javan [Map-rajapinta](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html).
* Javan [PriorityQueue](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html).

> Kun hyödynnät C++ -demon lähdekoodia toteutuksessasi, pyri *ymmärtämään* miten koodi toimii ja miksi, peilaten luennon teoriaan ja verkkotietorakenteeseen sekä sen algoritmeihin käsitteenä,  ja *vasta sitten* toteuta Java -toteutuksesi.

## Aihe

Tämän harjoituksen aiheena on **verkko -tietorakenne** (*graph*). Verkko on edistynyt tietorakenne, joten huolehdi että olet ensin tutustunut tietorakenteeseen kurssin luentojen kautta sekä tarvittaessa perehtymällä tähän tietorakenteeseen liittyvään kirjallisuuteen.

Verkon algoritmien toteututukset hyödyntävät kurssilla aiemmin opeteltuja asioita, kuten jono, pino ja tiivisteet.

Huomaa että verkon tietorakenteiden ja algoritmien toteutuksesta on olemassa kurssilla aiemmin tehdyt **demovideot** joissa verkon algoritmeja tutkitaan C++:lla tehdystä esimerkistä. Voit hyödyntää näitä videoita kun toteutat tätä tehtävää. Löydät linkin demovideoihin kurssin materiaaleista.

Demovideot olettavat että **olet katsonut ensin** asiaan liittyvät **luentovideot**. Peruskäsitteitä ei siis demovideoilla selitetä. C++ -demon arkkitehtuuri eroaa myös jossain määrin tämän tehtävän rakenteesta, joten ota se huomioon videoita katsellessasi. Esimerkiksi Djikstran algoritmi toteutetaan tässä tehtävässä metodiksi `Graph` -luokkaan (ei omaksi luokakseen kuten demossa), ja Javan toteutushan tietysti yksityiskohdiltaan eroaa C++ -toteutuksesta kielten ja kirjastojen erojen vuoksi. Pääasia on yleiset käsitteet ja algoritmit mitä tästä demosta saat irti. Näistä enemmän yksityiskohtaisemmissa ohjeissa alla.

Huomautuksia C++ -demovideoista:

* Ensimmäisellä videolla näkyy luokka `AdjacencyList`. Käytännössä se on graafi eli verkko, ja nimesinkin sen myöhemmin (toinen video) uudestaan `Graph` -nimiseksi luokaksi. Se on se mitä löydät demon C++ -lähdekoodista GitHubissa (linkki alla).
* Videoilla voidaan viitata kaksi vuotta sitten toteutetun kurssin detaljeihin. Ignooraa nämä asiat ja seuraa nykyisen kurssitoteutuksen ohjeita. 
* Kannattaa katsoa kaikki demot, sillä tentissä niistä voi olla hyötyä asioiden ymmärtämisessä. Ohjelmointitehtävässä kaikkia algoritmeja ei tarvitse kuitenkaan toteuttaa.
* Olin videoiden tallennusaikaan aika pahassa yskässä joten pahoittelut etukäteen runsaasta köhimisestä.

Huomaa myös että tässä harjoituksessa **saa poikkeuksellisesti käyttää** Javan tietosäiliöluokkia ja algoritmeja joita tähän asti *ei ole* saanut käyttää:

* `Stack` -- pinotietorakenne. Toki voit käyttää myös itse toteuttamaasi pinotietorakennetta, se toimii tässä ihan yhtä hyvin jos tehtävän testit menivät läpi.
* `Queue` -- jonotietorakenne. Toki voit käyttää myös itse toteuttamaasi jonoakin.
* `PriorityQueue` -- tätä tarvitaan Dijkstran algoritmin toteutuksessa.
* Rajapinta `Set` -- kun tarvitaan tietosäiliötä jossa elementtien järjestyksellä ei ole väliä, mutta joka huolehtii siitä että oliot ovat säiliössä vain kerran. Luokka `HashSet` on yksi rajapinnan toteutuksista.
* Rajapinta `List` ja sen toteutus `ArrayList` -- taulukkotietorakenne, kun tarvitaan sellaista. Demokoodissa käytetään C++:n `std::vector` -luokkaa, Javassa on myös `Vector` mutta `ArrayList` on parempi vaihtoehto koska emme tee tässä rinnakkaista ohjelmointia jolloin `ArrayList` on kevyempi ja nopeampi toteutus.
* `Map` -- rajapinta dictionary -tyyppiseen avain-arvo -tietorakenteeseen. Verkon solmut ja solmujen reunat tallennetaan tällaiseen tietorakenteeseen.
* `Hashtable` sekä `HashMap`, jotka ovat Javan `Map` -rajapinnan toteutuksia.

Nämä kaikki rajapinnat ja luokat löytyvät Javan packagesta `java.util`.

Verkkotietorakenteen luokat ovat `shared` -packagessa `oy.interact.tira.student.graph`. Täydennät ja muokkaat siellä olevien luokkien koodia ohjeiden mukaisesti.


## Tavoite

Toteuta ja testaa verkkotietorakenteen puuttuvat algoritmit. Testaa toteutusta tarvittaessa sitä korjaten siten, että toteutus läpäisee testit. Tarkemmin tämän vaiheet on kuvattu alla luvussa Toteutuksen askeleet.

Testit testaavat sekä suunnattuja (directed) että suuntaamattomia (undirected) verkkoja. 

Kun testit menevät läpi, voit kokeilla tietorakenteen toimintaa soveltavan tehtävän Mazes -pelissä. 

**Huomaa** että koska käytät verkon algoritmien toteutuksessa Javan nopeita tietorakenteita jotka pohjautuvat `Map`:iin ja `Set` -rajapintoihin, nämä käyttävät hyväkseen **tiivisteitä**. 

## Toteutuksen askeleet

### 1. Valmiina annettuun koodiin tutustuminen

Osa verkon toteutukseen tarvittavista yksinkertaisista apuluokista on *annettu valmiina*. Tutustu näihin **huolellisesti**, ja **vertaa** luokkia niihin käsitteisiin joita verkoista luennoilla ja kirjallisuudessa on käyty läpi:

* `Vertex<T>` -- verkon solmu, geneerinen luokka joka voi sisältää mitä vain sovelluskohtaista tietoa. **Tutki** tarkkaan luokan toteutukseen, erityisesti siihen miten se toteuttaa `Comparable` -rajapinnan ja **ylikuormittaa** seka `equals` että `hashCode` -metodit.
* `Edge<T>` -- verkon reuna, geneerinen luokka joka määrittelee kahden solmun välisen reunan. Huomaa että reuna sisältää sekä lähtö- että määränpääsolmun viittauksen, että reunan painon.
* `Visit<T>` -- apuluokka jota käytetään Dijkstran algoritmin toteutuksessa, määrittelee polun jota pitkin kuljetaan etsiessä lyhintä polkua lähtösolmusta määränpääsolmuun.

Kuten yllä mainittiin, **verkkotietorakenteen nopea toteutus nojaa vahvasti** `Map` -rajapintaan ja hakualgoritmit hyödyntävät nopeaa `Set` -rajapinnan toteutuksia (`HashMap`, `HashSet`). Näiden toiminta ja suoritustehokkuus perustuu vahvasti siihen että **verteksit laskevat omasta elementistään *tiivisteen***:

```Java
   @Override
   public int hashCode() {
      return element.hashCode();
   }
```

Tästä on hyötyä kun:

1. lisätään ja haetaan verkon reunuslistalta verteksejä ja niiden reunoja: verteksi löytyy nopeasti koska haussa voidaan hyödyntää tiivisteitä.
2. tehdään leveys- ja syvyyshakua, tietojoukolla `Set` voidaan nopeasti tarkistaa onko verteksissä jo käyty -- `Set` hyödyntää tiivistettä siihen, että joukossa jo oleva elementti löytyy nopeasti. Ja lisäksi varmistaa sen, että kahta identtistä elementtiä ei `Set`:iin voi lisätä.

*Jos* `Map`:n ja `Set`:n sijaan näissä tilanteissa käytettäisiin yksinkertaisia tietorakenteita (esim. `ArrayList`, `LinkedList`tai `Vector`), jouduttaisiin tekemään *hidas lineaarinen haku* etsiessä onko elementti jo tietorakenteessa tai onko siinä jo vierailtu. 

Kun toteutuksesi leveys- ja syvyyshauista käyttää `Set` -rajapinnan toteutusta `HashSet`, sen `contains()`, jolla tarkistat onko joukossa jo se vierailtu verteksi, on aikakompleksisuusluokaltaan O(1) -- koska se hyödyntää tiivisteitä.

Jos käytät yksinkertaista taulukkotietorakennetta (`ArrayList`), sen `contains` on O(n). Suurilla verteksien määrillä näiden operaatioiden ero kasvaa merkittäväksi.

Siksi **on tärkeää** tuntea erilaiset tietorakenteet ja millä periaatteella ne toimivat. On **erittäin tärkeää** osata valita oikea tietorakenne ja algoritmi riippuen tehtävän tavoitteista.

Huomasit yltä että `Vertex` kutsuu elementtinsä `hashCode()`:a. Eli tietorakenne luottaa siihen että verkkoon tallennetut elementit sisältävät hyvän hajautusfunktion.

### 2. Toteutus

Varsinainen itse toteutettava koodi on enimmäkseen luokassa `Graph`. Luokan koodissa on valmiina metodien esittelyt, toteutus poistettuna. Tehtävänäsi on:

1. Tutustua tarkkaan verkkotietorakenteen perusteisiin (luennot, kirjallisuus, demovideot).
2. Toteuttaa `Graph` -luokan metodit siten että tehtävän testit menevät läpi.
3. Viimeistellä Mazes -pelin toiminnallisuus jotta hakualgoritmit näkyvät visualisoituna pelissä.

Lue huolellisesti `Graph` -luokan metodien kommenteissa olevat kuvaukset siitä mitä algoritmin tulisi tehdä. Kuvaukset eivät luonnollisesti ole tyhjentäviä, vaan sinun täytyy ensin tuntea luentojen ja kirjallisuuden perusteella verkkotietorakenteen ja sen algoritmien perusteet. Hyödynnä myös aiemmin mainittuja C++ -toteutuksen demovideoita.

**Tee tietorakenteen toteutus** `shared` -kirjastoon, kuten aikaisemmissakin tehtävissä. Verkkotietorakenteen ja sen algoritmien testit sijaitsevat myös `shared` -kirjastossa.

**Valitse ensin** sopiva tietorakenne johon solmut ja reunat tallennetaan `Graph` -luokassa jäsenmuuttujaan.

Kätevintä tässä työssä on käyttää reunuslistaa (edge list) ja tallentaa solmu ja sen reunat avain-arvopareina valmiiseen Javan tietosäiliöluokkaan joka tukee avain-arvopareja. Näitä ovat `Hashtable` ja `HashMap`. Ne kaikki toteuttavat rajapinnan `Map`, joten kannattaa tehdä jäsenmuuttujaksi `Map` joka sisältää avaimena solmun `Vertex<T>` ja arvona listan reunoja `List<Edge<T>>`.

Kun jäsenmuuttujan esittelyssä sen tyyppi on `Map`, voit sitten `Graph` -luokan *muodostimessa* luoda jäsenmuuttujaan jonkun yllämainituista kolmesta toteutuksesta, esimerkiksi:

```Java
   this.edgeList = new HashMap<Vertex<T>, List<Edge<T>>>();
```

Huomaa siis että nyt avain-arvoparit ovat siis:

1. avaimena on vertex eli verkon solmu, ja
2. arvona on lista reunoja.

Pari vinkkiä joilla pääset alkuun sen suhteen miten `Map` -rajapintaa tässä käytetään:

1. Saat `Map`:stä kaikki "entryt" eli avain-arvoparit kutsumalla sen metodia `entrySet()`. Voit sitten käydä läpi kaikki solmut (vertex) ja niiden reunat (edge) for -silmukassa.
2. Saat `Map`:stä kaikki solmut (vertex) ilman reunoja kutsumalla sen `keySet()` -metodia.
3. Voit lisätä uusia avainarvopareja kutsumalla `Map`:n metodia `put(key, value)`.
4. Kun haluat jonkun tietyn avaimen (vertex) arvot (taulukon reunoja), kutsu `Map`:n metodia `get(vertex)` ja saat taulukon reunoja.

> Metodi `Graph.toString()` on valmiina toteutettuna mutta *kommentoituna* koodissa. Voit ottaa kommentit pois ja katsoa sopiiko se yhteen oman koodisi kanssa, kunhan verkon muodostuksen perusasiat on toteutettu. Jos `Map` -jäsenmuuttujasi nimi on eri kuin koodissa, muuta jompi kumpi nimi niin että koodi toimii. 
>
> Tässä on samalla esimerkki siitä miten reunuslistaa käydään läpi silmukassa, sitä siellä sun täällä tarvitaan. Metodi on hyödyllinen myös debuggausta varten; jos sinulla on ongelmia ja luulet että tietosisältö ei ole sitä mitä pitää, voit tehdä koodia joka tulostaa `Graph` -olion konsoliin. Voit sitten katsoa näyttääkö siltä että asiat on oikein vai väärin. Testit tekevät tätä, joten voit sieltä katsoa miltä verkko "näyttää".


### Verkon muodostamisen metodien toteutus

**Toteuta** alla listatut `Graph`:n **perusmetodit** joita tarvitaan ensin, verteksin luominen, reunan lisääminen, jne. Näissä kaikkien toteutuksissa tarvitset `Map` -luokkaa, joten kannattaa katsoa miten sen avulla saadaan näitä avain-arvopareja hallittua. Asian pitäis olla suurin piirtein käsitetasolla tutun näköistä hommaa, sillä olet toteuttanut jo kaksi avain-arvopareja käsittelevää tietosäiliöluokkaa aiemmissa harjoituksissa.

Alla lista näistä perusmetodeista joiden **tarkempi kuvaus löytyy kunkin metodin koodin kommenteista**.

* `createVertexFor(T element)` -- luodaan uusi solmu verkkoon (tyhjällä reunuslistalla), lisätään se `Map`:iin ja palautetaan luotu solmu kutsujalle.
* `getVertices()` -- palauttaa joukon verkkoon lisätyistä solmuista.
* `addEdge(Edge.EdgeType type, Vertex<T> source, Vertex<T> destination, double weight)` -- lisää annetun reunatyypin (suunnattu tai suuntaamaton) mukaisen reunan lähtösolmusta kohdesolmuun annetulla reunan painolla.
* `addDirectedEdge(Vertex<T> source, Vertex<T> destination, double weight)` -- lisää suunnatun reunan lähtösolmusta kohdesolmuun tietyllä reunan painolla.
* `getEdges(Vertex<T> source)` -- antaa listan kaikista reunoista jotka lähtevät annetusta lähtösolmusta.
* `getVertexFor(T element)` -- etsii ja palauttaa solmun jossa kyseinen elementti on. Jos tällaista ei löydy, palauttaa null:n.

> **Huomaa** että verkko on *useimmiten* joko suuntaamaton tai suunnattu. Tarkoittaa sitä että *kaikki* reunat tietyssä verkossa ovat *jompaa kumpaa* lajia, eivät molempia. Tämän kurssin tehtävissä kaikki verkot ovat jompaa kumpaa laatua.

Tämän jälkeen pitäisi olla mahdollista suorittaa alla olevassa luvussa mainittu `BasicGraphTests` -testi. Jos se testi ei mene läpi, toteutuksessa lienee jotain vikaa. Korjaa mahdolliset virheet ja jatka eteenpäin.


### Leveys- ja syvyyshakujen toteutus

**Seuraavaksi** voit jatkaa toteuttamaan leveys- ja syvyyshaut (breadth-first-search sekä depth-first search). Näitä testataan testillä `SearchGraphTests`. Testi sisältää erikseen metodit molemmille hauille, sekä suunnatuille että suuntaamattomille graafeille. Voit siis toteuttaa ensi toisen ja sitten toisen haun ja testata niitä yksittäisillä testiluokan *testimetodeilla* erikseen. Kun olet saanut kaikki testattua, testaa vielä koko testiluokalla kaikki haut.

Tämä testiluokka testaa metodeja:

* `Graph.breadthFirstSearch(Vertex<T> from, Vertex<T> target)` -- tehdään leveyshaku alkaen solmusta `from`. *Jos* solmu `target` on eri kuin `null`, haku pysäytetään kun kyseinen solmu kohdataan verkossa ensimmäisen kerran. Algoritmi palauttaa listan solmuja joiden kautta haku eteni, siinä järjestyksessä kun ne kohdattiin. Myös lähtö- ja mahdollinen määränpääsolmu on mukana tällä listalla.
* `Graph.depthFirstSearch(Vertex<T> from, Vertex<T> target)` -- tehdään syvyyshaku alkaen solmusta `from`. Jos solmu `target` on eri kuin `null`, haku pysäytetään kun kyseinen solmu kohdataan verkossa ensimmäisen kerran. Algoritmi palauttaa listan solmuja joiden kautta haku eteni, siinä järjestyksessä kun ne kohdattiin. Myös lähtö- ja mahdollinen määränpääsolmu on mukana tällä listalla.

Leveys- ja syvyyshaku tuottavat *erilaisen* polun verkon solmuja vieraillessaan. Tämä on aika odotettua, sillä algoritmithan etenevät verkossa ihan eri tavalla. Kun suoritat koko testiluokan kaikki testit kerrallaan, testi varmistaa lopuksi, että leveys- ja syvyyshakujen etenemät polut **ovat erilaisia**. Jos näin ei ole, BFS ja DFS -toteutuksesi ovat itse asiassa sama algoritmi. Olet siis toteuttanut *kahdesti* joko leveys- tai syvyyshaun. Jos näin on, *korjaa tilanne*, ja toteuta erikseen leveys- ja erikseen syvyyshakualgoritmi.

Ennen kuin jatkat, ja jos teit muutoksia muihinkin kuin leveyshakualgoritmeihin, esimerkiksi löytäessäsi mahdollisia bugeja, tee **regressiotestausta** eli suorita uudelleen myös `BasicGraphTests`. Näin huomaat jos joku muutos koodiin rikkoi aiemmin toimineen algoritmin.

> **Huomaa** että molemmat hakualgoritmit suorittavat haun lähtösolmusta käsin. Algoritmi lopettaa kun se on käynyt läpi kaikki *löytämänsä* solmut (tai jos `target != null` ja kohdesolmu löytyi). Eli algoritmin *ei tarvitse* tarkistaa löytyikö varmasti *kaikki* verkon solmut ja jatkaa etsintää jostain toisesta paikkaa jos kaikkia ei löydetty. Onhan mahdollista että verkko ei ole yhteydellinen (*connected*) eli että jokaisesta solmusta *ei pääse* kaikkiin muihin solmuihin.
>
> Jos kaikki verkon solmut eivät ole löytyneiden solmujen joukossa, tämä on merkki siitä että verkko sisältää erillisiä alueita (on siis *disconnected*). Toki varsinkin suunnatussa verkossa on mahdollista että solmusta A pääsee kaikkiin solmuihin mutta solmusta B ei pääse. Tällä kurssin toteutuksella ei tarvitse toteuttaa algoritmeja jotka tutkivat onko verkossa erillisiä alueita.

### Dijkstran lyhimmän polun algoritmin toteutus

**Toteuta** Dijkstran lyhimmän polun hakualgoritmi. Tässä haetaan lyhintä polkua *reunojen painojen mukaan*, lähtösolmusta kohdesolmuun. Tässä lyhin polku ei siis tarkoita reunojen lukumäärää solmujen välillä, vaan pienintä reunojen painojen summaa lähtö- ja kohdesolmun välillä. Esimerkiksi rautatieasemien etäisyyttä kilometreinä.

Varmista luentomateriaalista että olet ymmärtänyt algoritmin toimintaperiaatteen, ja lisäksi saat vinkkejä toteutukseen kurssin C++ -demosta.

* Toteuta `Graph.DijkstraResult<T> shortestPathDijkstra(Vertex<T> start, Vertex<T> end)`, jossa parametreina lähtö- ja kohdesolmu.
* Kuten luennoista ja demoista näet, tarvitset tässä **apualgoritmeja**. Näistä tarkemmin luentomateriaalissa ja koodissa olevien toteuttamattomien `Graph`:n metodien yhteydessä olevissa kommenteissa.
* Lisäksi käytät kurssilla ensimmäistä kertaa **prioriteettijonoa** `PriorityQueue`. Prioriteettijonon tarkoitus on pitää sisällään tähän mennessä lyhimpiä polkuja lähdesolmusta, etsittäessä lyhimpiä polkuja kohteeseen. Prioriteettijonoa käyttäessäsi sen tarkoitus on järjestää solmut jonoon järjestykseen, niiden etäisyyden (reunojen painon) mukaan toisistaan, siten että lyhin solmujen välinen etäisyys on prioriteettijonossa aina kärjessä.

Prioriteettijonolle on annettava `Comparator` jonka avulla se pistää elementit jonossa prioriteetin mukaiseen järjestykseen. Tässä tapauksessa siis solmujen välinen etäisyys on se kriteeri. Jonon kärkeen tulevat siis ne verteksit joiden etäisyys (reunojen painojen summa) polkua pitkin on on pienin.

Tähän mennessä olet käyttänyt `Comparator` -luokkaa siten että se vertailee kahta oliota toisiinsa suoraan. Esimerkiksi tällainen comparator lajittelee henkilöitä nimen mukaiseen nousevaan aakkosjärjestykseen.

```Java
   Comparator<Person> comparator = new Comparator<Person>() {
      @Override
      public int compare(final Person first, final Person second) {
         return first.compareTo(second);
      }
   };
```

Nyt tarkoitus on siis vertailla kahta verteksiä siten että niitä vertailtaessa, ne tulevat jonon kärkeen joiden etäisyys (reunojen painojen summa) on lyhyin annettua polkua pitkin. Nyt tarvittava `Comparator` luodaan ihan samaan tyyliin kuin yllä, mutta verteksien vertailussa tuleekin käyttää hyödyksi Dijkstran algoritmin apufunktiota `distance` (kts. luennot, demot):

```Java
// In shortestPathsFrom..:
   Map<Vertex<T>, Visit<T>> paths = new HashMap<>();
// ...
   PriorityQueue<Vertex<T>> queue = new PriorityQueue<>(new Comparator<Vertex<T>>() {
      @Override
      public int compare(Vertex<T> o1, Vertex<T> o2) {
         return (int)(distance(o1, paths) - distance(o2, paths));
      }
   });
// ...
```
Eli prioriteettijono vertailee verteksejä laskemalla etäisyydet molemmista vertekseistä `o1` ja `o2` annettua polkua pitkin. Se verteksi jonka polku on lyhin, tulee jonon kärkeen.

Lyhymmän polun hakualgoritmi palauttaa lopuksi `DijkstraResult` -tyyppisen luokan, jossa jäsenmuuttujat kertovat haun tuloksesta:

* `pathFound` -- löytyikö polku lähtösolmusta kohdesolmuun vai ei.
* `path` -- lista jossa lyhin polku lähtösolmusta kohdesolmuun. On `null` jos polkua ei löytynyt.
* `steps` -- kuinka monta askelta (reunaa) polulla on.
* `totalWeight` -- mikä on yhteenlaskettu polun reunojen painojen summa.

Kun olet saanut toteutuksen testikuntoon, testaa sitä `/task_10/DijkstraSearchTests` -testillä.

### 3. Testaaminen

Seuraavat osin jo aiemmin ohjeessa mainitut testit auttavat toteutuksen oikeellisuuden testaamista, eri vaiheissa toteutuksesi etenemistä:

* `task_10/BasicGraphTests` -- testataan yksinkertaisia pieniä suunnattuja ja suuntaamattomia verkkoja ja tutkitaan onko verkkoon lisätyt solmut ja reunat sitä mitä pitäisi. Voit siis suorittaa tätä testiä aika aikaisessa vaiheessa toteutusta (solmuja ja reunoja voidaan lisätä tietorakenteeseen).
* `task_10/SearchGraphTests` -- kun olet toteuttanut leveys- ja/tai syvyyshaun, voit käyttää tämän testin testifunktioita testaamaan näiden hakujen oikeellisuutta.
* `task_10/DijkstraSearchTests` -- kun olet toteuttanut Dijkstran lyhimmän polun hakualgoritmin, voit testata tällä testillä sen oikeellisuutta. Testi testaa tätä erilaisilla verkoilla, mukaanlukien C++ -demojen Suomen junaverkostoaineistolla.


## Soveltava tehtävä - Mazes

Kun olet testannut toteutuksen oikeellisuutta, kokeile myös miten toteutuksesi toimii Mazes -pelin käyttöliittymän kautta. Huomaa että osa toiminnoista ei toimi jos et ole toteuttanut niihin liittyviä verkon algoritmeja.

> **Muista** ensin tehdä jo toivottavasti opittu rutiini -- **luo** ensin `shared` -kirjastosta jar -tiedosto komennolla Maven > Lifecycle > install tai komentoriviltä (shared -hakemistossa): `mvn clean install`.


### Hajautusfunktion toteutus peliin

**Viimeistele** ainoa puuttuva asia Mazes -pelistä. Tämä onnistuu yhdellä rivillä koodia.

Tässä pelissä `Mazes`:n verkko `Graph` sisältää verteksejä joissa on tietoa pelin solmuista -- niiden koordinaatit x ja y -koordinaatistossa. Tämä on toteutettu luokkaan `MazeNode`. Luokassa on:

* x ja y -koordinaatit pelin solmupisteille
* molempien arvo pitää olla suurempi kuin nolla, ja suurin mahdollinen koordinaatin arvo on `Short.MAX_VALUE`. Koordinaatit ovat siis 16 -bittisiä kokonaislukuja, joiden maksimiarvo on 32767.

Eli tässä verkko (*graph*) sisältää nyt sovelluskohtaisena tietona `MazeNode` -olioita. Näillä on käytännössä vain kaksi jäsenmuuttujaa, solmun sijainti pelin sokkelikon koordinaatistossa:

```Java
public class MazeNode implements Comparable<MazeNode> {
   
   private int xCoordinate;
   private int yCoordinate;
```

Koska `MazeNode` -olioita laitetaan `Vertex`:n elementeiksi, ja `Vertex.hashCode` laskee tiivisteen kutsumalla `element.hashCode`, tässä sovelluksessa tiivisteen laskenta tapahtuu siis `MazeNode.hashCode`:ssa.

Tällä hetkellä se palauttaa nollan. **Toteuta** tähän metodiin tehokas hajautusfunktio, joka tuottaa uniikkeja tiivisteitä `MazeNode` -olioista.

Tässä auttaa se tieto, että verkossahan on vertekseinä aina yksi koordinaattipiste **vain kerran**. Mitään koordinattipistettä ei lisätä verkkoon kahdesti. Eli x- ja y -koordinaattien yhdistelmä oon aina uniikki.

Huomaat myös että `MazeNode`:n koordinaatit ovat käytännössä arvoltaan aina `short` eli 16 -bittisiä. Vaikka jäsenmuuttujat ovat `int`, `MazeNoden`:n muodostin estää laittamasta arvoiksi muita kuin `short` arvoalueen koordinaatteja. Arvot ovat siis aina välillä `0...32767`.

Nyt on hyvä muistella mitä **Laitteet ja tietoverkot** -kurssilla puhuttiin tiedon esitysmuodoista, binääriluvuista ja minkä kokoisia tietotyyppejä numeeriset tiedot voivat olla (8, 16, 32, 64 bittiä, jne.).

Sitten tiedät myös että Javassa `int` on 32 -bittinen numero. Ja siihenhän mahtuu kaksi 16-bittistä arvoa.

Esimerkkinä vaikkapa koordinaattipiste x: 2 ja y: 4. Nämä ovat 16-bittisinä binäärilukuna:

```
2: 0000 0000 0000 0010
4: 0000 0000 0000 0100
```

Tuostahan saadaan "pötköön" laitettuna yksi 32-bittinen kokonaisluku binäärilukuna, niin että eka numero (2) on vasemmalla ja toinen numero (4) on oikealla:

```
0000 0000 0000 0010 0000 0000 0000 0100
```

Nyt kun verkkotietorakenteessa (eikä tässä pelissä) voi olla **toista** verteksiä eli koordinaattipistettä 2,4 (samassa koordinaattipisteessä) niin silloinhan on täysin varmaa että tuo saatu 32-bittinen numero on tasan varmasti uniikki tässä pelissä ihan aina. Sitä kannattaisi siis käyttää tiivisteenä...

Bittien siirto, jolla ym. operaatio onnistuu (*bit shifting*) on myös asia joka opetettiin **Laitteet ja tietoverkot** -kurssilla. Samoin binäärinen or -operaatio. Kertaa tarvittaessa nämä asiat kurssin materiaalista tai muista materiaaleista.


**Toteuta** siis `MazeNode.hashCode` siten että se muodostaa koordinaateista uuden `int` arvon, tiivisteen...:

1. siirtämällä x-koordinaatin bittejä vasemmalle 16 bitin verran, jolloin syntyy välituotteena arvo...:

```
0000 0000 0000 0010 0000 0000 0000 0000
```

2. ...tekemällä tästä syntyneen arvon ja y-koordinaatin kesken *binäärisen* or -operaation, jolloin syntyy arvo

```
0000 0000 0000 0010 0000 0000 0000 0100
```

3. joka palautetaan kutsujalle.

Tämä kaikki onnistuu siis yhdellä rivillä koodia.

**Testaa** toteutustasi tämän komponentin testillä `MazeNodeHashTests` jonka löydät siis tästä Mazes -sovelluksesta. Testi varmistaa että kaikki luodut `MazeNode` -olit lisätään varmasti Javan `Map` -tietorakenteeseen, joka hyödyntää tätä tiivistettä. Lisäksi testi mittaa kauanko lisäämisessä kestää. Jos tiivisteissä on on ongelmia, testiepäonnistuu ja/tai testi on erittäin hidas. Opettajan yllä kuvatulla ratkaisulla ja tietokoneella testi tulostaa:

```console
Starting to add 25 000 000 MazeNodex to HashMap using hashing
Duration of adding 25 000 000 MazeNodes to Map: 3 426 ms
```

> Jos koneesi muisti ei riitä 25 miljoonan verteksin kokoisen verkon luomiseen, pienennä tuota testikoodissa olevaa vakion `MAX_COORDINATE_VALUE` arvoa vähän (esim. 500 askelta) kerrallaan. Testi tulostaa muistin loppumisesta tiedon konsoliin. Jos testi epäonnistuu muusta syystä, sekin tulee näkyville. Tässä tilanteessa ratkaise ongelma, tarvittaessa opettajan avustuksella.

Kun testi menee läpi, voit kokeilla miten peli toimii. Jatka tästä alaspäin.

### Kokeile toimintaa MazeApp:ssa

Käyttöliittymässä `MazeApp` on jo valmiiksi toteutettuna koodi joka luo `Graph` -luokan ja luo automaattisesti satunnaisen pelikentän ja lisää verkon solmut ja reunat verkkotietorakenteeseen:

```Java
    MazeApp() throws Exception {
         maze = new Maze();
         maze.createMaze(sizeX, sizeY, true);
```

Kyseinen `Maze` -luokka sisältää toteuttamasi `Graph` -luokan olion jäsenmuuttujana:

```Java
public class Maze implements GraphPathListener<MazeNode> {

   private Graph<MazeNode> graph;
```

**Tutustu** koodiin `Maze.createMaze()` ja tutki millä logiikalla pelikenttä luodaan verkkotietorakenteeseen. Aina kun käynnistät pelin, se luo animoiden uuden satunnaisen (syklittömän) verkon.

Kun sovellus on käynnissä, voit luoda uusia eri kokoisia satunnaisia pelikenttiä **Maze Controls** -valikosta. Kokeile näitä. **Odota animaation päättymistä** ennen kuin yrität käyttää ikkunan alareunassa näkyviä painikkeita:

* BFS tekee verkkoon leveyshaun satunnaisesta lähtöpisteestä verkon yläreunasssa.
* BFS w(ith) target tekee leveyshaun kunnes se törmää kohdeverteksiin verkon alareunassa.
* DFS tekee verkkoon syvyyshaun satunnaisesta lähtöpisteestä verkon yläreunasssa.
* DFS w(ith) target tekee syvyyshaun kunnes se törmää kohdeverteksiin verkon alareunassa.
* Dijkstra suorittaa lyhimmän polun hakualgoritmin satunnaisesta yläreunan verteksistä satunnaiseen alareunan verteksiin.

Kokeile myös ladata peliin valmiita verkkoja tiedostoista, valikkokomennolla "Load maze file...":

* `large-cyclic-mace.txt` sisältää verkon jossa syklejä ja reunoilla on vaihtelevia painoja. Kokeile Dijkstran algoritmia miten se kiertää verkossa olevat alueet joissa painot ovat suurempia kuin oletusarvo 1 (jota ei näytetä reunojen yhteydessä). Jos lähtö- ja kohdeverteksit eivät mene siten että matkalla olisi painavampia reunoja, paina Dijkstra -nappia kunnes lähtö- ja kohdeverteksien välissä on painavampia reunoja. Tutki että reitti varmasti kiertää nämä alueet.
* `maze-weighted.txt` on toinen mutta pienempi verkko joka sisältää painavampia reunoja.
* verkossa `disc-maze-weighed.txt` on kaksi erillistä aluetta. Huomaat että syvyys- ja leveyshakualgoritmit eivät voi edetä toiselle alueelle. Dijkstra taas ilmoittaa virheestä jos lähtö- ja kohdeverteksit ovat erillisillä alueilla.

Jos huomaat että algoritmit Mazes -pelissä eivät oikein toimi siten kuin niiden mielestäsi pitäisi, vaikka testit menivätkin läpi, voit varmistaa asian kysymällä opettajilta. Voi olla että testit eivät kaikkea huomaa. 

Voit myös tallentaa generoidun satunnaisen verkon tiedostoon ja **muokata siitä oman pelikenttäsi**. Tiedosto tallentuu nimellä `new_maze.txt`. Kyseistä tekstitiedostoa voi editoida millä tahansa tekstieditorilla. Tiedostossa on seuraavat tiedot:

```
# Generated maze from MazeApp
# Maze of width x height y grid
MAZE 7 7
0,0 1,0 1.0
0,0 0,1 1.0
0,2 1,2 1.0
0,2 0,3 1.0
...
```
* Hashtagillä (#) alkavat rivit ovat kommentteja eivätkä vaikuta verkon muodostamiseen.
* Alussa pitää olla rivi jolla sana "MAZE" ja sen jälkeen pelin verkon koko, esimerkissä 7 x 7 solmua
* sen jälkeen luetellaan jokaisella rivillä verkon kahden solmun koordinaattipisteet ja niiden välisen reunan paino, välilyönneillä eroteltuina.

Yllä rivillä `0,0 1,0 1.0` todetaan siis että on verteksi pisteessä 0,0 ja toinen verteksi pisteessä 1,0 ja niiden välisen reunan paino on 1.0.

Jos editoit tiedostoja, varmista että koordinaattipisteet pysyvät verkon koon alueella (esimerkissä koordinaattien tulee olla välillä 0...6). Varmista myös ettet tee vahingossa kahdesti samaa reunaa. 

Verkko tässä sovelluksessa on luonnollisesti suuntaamaton, joten verteksistä pisteessä 0,0 muodostuu reuna verteksiin 1,0, ja päinvastoin.

## Lopuksi

Kun olet valmis, varmista että kaikki koodi on paikallisessa git -repositoryssäsi ja myös etärepositoryssäsi (komennot `git commit`, tarvittaessa uusille tiedostoille `git add` sekä `git push`).

**Varmista** vielä että

1. Kaikki `shared` -komponentin testit menevät läpi.
2. Olet tehnyt jokaisen tehtävän soveltavat tehtävät loppuun saakka, ne toimivat ja tehtäväkohtaiset testit menevät läpi.
3. Olet ymmrätänyt mitä teit ja mieti mitä opit. Näillä asioilla on merkitystä kun suoritat kurssin tentit!
4. Olet commitoinut git:llä kaiken koodin omalla koneellasi, ja työntänyt kaiken koodin etärepoosi.

Opettaja näkee vain **etäreposi** koodin, ei sitä mitä on omalla koneellasi. **Etärepon toteutus arvioidaan**, joten sen perusteella tapahtuu kurssin arviointikin.

## Tietoja

* Kurssimateriaalia Tietorakenteet ja algoritmit -kurssille | Data structures and algorithms 2021-2025.
* Tietojenkäsittelytieteet, Tieto- ja sähkötekniikan tiedekunta, Oulun yliopisto.
* (c) Antti Juustila 2021-2025, INTERACT Research Group.