# Mitä ParsingApp tulostaa ennen kuin olet toteuttanut XML:n tarkistuksen

Ohjelma tulostaa ensimmäisen, **oikeellisen** XML:n kohdalla:

```
# Persons Document

Generated at Fri Sep 05 14:26:45 EEST 2025

## Document Information

 * Subject: Oulu Customers
 * Description: Customers from Oulu city area
 * Original Document Date: 2025-06-18 13.39 EET

# List of Customers

## Customer  1

 - First name: Antti
 - Middle name: Odysseu
 - Family name: Juustila
 - Age: 42

## Customer  2

 - First name: Jouni
 - Middle name: Hector
 - Family name: Lappalainen
 - Age: 41

# Summary of records:

 * Persons in document: 2

---
```

Tämä vastaa XML:n sisältöä.

**EI-oikeellisen** XML:n sisältö voi näyttää vaikka tältä, olennaisilta osin:

```
...
# List of Customers
 - First name: Antti
 - Middle name: Odysseu
 - Family name: Juustila
 - Age: 42
 - First name: Jouni
 - Middle name: Hector
 - Family name: Lappalainen
 - Age: 41

# Summary of records:

 * Persons in document: 0

---
```

Kuten huomaat, XML:ssä piti olla kahden henkilön tiedot, mutta ensimmäisen `<person>` -elementti **puuttuu**. Antti ja Jouni ovat nyt yksi henkilö, ja lisäksi summary väittää että henkilöitä oli nolla kappaletta.

Tai sitten vaikka tältä, vähän tilanteesta riippuen:

```
# List of Customers
 - First name: Antti
 - Middle name: Odysseu
 - Family name: Juustila
 - Age: 42

## Customer  1

 - First name: Jouni
 - Middle name: Hector
 - Family name: Lappalainen
 - Age: 41

# Summary of records:

 * Persons in document: 1
 ```

Eli otsikon "List of Customers jälkeen ei tulekaan "## Customer 1.." vaan ensin vastaan tulleen asiakkaan tiedot, ja toinen asiakas on sitten "Customer 1". Ja lopussa ilmoitetaan asiakkaita olevan vain yksi kappaletta.


# Mitä ParsingApp tulostaa kun olet toteuttanut XML:n tarkistuksen?

Ensimmäinen, oikeellinen XML muunnettuna Markdown -muotoon tulostuu kuten aikaisemminkin.

Oikeellisuuden tarkistuksessa, jonka toteutat pinon avulla, pitäisi huomata, että toinen dokumentti on virheellinen

`main`:n `catch` -lohkossa pitää tulostua virheilmoitus, eli poikkeusolion mukanaan tuoma viesti siitä, mikä oli pielessä:

```
ERROR: Mismatching elements, expected </persons> but got </person>
```

Tämä siis jos yksityiskohtia ei tulosteta näkyville (`PersonXMLToMarkdownHandler`:n muodostimen parametri on `false`);
