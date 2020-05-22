# deploy on kubernetes

1. Create namespace:

```bash
kubectl get namespaces

kubectl create namespace optishop
```

We have a single node cluster, therefore we have to taint master node in order to be used as a worker

```bash
kubectl taint nodes --all node-role.kubernetes.io/master-
```

Tired of using -n in every command? Choose the namespace not to type again -n:

```bash
kubectl config set-context --current --namespace optishop
```

Deploy your container in the selected namespace

```bash
kubectl apply -f <path_to_deployment_folder>/deployment.yaml
```

Want to check the logs?:

```bash
kubectl logs -f <pod_name>
```
