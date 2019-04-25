**A webshopról**

**Főoldal**

A termékek táblázatos formában kerülnek megjelenítésre, név, majd gyártó alapján rendezve, kategória szerint csoportosítva - az adatbázisban megadott kategóriák sorrendje alapján (először az összes kategória összes terméke megjelenik, de lehet külön szűrni az egyes kategóriákra - ez nem frontend oldalon történik, hanem adatbázis szinten). A termékre kattintva annak részletező oldalára (termékoldalra) jutunk.
Az oldal a / címen elérhető, RESTful végpont: /products.
A főoldalon egy elkülönített részen megjelenik egy ajánlás az utolsó 3 vásárolt termékkel (véletlenszerűen).

**Termékoldal**

Az oldal a /product.html?address={address} címen elérhető, RESTful végpont a /products/{address}, ahol az első paraméter a termék címe. A termék adatai tételesen kerülnek kiírásra.
A terméket kosárba lehet tenni, akár többet is.
Megjelennek a termékről írt értékelések (az értékelésekről bővebben lentebb).

**Regisztráció**

A felhasználó regisztrálhatja magát: meg kell adnia a felhasználónevét, a viselt nevét és a jelszót és minden adat kötelező. A felhasználónévnek egyedinek kell lennie.
Elérhető a /register.html oldalon, ami a /users végpontot hívja POST metódussal. A jelszó hash-elve kerül letárolásra. Amennyiben már létezik a felhasználónév, vagy nem elég erős a jelszó, a felhasználó hibaüzenet kap a pontos problémáról.
Opcionális tétel volt annak ellenőrzése, hogy a jelszó elég erős-e - ez implementálva lett.

**Bejelentkezés, kijelentkezés**

Ha a felhasználó be szeretne jelentkezni, az alkalmazásba illő megjelenéssel rendelező bejelentkező oldalt kap. Hibás adatok bevitele esetén megjelenik egy hibaüzenet az mezők fölött.
Kijelentkezés után a felhasználó a bejelentkezési oldalra jut.

**Hibaoldalak**

Ha a felhasználó hibás url-t ír be, vagy olyan oldalra hivatkozik, mely nem létezik, informatív, az alkalmazásba illő megjelenéssel rendelező hibaoldalt kap.

**Szerepkör szerinti menü**

Minden oldalon megjelenik a felhasználó neve, és attól függően, hogy milyen jogosultságokkal rendelkezik, megjelenik a hozzá tartozó menü, gombok. Ha olyan oldal címét írja be, amelyhez bejelentkezés szükséges, a rendszer automatikusan a bejelentkező oldalra irányítja. Bejelentkezés nélkül a menüben van egy bejelentkezés, bejelentkezett felhasználónál egy kijelentkezés menüpont. Egy RESTful webszolgáltatás, mely visszaadja, hogy mi a bejelentkezett felhasználó neve és jogosultsága. A JavaScriptnek kell az oldalon a jogosultság alapján megjeleníteni a menüpontokat és felhasználói elemeket.

- Termék adminisztráció menüpont kizárólag admin felhasználónak jelenik meg. Látogatóként a termék adminisztráció címét beírva átirányít a bejelentkező oldalra.
- Kosárba helyezés gomb és kijelentkezés menüpont csak bejelentkezett felhasználónak jelenik meg.

**Felhasználói profil**

A felhasználó módosíthatja a viselt nevét és a jelszavát, ehhez kétszer kell megadnia a jelszót, és azoknak egyeznie kell.
Ellenőrizzük, hogy a jelszó elég erős-e: a felhasználónak meg kell adnia legalább 8 karaktert, egy kisbetűnek, egy nagybetűnek és egy számjegynek szerepelnie kell benne. Hibaüzenet kap a pontos problémáról, amennyiben: üresen hagyja a nevet, eltérő vagy nem elég erős a két jelszó.
A jelszó egyezőség ellenőrzése a szerver oldalon van.

**Felhasználó kosara, megrendelés**

A kosárba tett termékek listaszerűen kerülnek megjelenítésre, a tételek és kosár összértékével együtt, az egyes termékeket törölni lehet innen, s a kosarat is lehet üríteni. A kosár elérhető a /basket.html címen, RESTful végpont: /basket. A felhasználó itt tudja leadni a rendelést, ami után a kosár üres lesz. A rendeléshez itt tud megadni szállítási címet (erről bővebben lentebb). Üres kosár rendelése esetén hibaüzenetet kapunk.

**Szállítási cím megadása**

Megrendeléskor meg lehet adni szállítási címet is, többsorosat. Ez mind a rendelések listázásakor, mind az adminisztrációs felületen megjelenésre kerül. A felhasználó a rendeléskor már megadott szállítási címek közül választhat. Amennyiben kétszer ugyanazt a szállítási címet adta meg, csak egyszer kerül megjelenítésre.

**Felhasználó rendelései**

A rendelés leadása után a felhasználó a rendeléseim oldalon megtekintheti a leadott rendeléseit, mely tartalmazza a rendelés leadásának dátumát, és a megrendelt termékeket, dátum szerint sorba vannak rendezve.
Rendelés: /myorders RESTful végponton, POST metódussal. A rendelések listázása a /myorders.html címen, /myorders RESTful végponton GET metódussal.

**Termékértékelés**

A termékhez bejelentkezett felhasználó értékelést tud írni, valamint egy pontszámot tud adni (1-5 skálán). A beírt szövegben nem lehet HTML kód. Az értékeléseket a termékoldalon tudja beírni, és ott is jelennek meg, dátum szerint visszafelé. A felhasználó kizárólag a saját értékelését szerkeszteni vagy törölni tudja. Csak olyan felhasználó írhat termékértékelést, akinek az adott termékhez kiszállított státuszban lévő rendelése van (ha olyan felhasználó próbál értékelést hagyni, akinek nincs kiszállított olyan terméke, hibaüzenetet kap). Az értékelést és pontszámot beírva az megjelenik a termék lapon. Az értékelések dátum szerint visszafele sorrendben jelennek meg.

**Termékadminisztráció**

Klasszikus CRUD felület, kizárólag adminnak jelenik meg tartalom az oldalon. Elérhető az /adminproducts.html címen, RESTful végpont: /products. A keresőbarát címet a rendszer automatikusan generálja úgy, hogy az ékezetes karaktereket eltávolítja, a szóközöket kötőjelre cseréli, majd az összes további karaktert eltávolítja. (Keresőbarát név, ha a termék neve: 'phillips villanyborotva szakállvágóval', akkor az a keresőbarát cím, amit tárolunk az adatbázisban hogy 'phillips-villanyborotva-szakallvagoval'.) A következő funkciók elérhetők:

- Termékek listázása, nem azonos a vásárlók számára megjelenített oldallal.
- Új termék felvétele. Minden, az egyedi terméklapon megjelenő adat megadása kötelező. Kód és cím egyedi. Ár egész, és maximum 2M Ft.
- Termék adatainak módosítása (minden adat szerkeszthető)
- Termék törlése (csak logikailag lehet törölni, hiszen később szerepelhet rendelésben)

**Kategóriák**

Kategóriákat adminisztrálni lehet. Külön adminisztrációs felületen kerülnek listázásra, a sorszámuk szerint sorba rendezve. Amennyiben olyan sorszámot adunk meg, ami már létezik, az összes utána következőt eggyel hátrébb tolja, ezzel az adott kategóriát középre beszúrja. Nem adhatunk meg nagyobb kategória sorszámot, mint amennyi éppen van. (Azaz nem lehet lyuk a számozásban.) Ha nem adunk meg sorszámot, az adott kategória a legvégére kerül. Ugyanezek elvek mentén lehet módosítani. Kategóriát lehet törölni is. Felületen lehet újat felvenni, annak nevének és sorszámának megadásával. A név megadása kötelező. Termék adminisztráció esetén, termék felvételekor és módosításkor meg lehet adni kategóriát is. A kiválasztott kategória megjelenik a termék oldalon. Nem szükséges minden terméknek kategóriát megadni. A kategóriáknál el kell tárolni, hogy milyen sorrendben jelenjenek meg.

**Felhasználókezelés**

A felhasználókat listázni lehet, ahol megjelennek az adatai (felhasználónév, viselt név) - klasszikus CRUD felület, csak az admin számára elérhető. A felhasználó viselt nevét és jelszavát lehet módosítani, és ezeket kötelező megadni. Felhasználót lehet törölni is, ilyenkor a hozzá kapcsolódó megrendelésből el kell távolítani. A `/users` végpontot hívja.

**Összes megrendelés**

A rendeléseket listázni lehet, megjelenítésre kerül, hogy ki és mikor adta le a megrendelést, valamint hány tétel van benne, és az értékét - klasszikus CRUD felület, csak az admin számára elérhető. A rendelés státuszát is megjelenik, ami alapesetben aktív. A rendelések megjelenítési sorrendje alapértelmezetten a feladás ideje szerint csökkenő. Amennyiben kiválasztja a felhasználó a rendelést, megjelennek a benne szereplő termékek, egységárral. A megrendelést lehet módosítani, de csak tételt lehet belőle törölni. A teljes megrendelést törölni lehet (csak logikai törlés, ekkor a státusza legyen törölt). Listázáskor szűrni lehet, hogy ne az összes, csak az aktív státuszú megrendelés jelenjen meg. A rendelések listája elérhető az `/orders.html` címen, `/orders` RESTful végponton. Tételt törölni a `/orders/{id}/{address}` címen DELETE metódussal lehet.

**Dashboard**

A /dashboard.html címen jelenik meg, /dashboard végponton: felhasználók, termékek, megrendelések száma - mindez dinamikusan változik az webshop aktivitása alapján. Az oldalon csak admin felhasználó esetén jelenik meg tartalom.
A megjelenítés formája egyéni döntés volt.

**Riportok**

2 riportot tartalmaz: táblázatos formában megjelenítve, hogy havonta mely termékből mennyi fogyott, kizárólag a kiszállított megrendelések alapján. 
A /reports.html címen jelenik meg. Az első a /reports/orders, a másik a /reports/products végponton elérhető.

_Első riport_
- Tartalmazza az összes megrendelés adatát
- Havi és állapot szerinti bontásban
- Új megrendeléskor, megrendelés módosításkor és törléskor változnak az adatok
- Kiszámolásra kerül a megrendelések száma és összértéke

_Második riport_
- Csak a kiszállított megrendelések adatait tartalmazza
- Havi és termék szerinti bontásban
- Új megrendeléskor, megrendelés módosításkor és törléskor változnak az adatok
- Kiírásra kerül a termék egységára, valamint a megrendelt darabszám és az összesített ár