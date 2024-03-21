
USER_NAME=$(whoami)
PROJET_DIR="/Users/$USER_NAME/Documents/GitHub/Empreinte-Carbone-SIRIUS"

# Récupere le chemin vers le module xmart-city-backend
MODULE_DIR="${PROJET_DIR}/xmart-city-backend"

# On s'y rend
cd "${MODULE_DIR}" || exit

# Exécute la commande Maven pour construire le jar
mvn clean install
mvn clean package

#----- Chat gpt pour la verif -------

# Vérifiez si la construction a réussi
if [ $? -eq 0 ]; then
    echo "Le jar a été généré avec succès."
else
    echo "Erreur lors de la génération du jar."
    exit 1
fi
