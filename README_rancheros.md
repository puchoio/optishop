https://rancher.com/docs/os/v1.2/en/running-rancheros/server/install-to-disk/
Coge un pincho de más de 512M y lo pinchas en tu portatil (formateado)
https://rancher.com/docs/os/v1.2/en/running-rancheros/workstation/boot-from-iso
Te descargas la iso: https://github.com/rancher/os/releases/
Hazle (esto hace un pincho bootable con rancherOS): sudo dd bs=4M if=rancheros.iso of=/dev/sdc<o donde esté tu pincho> status=progress oflag=sync
Create un cloud-config.yml en el portatil: https://rancher.com/docs/os/v1.2/en/configuration/#cloud-config
Formato:
	#cloud-config
	ssh_authorized_keys:
	  - ssh-rsa AAA...ZZZ example1@rancher (para esto te haces un ssh keygen y metes la pub aquí)
Pincha el pincho en la torre arranca desde pincho.
Tras el arranque de la torre hacemos: scp <user del portatil>@<host del portatil>:/<ruta a cloud-config.yml> trayendonos el fichero
hacemos en la terminal de la torre: sudo ros install -c cloud-config.yml -d /dev/sda
Con esto hecho termina y te dice de reboot.
Despincha y arranca.
una vez arrancado lo tratamos como un servidor: nos conectamos vía SSH: ssh -i <ruta a la clave PRIVADA> rancher@<ip de la torre>
INSTALACIÓN DEL KLUSTER EN LA TORRE:
Ya conectados hacemos: wget https://storage.googleapis.com/kubernetes-release/release/v1.18.4/bin/linux/amd64/kubectl
Modificamos los permisos: chmod -x kubectl
Lo movemos a /bin: sudo mv ./kubectl /bin/kubectl
Probamos que todo ha ido bien: kubectl version

Ya conectados hacemos: wget https://storage.googleapis.com/kubernetes-release/release/v1.18.4/bin/linux/amd64/kubeadm
Modificamos los permisos: chmod -x kubeadm
Lo movemos a /bin: sudo mv ./kubeadm /bin/kubeadm
Probamos que todo ha ido bien: kubeadm version

Ya conectados hacemos: wget https://storage.googleapis.com/kubernetes-release/release/v1.18.4/bin/linux/amd64/kubelet
Modificamos los permisos: chmod -x kubelet
Lo movemos a /bin: sudo mv ./kubelet /bin/kubelet

CONNTRACK (como no se puede hacer con el tar de la torre, lo hacemos en el portatil y después hacemos scp):
# download and install the conntrack dependencies
cd <donde quieras almacenar toda esta broza>
# libnfnetlink
wget http://netfilter.org/projects/libnfnetlink/files/libnfnetlink-1.0.1.tar.bz2
tar xvfj libnfnetlink-1.0.1.tar.bz2

# libmnl
wget http://netfilter.org/projects/libmnl/files/libmnl-1.0.3.tar.bz2
tar xvjf libmnl-1.0.3.tar.bz2

# libnetfilter
wget http://netfilter.org/projects/libnetfilter_conntrack/files/libnetfilter_conntrack-1.0.4.tar.bz2
tar xvjf libnetfilter_conntrack-1.0.4.tar.bz2

# libnetfilter_cttimeout
wget http://netfilter.org/projects/libnetfilter_cttimeout/files/libnetfilter_cttimeout-1.0.0.tar.bz2
tar xvjf libnetfilter_cttimeout-1.0.0.tar.bz2

# libnetfilter_cthelper
wget http://netfilter.org/projects/libnetfilter_cthelper/files/libnetfilter_cthelper-1.0.0.tar.bz2
tar xvfj libnetfilter_cthelper-1.0.0.tar.bz2

# libnetfilter_queue
wget http://netfilter.org/projects/libnetfilter_queue/files/libnetfilter_queue-1.0.2.tar.bz2
tar xvjf libnetfilter_queue-1.0.2.tar.bz2

# Conntrack-tools
wget http://www.netfilter.org/projects/conntrack-tools/files/conntrack-tools-1.4.2.tar.bz2
tar xvjf conntrack-tools-1.4.2.tar.bz2

# gcc
curl https://ftp.gnu.org/gnu/gcc/gcc-4.9.3/gcc-4.9.3.tar.gz -o ./gcc
##########
scp -ri ~/.ssh/id_rsa * rancher@<ip de la torre>:/tmp/

ssh -i ~/.ssh/id_rsa rancher@<ip de la torre>

cd libnfnetlink-1.0.1
./configure && make && make install
cd ..
cd libmnl-1.0.3
./configure && make && make install
cd ..
cd libnetfilter_conntrack-1.0.4
./configure PKG_CONFIG_PATH=/usr/local/lib/pkgconfig && make && make install
cd ..
cd libnetfilter_cttimeout-1.0.0
./configure PKG_CONFIG_PATH=/usr/local/lib/pkgconfig && make && make install
cd ..
cd libnetfilter_cthelper-1.0.0
./configure PKG_CONFIG_PATH=/usr/local/lib/pkgconfig && make && make install
cd ..
cd libnetfilter_queue-1.0.2
./configure PKG_CONFIG_PATH=/usr/local/lib/pkgconfig && make && make install
cd ..
cd conntrack-tools-1.4.2
./configure PKG_CONFIG_PATH=/usr/local/lib/pkgconfig && make && make install
