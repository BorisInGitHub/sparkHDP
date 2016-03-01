# Cluster HDP/Ambari on Vagrant/VirtualBox

We'll create a HDP cluster with 4 nodes.


https://developer.nrel.gov/downloads/vagrant-boxes/CentOS-6.6-x86_64-v20150426.box

http://uprush.github.io/hdp/2014/12/29/hdp-cluster-on-your-laptop/



## System requirements
* More than 8GB RAM avaliable for the HDP virtual machines

## Install Vagrant
* Download and install [Vagrant](https://www.vagrantup.com/)
* Install [Oracle VirtualBox](https://www.virtualbox.org/) as the Vagrant Provider


## Configure Vagrant
* Create a working directory
```
mkdir hdp23
cd hdp23
```

* Generate an example Vagrantfile
```
vagrant init
```

* Configure the Vagranfile ```vi Vagrantfile```, with the Vagrantfile reference in ```src/vagrant``` directory

* Add the centos box
```
vagrant box add chef/centos-6.6 https://vagrantcloud.com/jcalonsoh/boxes/chef-centos-6.6/versions/1.0.0
```


## Start Ambari1

* Provision Ambari1
```
vagrant up ambari1
```

* Connect to Ambari1
```
vagrant ssh ambari1
```

* Add FDQN to the ```/etc/hosts``` file
```
192.168.0.11 ambari1.mycluster ambari1
192.168.0.12 master1.mycluster master1
192.168.0.21 slave1.mycluster slave1
192.168.0.22 slave2.mycluster slave2
```

* Generate key (copu id_rsa to your machine, and id_rsa_pud to the authorized_keys of each nodes)
```
ssh-keygen
cp ~/.ssh/id_rsa /vagrant_data/id_rsa
cp ~/.ssh/id_rsa.pub /vagrant_data/id_rsa.pub
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
```

* Install Ambari-server
```
sudo su
# Install
wget -nv http://public-repo-1.hortonworks.com/ambari/centos6/2.x/updates/2.2.0.0/ambari.repo -O /etc/yum.repos.d/ambari.repo
yum -y install ambari-server

# Setup. There are several options to configure during setup.
ambari-server setup

# Start Ambari Server
ambari-server start
```


## Start Master1
* Provision Master1
```
vagrant up master1
```

* Connect to Master1
```
vagrant ssh master1
```

* Add FDQN to the ```/etc/hosts``` file
```
192.168.0.11 ambari1.mycluster ambari1
192.168.0.12 master1.mycluster master1
192.168.0.21 slave1.mycluster slave1
192.168.0.22 slave2.mycluster slave2
```

* Authorized key
```
sudo su
mkdir ~/.ssh
cp /vagrant_data/id_rsa.pub ~/.ssh/authorized_keys
```



## Start Slave1
* Provision Slave1
```
vagrant up slave1
```

* Connect to Slave1
```
vagrant ssh slave1
```

* Add FDQN to the ```/etc/hosts``` file
```
192.168.0.11 ambari1.mycluster ambari1
192.168.0.12 master1.mycluster master1
192.168.0.21 slave1.mycluster slave1
192.168.0.22 slave2.mycluster slave2
```

* Authorized key
```
sudo su
mkdir ~/.ssh
cp /vagrant_data/id_rsa.pub ~/.ssh/authorized_keys
```



## Start Slave2
* Provision Slave2
```
vagrant up slave2
```

* Connect to Slave2
```
vagrant ssh slave2
```

* Add FDQN to the ```/etc/hosts``` file
```
192.168.0.11 ambari1.mycluster ambari1
192.168.0.12 master1.mycluster master1
192.168.0.21 slave1.mycluster slave1
192.168.0.22 slave2.mycluster slave2
```

* Authorized key
```
sudo su
mkdir ~/.ssh
cp /vagrant_data/id_rsa.pub ~/.ssh/authorized_keys
mkdir /home/vagrant/.ssh
cp /vagrant_data/id_rsa.pub /home/vagrant/.ssh/authorized_keys
```


## Install HDP
* Go to [Ambari UI](http://localhost:8080) (admin/admin)

Clustername : mycluster

hostname (one by line): master1.mycluster slave1.mycluster slave2.mycluster

ssh user vagrant