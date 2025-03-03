Jeu de Mozart Distribué avec AKKA
=================================

Description :
-------------
Implémentation du Jeu de Mozart en système distribué avec AKKA.
Le projet gère la communication entre les musiciens, la coordination par un chef d’orchestre
et la résilience aux pannes.

Technologies utilisées :
------------------------
- Scala 2.13.6
- AKKA 2.6.14
- ConfigFactory pour la configuration réseau

Structure du projet :
---------------------
JeuMozart_AKKA/
- │── src/
- │   ├── main/
- │   │   ├── scala/
- │   │   │   ├── DisplayActor.scala
- │   │   │   ├── Musicien.scala
- │   │   │   ├── Projet.scala
- │   │   │   ├── Messages.scala

Lancer le projet :
------------------
1. Cloner le dépôt :
   git clone https://github.com/ton-user/JeuMozart_AKKA.git
   cd JeuMozart_AKKA

2. Compiler :
   sbt compile

3. Exécuter les musiciens (exécuter chaque commande séparément) :
   - sbt run 0  # Chef d'orchestre
   - sbt run 1  # Musicien 1
   - sbt run 2  # Musicien 2
   - sbt run 3  # Musicien 3

Fonctionnalités :
-----------------
- Gestion du chef d’orchestre qui distribue les mesures
- Communication entre les musiciens pour jouer la musique
- Gestion des pannes :
    - Un musicien peut tomber en panne
    - Si le chef tombe en panne, un autre musicien devient chef

Livrables :
-----------
- Rapport (rapport.pdf) avec :
    1. Schéma des acteurs et messages
    2. Explication du code
    3. Gestion des pannes
    4. Résultats des tests
- Projet compressé (projet.zip)

Auteurs :
---------
- [Melissa LATEB]