#execution du script dans vm

eval $(ssh-agent)
ssh-add $HOME/.ssh/id_rsa
ssh toto@172.31.249.142 "cd Scripts && ./execute-main-server.sh"
# Fonction pour arrêter le serveur sur la machine distante
function stop_server {
    echo "Arrêt du serveur sur la machine distante..."
    ssh  toto@172.31.249.142 "killall java"
}

# Gestionnaire de signal pour intercepter SIGINT (^C) et appeler la fonction stop_server
trap stop_server SIGINT