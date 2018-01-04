# AtelierJupiter
Ce projet constitue une base pour vos expérimentations avec JUnit5.

## Présentation
Nous vous proposons de gérer des consommations de bières !

Une consommation se compose d'un trio : bière / date / lieu.  
Une bière est définie par son nom, son degré d'alcool et sa provenance.

Il est possible de :
* récupérer toutes les consommations
* sauvegarder une consommation
* supprimer une consommation
* récupérer une consommation par lieu ou par date


```
AtelierJupiter/
├── dao/
│   ├── exception/
│   │   └── BoboException.java
│   └── ConsommationDao.java
├── domain/
│   ├── Biere.java
│   ├── Consommation.java
│   └── User.java
├── service/
│   └── ConsommationService.java
├── util/
│   └── DateUtil.java
├── web/
│   └── AccueilController.java
└── Application.java
```

## Pour débuter

**Vous devez impérativement utiliser intelliJ 2017.**  
Si ce n'est pas le cas, vous trouverez une version sur le réseau : `\\SRVDATA\Logiciels\Outils Developpement\IntelliJ`

* Cloner ce dépôt avec `git clone https://github.com/paulinedess/AtelierJupiter.git`
* Placez vous sur la branche **/pratique** et expérimentez les fonctionnalités de JUnit5 dans la classe de test *ConsommationDaoJupiterTest*.

Vous trouverez dans cette classe, les squelettes des méthodes de tests ainsi que des indications dans les *TODO*.  

NB : Les tests avec JUnit4 sont réalisés dans la classe *ConsommationDaoTest.*

## Exemples

Vous pouvez consulter la documentation de JUnit5 : [ici](http://junit.org/junit5/docs/current/user-guide/)

Ou jeter un oeil sur les tests que nous avons réalisé : [là](https://github.com/KenaDess/AtelierJupiter/blob/master/src/test/java/com/norsys/dao/ConsommationDaoJupiterTest.java)
