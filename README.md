# 🎬 CinemaRoom Reservation System 🍿

In deze oefening wordt een systeem geïmplementeerd waarmee klanten stoelen kunnen reserveren in een bioscoopzaal. De Java-klas CinemaRoom beheert de reserveringen van stoelen voor een specifieke filmvoorstelling. De uitdaging ligt in het reserveren van meerdere stoelen die naast elkaar liggen in dezelfde rij.

---

## 🚀 Functionaliteiten

### 🏗️ 1. Slimme Zaalconfiguratie
Bij het aanmaken van een `CinemaRoom` geef je de filmnaam, het aantal rijen en het aantal stoelen per rij op. Het systeem genereert automatisch alle stoelen voor je!
*   **Validatie:** Geen ongeldige zalen! Het aantal rijen en stoelen moet altijd groter zijn dan 0.

### 💺 2. Opeenvolgende Stoelen Reserveren
Wil je met een groepje vrienden naast elkaar zitten? De methode `reserveConsecutiveSeats` zoekt automatisch naar de eerste beschikbare rij waar voldoende stoelen naast elkaar vrij zijn.
*   **Foutafhandeling:** Indien er nergens genoeg plek is, wordt er netjes een `NotEnoughConsecutiveSeatsInRowException` gegooid.

### 📍 3. Specifieke Stoelreservering
Heb je een voorkeur voor een bepaalde plek? Je kunt ook reserveren vanaf een specifiek stoelnummer in een gekozen rij.
*   **Controle:** Het systeem checkt of alle stoelen vanaf dat startpunt in die rij daadwerkelijk beschikbaar zijn.

### 📊 4. Real-time Stoelbeheer
Het systeem houdt nauwkeurig twee lijsten bij:
*   ✅ **Beschikbare stoelen**: Stoelen die nog vrij zijn voor verkoop.
*   🔒 **Gereserveerde stoelen**: Stoelen die al bezet zijn.
Zodra een stoel gereserveerd wordt, verhuist deze automatisch van de beschikbare naar de gereserveerde lijst.

---

## 🧪 Testen & Kwaliteit

Kwaliteit staat voorop! De `CinemaRoomTest` suite bevat uitgebreide tests met **JUnit** en **AssertJ**.
*   ✅ Volledige dekking van de reserveringslogica.
*   ✅ Verificatie van edge cases en foutmeldingen.
*   🎓 Ideaal als leervoorbeeld voor studenten om professionele unit tests te leren schrijven.

![Coverage](.github/badges/jacoco.svg)

---


