[LT]
# Vartotojo užklausų apdorojimo sąsaja
Sąsajos veikimui reikalingi:
* [POstrgeSQL](https://www.postgresql.org/download/)
* [Java SE Development Kit 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

Sekmingai paleista sistema bus pasiekiama `localhost:8083`

Egzistuojantys endpoint'ai:
* [GET] /nss/v2/getInfo
  * Turi būti pateiktas užklausos parametras `name` kuris turi būti egzistuojančio vartotojo vardas.
  * Gražinamas JSON atsakymas.
* [GET] /nss/v2/getRssFeeds
  * Gražina RSS šaltiniu sąraša iš DB. Į DB gali būti pridėtas bet koks validus RSS šaltinis.
* [GET] /nss/v2/getSources
  * Gražina galimu NewsAPI šaltinių [sąrašą](https://newsapi.org/docs/endpoints/sources).
* [POST] /nss/v2/subscribe
  * Užklausos kuno pvz.:
```yaml
{
    "name": "medicine.eu",
    "searchTerms": [
        "covid",
        "vaccine"
    ],
    "excludedTerms": [],
    "sources": [
        "ABC News (AU)",
        "Google News (Australia)"
    ],
    "rssFeeds": []
}
```
* [DELETE] /nss/v2/unsubscribe
  * turi būti užklausos parametras `topic`, unikalus Kafka temos pavadinimas kūriamas kartu su prenumeravimu, gali būti peržiūrėtas sekmingo prenumeravimo metu ir naudojant `/nss/v2/getInfo` endpoint'ą
