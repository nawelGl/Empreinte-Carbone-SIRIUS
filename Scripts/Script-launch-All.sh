USER_NAME=$(whoami)
#PROJET_DIR="/Users/$USER_NAME/Documents/GitHub/Empreinte-Carbone-SIRIUS"

#MODULE_DIR="${PROJET_DIR}/Scripts"
#cd MODULE_DIR
cd Scripts
#./exec-jar-server-depuis-ordi.sh &
#./Compilation-exec-front.sh
chmod +x Compilation-exec-front.sh
chmod +x exec-jar-server-depuis-ordi.sh
parallel ::: "./exec-jar-server-depuis-ordi.sh"  "./Compilation-exec-front.sh"

