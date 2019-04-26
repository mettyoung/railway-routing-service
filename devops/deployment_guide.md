# Deployment Guide
This guide documents the steps performed in order to deliver the software to the vagrant or production servers.

## Assumptions
This deployment guide assumes that you have Python 2 and Ansible installed in your Linux machine, as well as SSH access
to the Ubuntu 16.04 server you wish to deploy the app to. Please use the production.yml playbook. You are also required
to create a SSH alias "linode-02" in your machine's SSH config which will be used by the inventory file.

## Software Packaging
Execute the following commands in @/backend:

    ./gradlew clean build docker dockerArchive

## OS Configuration
Execute the following commands in @/devops.

    # Vagrant
    ansible-playbook -i environments/vagrant os.yml
    # Production
    ansible-playbook -i environments/production os.yml

## Software Deployment
Execute the following commands in @/devops.

    # Vagrant
    ansible-playbook -i environments/vagrant webapp.yml -e "webapp_image=railway-routing-service:1.0.0"
    # Production
    ansible-playbook -i environments/production webapp.yml -e "webapp_image=railway-routing-service:1.0.0"