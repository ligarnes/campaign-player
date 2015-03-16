# Environnement de développement #
Tous les projets sont des projets qui utilise maven

Le plus simple pour mettre en place un environnement de développement est d'utiliser eclipse avec le plugin m2e.

Pour ce faire vous pouvez suivre les étapes suivantes
  * Télécharger eclipse: http://www.eclipse.org/downloads/
  * Télécharger le plugin m2e: http://eclipse.org/m2e/

  * faire un checkout complet du projet
  * Lancer eclipse
  * Import -> Existing Maven Project pour chacun des projet
  * une fois chaque projet importer faire dans l'ordre (Rmi-Server, DistributedBeanManager, CoreSystemBean, GuiShared, Player, Pathfinder-System):
    * clique droit sur le projet -> run as -> maven install
    * clique droit sur le projet refresh
  * Si des changements sont effectué sur le projet Pathfinder-System il est nécessaire de faire **maven install** puis de copier le jar générer dans Pathfinder-System/target et de le copier dans le dossier CampaignPlayer/ressources/plugin/pathfinder (voir https://code.google.com/p/campaign-player/wiki/CopieAutomatiqueDeFichierDePlugin)

Si tout fonctionne correctement il n'y aura aucune erreur et vous pourrez exécuter le projet Player.