USER_NAME=$(whoami)
PROJET_DIR="/Users/$USER_NAME/Documents/GitHub/Empreinte-Carbone-SIRIUS"

cd

# Récupere le chemin vers le module xmart-city-backend
MODULE_DIR="${PROJET_DIR}/front-end"

# On s'y rend
cd "${MODULE_DIR}" || exit

mvn compile
mvn exec:java

cd

#echo "Exécution du script sur l'ordinateur local..."
#cd PROJET_DIR"/Scripts"

#chmod +x exec-jar-server-depuis-ordi.sh
#./exec-jar-server-depuis-ordi.sh