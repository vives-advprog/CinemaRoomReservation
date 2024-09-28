# CinemaRoomReservation - Stoelreservering voor een Film

In deze oefening wordt een systeem geïmplementeerd waarmee klanten stoelen kunnen reserveren in een bioscoopzaal. De Java-klas `CinemaRoom` beheert de reserveringen van stoelen voor een specifieke filmvoorstelling. De uitdaging ligt in het reserveren van meerdere stoelen die naast elkaar liggen in dezelfde rij.

---

## Vereisten:

1. **Constructor**:
    - Maak een `CinemaRoom` aan met de naam van de film, het aantal rijen, en het aantal stoelen per rij.
    - Het systeem moet automatisch stoelen genereren op basis van het aantal rijen en het aantal stoelen per rij.

2. **Stoelreservering**:
    - Reserveer een bepaald aantal stoelen die naast elkaar moeten liggen in dezelfde rij.
    - De methode `reserveConsecutiveSeats` krijgt als parameter het aantal gewenste stoelen mee
    - Als er niet genoeg opeenvolgende stoelen beschikbaar zijn, moet het systeem een foutmelding geven.

3. **Specifieke stoelreservering**:
    - Reserveer een specifiek aantal stoelen, beginnend vanaf een bepaald stoelnummer in een gegeven rij.
    - Als er niet voldoende opeenvolgende stoelen beschikbaar zijn vanaf dat startpunt, moet het systeem een foutmelding geven.

4. **Beschikbare en gereserveerde stoelen**:
    - Beheer twee lijsten: één voor beschikbare stoelen en één voor gereserveerde stoelen.
    - Zodra een stoel is gereserveerd, moet deze worden verwijderd uit de lijst met beschikbare stoelen en toegevoegd worden aan de lijst met gereserveerde stoelen.

5. **Validatie**:
   - Zorg ervoor dat foutmeldingen worden gegooid bij ongeldig aantal rijen of stoelen.
    
---

## Testen

De testklasse `CinemaRoomTest` bevat een complete testsuite voor alle logica in de klasse `CinemaRoom`. Dit project biedt studenten de mogelijkheid te leren werken met testframeworks zoals AssertJ en JUnit en kan als voorbeeld gebruikt worden voor het schrijven van testen in volgende projecten en opdrachten.

