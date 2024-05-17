eval $(ssh-agent)
ssh-add $HOME/.ssh/id_rsa
ssh toto@172.31.249.142
"cd Scripts && chmod +x execute-main-server.sh && ./execute-main-server.sh"
