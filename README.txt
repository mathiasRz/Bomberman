Liste des designs patterns utilisés :

Pattern modèle-vue-controleur : organiser le code en trois partie -modèle : le jeu
								  -vue : l'interface du jeu 
							          - controleur : met en lien la vue et le modele
Pattern Etat : permet de gérer les différent état du jeu (reset,start,pause,mode manuel..)
Pattern Observateur : interface graphique du jeu qui écoute la classe du jeu pour mettre à jour ses élément
Pattern Fabrique : permet de fabriquer a la chaine des agents spécifique en les faisant rentrer dans un "moule", pour faciliter leur utilisation 
Pattern Strategie : implémente les différentes stratégie des agents (bombermans ou ennemis) 


Liste des fonctionnalités nouvelles :
Joueur controlable : commande Z,Q,S,D et B pour poser une bombe.(il faut cliquer sur la fenetre du jeu pour que le jeu prenne en  compte les frappes de touche)
IA bomberman et ennemi : les bombermans cherchent à eviter le danger et recherche un gilet d'invincibilité pour pouvoir jouer aggressivement,
			 les ennemis traquent le bomberman le plus proche avec une probabilité a chaque tour de le traquer de 60%, les 40% du temps restants
			 ils jouent au hasard pour pas que ce soit trop dur.