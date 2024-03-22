m2=${M2_REPO}

#eval $(ssh-agent)
#ssh-add $HOME/.ssh/id_rsa
ssh toto@172.31.249.142 "mkdir -p .m2/repository/edu/ezip/ing1/pds/xmart-city-backend"

cd ../xmart-city-backend/target
scp -P 22 -r xmart-city-backend-1.0-SNAPSHOT-jar-with-dependencies.jar \
			toto@172.31.249.142:.m2/repository/edu/ezip/ing1/pds/xmart-city-backend/








