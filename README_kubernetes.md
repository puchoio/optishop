# install kubernetes on node

```bash
sudo apt-get install docker.io
```

```bash
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add
```

```bash
sudo apt-add-repository "deb http://apt.kubernetes.io/ kubernetes-xenial main"
```
```bash
sudo apt-get update
```

```bash
sudo apt-get install kubeadm kubelet kubectl
```

```bash
sudo apt-mark hold kubeadm kubelet kubectl
```

for real environment youmust deactivated de swap option
```bash
sudo swapoff –a
```
now we have to set masters and workers nodes, in this case i will only set a master node but i will provide you both ways to do this
```bash
sudo hostnamectl set-hostname master-node

sudo hostnamectl set-hostname worker01
```
initialize the master node
```bash
sudo kubeadm init --pod-network-cidr=10.244.0.0/16
```
Once this command finishes, it will display a kubeadm join message at the end. Make a note of the whole entry. This will be used to join the worker nodes to the cluster. in our case is: 

```
Your Kubernetes control-plane has initialized successfully!

To start using your cluster, you need to run the following as a regular user:

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

You should now deploy a pod network to the cluster.
Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/

Then you can join any number of worker nodes by running the following on each as root:

kubeadm join 192.168.1.129:6443 --token 4rn7fp.fdf39d71x4wz2nn8 \
    --discovery-token-ca-cert-hash sha256:2b66294d833ecacae427ce08c813ed477da69f548245db790905807da3d45d2e 
```

next, we create de directory cluster


