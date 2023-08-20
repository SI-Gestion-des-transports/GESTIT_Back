#Erreurs Vscode
## Failed to get workspace folder from test item: $(symbol-method) test_create_retourAttendu_erreur400BadRequest().
Provient d'un bug dans VScode (apparemment d'un framework utilisé nommé Electron), lorsque le chemin du projet passe par un 
lien symbolique, sous Linux.
La solution est de déplacer le projet sur la même partition que l'installation Linux