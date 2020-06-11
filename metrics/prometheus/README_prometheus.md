### install istio (https://www.linode.com/docs/kubernetes/how-to-deploy-istio-with-kubernetes/)

1. Install istio

```bash
curl -L https://git.io/getLatestIstio | ISTIO_VERSION=1.4.2 sh -
```

1. Install helm charts
  1. Add the Istio Helm repo:

```bash
helm repo add istio.io https://storage.googleapis.com/istio-release/releases/1.4.2/charts/
```

  1. Update the helm repo listing:

```bash
helm repo update
```

  1. Verify that you have the repo:

```bash
helm repo list | grep istio.io
```

  1. Install Istio’s Custom Resource Definitions (CRD) with the helm chart. This command also creates a Pod namespace called istio-system which you will continue to use for the remainder of this guide.

```bash
helm install istio-init istio.io/istio-init
```

  1. Verify that all CRDs were successfully installed:

```bash
kubectl get crds | grep 'istio.io' | wc -l
```

  1. Install the Helm chart for Istio. There are many installation options available for Istio. For this guide, the command enables Grafana, which you will use later to visualize your cluster’s data.

```bash
helm install istio istio.io/istio --set grafana.enabled=true
```

  1. Verify that the Istio services and Grafana are running:

```bash
kubectl get svc
```

  1. You can also see the Pods that are running by using this command:

```bash
kubectl get pods
```

  1. Before moving on, be sure that all Pods are in the Running or Completed status (Note: If you need to troubleshoot, you can check a specific Pod by using kubectl, remembering that you set the namespace to istio-system):

```bash
kubectl describe pods pod_name -n pod_namespace
```
  And check the logs by using:

```bash
kubectl logs pod_name -n pod_namespace
```
1. Set up Envoy Proxies
  1. Istio’s service mesh runs by employing sidecar proxies. You will enable them by injecting them into the containers. This command is using the default namespace which is where you will be deploying the Bookinfo application.

```bash
kubectl label namespace default istio-injection=enabled
```

  Note: This deployment uses automatic sidecar injection. Automatic injection can be disabled and manual injection enabled during installation via istioctl. If you disabled automatic injection during installation, use the following command to modify the bookinfo.yaml file before deploying the application:

```bash
kubectl apply -f <(istioctl kube-inject -f ~/istio-1.4.2/samples/bookinfo/platform/kube/bookinfo.yaml)
```

  1. Verify that the ISTIO-INJECTION was enabled for the default namespace:

```bash
kubectl get namespace -L istio-injection
```

1. Install the Istio Scraper App
  1. Start the scraper application with the following command:

```bash
kubectl apply -f ~/istio-1.4.2/samples/bookinfo/platform/kube/bookinfo.yaml
```

  1. Check that all the services are up and running:

```bash
kubectl get services
```

  1. Check that the Pods are all up:

```bash
kubectl get pods
```

  1. Check that the Bookinfo application is running. This command will pull the title tag and contents from the /productpage running on the ratings Pod:

```bash
kubectl exec -it $(kubectl get pod -l app=ratings -o jsonpath='{.items[0].metadata.name}') -c ratings -- curl productpage:9080/productpage | grep -o "<title>.*</title>"
```

1. Open the Istio Gateway
  1. Apply the ingress gateway with the following command:

```bash
kubectl apply -f ~/istio-1.4.2/samples/bookinfo/networking/bookinfo-gateway.yaml
```

  1. Confirm that the gateway is open:

```bash
kubectl get gateway
```

  1. Access your ingress gateway’s external IP. This IP will correspond to the value listed under EXTERNAL-IP.

```bash
kubectl get svc istio-ingressgateway
```

1. Apply Default Destination Rules
  1. Apply destination rules to your cluster:

```bash
kubectl apply -f ~/istio-1.4.2/samples/bookinfo/networking/destination-rule-all.yaml
```

  1. To view all the applied rules issue the following command:

```bash
kubectl get destinationrules -o yaml
```

1. Visualizations with Grafana
  1. Apply the ingress gateway with the following command:

```bash
kubectl apply -f ~/istio-1.4.2/samples/bookinfo/networking/grafana-gateway.yaml
```

  1. Confirm that the gateway is open:

```bash
kubectl get gateway
```

  1. Once this is completed, visit the following URL in your web browser to access your Mesh Dashboard:

```bash
http://INGRESSGATEWAYIP:15031/dashboard/db/istio-mesh-dashboard
```

  1. Send data by visiting a product page, replacing 192.0.2.0 with the value for your ingress gateway’s external IP:

```bash
http://192.0.2.0/productpage
```

1. Removing Clusters and Deployments

```bash
helm uninstall istio-init
helm uninstall istio
linode-cli k8s-alpha delete istio-cluster
```
