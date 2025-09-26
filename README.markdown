# TIRA-2025

Tämä repository (koodiarkisto tai tietovarasto), lyhyesti "repo", sisältää ohjelmointitehtävät kurssille Tietorakenteet ja algoritmit (lyhyesti TIRA). Ohjelmointitehtävät luetellaan alempana tässä ohjeessa.

Sinä opiskelijana luot tästä repositorystä oman kopiosi eli forkin GitHubissa oman tunnuksesi alle. Sen jälkeen kloonaat (clone) tämän forkin omalle tietokoneellesi ja teet kurssin tehtävät omalla tietokoneellasi. Sitä mukaa kun saat tehtäviä tehtyä, toimitat tekemisesi forkin kautta opettajien arvioitavaksi.

> Etärepository **on pidettävä** aina ajan tasalla. **Jokaisena päivänä** kun koodaat kurssitehtäviä, teet commitin paikalliseen repositoryyn ja viet lopuksi muutokset etärepoosi. Näin jos sinulla on ongelmia, opettajat pääsevät välittömästi hakemaan koodisi etärepostasi, ja voivat ohjata sinua eteenpäin.
>
> Jos et saa tehtäviä tehdyksi kurssin määräaikoihin (*deadline*) mennessä, ja committisi ohjelmointitehtäviin tulevat epärealisisen lähellä deadlineja yhdellä kertaa, selvität asian opettajien kanssa. Tämä on yleensä merkki plagioinnista ja tehtävien ratkaisujen kopioimisesta muualta. Kaikki kurssin tehtävät ovat yksilösuorituksia, joten ryhmätyötä tässä kurssilla ei tehdä.

**TÄRKEÄ huomautus**: tehtävissä **ei saa käyttää** Javan Collection (tietosäiliö) luokkia eikä niihin liittyviä algoritmeja (esimerkiksi `Collections`, `Arrays`, `System`, `Set`, `ArrayList`, `LinkedList`, `Stack`, `Vector`, `Queue`, mikään `Map` -rajapinnan toteutus (esim. `HashMap`, `Hashtable`), `Arrays.sort`, `Collections.sort`. jne.) ellei *erityisesti* sanota että se on sallittua jossain *tietyssä* tehtävässä. 

On **sallittua** käyttää `String` -luokkaa ja kaikkia primitiivisiä (alkeis-)tietotyyppejä kuten int, long, short, double, float, char, näiden luokkaversiot (`Integer`, `Double`, jne.) ja "tavalliset" taulukot (esim. `String [] arrayOfStrings`). Niissä tehtävissä joissa pitää toteuttaa **hajautusfunktio** (tiivistefunktio, hash), **ei saa käyttää** Javan luokkien valmista `hashCode()` -metodia (esim. `String.hashCode()`), ellei taas erikseen toisin mainita, vaan tiivistefunktiot toteutetaan *itse*. Omia apuluokkia ja -algoritmeja on luonnollisesti hyväkin tehdä, tehtävästä riippuen.

## Ohjelmointitehtävät

Tässä kuvataan ohjelmointitehtävien alihakemistot ja tehtävät lyhyesti. Kunkin tehtävän tavoite ja ohjeet löytyvät tehtäväkohtaisesta alihakemiston README.markdown -tiedostosta. Ohjelmointitehtävien deadlinet löytyvät Moodlesta.

| Hakemisto | Tarkoitus tai tehtävä                                         |
|-----------|---------------------------------------------------------------|
| `shared`    | Kirjastokomponentti, johon toteutetaan ja testataan<br>yleiskäyttöiset tietorakenteet ja algoritmit, eri tehtävissä<br>Jos koodi ei käänny tai testit eivät mene läpi, korjaa<br>ongelmat sillä muuten et voi tehdä soveltavia tehtäviäkään.   |
| `task_01`   | Oikeellisuuden varmistaminen invarianteilla.               |
| `task_02`   | Rekursiivinen puolitushakualgoritmi ja soveltava tehtävä.  |
| `task_03`   | Osittamis- ja filtteröintialgoritmit ja soveltava tehtävä. |
| `task_04`   | Nopea lajittelualgoritmi ja soveltava tehtävä.             |
| `task_05`   | Pinotietorakenne ja soveltava tehtävä.                     |
| `task_06`   | Jonotietorakenne ja soveltava tehtävä.                     |
| `task_07`   | Linkitetty lista -tietorakenne ja soveltava tehtävä.       |
| `task_08`   | Binäärinen hakupuu -tietorakenne ja soveltava tehtävä.     |
| `task_09`   | Hajautustaulu -tietorakenne sekä hajautusfunktio ja<br>soveltava tehtävä (sama kuin `task_08`).|
| `task_10`   | Verkkotietorakenne ja soveltava tehtävä.                   |

Kirjasto `shared` tulee kurssin edetessä sisältämään kaikki toteuttamasi algoritmit ja tietorakenteet sekä niiden testit. Mikäli kirjaston koodi ei käänny tai ei mene testeistä läpi, Java ei pysty kääntämään tästä kirjastoa joita soveltavissa tehtävissä `task_nn` tarvitaan. Tällöin nekään eivät toimi.

Aina kun lisäät koodia tai korjaat bugeja `shared` -kirjastosta, muista kääntää tämä *kirjaston hakemistosta löytyvien ohjeiden mukaisesti*. Muuten lisäykset tai korjaukset eivät ole käytettävissä soveltavissa tehtävissä!

> **Älä siirtele** tai **muuta** hakemistorakenteita tai annettuja tiedostoja (lähdekoodi tai testiaineistot) mihinkään muualle kuin missä ne ovat, annettuina. **Sijoita** tehtävien ohjeissa mainitut luotavat tiedostot juuri sinne mihin ohje pyytää ne sijoittamaan, ei muualle. Älä muuta annettujen luokkien tai niiden metodien tai muita julkisia rajapintoja, niiden nimiä, tai poista niitä. Älä tee tätä edes silloin jos huomaat että jossain metodin nimessä on kirjoitusvirhe tai et pidä koodissa olevasta metodin tai luokan nimestä.
> 
> Opettajien testausympäristö esimerkiksi korvaa testit omassa palautuksessasi opettajien testeillä. Testit olettavat luokkien sijaitsevan juuri siinä hakemistossa missä ne joko alun perin ovat tai mihin ne tulee sijoittaa. Jos muutat tiedostojen sijainteja, hakemistojen nimiä tai paikkoja, tai luokkien rajapintoja, tai muuta vastaavaa, testit opettajan koneilla -- joissa tarkistus ja arviointi tehdään -- **eivät mene läpi koska koodi ei käänny**. Tällöin palautus arvostellaan hylätyksi.


## Työkalut

Kurssilla tarvittavien työkalut ja niiden asennusohjeet on kerrottu Moodlessa. 

**Asenna työkalut** ja jatka sitten ohjeiden lukemista eteenpäin. Katso myös Moodlesta löytyvät demovideot aiheesta.


## Miten pystytän työtilani

Ohjeessa [SETUP.markdown](SETUP.markdown) kerrotaan miten voit forkata ja kloonata kurssin alkuperäisen repon omaa työskentelyäasi varten. Tämän tehtyäsi työskentelet omalla koneellasi *paikallisen* (local) reposi kanssa, ja kun olet saanut tehtävää eteenpäin, työntää työsi omaan etärepositoryysi.

Käytät git:iä kurssilla siihen että:

1. lisäät uusia kooditiedostoja ja muutoksia olemassa olevaan koodiin omalla koneella olevaan paikalliseen git-repositoryysi,
1. työnnät (push) paikallisessa repossa olevaa koodia omaan etärepositooryysi,
1. josta opettajat voivat tarkastella edistymisetäsi, auttaa sinua ongelmien kanssa ja lopuksi hakea koodisi testattavaksi ja arvioitavaksi arvosanan antamista varten.

Seuraa siis ohjeita [SETUP.markdown](SETUP.markdown) -tiedostossa. Tämä pitää tehdä *vain kerran* kurssin alkaessa.

## Tehtävien toteutusten testaaminen

Jokaisessa tehtävässä on oma ohjeensa miten toteutat ja testaat algoritmien ja tietorakenteiden toteutukset oikeellisiksi ja/tai aikatehokkaiksi. Seuraa näitä ohjeita.

> On mahdollista testata toteutetut tehtävät myös kerralla. Yleensä tämä tehdään vasta kun olet tehnyt kaikki tehtävät ja voit varmistaa että kaikki varmasti toimii.
>
> Voit ajaa kaikki testit kerralla terminaalissa (*console*) tämän projektin juurihakemistoon, jossa tämä README -tiedostokin on, ja antaa komento `mvn test`. Tämä suorittaa sekä `shared` -kirjastoon että soveltavien tehtävien testit, jos soveltavassa tehtävässä on testejä. Varmista että kaikki testit menivät läpi ja mitään testejä ei skipattu (skip). Toki jos teet tämän vaikka tehtävän 5 valmiiksi saatuasi, sen jälkeen tulevien tehtävien testit skipataan.

## Ajoitus, arvostelu ja määräajat

**Noudata kurssin ohjelmointitehtävien määräaikoja; mikäli deadlineen kuuluvien pakollisten tehtävien testit eivät mene läpi deadlinen kohdalla, tämä alentaa kurssin arvosanaa.**

Huomaa että jos se sinulle on mahdollista, voit edetä **nopeammin** kuin kurssin aikataulu esittää. Deadlinesta ei kuitenkaan saa myöhästyä. Jos sairastut, sinulla on opintopsykologin todistus tai joku muu dokumentti joka voi mahdollistaa deadlinesta joustamisen, ota yhteyttä **etukäteen** tai välittömästi kuin mahdollista, kurssin vastuuhenkilöön. Deadlinesta ei jousteta muista kuin yllä mainituista syistä (eli pätevä syy ei ole esimerkiksi en ehtinyt, en jaksanut, en osannut, tein muita mielestäni tärkeämpiä asioita, olin matkoilla, tms.).

Plagiointi ja "tekoälyn" käyttö on kielletty tämän kurssin tehtävien tekemisessä. Jos tällaista ilmenee, tapaus käsitellään yliopiston ohjeistuksen mukaisesti. Kurssin materiaalia, mukaanlukien tehtävien ohjeet ja annettu lähdekoodi, ei saa kopioida mihinkään "AI" -työkaluun.

## Tietoja

* Kurssimateriaalia Tietorakenteet ja algoritmit -kurssille | Data structures and algorithms 2021-2025.
* Tietojenkäsittelytieteet, Tieto- ja sähkötekniikan tiedekunta, Oulun yliopisto.
* (c) Antti Juustila 2021-2025.
