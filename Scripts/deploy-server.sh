m2=${M2_REPO}
cd ~
eval $(ssh-agent)
ssh-add $HOME/.ssh/id_rsa

scp -P 22 -rp .m2/repository/edu/ezip/ing1/pds/xmart-city-backend/1.0-SNAPSHOT \
			toto@172.31.249.142:.m2/repository/edu/ezip/ing1/pds/xmart-city-backend/



