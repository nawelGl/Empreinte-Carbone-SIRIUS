# Envoi le script .sh qui permet d'executer le jar

#USER_NAME=$(whoami)
#PROJET_DIR="/Users/$USER_NAME/Documents/GitHub/Empreinte-Carbone-SIRIUS"

# Récupere le chemin vers le module xmart-city-backend
#MODULE_DIR="${PROJET_DIR}/Scripts"
#cd MODULE_DIR
cd Scripts
scp -P 3993 -p execute-main-server.sh	\
			toto@172.31.249.142:Scripts/execute-main-server.sh
