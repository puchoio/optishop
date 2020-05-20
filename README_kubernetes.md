# install kubernetes on node (based on: https://phoenixnap.com/kb/install-kubernetes-on-ubuntu)

1. Install docker:

```bash
sudo apt-get install docker.io
```
2. Check docker installation and daemon running:

```bash
sudo systemctl status docker
```

3. Check docker installation and daemon running:

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

**for real environment you must deactivated the swap option**

```bash
sudo swapoff –a
```
4. Now we have to set masters and workers nodes, in this case I will only set a master node but I will provide both ways to do this

```bash
sudo hostnamectl set-hostname master-node

sudo hostnamectl set-hostname worker01
```
5. Initialize the master node

```bash
sudo kubeadm init --pod-network-cidr=10.244.0.0/16
```
- Once this command finishes, it will display a kubeadm join message at the end. Make a note of the whole entry. This will be used to join the worker nodes to the cluster. in our case is: 

lechowsky:

```
Your Kubernetes control-plane has initialized successfully!

To start using your cluster, you need to run the following as a regular user:

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

You should now deploy a pod network to the cluster.
Run "sudo kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/

Then you can join any number of worker nodes by running the following on each as root:

kubeadm join 192.168.1.129:6443 --token 4rn7fp.fdf39d71x4wz2nn8 \
    --discovery-token-ca-cert-hash sha256:2b66294d833ecacae427ce08c813ed477da69f548245db790905807da3d45d2e 
```
pucho:

```
Your Kubernetes control-plane has initialized successfully!

To start using your cluster, you need to run the following as a regular user:

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

You should now deploy a pod network to the cluster.
Run "sudo kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/

Then you can join any number of worker nodes by running the following on each as root:

kubeadm join 192.168.1.137:6443 --token kbt13i.9kqgumd6wa05zbz1 \
    --discovery-token-ca-cert-hash sha256:bf7cfe08612ed7b2c8d41e6da66a8abfc80138edb732b7eb5d7047f38acea6b5
```

Check that pods are running on the k8s node:

```bash
kubectl get pods --all-namespaces
```

If we want to add worker nodes to the cluster:

```bash
kubeadm join --discovery-token abcdef.1234567890abcdef --discovery-token-ca-cert-hash sha256:1234..cdef 1.2.3.4:6443
```

