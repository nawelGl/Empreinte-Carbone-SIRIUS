

#revenir au repertoir du proto
cd ../"xmart-city-backend"

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
