install vagrant and the provider (virtualbox)

vagrant version ^1.8.4
virtualbox version ^4.3.34


the vagrant container has the ip 192.168.111.111
so point your hosts file:

192.168.111.111 spaceinvaders.app




to build from scratch: (not necessary)

after succesfully vagrant up go inside virtualbox

vagrant ssh

cd db

composer create-project --prefer-dist laravel/laravel spaceinvaders
