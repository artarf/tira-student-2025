# Jono

Tehtävässä toteutat jonotietorakenteen ja sovellat sitä annetussa ongelmanratkaisutehtävässä.

Jonon toteutus tehdään `shared` -komponenttiin, annetun rajapinnan pohjalta ja testataan toimivaksi. Jonon toteutus vaatii noin 120 riviä koodia. 

Soveltavassa osuudessa täydennät annettua koodia, tämä vaatii noin 10 koodirivin lisäämistä. Kun `shared` -komponenttiin toteutettu jonotietorakenne toimii, sen testit menevät läpi, muista käyttää Maven > Install -komentoa `shared` projektissa, jolloin toteutus on saatavilla myös tässä komponentissa, soveltavaan tehtävään.

Jonon toteutuksessa käytetään sisäisenä tietorakenteena taulukkoa, jonka kokoa voidaan tarvittaessa kasvattaa dynaamisesti.

Toteutuksessa **vaaditaan** jonon algoritmeille seuraavat aikakompleksisuusluokat:

  * `capacity()`: O(1).
  * `enqueue()`: O(1) paitsi kun joudutaan reallokoimaan: O(n).
  * `dequeue()`: O(1).
  * `element()`: O(1).
  * `size():` O(1).
  * `isEmpty():` O(1).
  * `clear()`: O(1).
  * `toString()`: O(n).

Mikäli koodi ei vastaa kyseistä aikakompleksisuusluokkaa, toteutus on korjattava vastaamaan näitä, ennen kuin tehtävän deadline tulee vastaan. Sen jälkeen suoritus on hylätty.

**Lue** huolellisesti annetun rajapinnan `QueueInterface` dokumentaatio kommenteista.

## Lähteitä

* Kurssin luentokalvot.
* Liveluento (tallenne) ja sen vinkit ja esimerkit.
* Kurssikirja: Introduction to Algorithms, 4th ed (Comer et al) ss 254-257.
* Jonon rajapintaluokka `oy.interact.tira.student.QueueInterface` ja sen dokumentaatio, `shared` -komponentissa.

## Yleistä toteutuksesta

Metodin `clear()` toteutuksessa pinon kapasiteetiksi on tultava oletuskapasiteetti jos sisäisenä tietorakenteena on taulukko. Huomaa myös että aikakompleksisuusvaatimus tässä *todellakin* on O(1), eikä O(n).

Jonon ei tule käyttää enempää muistia kuin mitä sen maksimikapasiteetti milloinkin on. Esimerkiksi kahden (tai useamman) taulukon pitäminen *pysyvästi* muistissa (jonon jäsenmuuttujana) on virhe.

Jonoa toteuttaessasi, **varmista** ettet laita luokan jäsenmuuttujiksi sellaisia tietoelementtejä joita ei tarvita koko jono-olion elinajan. Jos tarvitset jotain muuttujaa ja sen arvoa vain yhdessä metodissa, muuttuja kuuluu metodin paikalliseksi muuttujaksi. Turhat jäsenmuuttujat kuluttavat turhaa muistia. Ne voivat myös aiheuttaa bugeja.

**Huomaa** että `toString()` -metodi tässä (kuten jonotietorakenteessakin) ja myöhemmissäkin harjoituksissa toteutetaan käyttäen Javan `StringBuilder` -luokkaa, **ei** `String` -oliota muokkaillen. Suuria määriä elementtejä käsitellessä `String`:n avulla merkkijonon muodostaminen on **satoja tai tuhansia kertoja hitaampaa** kuin `StringBuilder`in käyttäminen. Jos `toString()` käyttää `String`iä, tehtävä ei ole hyväksyttävä. Lisätietoa tästä pino -harjoituksen README:ssä.

## Askel 1 - Ohjeet

**Toteuta** `shared` -komponentin rajapinta `oy.interact.tira.util.QueueInterface` omaan luokkaasi `oy.interact.tira.student.ArrayQueue`. Älä muuta millään tavalla annettua rajapintaluokkaa. **Huomioi package -polut**; missä rajapinta on ja minne toteutusluokka menee!

**Lue huolellisesti** rajapintaluokan dokumentaatio, joka kertoo miten rajapinnan tulee käyttäytyä. Toteuta jono näiden ohjeiden mukaisesti.

Huomaa, että luokan metodit toimivat yhdessä ja yhteistyössä, käsitellen jonon sisäistä taulukkoa, jota siellä käytetään elementtien muistissa pitämiseen. Jonoa ei siis kannata yrittää testata ennenkuin olet saanut toteutettua sen kaikki metodit.

Samalla tavalla kuin pino, jono on myös geneerinen tietorakenne. Huomaa pinon ja jonon **erot**: pinossa elementtejä lisätään aina taulukon loppuun ja otetaan aina sieltä lopusta pois. 

Jonossa sen sijaan elementtejä lisätään aina taulukkoon *viimeksi lisätyn* elementin jälkeen, ja poistetaan "alusta" sinne lisätty ensimmäinen elementti.

"lisätään jälkeen" ja "...poistetaan alusta" **ei** kuitenkaan tarkoita sitä että lisätään isompaan indeksiin tai poistetaan aina taulukon alusta, pienemmästä indeksistä.

Katsotaan miten jono käyttäytyy. Jonon (jossa kokonaislukuja) alkutilan pitäisi näyttää tältä, kun jono on tyhjä:

```console
value:  _ _ _ _ _ _ _ _ _ _
index:  0 1 2 3 4 5 6 7 8 9
        ^
        head tail -- head ja tail viittaavat molemmat indeksiin 0
size: 0
capacity == array.length == 10
```

Muuttuja `head` viittaa siis nimensä mukaisesti jonon kärkeen, alkuun, ja `tail` vastaavasti jonon "hännille" eli loppuun. Lisääminen tehdään aina loppuun (`tail`), ja jonosta poistetaan aina kärjessä (`head`) oleva elementti.

> Lisäämisen ja poistamisen **pitää olla** O(1) -operaatioita, eli mitään järjestelyä tai siirtelyä lisätessä tai poistaessa jonosta **ei saa tehdä**. Elementtien siirtelyä tehdään vain taulukon reallokoinnin yhteydessä!

Sitten kun jonoon on lisätty muutama elementti (vaikkapa 4,5 ja 6), se näyttää tältä:

```console
value:  4 5 6 _ _ _ _ _ _ _
index:  0 1 2 3 4 5 6 7 8 9
        ^     ^tail
        head -- head on 0 ja tail 3.
size: 3
capacity == array.length == 10
```
Huomaa että `tail` osoittaa aina siihen taulukon indeksiin johon seuraava elementti laitetaan, eli siellä ei ole mitään. `head` taas osoittaa siihen missä on jonon kärki eli ensimmäinen elementti (paitsi silloin kun jono on tyhjä).

Kun jonoon lisätään ja sieltä otetaan pois elementtejä kutsumalla `enqueue` ja `dequeue` -metodeja, jonon sisäisen taulukon tilanne voi olla jossain vaiheessa vaikkapa tämä:

```text
Esimerkki kokonaislukujonosta (Integer), taulukon koko 10:
value: [11,12,13,_,_,_,_,8,9,10]
index:  0   1  2 3 4 5 6 7 8  9
                 ^       ^
                 tail    head
size: 6
capacity == array.length == 10
```

Eli jonoon on lisätty numeroita 1:stä eteenpäin, sitten poistettu numeroita (1...7) jonka jälkeen on taas lisätty numerot 8...13. Tila ei ole koskaan ollut vielä loppumassa, joten reallokointia ei ole tarvinnut tehdä. Taulukossa on ollut tilaa alussa, vaikka lopussa paikat on täytetty. On siis jatkettu täyttämistä *alusta* jossa tilaa on, jolloin indeksi `tail` < `head`.

Nyt seuraava lisättävä elementti menee "jonon hännille" indeksiin 3. Kun taas jonon "kärjestä" pois otettava elementti on indeksissä 8. Indeksimuuttuja `tail` joka kertoo missä jonon häntä on (tai häntää seuraava paikka, uudelle elementille), *voi olla siis arvoltaan pienempi* kuin jonon kärki jonka indeksi on tässä esimerkissä 8.

> Tämä on taulukkopohjaisen jonon toiminnassa se vaikeimmin ymmärrettävä asia. Pino on yksinkertaisempi, siellä elementit ovat aina indeksien `0..<size` välissä, ja lisääminen ja poistaminen tehdään aina loppuun. **Ota siis tämä jonon erilainen käyttäytyminen haltuun**, sillä toteutuksen tekeminen on helpompaa kun **periaate** miten jono toimii, on ensin hallussa.

Pinon `toString()` toteutus oli helppo - edetään vain indeksistä 0 indeksiin jossa on pinon viimeinen elementti. Nyt jonon kanssa ei näin kuitenkaan voida tehdä. Jos yo. taulukko laitettaisiin sellaisenaan alusta indeksistä 0 loppuun asti merkkijonoon, tulisi tästä merkkijono:

"[11, 12, 13, null, null, null, null, 8, 9, 10]"

Vaikka tuosta hypitään yli null:t, järjestys on silti väärä: numero 8 pitäisi olla tällä hetkellä jonon kärjessä, eli sen pitäisi tulla stringiin ensimmäisenä. Vastaavasti numero 13 on jonon viimeinen elementti, joten sen pitäisi tulla viimeisenä. Tässä on kyseisen jonon oikeellinen `toString` -tuotos:

"[8, 9, 10, 11, 12, 13]"

Huomaa siis tämä kun toteutat näitä jonon algoritmeja taulukkomuotoisessa toteutuksessa.

Jonon **taulukon reallokointi** tapahtuu samaan tyyliin kuin pino -harjoituksessa Nyt on vaan tässäkin huomattava se, että **taulukon käsittely on erilaista jonossa kuin pinossa**. Pinossa oliot ovat taulukossa aina indeksistä 0 eteenpäin. Mutta jonossa, kun elementtejä lisätään ja otetaan jonosta pois, elementit voivat olla jonossa reallokoinnin tullessa tarpeen, vaikkapa alla kuvatulla tavalla.

Esimerkiksi, jos jonon kapasiteetti oli aluksi 10, ja se on ehditty täyttää seuraavalla tavalla:

```console
value:  6 1 0 9 9 1 8 2 9 2
index:  0 1 2 3 4 5 6 7 8 9
                    ^      
                    tail head (head ja tail molemmat indeksissä 6; taulukko on täynnä!)
```

Kun reallokointi tämän jälkeen tehdään, uudessa taulukossa on nyt tilaa enemmän (20 elementille) ja taulukon pitäisi näyttää tältä:

```console
value:  8 2 9 2 6 1 0 9 9 1 
index:  0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19
        ^                    ^      
        head                 tail 
```
Ja uusi elementti voidaan lisätä nyt indeksiin `tail`.

Eli tässä vaiheessa kun reallokointi tehdään, voidaan tavallaan "siistiä" taulukko siten että jonon kärki on taulukon alussa ja häntä jossain kohtaa taulukon keskivaiheilla. Mutta taas kun taulukkoon lisätään ja sieltä poistetaan elementtejä, sisältö taulukossa voi olla taas kuten edellä on kuvattu.

> Huomaa myös että kun poistat elementin listalta, sen viemä muisti on vapautettava. Taulukkoa käytettäessä tämä tarkoittaa sitä että elementin indeksiin on sijoitettava null. Muuten tapahtuu **muistivuoto** (*memory leak*), olio on muistissa vaikkei sitä enää tarvita. Muisti vapautuu vasta kun taulukkoon laitetaan joku toinen olio tai koko jonotietorakenneolio häipyy muistista (tai sovellus sammuu).


## Testaaminen yksikkötesteillä

Kun olet valmis testaamaan taulukkototeutustasi, **Luo** (`shared` -komponentissa) toteuttamasi olio `oy.interact.tira.factories.ArrayQueueFactory` -luokan eri metodeissa ja palauta olio kutsujalle. Testit ja muu annettu koodi saavat jonon toteutuksesi käsiinsä vain tämän tehdasluokan kautta, joten jos tämä unohtuu tehdä, testit epäonnistuvat. 

Jos sinulla ei ole tarvittavaa kahta muodostinta luokassasi, tee ne:

1. Parametriton versio luo jonon taulukon *oletuskapasiteetin* kokoiseksi jos toteutuksena on taulukko.
2. Parametrillinen versio (int) luo taulukon sen kokoiseksi kun parametri kertoo.

Muistaa antaa kaikille jäsenmuuttujille järkevät arvot muodostimissa.

**Testaa** toteutustasi testiluokan `ArrayQueueTests` kautta. Testi löytyy hakemistosta `task_05`.

Jos kaikki toimii, voit edetä seuraavaan askeleeseen. Muista ensin tehdä `shared` -komponentissa Maven > Lifecycle > install -komento. Kaikki testit ajetaan ja jos ne menevät läpi, jonotietorakenne on käytettävissä tässä projektissa soveltavaan tehtävään.

## Askel 2 - Ohjeet

Tässä askeleessa jonotietorakennetta käytetään johonkin järkevään, soveltavaan tehtävään. Suurin osa koodista on annettu valmiina, täydennät vain pientä osaa annettua koodia ja muokkaat sitä kun ratkaiset tehtävää.

Tässä tehtävässä simuloidaan Pienen suklaakaupan jonotustilannetta. Kaupalla on sellainen ongelma, että se on erittäin suosittu. Erityisesti ruuhka-aikoina asiakkaita tulee paljon, ja heidän palvelemisensa vie aikaa. Jonon pituus kasvaa, asiakkaat turhautuvat ja kauppa saa huonoa mainetta huonosta asiakaspalvelusta.

Tällä hetkellä kaupassa on vain yksi kassa ja yksi työntekijä. Hypoteesi on, että ruuhka-aikojen asiakaspalvelua voisi parantaa kassoja lisäämällä. Mutta kassat maksavat, työntekijöiden palkkaus maksaa, joten olisi arvioitava mikä on se optimaalinen määrä kassoja ja kassatyöntekijöitä -- ja siten jonoja asiakkaita -- joilla ruuhka-ajat saataisiin hoidettua paremmin mutta kustannustehokkaasti.

Onneksi Pienen suklaakaupan omistaja tuntee useita Tietorakenteet ja algoritmit -kurssin opiskelijoita, ja sinä olet yksi heistä.

Tehtäväsi on hyödyntää toteuttamaasi jonotietorakennetta simuloimaan jonotustilannetta ruuhka-aikoina ja löytää optimimäärä kassoja eli jonoja, joilla ongelma ratkeaisi parhaiten.

Tätä varten Pieni suklaakauppa on toimittanut meille tietoa joilla voimme simuloida jonojen määrän vaikutusta:

1. kustannuksiin, jotka johtuvat **jonotuksesta**. Asiakkaiden määrä jonoissa on kustannus, tilan käyttö, asiakastyytymättömyys voidaan arvioida kustannuksena, uusia asiakkaita ei tule kauppaan jos kauppa on tupaten täynnä jonottavia asiakkaita, jne.
2. **yksittäisen kassan** kustannuksiin. Kassakoneet maksavat, asiakaspalvelijoita pitää palkata lisää, jne.
3. **ylityökustannuksiin**. Jos jonossa olevia asiakkaita ei saada palveltua kaupan sulkeuduttua, kassatyöntekijät joutuvat tekemään ylitöitä, josta tulee ylimääräisiä kustannuksia.

Simulaation vakioparametrit ovat (`LittleChocolateShop` -luokan jäsenmuuttujia):

* Simulaation kestoaika (kaupan aukioloaika) aikayksiköissä: `TIME_UNITS_OPEN = 3_600`.
* Yksittäisen asiakkaan jonotuksen kustannus per aikayksikkö: `CUSTOMER_TIME_IN_QUEUE_UNIT_COST = 0.02`.
* Yksittäisen jonon (kassan) ylläpidon vakiokustannus: `SINGLE_QUEUE_FIXED_COST = 710.0`.
* Sulkemisajan jälkeen tehtävä jonojen purkamisen kustannus per aikayksikkö: `OVERTIME_COSTS_PER_QUEUE = 10.0`.

Eli suomeksi; simuloidaan kaupan toimintaa 3600 aikayksikön verran. Aina kun asiakas seisoo jonossa odottaen palvelua, kustannus on 0.02 euroa (kaksi eurosenttiä) per aikayksikkö. Jokaisen jonon (kassan) avaamisen kustannus on 710.00 euroa. Jos jonojen purkaminen jatkuu aukioloajan päättymisen jälkeen, ylitöiden aiheuttama lisäkustannus per jono on 10.00 euroa per kassa.

Nämä ovat siis vakioparametreja joita *ei simulaatiossa muuteta*. Simulaatiossa on yksi muuttuva parametri: jonojen lukumäärä (jäsenmuuttuja `preparedQueues` eli käytössä olevien kassojen määrä simulaation suorittamisen yhteydessä).

**Ennen** ohjelman suoritusta sinun on viimeisteltävä simulaation koodi luokassa `LittleChocolateShop`:

* Metodissa `prepareQueues` seuraa koodin kommentteja ja luo jonoja sen mukaan mikä on parametrin `count` arvo. 
* Metodissa `newCustomerEntered()` seuraa koodin kommentteja ja lisää uuden jonon tulleen asiakkaan jonotusnumero jonoon. Koodi etsii jo valmiiksi lyhimmän jonon johon jonotusnumero lisätään.
* Metodissa `customersServed()` seuraa koodin kommentteja ja ota valmiiksi haetusta jonosta seuraavaksi palveltavan asiakkaan jonotusnumero muuttujaan, joka tulostetaan myöhemmin ko. metodissa konsoliin.

Nämä käsiteltävät jonot ovat tietysti sinun itsesi toteuttamia jonotietorakenne -olioita `shared` -komponentista.

Aloitat simulaation yhdellä kassalla joka onkin valmiiksi arvona `preparedQueues` -muuttujassa. 

**Suorita** ohjelma ja seuraa simulaation toimintaa. Sovellus piirtää näkyviin jonon jonotustilanteen:

* Vasemmalla on jonon kärki `head`, asiakkaan jonotusnumero sille seuraavaksi palveltavalle asiakkaalle.
* Oikealla on jonon häntä `tail`, jonne seuraava jonottava asiakas (jonotusnumero) sijoitetaan.

Aina kun jonon kärjessä oleva asiakas on palveltu ja hän poistuu liikkeestä, jonon ruutu piirretään punaisella taustalla. Aina kun jonon hännille ilmestyy uusi asiakas, ruutu piirretään sinisellä taustalla.

Jonon nimen perässä näkyy hakasuluissa jonossa olevien asiakkaiden määrä.

**Seuraa** myös konsoliin tulostettavia tietoja:

```console
[  20/3600] Customer # 1 entered queue Queue 1
[  40/3600] Customer # 2 entered queue Queue 1
[  55/3600] Served customer # 1 from queue Queue 1
[  60/3600] Customer # 3 entered queue Queue 1
[  80/3600] Customer # 4 entered queue Queue 1
...
[10725/3600] Served customer # 179 from queue Queue 1
Serving 180 customers took 10725 time units in queues
Unit Costs: time customers in queues: 0,02, queue fixed cost: 710,00
Overtime cost per queue: 10,00
Total costs: time in queues: 13 978,60
Queue fixed costs (1 queues used): 710,00
Overtime costs: 71250,00
TOTAL sum of costs: 85 938,60
```

Konsoliin tulostuu aikaleima, simulaation kokonaisaika, ja jonon tapahtumatietoja. Lopuksi tulostuu myös analyysin kustannustietoja.

Huomaat että yhdellä kassalla/jonolla asiakaspalvelu on tosiaankin aika hidasta.

**Muuta** jäsenmuuttujan `preparedQueues` arvoksi 2. Sammuta ohjelma ja suorita se uudelleen. Tarkkaile jonojen toimintaa käyttöliittymässä, konsolitulostuksia ja erityisesti lopun tulostuksia simulaation kustannuksista.

Huomaat varmaankin että eroa tuli. Kysymys kuuluukin, **mikä on optimaalinen jonojen määrä** jolla kokonaiskustannukset saataisiin mahdollisimman pieniksi?

Koska simulaation suorittaminen näin on hidasta, **muuta** nyt luokassa `QueuePanel` olevien simulaatiota kontrolloivan muuttujan arvoa. Arvo siellä on alun perin:

```Java
    private boolean measureAndCompare = false;
```

Muuta se:

```Java
    private boolean measureAndCompare = true;
```

Tämä muuttaa sovelluksen toimintaa siten että simulaation tulostuksia vähennetään, sovellus suorittaa simulaation aluksi yhdellä jonolla, sitten kahdella, sitten kolmella, jne. Simulaatio lopetetaan kun se on suoritettu kahdeksalla jonolla.

**Suorita** simulaatio ja odota sen päättymistä. Konsoliin tulostuu nyt seuraavan näköista dataa:

```console
Number of queues,Queue fixed costs,Customers in queues costs,Overtime costs,Totalcosts
1,710.00,13978.60,71250.00,85938.60
2,1420.00,4718.80,44600.00,50738.80
...
```
Tämä on pilkuilla eroteltuja mittaustuloksia (*comma separated values*, csv). **Vie** data taulukkolaskinsovellukseen (Excel, Apple Numbers, Google Sheets, tms). **Piirrä** datasta graafi josta näet miten eri osakustannukset ja kokonaiskustannus muuttuvat jonojen määrän kasvaessa.

> Huom: tässä desimaalierottimena on käytetty pistettä, koska csv käyttää pilkkua dataelementtien erottamiseen toisistaan, joten pilkkua ei voi käyttää desimaaliosan erottimena. Jos taulukkolaskinsovellukseen viety data ei ymmärrä pisteellä eroteltua dataa reaaliluvuiksi (esim. suomalaisessa localessa reaalilukujen desimaalierotin on pilkku, ei piste), muuta *siinä taulukkolaskinsovelluksessa* pisteet pilkuiksi.

**Mikä on kokonaiskustannuksiltaan halvin ratkaisu** Pienen suklaakaupan asiakaspalveluongelmiin? Monellako jonolla eli kassalla suosittelisit kaupan omistajalle ruuhka-aikojen asiakaspalvelun hoidettavan, annetuilla kustannusparametreilla?

## Tarkistukset

Analysoi erityisesti sitä, onko jonon toteutuksesi oikeasti sellainen että se vastaa tehtävän alussa esiteltyjä aikakompleksisuusvaatimuksia. Jos ei vastaa, korjaa toteutusta.

> Mikä tahansa metodi jossa on *silmukka*, ei *voi* olla O(1) -- onko sinulla silmukoita metodeissa joissa vaatimus oli O(1)? Tai kutsutko tällaisesta metodista jotain *toista* metodia jonka O on muuta kuin O(1)? Jos näin on, aikakompleksisuusvaatimus ei täyty.

**Varmista** ettet ole käyttänyt toteutuksessa kurssilla kiellettyjä ratkaisuja. Tähän on työkalu jolla voit tarkistaa yleisimmät virheet tässä asiassa. Lue sen ohjeet `shared` -komponentin readme:stä.


## Lopuksi

Kun olet valmis, varmista että kaikki uusi ja muutettu koodi on paikallisessa git -repositoryssäsi ja myös etärepositoryssäsi (komennot `git commit`, tarvittaessa uusille tiedostoille `git add` sekä `git push`).

## Tietoja

* Kurssimateriaalia Tietorakenteet ja algoritmit -kurssille | Data structures and algorithms 2021-2025.
* Tietojenkäsittelytieteet, Tieto- ja sähkötekniikan tiedekunta, Oulun yliopisto.
* (c) Antti Juustila 2021-2025, INTERACT Research Group.