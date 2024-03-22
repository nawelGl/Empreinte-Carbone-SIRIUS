

echo "Connexion à la VM avec SSH et lancement du serveur..."
sshpass -p "toto" ssh toto@172.31.249.142 'bash -s' << 'ENDSSH'
cd Scripts
./execute-main-server.sh
ENDSSH
